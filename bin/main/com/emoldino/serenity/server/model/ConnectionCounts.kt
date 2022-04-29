package com.emoldino.serenity.server.model


data class ConnectionCounts(
  val totalCount: Int,
  val subscriptions: List<SubscriptionCount>
)
