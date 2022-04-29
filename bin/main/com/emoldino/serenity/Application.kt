@file:Suppress("NAME_SHADOWING")

package com.emoldino.serenity

import com.emoldino.serenity.common.BackgroundJob
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.emoldino.serenity.common.KeyGenerator
import com.emoldino.serenity.exception.EmolError
import com.emoldino.serenity.exception.EmolException
import com.emoldino.serenity.exception.ErrorCode
import com.emoldino.serenity.extensions.stackTraceString
import com.emoldino.serenity.server.auth.JwtConfig
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.dto.Response
import com.emoldino.serenity.server.jpa.own.dto.TenantDto
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.jpa.own.repository.AdminRepository
import com.emoldino.serenity.server.jpa.own.repository.MemberDetailRepository
import com.emoldino.serenity.server.jpa.own.repository.MemberRepository
import com.emoldino.serenity.server.jpa.own.repository.TenantRepository
import com.emoldino.serenity.server.kafka.KafkaEventService
import com.emoldino.serenity.server.kafka.buildConsumer
import com.emoldino.serenity.server.kafka.buildProducer
import com.emoldino.serenity.server.route.*
import com.emoldino.serenity.server.service.AdminService
import com.emoldino.serenity.server.service.SsoService
import com.emoldino.serenity.server.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import io.ktor.util.date.*
import io.ktor.webjars.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import java.time.Duration
import kotlin.concurrent.thread
import com.emoldino.serenity.server.route.deepchain
import com.emoldino.serenity.server.service.TenantService
import org.apache.kafka.clients.producer.KafkaProducer
import java.util.concurrent.ConcurrentHashMap


fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

private val logger = KotlinLogging.logger {}

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

  //val random = Random(Date().time)
//  val gson = GsonBuilder().registerTypeAdapter(Timestamp::class.java, TimestampSerializer())
//    .registerTypeAdapter(BigDecimal::class.java, BigDecimalSerializer()).create()
  //val pushServerMap = ConcurrentHashMap<String, PushServer<User>>()

  val applicable: Boolean = environment.config.config("ktor.deployment").property("applicable").getString().toBoolean()
  if (testing || !applicable) {
    return
  }

  Env.branch = environment.config.config("ktor.deployment").property("branch").getString()
  Env.topicLogging = environment.config.config("ktor.kafka.consumer").property("logging").getString().toBoolean()
  Env.aiServerUrl = environment.config.config("ktor.ai").property("serverUrl").getString()
  Env.serenityServerUrl = environment.config.config("ktor.deployment").property("serverUrl").getString()

  val monitor = ApplicationEvents()

  val started: (Application) -> Unit = {
    logger.debug(Env.message("app.main.start"), it)
  }

  var stopped: (Application) -> Unit = {}
  stopped = {
    monitor.unsubscribe(ApplicationStarted, started)
    monitor.unsubscribe(ApplicationStopped, stopped)
    logger.debug(Env.message("app.main.stop"), it)
  }

  monitor.subscribe(ApplicationStarted, started)
  monitor.subscribe(ApplicationStopped, stopped)

  Env.initDB(environment.config.config("ktor.db"))
  Env.initMailSender(environment.config.config("ktor.mail"))
