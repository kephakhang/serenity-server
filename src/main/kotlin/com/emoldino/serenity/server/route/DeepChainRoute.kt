package com.emoldino.serenity.server.route

import com.emoldino.serenity.extensions.stackTraceString
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.enum.MicroService
import com.emoldino.serenity.server.model.Body
import com.emoldino.serenity.server.model.KafkaEvent
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Instant

fun Route.deepchain() {

    fun CoroutineScope.sendTo(url: String, strBody: String) {
        launch {
            try {
                val client = HttpClient.newBuilder().build()
                val request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(strBody))
                    .build()
                val response = client.send(request, HttpResponse.BodyHandlers.ofString())
                logger.debug(url + " : responseBody " + response.body())
                if (response.statusCode() === 200) {
                    logger.debug(url + " : " + Env.gson.toJson(mapOf("result" to "success")))
                } else {
                    logger.error(url + " : Error : " + response.statusCode() + ":" + response.body())
                }
            } catch (e: Throwable) {
                logger.error("sendTo() error : " + e.stackTraceString)
            }
        }
    }

    post("/api/ai/launch") {
        aop(call, false) {
            val body: Body = call.receive<Body>()
            val strBody = Env.gson.toJson(body)
            logger.debug(Env.aiServerUrl + "/api/deepchain/launch : ${strBody}")
            GlobalScope.launch {
                sendTo(Env.aiServerUrl + "/api/deepchain/launch", strBody)
            }

            call.respond(HttpStatusCode.OK)
        }
    }

    post("/api/ai/fetchData") {
        aop(call, false) {
            val fetch: Body = call.receive<Body>()
            val fetchStr = Env.gson.toJson(fetch)
            logger.debug("/api/ai/fetchData : ${fetchStr}")
            val event = KafkaEvent(MicroService.AI.no, Instant.now(), fetch)
            try {
                Env.kafkaEventServiceMap[fetch.tenantId]?.send(event)
                logger.debug("/api/ai/fetchData : sendToKafka OK")
                call.respond(mapOf("result" to "success"))
            } catch (ex: Exception) {
                logger.error("/api/ai/fetchData : sendToKafka ERROR : ${ex.stackTraceString}")
                call.respond(mapOf("status" to 500, "result" to "failure", "reason" to ex.localizedMessage))
            }
        }
    }

    post("/api/ai/status") {
        aop(call, false) {
            val body = call.receive<Body>()
            logger.debug("/api/ai/status from deepchain : ${Env.gson.toJson(body)}")
            call.respond(HttpStatusCode.OK, body)
        }
    }

    post("/api/ai/results") {
        aop(call, false) {
            val body = call.receive<Body>()
            val strBody = Env.gson.toJson(body)
            logger.debug("/api/ai/results : requestBody : ${strBody}")

            if (body.tenantId.equals("test")) {
                call.respond(HttpStatusCode.OK, body)
            } else {
                val url = Env.tenantMap[body.tenantId]?.hostUrl
                if (url !== null) {
                    GlobalScope.launch {
                        val fullUrl = url + "/api/integration/ai/results"

                        sendTo(fullUrl.replace("//api", "/api"), strBody)
                    }
                    call.respond(HttpStatusCode.OK, body)
                } else { //error case
                    logger.error(Env.aiServerUrl + "/api/ai/results Error : ${strBody}")
                    call.respond(HttpStatusCode.ServiceUnavailable, "Unknown TenanatId : ${body.tenantId}")
                }
            }
        }
    }
}

