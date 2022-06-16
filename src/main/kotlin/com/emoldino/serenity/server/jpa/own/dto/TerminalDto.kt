package com.emoldino.serenity.server.jpa.own.dto

import com.emoldino.serenity.common.DateUtil
import java.time.LocalDateTime
import com.emoldino.serenity.server.jpa.own.entity.Terminal as Terminal


data class TerminalDto(

    val id: String? = "",

    val tenantId: String? = "",

    val version: Int = 3, // v3

    val status: Int = 0,

    val ip: String = "",

    val regDatetime: String = "",

    val modDatetime: String ="",

    ) {

    fun toTerminal(): Terminal {
        val dto = this
        val tenant = Terminal()
        return tenant.apply {
            id = dto.id
            teId = dto.tenantId
            trVersion = dto.version
            trStatus = dto.status
            trIp = dto.ip
            regDatetime = DateUtil.parseLocalDatetime(dto.regDatetime)
            modDatetime = DateUtil.parseLocalDatetime(dto.modDatetime)
        }
    }
}
