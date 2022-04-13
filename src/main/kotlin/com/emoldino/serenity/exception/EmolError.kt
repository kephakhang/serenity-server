package com.emoldino.serenity.exception

import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import mu.KotlinLogging
import java.util.*
import kotlin.collections.HashMap

val logger = KotlinLogging.logger {}

class EmolError(
  val code: String,
  val status: Int,
  var description: String,
  val message: HashMap<String, String>,
  val origin: String) {
  companion object {

    val lang = Locale.getDefault().language
    val messageConfig = HoconApplicationConfig(ConfigFactory.load("i18n/" + lang))
    val errorConfig = HoconApplicationConfig(ConfigFactory.load("i18n/error"))

    fun error(code: ErrorCode, vararg args: Any?): EmolError {
      try {
        val errConf: ApplicationConfig = EmolError.errorConfig.config("error." + code.name)
        val errMessageConf: ApplicationConfig = EmolError.errorConfig.config("error." + code.name + ".message")

        val error: EmolError = EmolError(
          code.name,
          errConf.property("status").getString().toInt(),
          errConf.property("description").getString(),
          HashMap<String, String>(),
          errConf.property("origin").getString()
        )
        ErrorMessageLang.values().forEach {
          if( args.size > 0 ) {
            error.message.put(it.name, errMessageConf.property(it.name).getString().format(args))
          } else {
            error.message.put(it.name, errMessageConf.property(it.name).getString())
          }

        }
        return error
      }catch(e: Throwable) {
        logger.error( "Env.message : " +  e.stackTrace)
        throw e
      }
    }

    fun error(kex: EmolException): EmolError {
      val error: EmolError = error(kex.code, kex.argList.toArray())

      kex.cause?.let {
        error.description = it.localizedMessage
      }
      return error
    }
  }
}
