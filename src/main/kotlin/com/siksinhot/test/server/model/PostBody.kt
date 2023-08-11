package com.siksinhot.test.server.model

enum class IntegrationType(val value: String, val no: Int) {
  TEST("test", 0),
  FROM_SIKSIN("fromSiksin", 1),
  TO_SIKSIN("toSiksin", 2),
  FROM_META("fromMeta", 3),
  TO_META("toMeta", 4)
}

data class PostBody(
  val requestId: String, //UUID
  val callbackUrl: String, //
  val type: String,  // place | review | ...
  val data: Any
)
