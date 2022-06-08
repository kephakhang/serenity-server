package com.emoldino.serenity.server.jpa.own.entity

import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.entity.BaseEntity
import com.emoldino.serenity.server.jpa.own.dto.CallDto
import com.emoldino.serenity.server.model.PostBody
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity(name = "Call")
@Table(name = Env.tablePrefix + "call")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Call (

  @Column(name = "ca_requst_id", nullable = false)
  var caRequestId: String = "",

  @Column(name = "ca_uri", nullable = false)
  var caUri: String = "",

  @Column(name = "ca_request_url", nullable = false)
  var caRequestUrl: String = "",

  @Column(name = "ca_method", nullable = false)
  var caMethod: String = "",

  @Column(name = "ca_request_body", nullable = false)
  var caRequestBody: String,

  @Column(name = "ca_response_body", nullable = false)
  var caResponseBody: String

  )  : BaseEntity() {

  fun toCallDto(): CallDto {
    return CallDto(
      uuid = id,
      requestId = caRequestId,
      uri = caUri,
      requestUrl = caRequestUrl,
      method = caMethod,
      requestBody = Env.gson.fromJson(caRequestBody, PostBody::class.java),
      responseBody = Env.gson.fromJson(caResponseBody, PostBody::class.java),
      regDatetime =  regDatetime,
      modDatetime = modDatetime
    )
  }
}
