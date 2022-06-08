package com.emoldino.serenity.server.kafka

import com.emoldino.serenity.extensions.stackTraceString
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.entity.QAdmin.admin
import com.emoldino.serenity.server.retrofit.MmsService
import com.emoldino.serenity.server.service.TenantService
import io.ktor.server.application.*
import io.ktor.util.*
import kotlinx.coroutines.suspendCancellableCoroutine
import mu.KotlinLogging
import org.apache.kafka.clients.admin.Admin
import org.apache.kafka.clients.admin.ListTopicsResult
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.PartitionInfo
import org.eclipse.jetty.util.LazyList.getList
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


private val logger = KotlinLogging.logger {}

@KtorExperimentalAPI
fun buildProducer(environment: ApplicationEnvironment, tenantService: TenantService): KafkaProducer<String, Any>? {
    val producerConfig = environment.config.config("ktor.kafka.producer")
    val producerProps = Properties().apply {
        this[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] =
            producerConfig.property("bootstrap.servers").getString().split(",")
        this[ProducerConfig.CLIENT_ID_CONFIG] = producerConfig.property("client.id").getString()
        this[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = producerConfig.property("key.serializer").getString()
        this[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = producerConfig.property("value.serializer").getString()
//    this[ProducerConfig.CLIENT_DNS_LOOKUP_CONFIG] = "default"
//        this["ssl.truststore.location"] = producerConfig.property("ssl.truststore.location").getString()
//        this["ssl.truststore.password"] = producerConfig.property("ssl.truststore.password").getString()
//        this["ssl.keystore.location"] = producerConfig.property("ssl.keystore.location").getString()
//        this["ssl.keystore.password"] = producerConfig.property("ssl.keystore.password").getString()
//        this["ssl.key.password"] = producerConfig.property("ssl.key.password").getString()
//        this["security.protocol"] = producerConfig.property("security.protocol").getString()
//        this["ssl.endpoint.identification.algorithm"] = producerConfig.property("ssl.endpoint.identification.algorithm").getString()
    }

    try {
        Env.kafkaAdmin = Admin.create(producerProps)
        val producer = KafkaProducer<String, Any>(producerProps)

        val listTopics: ListTopicsResult = Env.kafkaAdmin.listTopics()
        val names = listTopics.names().get()
        logger.debug("topic list : ${names}")

        val tenantList = tenantService.getList()

        logger.debug("tenant list : ${tenantList}")

        for (i in tenantList.indices) {

            // tenantMap 에 tenant 정보 추가
            val it = tenantList[i]
            Env.tenantMap.put(it.id!!, it)

            // Retrofit mmsServiceMap 에 tenant 별 서비스 추가
            val mmsService = MmsService(it.hostUrl)
            Env.mmsServiceMap.put(it.id!!, mmsService)

            if (!names.contains(it.id!!)) { // tenantId 이름으로 Kafka 에 Topic 이 등록되어 있지 않으면 topic 신규 등록
                val topic: NewTopic = NewTopic(it.id!!, 3, 3)
                logger.debug("createTopics : ${topic}")
                try {
                    Env.kafkaAdmin.createTopics(Collections.singleton(topic))
                } catch (ex: Exception) {
                    logger.error("createTopics() ERROR : ${it.id!!} : ${ex.stackTraceString}")
                }
            }
            logger.debug("add KafkaEventService : ${it.id!!}")
            Env.kafkaEventServiceMap.put(it.id!!, KafkaEventService(it.id!!, producer))
        }

        if (!names.contains("test")) {
            val topic: NewTopic = NewTopic("test", 3, 3)
            logger.debug("createTopics : test")
            try {
                Env.kafkaAdmin.createTopics(Collections.singleton(topic))
            } catch (ex: Exception) {
                logger.error("createTopics() ERROR : test  : ${ex.stackTraceString}")
            }
        }
        Env.kafkaEventServiceMap.put("test", KafkaEventService("test", producer))

        return producer
    } catch (ex: Exception) {
        logger.error("buildProducer : ERROR : ${ex.stackTraceString}")
        return null
    }
}

suspend inline fun <reified K : Any, reified V : Any> KafkaProducer<K, V>.dispatch(record: ProducerRecord<K, V>) =
    suspendCancellableCoroutine<RecordMetadata> { continuation ->
        this.send(record) { metadata, exception ->
            if (metadata == null) continuation.resumeWithException(exception!!) else continuation.resume(metadata)
        }
    }
