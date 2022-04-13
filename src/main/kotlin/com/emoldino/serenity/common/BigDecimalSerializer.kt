package com.emoldino.serenity.common

import com.google.gson.*
import java.lang.reflect.Type
import java.math.BigDecimal

class BigDecimalSerializer : JsonSerializer<BigDecimal> {
  override fun serialize(src: BigDecimal?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
    if (src == null) {
      return JsonNull()
    } else {
      return JsonPrimitive(src.stripTrailingZeros().toPlainString())
    }
  }
}
