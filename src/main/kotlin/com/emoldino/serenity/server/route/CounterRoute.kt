package com.emoldino.serenity.server.route

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.CounterDto
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.jpa.own.enum.UserLevel
import com.emoldino.serenity.server.service.CounterService
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.counter(counterService: CounterService) {

    get("/api/v1/counter") {
        aop(call, true) {
            val counterId = call.request.queryParameters["counterId"]?.toString()?: throw SessionNotFoundException(null, "request parameter is missed : Counter Id not found")
            val counter = counterService.getCounter(counterId) ?: throw SessionNotFoundException(null, "Counter Not Found")
            call.respond(HttpStatusCode.OK, counter)
        }
    }

    get("/api/v1/counter/list") {
        aop(call, true) {
            val tenantId = call.request.queryParameters["tenantId"]?.toString()
            val terminalId = call.request.queryParameters["terminalId"]?.toString()
            val list: List<CounterDto> = counterService.getList(tenantId, terminalId)
            call.respond(HttpStatusCode.OK, list)
        }
    }

    get("/api/v1/counter/test") {
        call.respond(HttpStatusCode.OK, mapOf("hello" to "counter's world"))
    }

    post("/api/v1/counter") {
        aop(call, true) {
            val counterDto: CounterDto = call.receive<CounterDto>()
            if (it!!.level >= UserLevel.FACTORY.no) {
                call.respond(counterService.save(counterDto))
            } else {
                throw SessionNotFoundException(null, "Wrong User Access")
            }
        }
    }

    put("/api/v1/counter") {
        aop(call, true) {
            val counterDto: CounterDto = call.receive<CounterDto>()
            if (it!!.level >= UserLevel.FACTORY.no) {
                call.respond(counterService.update(counterDto))
            } else {
                throw SessionNotFoundException(null, "Wrong User Access")
            }
        }
    }
}

