package com.siksinhot.twinkorea.converter

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
class BooleanToYNConverter : AttributeConverter<Boolean, String> {
    /**
     * Boolean 값을 Y 또는 N 으로 컨버트
     *
     * @param attribute boolean 값
     * @return String true 인 경우 Y 또는 false 인 경우 N
     */
    override fun convertToDatabaseColumn(attribute: Boolean): String {
        return if (attribute)  "Y" else "N"
    }

    /**
     * Y 또는 N 을 Boolean 으로 컨버트
     *
     * @param yn Y 또는 N
     * @return Boolean 대소문자 상관없이 Y 인 경우 true, N 인 경우 false
     */
    override fun convertToEntityAttribute(yn: String): Boolean {

        return "Y" == yn
    }
}