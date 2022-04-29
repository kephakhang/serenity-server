package com.emoldino.serenity.server.kafka

import com.emoldino.serenity.common.ClosableJob
import com.emoldino.serenity.extensions.stackTraceString
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.model.*
import io.ktor.application.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import org.eclipse.jetty.http.HttpStatus
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.LinkedHashMap

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

            if ((Env.branch === "master" && record.key()
                .toString() != Env.owner) || record.value() is Throwable
            ) { // DeSerialization 오류시 Exception Return from consumer
              logger.warn(Env.message("app.kafka.consumer.unknownRecord"), record.value() as Throwable)
              Thread.sleep(1000)
              continue
            }

            val message: KafkaEvent = record.value() as KafkaEvent
            val body: Body = message.data
            if (topic.equals(body.tenantId)) {
              when(body.type) {
                IntegrationType.AI.value -> {
                  fetchDataFromMms(body)
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

  fun fetchDataFromMms(body: Body) {

    val requestId = body.requestId
    val tenantId = body.tenantId
    val moldId = body.data["moldId"] as String
    val lst = "20210428101141" //body.data["lst"] as String
    val aiType = "EM_AI_ANOM" //body.data["aiType"] as String

    val strMessage = Env.gson.toJson(body)
    logger.debug("get data of AI from Kafka ${strMessage}")

    val requestBody: String = "{\n" +
        "      \"requestId\": \"" + requestId + "\" ,\n" +
        "      \"tenantId\": \"" + tenantId + "\" ,\n" +
        "      \"type\": \"ai\" ,\n" +
        "      \"data\": {\n" +
        "        \"moldId\": \"" + moldId + "\" ,\n" +
        "        \"lst\": \"" + lst + "\" ,\n" +
        "        \"aiType\": \"" + aiType + "\" ,\n" +
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
    logger.debug("/api/integration/ai/fetchData : requestBody : " + requestBody)

    if (!body.tenantId.equals("test")) {
      logger.debug("fetchDataFromMms : tenantId : ${body.tenantId}")
      var mmsUrl = Env.tenantMap[body.tenantId]?.hostUrl
      mmsUrl?.let { it
        var mmsApiUrl = it + "/api/integration/ai/fetchData"
        mmsApiUrl = mmsApiUrl.replace("//api", "/api")
        logger.debug("fetchDataFromMms : mmsUrl : ${mmsApiUrl}")
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder()
          .uri(URI.create(mmsApiUrl))
          .header("Content-Type", "application/json")
          .POST(HttpRequest.BodyPublishers.ofString(strMessage))
          .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        logger.debug("/api/integration/ai/fetchData : responseBody " + response.body())
        if (response.statusCode() === 200) {
          logger.debug("/api/integration/ai/fetchData : " + Env.gson.toJson(mapOf("result" to "success")))
          sendCallbackToAi(response.statusCode(), response.body())
        } else {
          logger.error("/api/integration/ai/fetchData : Error : ${response.statusCode()} : + ${response.body()}")
          sendCallbackToAi(response.statusCode(), response.body())
        }
      }

      if (mmsUrl === null) {
        logger.error("fetchDataFromMms : mmsUrl is null")
      }
    } else {
      sendCallbackToAi(HttpStatus.OK_200, requestBody)
    }
  }

  fun sendCallbackToAi(status: Int, body: String) {

    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
      .uri(URI.create(Env.aiServerUrl + "/api/deepchain/callback"))
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(body))
      .build()
    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    logger.debug("/api/deepchain/callback : responseBody " + response.body())
    if (response.statusCode() === 200) {
      logger.debug("/api/deepchain/callback : " + Env.gson.toJson(mapOf("result" to "success")))
    } else {
      logger.error("/api/deepchain/callback : Error : " + response.statusCode() + ":" + response.body())
    }
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
    this[ConsumerConfig.GROUP_ID_CONFIG] = consumerConfig.property("group.id").getString()
    this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = consumerConfig.property("key.deserializer").getString()
    this[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = consumerConfig.property("value.deserializer").getString()
    this[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "latest"
//    this[ConsumerConfig.CLIENT_DNS_LOOKUP_CONFIG] = "default"
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


