package com.emoldino.serenity.server.jpa.own.dto

import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.entity.Call
import com.emoldino.serenity.server.model.PostBody
import java.time.LocalDateTime
import com.emoldino.serenity.server.jpa.own.entity.Tenant as Tenant


data class CallDto(

    val uuid: String? = null,

    val tenantId: String? = "",

    val requestId: String = "",

    val uri: String = "",

    val requestUrl: String = "",

    val method: String = "",

    val requestBody: PostBody? = null,

    val responseBody: PostBody? = null,

    val regDatetime: LocalDateTime = LocalDateTime.MIN,

    val modDatetime: LocalDateTime = LocalDateTime.MIN

    ) {

    fun toCall(): Call {
        val dto = this
        val call = Call(
                caRequestId = dto.requestId,
                caUri = dto.uri,
                caMethod = dto.method,
                caRequestBody = Env.gson.toJson(dto.requestBody!!),
                caResponseBody = Env.gson.toJson(dto.responseBody!!)
        )
        call.id = dto.uuid
        call.teId = dto.tenantId
        call.regDatetime = dto.regDatetime
        call.modDatetime = dto.modDatetime

        return call
    }
}