//    install(Sessions) {
//
//        cookie<MySession>("MY_SESSION") {
//            cookie.extensions["SameSite"] = "lax"
//        }
//    }

  install(Compression)
  install(Locations)

  install(CORS) {
    method(HttpMethod.Options)
    method(HttpMethod.Get)
    method(HttpMethod.Post)
    method(HttpMethod.Put)
    method(HttpMethod.Delete)
    method(HttpMethod.Patch)
    header(HttpHeaders.Authorization)
    header(HttpHeaders.XForwardedProto)
//    header("MyCustomHeader")
    allowCredentials = true
    allowCredentials = true
    allowNonSimpleContentTypes = true
    anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
  }

  install(CachingHeaders) {
    options { outgoingContent ->
      when (outgoingContent.contentType?.withoutParameters()) {
        ContentType.Text.CSS -> CachingOptions(
          CacheControl.MaxAge(maxAgeSeconds = 24 * 60 * 60),
          expires = null as? GMTDate?
        )
        else -> null
      }
    }
  }

  install(DataConversion)


  install(Webjars) {
    path = "/webjars" //defaults to /webjars /defaults to UTC zone
  }


  install(ContentNegotiation) {
    jackson {
      registerModule(JavaTimeModule())
      enable(SerializationFeature.INDENT_OUTPUT)
      setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }
  }

  // https://ktor.io/servers/features/forward-headers.html
  install(XForwardedHeaderSupport)

  install(io.ktor.websocket.WebSockets) {
    //ToDo 상용 배포 시 ping 값 주어야 함
    pingPeriod = Duration.ofSeconds(30)
    timeout = Duration.ofSeconds(30)
    maxFrameSize = Long.MAX_VALUE
    masking = false
  }

  logger.debug(Env.message("app.websocket.install"))

  //ref : https://ktor.io/docs/call-id.html
  install(CallId) {

    retrieveFromHeader(HttpHeaders.XRequestId)

    generate { KeyGenerator.generateOrderNo() }

    verify { it.isNotEmpty() }
  }

  install(CallLogging) {
    //level = Level.INFO
    callIdMdc(HttpHeaders.XRequestId)
    //filter { call -> call.request.path().startsWith("/v1/user/push") }
  }

  val adminService: AdminService = AdminService(AdminRepository())
  val userService: UserService = UserService(MemberRepository())
  val ssoService: SsoService = SsoService(MemberRepository(), MemberDetailRepository())
  val tenantService: TenantService = TenantService(TenantRepository())

  // ref : https://github.com/AndreasVolkmann/realworld-kotlin-ktor
  install(Authentication) {
    jwt("api") {
      authSchemes()
      verifier(JwtConfig.verifier)
      realm = JwtConfig.realm
      validate {
        it.payload.claims.forEach(::println)
        val uid = it.payload.getClaim("uid")?.asString() ?: return@validate null
        logger.debug("Required: $uid")
        userService.getUser(uid).let { user: UserDto ->
          user.copy(token = it.payload)
        }
      }
    }
  }

  // ref : https://ktor.io/docs/status-pages.html#redirect
  install(StatusPages) {
    exception<EmolException> { cause ->
      logger.error("routing error : ${cause.stackTraceString}")
      val tid = call.callId
      val isException = true
      val err = EmolError.error(cause)
      val requestUri: String = call.request.uri
      val method: String = call.request.httpMethod.value
      val response = Response(err as Any, tid, requestUri, method.uppercase())
      val session: UserDto? = call.request.call.authentication.principal<UserDto>()
      if (session != null) {
        call.response.header(JwtConfig.authHeader, JwtConfig.makeToken(session, session.token?.id))
      }

      call.respond(HttpStatusCode(err.status, err.description), response)

      if (logger.isDebugEnabled) {
        loggging(
          requestUri,
          isException,
          session,
          call.request,
          call.response
        )
      }
    }
    exception<Throwable> { cause ->
      logger.error("routing error : ${cause.stackTraceString}")
      val tid = call.callId
      val isException = true
      val err = EmolError.error(ErrorCode.E00000)
      err.description = cause.localizedMessage
      val requestUri: String = call.request.uri
      val method: String = call.request.httpMethod.value
      val response = Response(err as Any, tid, requestUri, method.uppercase())
      val session: UserDto? = call.request.call.authentication.principal<UserDto>()
      if (session != null) {
        call.response.header(JwtConfig.authHeader, JwtConfig.makeToken(session, session.token?.id))
      }

      call.respond(HttpStatusCode(err.status, err.description), response)

      if (logger.isDebugEnabled) {
        loggging(
          requestUri,
          isException,
          session,
          call.request,
          response
        )
      }
    }
  }

  // ref : https://ktor.kotlincn.net/advanced/pipeline/route.html
  val callMonitor = ApplicationEvents()
  // AOP::beforeCall()
  callMonitor.subscribe(Routing.RoutingCallStarted) { call: RoutingApplicationCall ->
    logger.debug("### Route started: ${call.route} : ${call.callId} [")
    val uri = call.request.uri
    val response = call.response
    when(call.request.httpMethod.value.uppercase()) {
      "OPTIONS" -> {
        if (uri.startsWith("/api/")) {
          response.header("Access-Control-Allow-Origin", "*")
          response.header("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT")
          response.header("Access-Control-Max-Age", "3600")
          response.header(
            "Access-Control-Allow-Headers",
            "X-Requested-With, sessionKey, Cache-Control, Content-Type, Accept, Authorization"
          )
          response.header("Content-Type", "application/json; charset=utf-8")

          GlobalScope.launch {
            call.respond(HttpStatusCode.OK, "{\"success\":true}")
          }
        } else {
          response.header("Access-Control-Allow-Origin", "*")
          response.header("Access-Control-Allow-Methods", "")
          response.header("Access-Control-Max-Age", "10")
          response.header("Content-Type", "application/json; charset=utf-8")
          GlobalScope.launch {
            call.respond(HttpStatusCode.Unauthorized, "{\"success\":false}")
          }
        }

      }
      else -> {

        if (uri.startsWith("/api/")) {
          response.header("Access-Control-Allow-Origin", "*")
          response.header("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT")
          response.header("Access-Control-Max-Age", "3600")
          response.header(
            "Access-Control-Allow-Headers",
            "X-Requested-With, sessionKey, Cache-Control, Content-Type, Accept, Authorization"
          )
          response.header("Content-Type", "application/json; charset=utf-8")
        } else {
          response.header("Access-Control-Allow-Origin", "*")
          response.header("Access-Control-Allow-Methods", "")
          response.header("Access-Control-Max-Age", "10")
          response.header("Content-Type", "application/json; charset=utf-8")
          GlobalScope.launch {
            call.respond(HttpStatusCode.Unauthorized, "{\"success\":false}")
          }
        }
      }
    }
  }

  // AOP::afterCall()
  callMonitor.subscribe(Routing.RoutingCallFinished) { call: RoutingApplicationCall ->
//    val tid = call.callId
    val isException = true
    val requestUri: String = call.request.uri
//    val method: String = call.request.httpMethod.value
    val session: UserDto? = call.request.call.authentication.principal<UserDto>()
    if (session != null) {
      call.response.header(JwtConfig.authHeader, JwtConfig.makeToken(session, session.token?.id))
    }

    if (logger.isDebugEnabled) {
      loggging(
        requestUri,
        isException,
        session,
        call.request
      )
    }
    logger.debug("### Route completed: ${call.route} : ${call.callId} ]")
  }

  Env.kafkaEventProducer = buildProducer(environment, tenantService)
  logger.debug("buildProducer is OK")

  logger.debug("cosumerJobs are starting")
  val cosumerJobs: ArrayList<BackgroundJob> = ArrayList<BackgroundJob>()
  if (applicable) { // Appicable="false" 이면 Consumer 를 띄우지 않는다.
    for (key in Env.tenantMap.keys()) {
        val topic: String = key
        logger.debug("cosumerJobs : ${topic}")
        val conf = BackgroundJob.JobConfiguration()
        conf.name = "Kafka-User-Consumer-" + topic + "-Job"
        @Suppress("UNCHECKED_CAST")
        conf.job = buildConsumer<String, Any>(environment, topic)
        val consumerJob = BackgroundJob(conf)
        conf.job?.let { thread(name = conf.name) { it.run() } }
        cosumerJobs.add(consumerJob)
        logger.debug("conumserJon run : ${conf.name} ")
    }
    val topic = "test"
    val conf = BackgroundJob.JobConfiguration()
    conf.name = "Kafka-User-Consumer-" + topic + "-Job"
    @Suppress("UNCHECKED_CAST")
    conf.job = buildConsumer<String, Any>(environment, topic)
    val consumerJob = BackgroundJob(conf)
    conf.job?.let { thread(name = conf.name) { it.run() } }
    cosumerJobs.add(consumerJob)
    logger.debug("conumserJon run : ${conf.name} ")
  }
  logger.debug("cosumerJobs are all running :  OK")

  routing {
    authenticate("api") {
      user(userService)
    }
    admin(adminService)
    sso(ssoService)
    test()
    deepchain()
  }

  logger.debug("Application start... OK")
}
