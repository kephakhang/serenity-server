@file:Suppress("NAME_SHADOWING")

package com.siksinhot.test.server.env

import com.siksinhot.test.server.model.ChannelList
import com.siksinhot.test.server.model.Event
import com.siksinhot.test.server.model.Message
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.siksinhot.test.exception.ErrorCode
import com.siksinhot.test.exception.ErrorMessageLang
import com.siksinhot.test.exception.TwinError
import com.siksinhot.test.exception.TwinException
import com.siksinhot.test.common.Jasypt
import com.siksinhot.test.server.retrofit.TwinkoreaService
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import mu.KotlinLogging
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.time.Clock
import java.time.LocalDateTime
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
        val owner = "siksinhot"
        val siksinhotTenantId = "siksinhot-own-kr"
        val connectionsLimit = 10
        val lang = Locale.getDefault().language
        val topicConsumeCounts: ConcurrentHashMap<String, Int> = ConcurrentHashMap<String, Int>()
        var topicLogging: Boolean = false
        val topicLogRollingPeriod: Long = 7L
        var branch = "local"

        //    @UseExperimental(io.ktor.util.KtorExperimentalAPI::class)
        val errorConfig = HoconApplicationConfig(ConfigFactory.load("i18n/error"))
        val messageConfig = HoconApplicationConfig(ConfigFactory.load("i18n/" + lang))
        val channelNum: Int = 2
        val greeting: String = "HELLO This is eMoldino's Serenity(Collaboration) Server !!!"
        val normalClosureMessage: String = "Normal closure"
        val dbConfig: MutableMap<String, String> = mutableMapOf()
        var objectMapper: ObjectMapper = ObjectMapper()
        val gson: Gson = GsonBuilder().disableHtmlEscaping().create()
        lateinit var emf: EntityManagerFactory
        lateinit var em: EntityManager
        lateinit var jasyptPassword: String
        lateinit var serverUrl: String
        lateinit var twinkoreaUrl: String
        lateinit var twinkoreaService: TwinkoreaService

        init {
            val javaTimeModule = JavaTimeModule()
            // Hack time module to allow 'Z' at the end of string (i.e. javascript json's)
            javaTimeModule.addDeserializer(
                LocalDateTime::class.java,
                LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
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
            objectMapper.dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        }

        fun now(): LocalDateTime {
            return LocalDateTime.now(Clock.systemUTC())
        }

        fun initEnv(conf: ApplicationConfig) {
            jasyptPassword = conf.property("jasypt.password").getString()
            serverUrl = conf.property("deployment.serverUrl").getString()
            twinkoreaUrl = conf.property("deployment.twinkoreaUrl").getString()
            twinkoreaService = TwinkoreaService(twinkoreaUrl)
        }

        fun initDB(conf: ApplicationConfig) {
            dbConfig["javax.persistence.jdbc.driver"] = conf.property("driver").getString() //"com.mysql.cj.jdbc.Driver"
            dbConfig["javax.persistence.jdbc.url"] = conf.property("url").getString()
            dbConfig["javax.persistence.jdbc.user"] = Jasypt.decode(conf.property("user").getString())
            dbConfig["javax.persistence.jdbc.password"] = Jasypt.decode(conf.property("password").getString())
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
            //"hibernate.query.plan_cache_max_size" to "0"
            dbConfig["hibernate.connection.isolation"] = "4"
            dbConfig["hibernate.c3p0.max_size"] = "10"
            dbConfig["hibernate.c3p0.min_size"] = "10"
            dbConfig["hibernate.c3p0.acquire_increment"] = "1"
            dbConfig["hibernate.c3p0.idle_test_period"] = "300"
            dbConfig["hibernate.c3p0.max_statements"] = "0"
            dbConfig["hibernate.c3p0.timeout"] = "100"
            dbConfig["hibernate.c3p0.unreturnedConnectionTimeout"] = "30"
            dbConfig["hibernate.c3p0.debugUnreturnedConnectionStackTraces"] = "true"
            dbConfig["hibernate.ddl-auto"] = "none"
            dbConfig["javax.persistence.validation.mode"] = "none"


            emf = Persistence.createEntityManagerFactory("twinkorea", dbConfig)
            em = emf.createEntityManager()
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
                return messageConfig.config(group).property(key)
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
                return messageConfig.config(group).property(key).getString()
            } catch (e: Throwable) {
                logger.error("Env.message : " + e.stackTrace)
                return ""
            }
        }

        fun error(code: ErrorCode, vararg args: String?): TwinError {
            try {
                val errConf: ApplicationConfig = errorConfig.config("error." + code.name)
                val errMessageConf: ApplicationConfig = errorConfig.config("error." + code.name + ".message")
                val error: TwinError = TwinError(
                    code.name,
                    errConf.property("status").getString().toInt(),
                    errConf.property("description").getString(),
                    HashMap<String, String>(),
                    errConf.property("origin").getString()
                )
                ErrorMessageLang.values().forEach {
                    if (args.size > 0) {
                        error.message.put(it.name, errMessageConf.property(it.name).getString().format(*args))
                    } else {
                        error.message.put(it.name, errMessageConf.property(it.name).getString())
                    }

                }
                return error
            } catch (e: Throwable) {
                com.siksinhot.test.exception.logger.error("Env.message : " + e.stackTrace)
                throw e
            }
        }

        fun error(kex: TwinException): TwinError {
            val error: TwinError = error(kex.code, *kex.argList.toTypedArray())
            kex.cause?.let {
                error.description = it.localizedMessage
            }
            return error
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
