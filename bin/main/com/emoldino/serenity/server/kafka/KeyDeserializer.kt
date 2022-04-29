package com.emoldino.serenity.server.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.emoldino.serenity.server.env.Env
import mu.KotlinLogging
import org.apache.kafka.common.serialization.Deserializer
import java.nio.charset.Charset

private val logger = KotlinLogging.logger {}

class KeyDeserializer(
  private val objectMapper: ObjectMapper = ObjectMapper()
    .registerModule(JavaTimeModule())
    .registerModule(KotlinModule())
) : Deserializer<String?> {

  override fun deserialize(topic: String, data: ByteArray): String {
    try {
        val key = String(data, Charset.defaultCharset())
        if (!key.equals(Env.owner)) {
          if (Env.branch.equals("master")) {
            logger.error("unkown kafka event's key : " + key)
            return "unknown"
          } else {
            return Env.owner
          }
        } else {
          return Env.owner
        }
    } catch (e: Throwable) {

      if (Env.branch.equals("master")) {
        logger.error("Error when deserializing of kafka event's key byte[] to string : " + e.stackTrace)
        return "unknown"
      } else {
        logger.warn("Error when deserializing of kafka event's key byte[] to string : " + e.stackTrace)
        return Env.owner
      }
    }
  }
}
