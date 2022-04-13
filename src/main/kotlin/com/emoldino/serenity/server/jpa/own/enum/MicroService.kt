package com.emoldino.serenity.server.jpa.own.enum

enum class MicroService(val value: String, val no: Int) {
  SSO("sso", 1),
  API("api", 3),
  DEEP_CHAIN("deep_chain", 2)
}
