package com.emoldino.serenity.server.kafka

import com.emoldino.serenity.common.ClosableJob
import com.emoldino.serenity.common.ComputerIdentifier
import com.emoldino.serenity.extensions.stackTraceString
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.model.*
import com.emoldino.serenity.server.websocket.PushServer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.sql.Timestamp
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

private val logger = KotlinLogging.logger {}

class Consumer<K, V>(private val consumer: KafkaConsumer<K, V>, val topic: String) : ClosableJob {
  private val closed: AtomicBoolean = AtomicBoolean(false)
  private var finished = CountDownLatch(1)

  init {
    consumer.subscribe(listOf(topic))
  }

  override fun run() {
    try {
      while (!closed.get()) {
        try {
          val records = consumer.poll(Duration.of(1000, ChronoUnit.MILLIS))
          for (record in records) {
            logger.debug(
              Env.message("app.kafka.consumer.record"),
              record.topic(),
              record.partition(),
              record.offset(),
              record.key(),
              record.value()
            )
            logger.debug("record.value() : " + record.value().toString())

            if ((Env.branch == "master" && record.key()
                .toString() != Env.owner) || record.value() is Throwable
            ) { // DeSerialization 오류시 Exception Return from consumer
              logger.warn(Env.message("app.kafka.consumer.unknownRecord"), record.value() as Throwable)
              Thread.sleep(1000)
              continue
            }

            when (topic) {
              //ToDo : 아래는 테스트 코드 필요한 경우 수정 필요
              Channel.TEST.value -> {
                val message: KafkaEvent = record.value() as KafkaEvent
                try {
                  sendDataToAi(Env.objectMapper.valueToTree(message.data))
                } catch (ex: Exception) {
                  logger.error("sendDataToAi : ", ex)
                }
              }
            }
          }
          if (!records.isEmpty) {
            consumer.commitAsync { offsets, exception ->
              if (exception != null) {
                logger.error(exception) { Env.message("app.kafka.consumer.commit.fail") + offsets }
              } else {
                logger.debug(Env.message("app.kafka.consumer.commit.success"), offsets)
              }
            }
          }
        } catch (e: Throwable) {
          logger.error(Env.message("app.kafka.consumer.unknownRecord") + e.stackTraceString)
          Thread.sleep(1000)
        }
      }
      logger.info { Env.message("app.kafka.consumer.finish") }
    } catch (e: Throwable) {
      when (e) {
        is WakeupException -> logger.info { Env.message("app.kafka.consumer.wakeup") }
        else -> logger.error(e) { Env.message("app.kafka.consumer.pollingFail") }
      }
    } finally {
      logger.info { Env.message("app.kafka.consumer.commit.sync") }
      consumer.commitSync()
      consumer.close()
      finished.countDown()
      logger.info { Env.message("app.kafka.consumer.closed") }
    }
  }

  fun sendDataToAi(message: JsonNode) {

    val requestId = message.get("requestId").asText()
    val tenantId = message.get("tenantId").asText()
    val moldId = message.get("moldId").asText()
    val data: JsonNode = message.get("data")
    val paramsNum = data.get("paramsNum")
    val dataTypeList = (data.get("dataType") as ArrayNode).map { it -> it.asText() }.toList()
    val startTime = data.get("period").get("startTime").asText()
    val endTime = data.get("period").get("endTime").asText()
    val scale: JsonNode = data.get("scale")


    logger.debug("get data of AI from Kafka", Env.gson.toJson(message))

    //ToDo : Just Test Code
//    val body = Json {
//      "requestId" to requestId
//      "tenantId" to tenantId
//      "moldId" to moldId
//      "data" to Json {
//        "cycleTime" to Json {
//          "hourly" to arrayOf(30)
//          "weyekly" to arrayOf(33)
//          "daily" to arrayOf(37)
//        }
//        "shotCount" to Json {
//          "hourly" to arrayOf(300)
//          "weyekly" to arrayOf(330)
//          "daily" to arrayOf(370)
//        }
//        "temperature" to Json {
//          "hourly" to arrayOf(300, 400, 500)
//          "weekly" to arrayOf(300, 400, 500)
//          "daily" to arrayOf(300, 400, 500)
//        }
//      }
//    }

    //val requestBody: String = body.toString()

    val requestBody: String = "{\n" +
            "      \"requestId\": \"12345678917\",\n" +
            "      \"tenantId\": \"dev:oem:us\",\n" +
            "      \"moldId\": \"test\",\n" +
            "      \"data\": {\n" +
            "        \"cycleTime\": {\n" +
            "          \"hourly\": [30],\n" +
            "          \"weyekly\": [33],\n" +
            "          \"daily\": [37]\n" +
            "        },\n" +
            "        \"shotCount\": {\n" +
            "          \"hourly\": [300],\n" +
            "          \"weyekly\": [330],\n" +
            "          \"daily\": [370]\n" +
            "        },\n" +
            "        \"temperature\": {\n" +
            "          \"hourly\": [300, 400, 500],\n" +
            "          \"weekly\": [300, 400, 500],\n" +
            "          \"daily\": [300, 400, 500]\n" +
            "        }\n" +
            "    }\n" +
            "}"
    logger.debug("/api/deepchain/callback : requestBody : " + requestBody)

    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
      .uri(URI.create(Env.aiServerUrl + "/api/deepchain/callback"))
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(requestBody))
      .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    logger.debug("/api/deepchain/callback : responseBody " + response.body())
    if (response.statusCode() === 200) {
      logger.debug("/api/deepchain/callback : " + Env.gson.toJson(mapOf("result" to "success")))
    } else {
      logger.error("/api/deepchain/callback : Error : " + response.statusCode() + ":" + response.body())
    }

