package com.emoldino.serenity.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.Decoder
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.reflect.KClass


class LocalDateTimeSerializer : KSerializer<LocalDateTime> {
  override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.LONG)
  override fun serialize(encoder: Encoder, value: LocalDateTime): Unit =
    encoder.encodeLong(value.toInstant(ZoneOffset.UTC).toEpochMilli())
  override fun deserialize(decoder: Decoder): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochSecond(decoder.decodeLong()), ZoneOffset.UTC)
}
