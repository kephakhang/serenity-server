@file:Suppress("NAME_SHADOWING")

package com.siksinhot.test.server

import com.siksinhot.test.common.KeyGenerator
import com.siksinhot.test.server.env.Env
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.siksinhot.test.server.env.Env.Companion.gson
import com.siksinhot.test.server.route.twin.twin
import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callid.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.plugins.dataconversion.*
import io.ktor.server.plugins.forwardedheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.webjars.*
import io.ktor.server.websocket.*
import io.ktor.util.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

private val logger = KotlinLogging.logger {}

@Suppress("unused") // Referenced in application.conf
@JvmOverloads
fun Application.module(testing: Boolean = false) {


    val applicable: Boolean =
        environment.config.config("ktor.deployment").property("applicable").getString().toBoolean()
    if (testing || !applicable) {
        return
    }

    Env.branch = environment.config.config("ktor.deployment").property("branch").getString()

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

    Env.initEnv(environment.config.config("ktor"))
    Env.initDB(environment.config.config("ktor.db"))

    install(Compression)
    install(Locations)


    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.XForwardedProto)
//    header("MyCustomHeader")
        allowCredentials = true
        allowCredentials = true
        allowNonSimpleContentTypes = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(CachingHeaders) {
        options { call, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> CachingOptions(
                    cacheControl = CacheControl.MaxAge(maxAgeSeconds = 3600),
                    expires = ZonedDateTime.now()
                )
                ContentType.Application.Json -> CachingOptions(
                    cacheControl = CacheControl.MaxAge(maxAgeSeconds = 60),
                    expires = ZonedDateTime.now()
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
            val javaTimeModule = JavaTimeModule()
            // Hack time module to allow 'Z' at the end of string (i.e. javascript json's)
            javaTimeModule.addDeserializer(
                LocalDateTime::class.java,
                LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
            )
            registerModule(javaTimeModule)
            configure(SerializationFeature.INDENT_OUTPUT, true)
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
            enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            disable(JsonGenerator.Feature.ESCAPE_NON_ASCII)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        }

//        gson {
//            setDateFormat(DateFormat.LONG)
//            setPrettyPrinting()
//        }
    }

    // https://ktor.io/servers/features/forward-headers.html
    install(ForwardedHeaders)

    logger.debug(Env.message("app.websocket.install"))

    //ref : https://ktor.io/docs/call-id.html
    install(CallId) {

        retrieveFromHeader(HttpHeaders.XRequestId)

        generate { KeyGenerator.generateOrderNo() }

        verify { it.isNotEmpty() }
    }

    install(CallLogging) {
        //level = Level.INFO
        mdc(HttpHeaders.XRequestId) { call ->
            call.request.header(HttpHeaders.XRequestId)
        }
        //filter { call -> call.request.path().startsWith("/v1/user/push") }
    }

//    val adminService: AdminService = AdminService(AdminRepository())
//    val userService: UserService = UserService(MemberRepository())
//    val ssoService: SsoService = SsoService(MemberRepository(), MemberDetailRepository())
//    val tenantService: TenantService = TenantService(TenantRepository())
//    val terminalService: TerminalService = TerminalService(TerminalRepository(), CounterRepository())
//    val counterService: CounterService = CounterService(CounterRepository())


    // ref : https://github.com/AndreasVolkmann/realworld-kotlin-ktor
//    install(Authentication) {
//        jwt("api") {
//            authSchemes()
//            verifier(JwtConfig.verifier)
//            realm = JwtConfig.realm
//            validate {
//                it.payload.claims.forEach(::println)
//                val uid = it.payload.getClaim("uid")?.asString() ?: return@validate null
//                logger.debug("Required: $uid")
//                userService.getUser(uid).let { user: UserDto ->
//                    user.copy(token = it.payload)
//                }
//            }
//        }
//    }

    // ref : https://ktor.io/docs/status-pages.html#redirect
//    install(StatusPages) {
//        exception<TwinException> { call, cause ->
//            logger.error("routing error : ${cause.stackTraceString}")
//            val tid = call.callId
//            val isException = true
//            val err = Env.error(cause)
//            val requestUri: String = call.request.uri
//            val method: String = call.request.httpMethod.value
//            val response = Response(err as Any, tid, requestUri, method.uppercase())
//            val session: UserDto? = call.request.call.authentication.principal<UserDto>()
//            if (session != null) {
//                call.response.header(JwtConfig.authHeader, JwtConfig.makeToken(session, session.token?.id))
//            }
//
//            call.respond(HttpStatusCode(err.status, err.description), response)
//
//            if (logger.isDebugEnabled) {
//                logging(
//                    requestUri,
//                    isException,
//                    session,
//                    call.request,
//                    call.response
//                )
//            }
//        }
//        exception<Throwable> { call, cause ->
//            logger.error("routing error : ${cause.stackTraceString}")
//            val tid = call.callId
//            val isException = true
//            val err = Env.error(ErrorCode.E00000)
//            err.description = cause.localizedMessage
//            val requestUri: String = call.request.uri
//            val method: String = call.request.httpMethod.value
//            val response = Response(err as Any, tid, requestUri, method.uppercase())
//            val session: UserDto? = call.request.call.authentication.principal<UserDto>()
//            if (session != null) {
//                call.response.header(JwtConfig.authHeader, JwtConfig.makeToken(session, session.token?.id))
//            }
//
//            call.respond(HttpStatusCode(err.status, err.description), response)
//
//            if (logger.isDebugEnabled) {
//                logging(
//                    requestUri,
//                    isException,
//                    session,
//                    call.request,
//                    response
//                )
//            }
//        }
//    }

    // ref : https://ktor.kotlincn.net/advanced/pipeline/route.html
    val callMonitor = ApplicationEvents()
    // AOP::beforeCall()
    callMonitor.subscribe(Routing.RoutingCallStarted) { call: RoutingApplicationCall ->
        logger.debug("### Route started: ${call.route} : ${call.callId} [")
        val uri = call.request.uri
        val response = call.response
        when (call.request.httpMethod.value.uppercase()) {
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
//        val session: UserDto? = call.request.call.authentication.principal<UserDto>()
//        if (session != null) {
//            call.response.header(JwtConfig.authHeader, JwtConfig.makeToken(session, session.token?.id))
//        }
//
//        if (logger.isDebugEnabled) {
//            loggging(
//                requestUri,
//                isException,
//                session,
//                call.request
//            )
//        }
//        logger.debug("### Route completed: ${call.route} : ${call.callId} ]")
    }

    routing {
//        place(placeService)
//        review(reviewService)
        staticResources("/static", "static", index="index.html")
//
//        get("/") {
//            //call.respondText(Env.greeting, contentType = ContentType.Text.Plain)
//            call.respondRedirect("/index.html")
//        }
//
//        openAPI(path="openapi", swaggerFile = "openapi/documentation.yaml")
//        authenticate("api") {
//            user(userService)
//            tenant(tenantService)
//            terminal(terminalService, counterService)
//            counter(counterService)
//        }
//        admin(adminService)
//        sso(ssoService)
//        test()
//        deepchain()
          twin()

    }

    logger.debug("Application start... OK")
}
