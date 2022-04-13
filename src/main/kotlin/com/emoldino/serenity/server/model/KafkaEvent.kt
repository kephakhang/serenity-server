package com.emoldino.serenity.server.model

import java.time.Instant

data class KafkaEvent(val type: Int, val timestamp: Instant, val data: Any)
