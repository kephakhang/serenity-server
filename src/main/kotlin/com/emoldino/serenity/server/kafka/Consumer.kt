package com.emoldino.serenity.server.kafka

import com.emoldino.serenity.common.ClosableJob
import com.emoldino.serenity.extensions.stackTraceString
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.entity.QCall.call
import com.emoldino.serenity.server.jpa.own.enum.AiCall
import com.emoldino.serenity.server.model.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.util.*
import io.ktor.utils.io.core.*
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import retrofit2.Call
import retrofit2.Response
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import rx.functions.*
import rx.Subscriber;
import rx.internal.util.ActionSubscriber
import rx.internal.util.InternalObservableUtils

private val logger = KotlinLogging.logger {}


class Consumer<K, V>(private val consumer: KafkaConsumer<K, V>, val topic: String) : ClosableJob {
    private val closed: AtomicBoolean = AtomicBoolean(false)
    private var finished = CountDownLatch(1)

    init {
        consumer.subscribe(listOf(topic))
    }

    override fun close() {
        logger.info { Env.message("app.kafka.consumer.jobClose") }
        closed.set(true)
        consumer.wakeup()
        finished.await(3000, TimeUnit.MILLISECONDS)
        logger.info { Env.message("app.kafka.consumer.jobClosed") }
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
                        val body: PostBody = message.data
                        if (topic.equals(body.tenantId)) {
                            when (body.type) {
                                IntegrationType.AI.value -> {
                                    when (message.uri) {
                                        AiCall.LAUNCH.name -> launchAI(body)
                                        AiCall.FETCH_DATA.name -> fetchDataFromMms(body)
                                        AiCall.RESULTS.name -> sendResultsToMms(body)
                                    }
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


    fun launchAI(body: PostBody) {
        val url = Env.aiServerUrl + "/api/deepchain/launch"
        Env.deepChainService.getInstance().launch(body).subscribe(EmolSubscriber.suscriber(url))
    }

    fun fetchDataFromMms(body: PostBody) {

        logger.debug("fetchDataFromMms : tenantId : ${body.tenantId}")
        val mmsService = Env.mmsServiceMap[body.tenantId]
        val url = Env.tenantMap[body.tenantId]?.hostUrl + "/api/integration/ai/fetchData"
        mmsService?.let {
            it.getInstance().fetchData(body).subscribe(EmolSubscriber.redirectSuscriber(url, ::sendCallbackToAi))
        }

        if (mmsService === null) {
            logger.error("Unknown Tenant : ${body.tenantId}")
        }
    }

    fun sendCallbackToAi(body: PostBody) {

        val url = Env.aiServerUrl + "/api/deepchain/callback"
        Env.deepChainService.getInstance().callback(body).subscribe(EmolSubscriber.suscriber(url))
    }

    fun sendResultsToMms(body: PostBody) {
        val mmsService = Env.mmsServiceMap[body.tenantId]
        mmsService?.let {
            val url = Env.tenantMap[body.tenantId]?.hostUrl + "/api/integration/ai/fetchData"
            it.getInstance().results(body).subscribe(EmolSubscriber.suscriber(url))
        }
        if (mmsService === null) {
            logger.error("Unknown Tenant : ${body.tenantId}")
        }
    }
}


fun <K, V> buildConsumer(
    environment: ApplicationEnvironment,
    topic: String
): Consumer<K, V> {
    val consumerConfig = environment.config.config("ktor.kafka.consumer")
    val consumerProps = Properties().apply {
        this[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] =
            consumerConfig.property("bootstrap.servers").getString().split(",")
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


