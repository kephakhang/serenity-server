package com.emoldino.serenity.server.route

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.TerminalDto
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.jpa.own.enum.UserLevel
import com.emoldino.serenity.server.service.CounterService
import com.emoldino.serenity.server.service.TerminalService
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.terminal(terminalService: TerminalService, counterService: CounterService) {

    get("/api/v1/terminal") {
        aop(call, true) {
            val terminalId = call.request.queryParameters["terminalId"]?.toString()?: throw SessionNotFoundException(null, "request parameter is missed : Terminal Id not found")
            val terminal = terminalService.getTerminal(terminalId) ?: throw SessionNotFoundException(null, "Terminal Not Found")
            call.respond(HttpStatusCode.OK, terminal)
        }
    }

    get("/api/v1/terminal/list") {
        aop(call, true) {
            val tenantId = call.request.queryParameters["tenantId"]?.toString()
            val list: List<TerminalDto> = terminalService.getList(tenantId)
            call.respond(HttpStatusCode.OK, list)
        }
    }

    get("/api/v1/terminal/test") {
        call.respond(HttpStatusCode.OK, mapOf("hello" to "terminal's world"))
    }

    post("/api/v1/terminal") {
        aop(call, true) {
            val terminalDto: TerminalDto = call.receive<TerminalDto>()
            if (it!!.level >= UserLevel.EMOLDINO.no) {
                call.respond(terminalService.save(terminalDto))
            } else {
                throw SessionNotFoundException(null, "Wrong User Access")
            }
        }
    }

    put("/api/v1/terminal") {
        aop(call, true) {
            val terminalDto: TerminalDto = call.receive<TerminalDto>()
            if (it!!.level >= UserLevel.EMOLDINO.no) {
                call.respond(terminalService.update(terminalDto))
            } else {
                throw SessionNotFoundException(null, "Wrong User Access")
            }
        }
    }
}

