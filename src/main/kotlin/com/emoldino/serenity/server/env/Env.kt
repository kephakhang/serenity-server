@file:Suppress("NAME_SHADOWING")

package com.emoldino.serenity.server.env

import com.emoldino.serenity.server.jpa.own.dto.TenantDto
import com.emoldino.serenity.server.kafka.KafkaEventService
import com.emoldino.serenity.server.model.ChannelList
import com.emoldino.serenity.server.model.Event
import com.emoldino.serenity.server.model.Message
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.google.gson.Gson
import com.sultanofcardio.models.MailServer
import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import mu.KotlinLogging
import org.apache.kafka.clients.admin.Admin
import org.apache.kafka.clients.producer.KafkaProducer
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

private val logger = KotlinLogging.logger {}

class Env {
  companion object {
    val owner = "emoldino"
    val connectionsLimit = 10
    val lang = Locale.getDefault().language
    val topicConsumeCounts: ConcurrentHashMap<String, Int> = ConcurrentHashMap<String, Int>()
    var topicLogging: Boolean = false
    val topicLogRollingPeriod: Long = 7L
    var branch = "local"
    var aiServerUrl = "http://13.125.216.230:3006"
    lateinit var serenityServerUrl: String
    var kafkaEventProducer: KafkaProducer<String, Any>? = null
    val kafkaEventServiceMap: ConcurrentHashMap<String,KafkaEventService> = ConcurrentHashMap<String,KafkaEventService>()
    const val tablePrefix = "tb_"


//    @UseExperimental(io.ktor.util.KtorExperimentalAPI::class)
    val messageConfig = HoconApplicationConfig(ConfigFactory.load("i18n/" + lang))
    val channelNum: Int = 2
    val greeting: String = "HELLO This is eMoldino's Serenity(Collaboration) Server !!!"
    val normalClosureMessage: String = "Normal closure"
    val dbConfig: MutableMap<String, String> = mutableMapOf()
    val tenantMap: ConcurrentHashMap<String, TenantDto> = ConcurrentHashMap<String, TenantDto>()
    lateinit var kafkaAdmin: Admin
    lateinit var emf: EntityManagerFactory
    lateinit var em: EntityManager
    lateinit var mailSender: MailServer
    lateinit var confirmEmailContent: String
    lateinit var apiHostUrl: String
    lateinit var email: String
    lateinit var confirmSuccessUrl: String
    lateinit var confirmFailureUrl: String
    var confirmExpireTime: Long = 0L

    fun now(): LocalDateTime {
      return LocalDateTime.now(Clock.systemUTC())
    }

    fun initDB(conf: ApplicationConfig) {
      dbConfig["javax.persistence.jdbc.driver"] = conf.property("driver").getString() //"com.mysql.cj.jdbc.Driver"
      dbConfig["javax.persistence.jdbc.url"] = conf.property("url").getString()
      dbConfig["javax.persistence.jdbc.user"] = conf.property("user").getString()
      dbConfig["javax.persistence.jdbc.password"] = conf.property("password").getString()
      dbConfig["hibernate.dialect"] = "org.hibernate.dialect.MySQL57Dialect"
      dbConfig["hibernate.show_sql"] = "true"
      dbConfig["hibernate.format_sql"] = "true"
      dbConfig["hibernate.use_sql_comments"] = "true"
      dbConfig["hibernate.id.new_generator_mappings"] = "false"
      dbConfig["hibernate.generate_statistics"] = "false"
      dbConfig["hibernate.connection.charSet"] = "UTF-8"
      dbConfig["hibernate.hbm2ddl.auto"] = "validate"
      dbConfig["hibernate.connection.provider_class"] = "org.hibernate.connection.C3P0ConnectionProvider"
//            isolation level : https://stackoverflow.com/questions/16162357/transaction-isolation-levels-relation-with-locks-on-table
//            1: READ UNCOMMITTED
//            2: READ COMMITTED
//            4: REPEATABLE READ
//            8: SERIALIZABLE
      dbConfig["hibernate.connection.isolation"] = "4"
      dbConfig["hibernate.c3p0.max_size"] = "10"
      dbConfig["hibernate.c3p0.min_size"] = "10"
      dbConfig["hibernate.c3p0.acquire_increment"] = "1"
      dbConfig["hibernate.c3p0.idle_test_period"] = "300"
      dbConfig["hibernate.c3p0.max_statements"] = "0"
      dbConfig["hibernate.c3p0.timeout"] = "100"
      dbConfig["hibernate.c3p0.unreturnedConnectionTimeout"] = "30"
      dbConfig["hibernate.c3p0.debugUnreturnedConnectionStackTraces"] = "true"


      emf = Persistence.createEntityManagerFactory("serenity", dbConfig)
      em = emf.createEntityManager()
    }

    fun initMailSender(conf: ApplicationConfig) {
      //MailSender
      val host = conf.property("host").getString()
      val port = conf.property("port").getString()
      val username = conf.property("username").getString()
      val password = conf.property("password").getString()
      mailSender = MailServer(host, port, username, password = password)
      confirmEmailContent = Env::class.java.getResource("/confirm.html").readText()
      apiHostUrl = conf.property("apiHostUrl").getString()
      confirmExpireTime = conf.property("confirmExpireTime").getString().toLong() * 60 // minutes x 60 = secs
      confirmSuccessUrl = conf.property("confirmSuccessUrl").getString()
      confirmFailureUrl = conf.property("confirmFailureUrl").getString()
    }

    var objectMapper: ObjectMapper = ObjectMapper()
    val gson: Gson = Gson()

    init {
      val javaTimeModule = JavaTimeModule()
      // Hack time module to allow 'Z' at the end of string (i.e. javascript json's)
      javaTimeModule.addDeserializer(
        LocalDateTime::class.java,
        LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"))
      )
      objectMapper.registerModule(javaTimeModule)
      //objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
      objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true)
      objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
      objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS)
      objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
      objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
      objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
      objectMapper.dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
    }


    fun String.md5(): String {
      return hashString(this, "MD5")
    }

    fun String.sha256(): String {
      return hashString(this, "SHA-256")
    }

    fun hashString(input: String, algorithm: String): String {
      return MessageDigest
        .getInstance(algorithm)
        .digest(input.toByteArray())
        .fold("", { str, it -> str + "%02x".format(it) })
    }

    fun isValidWebsocketMessage(input: String): Boolean {
      return input.replace("[0-9a-zA-Z_\\s\"{},\\.\\:\\[\\]]+".toRegex(), "").isEmpty()
    }

