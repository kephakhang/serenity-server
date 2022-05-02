package com.emoldino.serenity.server.jpa.own.dto


import com.auth0.jwt.interfaces.Payload
import com.fasterxml.jackson.annotation.JsonIgnore
import com.emoldino.serenity.server.jpa.own.entity.Admin
import com.emoldino.serenity.server.jpa.own.entity.Member
import com.fasterxml.jackson.annotation.JsonInclude
import io.ktor.server.auth.*
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto(
    @JsonIgnore
    val token: Payload? = null,  //Json Web Token after authentication

    var jwt: String? = null,

    val uuid: String? = null,

    val tenantId: String = "",

    @JsonIgnore
    val emailHash: String = "",

    @JsonIgnore
    val mobileHash: String = "",

    @JsonIgnore
    val password: String = "",

    val name: String = "",

    val level: Int = 1,

    val point: Long = 0,

    val status: Int = 0,

    val regDatetime: LocalDateTime = LocalDateTime.MIN,

    val modDatetime: LocalDateTime = LocalDateTime.MIN,

    val detail: UserDetailDto? = null
) : Principal {

    @get:JsonIgnore
    val profile
        get() = ProfileDto(name, detail?.gender, detail?.greeting, detail?.image, false)

    fun toMember(): Member {
        val dto = this
        val member = Member()
        return member.apply {
            id = uuid
            teId = tenantId
            mbEmailHash = emailHash
            mbMobileHash = mobileHash
            mbPassword = password
            mbName = name
            mbLevel = level
            mbStatus = status
            mbPoint = point
            regDatetime = dto.regDatetime
            modDatetime = dto.modDatetime
            detail = dto.detail?.toMemberDetail()
        }
    }

    fun toAdmin(): Admin {
        val dto = this
        val admin = Admin()
        return admin.apply {
            id = dto.uuid
            teId = dto.tenantId
            amEmailHash = dto.emailHash
            amPassword = dto.password
            amName = dto.name
            amLevel = dto.level
            amStatus = dto.status
            regDatetime = dto.regDatetime
            modDatetime = dto.modDatetime
        }
    }
}

class UserWrapper(val user: UserDto)
