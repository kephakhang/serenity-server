package com.emoldino.serenity.server.route

import com.emoldino.serenity.extensions.stackTraceString
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.enum.AiCall
import com.emoldino.serenity.server.jpa.own.enum.MicroService
import com.emoldino.serenity.server.model.PostBody
import com.emoldino.serenity.server.model.KafkaEvent
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.Instant

fun Route.deepchain() {

    post("/api/ai/launch") {
        aop(call, false) {
            val body: PostBody = call.receive<PostBody>()
            val strBody = Env.gson.toJson(body)
            logger.debug(Env.aiServerUrl + "/api/ai/launch: ${strBody}")
            val event = KafkaEvent(MicroService.MMS.no, Instant.now(), AiCall.LAUNCH.name, body)
            try {
                Env.kafkaEventServiceMap[body.tenantId]?.send(event)
                logger.debug("/api/ai/launch : sendToKafka OK")
                call.respond(mapOf("result" to "success"))
            } catch (ex: Exception) {
                logger.error("/api/ai/launch : sendToKafka ERROR : ${ex.stackTraceString}")
                call.respond(
                    HttpStatusCode.InternalServerError,
                    mapOf("status" to 500, "result" to "failure", "reason" to ex.localizedMessage)
                )
            }
        }
    }

    post("/api/ai/fetchData") {
        aop(call, false) {
            val body: PostBody = call.receive<PostBody>()
            val fetchStr = Env.gson.toJson(body)
            logger.debug("/api/ai/fetchData : ${fetchStr}")
            try {
                val event = KafkaEvent(MicroService.AI.no, Instant.now(), AiCall.FETCH_DATA.name, body)
                Env.kafkaEventServiceMap[body.tenantId]?.send(event)
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
            val body = call.receive<PostBody>()
            logger.debug("/api/ai/status from deepchain : ${Env.gson.toJson(body)}")
            call.respond(HttpStatusCode.OK, body)
        }
    }

    post("/api/ai/results") {
        aop(call, false) {
            val body = call.receive<PostBody>()
            val strBody = Env.gson.toJson(body)
            logger.debug("/api/ai/results : requestBody : ${strBody}")
            try {
                val event = KafkaEvent(MicroService.AI.no, Instant.now(), AiCall.RESULTS.name, body)
                Env.kafkaEventServiceMap[body.tenantId]?.send(event)
                logger.debug("/api/ai/results : sendToKafka OK")
                call.respond(mapOf("result" to "success"))
            } catch (ex: Exception) {
                logger.error("/api/ai/results : sendToKafka ERROR : ${ex.stackTraceString}")
                call.respond(mapOf("status" to 500, "result" to "failure", "reason" to ex.localizedMessage))
            }
        }
    }
}

