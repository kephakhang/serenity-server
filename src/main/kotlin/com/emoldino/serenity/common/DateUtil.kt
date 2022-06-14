package com.emoldino.serenity.common

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateUtil {
    companion object {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

        fun localDatetimeToStr(dateTime: LocalDateTime): String {
            //return LocalDateTime.ofInstant(dateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC).format(formatter)
            return dateTime.format(formatter)
        }

        fun parseLocalDatetime(str: String): LocalDateTime {
            return LocalDateTime.ofInstant(Instant.parse(str), ZoneOffset.UTC)
        }
    }
}