package com.emoldino.serenity.server.jpa.own.enum

enum class UserLevel(val value: String, val no: Int) {
  GUEST("guest", 0),
  USER("user", 0),
  COMPANY("company", 0),
  COMPANY_USER("company_user", 0),
  AGENT("agent", 0),
  AGENT_USER("agent_user", 0),
  ADMIN("admin", 0)
}
