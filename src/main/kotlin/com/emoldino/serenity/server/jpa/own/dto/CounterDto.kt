package com.emoldino.serenity.server.jpa.own.dto

import com.emoldino.serenity.common.DateUtil
import java.time.LocalDateTime
import com.emoldino.serenity.server.jpa.own.entity.Counter as Counter


data class CounterDto(

    val id: String? = "",

    val tenantId: String? = "",

    val terminalId: String? = "",

    val version: Int = 3, // v3

    val status: Int = 0, // 0:available, 1:installed

    val regDatetime: String = "",

    val modDatetime: String = "",

    ) {

    fun toCounter(): Counter {
        val dto = this
        val tenant = Counter()
        return tenant.apply {
            id = dto.id
            teId = dto.tenantId
            trId = dto.terminalId
            coVersion = dto.version
            coStatus = dto.status
            regDatetime = DateUtil.parseLocalDatetime(dto.regDatetime)
            modDatetime = DateUtil.parseLocalDatetime(dto.modDatetime)
        }
    }
}
