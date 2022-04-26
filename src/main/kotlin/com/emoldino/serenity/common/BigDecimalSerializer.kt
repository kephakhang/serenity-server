package com.emoldino.serenity.common


import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal


class BigDecimalSerializer : KSerializer<BigDecimal> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.DOUBLE)
  override fun serialize(encoder: Encoder, value: BigDecimal): Unit =
    encoder.encodeDouble(value.toDouble())
  override fun deserialize(decoder: Decoder): BigDecimal =
    decoder.decodeDouble().toBigDecimal()
}
