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
import mu.KotlinLogging
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Instant


fun Route.deepchain() {

  get("/api/ai/launch") {
    call.respondRedirect(Env.aiServerUrl + "/api/deepchain/launch")
//    val launch = call.receive<Body>()
//    val values = mapOf("requestId" to "1234567", "aiType" to "EM_AI_ANOM", "hostUrl" to Env.serenityServerUrl, "moldId" to "test")
//
//    val requestBody: String = Env.objectMapper
//      .writeValueAsString(values)
//
//    val client = HttpClient.newBuilder().build()
//    val request = HttpRequest.newBuilder()
//      .uri(URI.create(Env.aiServerUrl + "/launch"))
//      .POST(HttpRequest.BodyPublishers.ofString(requestBody))
//      .build()
//    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
//    if( response.statusCode() === 200) {
//      call.respond(mapOf("result" to "success"))
//    } else {
//      call.respond(mapOf("status" to response.statusCode(), "result" to "failure", "reason" to response.body()))
//    }
  }

  post("/api/ai/fetchData") {
    val fetch = call.receive<Body>()
    val fetchStr = Env.gson.toJson(fetch)
    logger.debug("/api/ai/fetchData : ${fetchStr}")
    val event = KafkaEvent(MicroService.DEEP_CHAIN.no, Instant.now(), fetch)
    try {
      Env.kafkaEventService?.send(event)
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
//    val requestId = status["requestId"]
//    val moldId = status["moldId"]
//    val results = status["results"]

    call.respond(HttpStatusCode.OK)
  }

  post("/api/ai/results") {
    val results = call.receive<Body>()
    System.out.println("/results from deepchain : " + results.toString())
    System.out.flush()
    logger.debug("/results from deepchain : " +  results.toString())
//    val requestId = results["requestId"]
//    val moldId = results["moldId"]
//    val data = results["results"]

    call.respond(HttpStatusCode.OK)
  }
}

