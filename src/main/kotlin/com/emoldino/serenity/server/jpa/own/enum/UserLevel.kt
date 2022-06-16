package com.emoldino.serenity.server.jpa.own.enum

enum class UserLevel(val value: String, val no: Int) {
  GUEST("guest", 0), // guest who have no permission
  USER("user", 1),  // user who have only READ permission
  FACTORY("factory", 10),  // Factory Admin who can register new counter
  COMPANY("company", 20),
  COMPANY_USER("company_user", 30),
  AGENT("agent", 40),
  AGENT_USER("agent_user", 50),
  EMOLDINO("emoldino", 100), // Emoldino Admin who can register accounts to OEM tenant
  ADMIN("admin", 1000)
}
