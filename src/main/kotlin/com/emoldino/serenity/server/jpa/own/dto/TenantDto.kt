package com.emoldino.serenity.server.jpa.own.dto

import com.emoldino.serenity.common.DateUtil
import java.time.LocalDateTime
import com.emoldino.serenity.server.jpa.own.entity.Tenant as Tenant


data class TenantDto(

    val id: String? = "",

    val name: String = "",

    val type: Int = 0, //0:OWN, 1:OEM, 2:MoldMaker, 3:Supply

    val description: String = "",

    val countryCode: String = "",

    val hostUrl: String = "",

    val prefix: String = "",

    val hostname: String = "",

    val regDatetime: String = "",

    val modDatetime: String ="",

    ) {

    fun toTenant(): Tenant {
        val dto = this
        val tenant = Tenant()
        return tenant.apply {
            id = dto.id
            name = dto.name
            type = dto.type
            description = dto.description
            jdbcHost = null
            jdbcUser = null
            jdbcPass = null
            countryId = dto.countryCode
            hostUrl = dto.hostUrl
            enable = true
            prefix = dto.prefix
            hostname = dto.hostname
            regDatetime = DateUtil.parseLocalDatetime(dto.regDatetime)
            modDatetime = DateUtil.parseLocalDatetime(dto.modDatetime)
        }
    }
}
