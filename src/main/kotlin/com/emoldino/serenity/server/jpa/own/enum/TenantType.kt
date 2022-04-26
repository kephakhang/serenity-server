package com.emoldino.serenity.server.jpa.own.enum

enum class TenantType(val value: String, val no: Int) {
  EMOLDINO("own", 0), //eMoldino tenant
  OEM("oem", 1), // OEM tenant
  MOLD_MAKER("mold-maker", 2), // Mold Maker tenant
  SUPPLY("supply", 3) // Supply tenant
}