    /*ToDo : add the real logic
    message.get("scale").fieldNames().forEach { f ->
      when(f) {
        "hourly" -> {
          if (message.get("scale").get(f).asBoolean()) {

          }
        }
        "weekly" -> {
          if (message.get("scale").get(f).asBoolean()) {

          }
        }
        "daily" -> {
          if (message.get("scale").get(f).asBoolean()) {

          }
        }
        "monthly" -> {
          if (message.get("scale").get(f).asBoolean()) {

          }
        }
        "yearly" -> {
          if (message.get("scale").get(f).asBoolean()) {

          }
        }
      }
    }

     */
  }

  override fun close() {
    logger.info { Env.message("app.kafka.consumer.jobClose") }
    closed.set(true)
    consumer.wakeup()
    finished.await(3000, TimeUnit.MILLISECONDS)
    logger.info { Env.message("app.kafka.consumer.jobClosed") }
  }

  companion object {
    @JvmStatic
    fun main(argv: Array<String>) {
      //ToDo : Just Test Code
      val body = Json {
        "requestId" to "12345678915"
        "tenantId" to "dev:oem:us"
        "moldId" to "test"
        "data" to Json {
          "cycleTime" to Json {
            "hourly" to arrayOf(30)
            "weyekly" to arrayOf(33)
            "daily" to arrayOf(37)
          }
          "shotCount" to Json {
            "hourly" to arrayOf(300)
            "weyekly" to arrayOf(330)
            "daily" to arrayOf(370)
          }
          "temperature" to Json {
            "hourly" to arrayOf(300, 400, 500)
            "weekly" to arrayOf(300, 400, 500)
            "daily" to arrayOf(300, 400, 500)
          }
        }
      }

      logger.debug(body.toString())
/*
    val requestBody: String = Env.gson.toJson(body)
    logger.debug("/api/deepchain/callback : requestBody : " + requestBody)

    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
      .uri(URI.create(Env.aiServerUrl + "/api/deepchain/callback"))
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(requestBody))
      .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    logger.debug("/api/deepchain/callback : " + response.body())
    if (response.statusCode() === 200) {
      logger.debug("/api/deepchain/callback : " + Env.gson.toJson(mapOf("result" to "success", "body" to body)))
    } else {
      logger.error(
        "/api/deepchain/callback : " + Env.gson.toJson(
          mapOf(
            "status" to response.statusCode(),
            "result" to "failure",
            "reason" to response.body(),
            "body" to body
          )
        )
      )
    }

*/
    }
  }
}

@KtorExperimentalAPI
fun <K, V> buildConsumer(
  environment: ApplicationEnvironment,
  topic: String
): Consumer<K, V> {
  val consumerConfig = environment.config.config("ktor.kafka.consumer")
  val consumerProps = Properties().apply {
    this[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = consumerConfig.property("bootstrap.servers").getString().split(",")
    this[ConsumerConfig.CLIENT_ID_CONFIG] = topic + "-" + consumerConfig.property("client.id").getString()
    this[ConsumerConfig.GROUP_ID_CONFIG] =
      consumerConfig.property("group.id").getString() + "-" + ComputerIdentifier.generateHostId()
    this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = consumerConfig.property("key.deserializer").getString()
    this[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = consumerConfig.property("value.deserializer").getString()
    this[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"
//        this["ssl.truststore.location"] = consumerConfig.property("ssl.truststore.location").getString()
//        this["ssl.truststore.password"] = consumerConfig.property("ssl.truststore.password").getString()
//        this["ssl.keystore.location"] = consumerConfig.property("ssl.keystore.location").getString()
//        this["ssl.keystore.password"] = consumerConfig.property("ssl.keystore.password").getString()
//        this["ssl.key.password"] = consumerConfig.property("ssl.key.password").getString()
//        this["security.protocol"] = consumerConfig.property("security.protocol").getString()
//        this["ssl.endpoint.identification.algorithm"] = consumerConfig.property("ssl.endpoint.identification.algorithm").getString()
  }
  return Consumer(KafkaConsumer(consumerProps), topic)
}