//    @UseExperimental(io.ktor.util.KtorExperimentalAPI::class)
    fun messageProp(key: String): ApplicationConfigValue? {
      try {
        val idx: Int = key.lastIndexOf(".")
        val group: String = key.substring(0, idx)
        val key: String = key.substring(idx + 1)
//        @UseExperimental(io.ktor.util.KtorExperimentalAPI::class)
        return Env.messageConfig.config(group).property(key)
      } catch (e: Throwable) {
        logger.error("Env.message : " + e.stackTrace)
//        @UseExperimental(io.ktor.util.KtorExperimentalAPI::class)
        return null
      }
    }

    fun message(key: String): String {
      try {
        val idx: Int = key.lastIndexOf(".")
        val group: String = key.substring(0, idx)
        val key: String = key.substring(idx + 1)
        logger.debug("Evn.message - group : " + group)
        logger.debug("Evn.message - key : " + key)
//        @UseExperimental(io.ktor.util.KtorExperimentalAPI::class)
        return Env.messageConfig.config(group).property(key).getString()
      } catch (e: Throwable) {
        logger.error("Env.message : " + e.stackTrace)
        return ""
      }
    }

    fun error(): Message {
      return Message(
        null,
        LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
        Event.ERROR.value,
        ChannelList()
      );
    }
  }
}
