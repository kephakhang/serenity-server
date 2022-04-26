package com.emoldino.serenity.server.jpa.own.dto


import com.auth0.jwt.interfaces.Payload
import com.fasterxml.jackson.annotation.JsonIgnore
import com.emoldino.serenity.server.jpa.own.entity.Admin
import com.emoldino.serenity.server.jpa.own.entity.Member
import io.ktor.auth.*
import java.time.LocalDateTime
import com.emoldino.serenity.server.jpa.own.entity.Tenant as Tenant


data class TenantDto(

  val id: String? = "",

  val name: String = "",

  val type: Int = 0, //0:OWN, 1:OEM, 2:MoldMaker, 3:Supply

  val description: String = "",

  val countryCode: String = "",

//  val jdbcHost: String? = null,
//
//  val jdbcUser: String? = null,
//
//  val jdbcPass: String? = null,
//
  val hostUrl: String? = null,
//
//  val enable: Boolean = true,

  val regDatetime: LocalDateTime = LocalDateTime.MIN,

  val modDatetime: LocalDateTime = LocalDateTime.MIN,

)  {

  fun toTenant(): Tenant {
    val dto = this
    return Tenant(
      id =  dto.id,
      name =  dto.name,
      type =  dto.type,
      description = dto.description,
      jdbcHost = null,
      jdbcUser = null,
      jdbcPass = null,
      countryId =  dto.countryCode,
      hostUrl = null,
      enable = true,
      regDatetime =  dto.regDatetime,
      modDatetime =  dto.modDatetime
    )
  }
}
