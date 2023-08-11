package com.siksinhot.test.server.model


data class ConnectionCounts(
  val totalCount: Int,
  val subscriptions: List<SubscriptionCount>
)
