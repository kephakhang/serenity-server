package com.emoldino.serenity.server.kafka

import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.dto.TenantDto
import com.emoldino.serenity.server.jpa.own.entity.QAdmin.admin
import com.emoldino.serenity.server.service.TenantService
import io.ktor.application.*
import io.ktor.util.*
import kotlinx.coroutines.suspendCancellableCoroutine
import org.apache.kafka.clients.admin.Admin
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@KtorExperimentalAPI
fun buildProducer(environment: ApplicationEnvironment, tenantService: TenantService): KafkaProducer<String, Any> {
  val producerConfig = environment.config.config("ktor.kafka.producer")
  val producerProps = Properties().apply {
    this[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = producerConfig.property("bootstrap.servers").getString().split(",")
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

  Env.kafkaAdmin = Admin.create(producerProps)
  val producer = KafkaProducer<String, Any>(producerProps)

  tenantService.getList().map{it -> {
    Env.tenantMap.put(it.id!!, it)
    val partitions = producer.partitionsFor(it.id!!)
    if (partitions === null || partitions.isEmpty()) {
      val topic: NewTopic = NewTopic(it.id!!, 3, 3)
      Env.kafkaAdmin.createTopics(Collections.singleton(topic))
    }
    Env.kafkaEventServiceMap.put(it.id!!, KafkaEventService(it.id!!, producer))
  }}

  val testPartitions = producer.partitionsFor("test")
  if (testPartitions === null || testPartitions.isEmpty()) {
    val topic: NewTopic = NewTopic("test", 3, 3)
    Env.kafkaAdmin.createTopics(Collections.singleton(topic))
  }
  Env.kafkaEventServiceMap.put("test", KafkaEventService("test", producer))

  return producer
}

suspend inline fun <reified K : Any, reified V : Any> KafkaProducer<K, V>.dispatch(record: ProducerRecord<K, V>) =
  suspendCancellableCoroutine<RecordMetadata> { continuation ->
    this.send(record) { metadata, exception ->
      if (metadata == null) continuation.resumeWithException(exception!!) else continuation.resume(metadata)
    }
  }
