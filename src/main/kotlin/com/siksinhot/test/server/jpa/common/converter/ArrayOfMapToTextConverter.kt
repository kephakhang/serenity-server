package com.siksinhot.twinkorea.converter

import com.siksinhot.test.server.env.Env
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter
class ArrayOfMapToTextConverter : AttributeConverter<Any, String> {

    override fun convertToDatabaseColumn(map: Any?): String? {
        if (map == null) return null
        return try {
            val jsonStr = Env.objectMapper.writeValueAsString(map)
            jsonStr
        } catch(ex: Exception) {
            null
        }
    }

    override fun convertToEntityAttribute(text: String?): Any? {
        if (text == null) return null
        return try {
            Env.objectMapper.readValue(text, Any::class.java)
        } catch(ex: Exception) {
            null
        }
    }
}