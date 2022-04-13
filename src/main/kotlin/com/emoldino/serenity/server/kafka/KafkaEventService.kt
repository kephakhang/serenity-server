package com.emoldino.serenity.server.kafka

import com.emoldino.serenity.server.model.KafkaEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord

class KafkaEventService(private val topic: String, private val kafkaProducer: KafkaProducer<String, Any>) {
  //var counter : AtomicLong = AtomicLong(0)
  suspend fun CoroutineScope.sendEvent(event: KafkaEvent) =
    kafkaProducer.dispatch(ProducerRecord<String, Any>(topic, "emoldino", event))

  fun send(event: KafkaEvent) {
    GlobalScope.launch {
      sendEvent(event)
    }
  }
}
