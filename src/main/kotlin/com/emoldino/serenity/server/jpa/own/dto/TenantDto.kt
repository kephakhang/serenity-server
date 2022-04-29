package com.emoldino.serenity.server.jpa.own.dto

import java.time.LocalDateTime
import com.emoldino.serenity.server.jpa.own.entity.Tenant as Tenant


data class TenantDto(

    val id: String? = "",

    val name: String = "",

    val type: Int = 0, //0:OWN, 1:OEM, 2:MoldMaker, 3:Supply

    val description: String = "",

    val countryCode: String = "",

    val hostUrl: String = "",

    val regDatetime: LocalDateTime = LocalDateTime.MIN,

    val modDatetime: LocalDateTime = LocalDateTime.MIN,

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
            regDatetime = dto.regDatetime
            modDatetime = dto.modDatetime
        }
    }
}
