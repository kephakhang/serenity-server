package com.emoldino.serenity.server.route

import com.emoldino.serenity.exception.EmolException
import com.emoldino.serenity.exception.ErrorCode
import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.dto.LoginDto
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.jpa.own.enum.MicroService
import com.emoldino.serenity.server.model.Body
import com.emoldino.serenity.server.model.KafkaEvent
import com.emoldino.serenity.server.service.UserService
import com.fasterxml.jackson.databind.node.ObjectNode
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.utils.EmptyContent.status
import io.ktor.http.*
import io.ktor.locations.get
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Instant


fun Route.deepchain() {

  post("/api/ai/launch") {
    call.respondRedirect(Env.aiServerUrl + "/api/deepchain/launch")
  }

  post("/api/ai/fetchData") {
    val fetch: Body = call.receive<Body>()
    val fetchStr = Json.encodeToString(fetch)
    logger.debug("/api/ai/fetchData : ${fetchStr}")
    val event = KafkaEvent(MicroService.AI.no, Instant.now(), fetch)
    try {
      Env.kafkaEventServiceMap[fetch.tenantId]?.send(event)
      call.respond(mapOf("result" to "success"))
    } catch (ex: Exception) {
      call.respond(mapOf("status" to 500, "result" to "failure", "reason" to ex.localizedMessage))
    }
  }

  post("/api/ai/status") {
    val status = call.receive<Body>()
    System.out.println("/status from deepchain : " + status.toString())
    System.out.flush()
    logger.debug("/status from deepchain : " + status.toString())
    call.respond(HttpStatusCode.OK)
  }

  post("/api/ai/results") {
    val results = call.receive<Body>()
    val strResults = Json.encodeToString(results)
    logger.debug("/results from deepchain : " +  strResults)

    if (results.tenantId.equals("test")) {
      logger.debug("/api/ai/results : " + Json.encodeToString(mapOf("result" to "success")))
      call.respond(HttpStatusCode.OK)
    } else {
      call.respondRedirect(Env.tenantMap[results.tenantId]?.hostUrl + "/mms/ai/results")
    }
  }
}

