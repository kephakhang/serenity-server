package com.emoldino.serenity.server.model

import java.math.BigDecimal

enum class IntegrationType(val value: String, val no: Int) {
  TEST("test", 0),
  AI("ai", 1),
  SIMULATOR("simulator", 2)
}

data class Body(
  val requestId: String,
  val tenantId: String,
  val type: String,
  val data: LinkedHashMap<String, Any>
)
