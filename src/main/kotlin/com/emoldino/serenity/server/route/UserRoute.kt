package com.emoldino.serenity.server.route

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.jpa.own.enum.UserLevel
import com.emoldino.serenity.server.service.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.user(userService: UserService) {

    get("/api/v1/user") {
        aop(call, true) {
            call.respond(HttpStatusCode.OK, it!!)
        }
    }

    get("/api/v1/user/list") {
        aop(call, true) {
            val pageno = call.request.queryParameters["pageno"]?.toLong() ?: 1L
            val size = call.request.queryParameters["size"]?.toLong() ?: 10L
            val list: List<UserDto> = userService.getList(pageno, size)
            call.respond(HttpStatusCode.OK, list)
        }
    }

    get("/api/v1/user/test") {
        call.respond(HttpStatusCode.OK, mapOf("hello" to "user's world"))
    }

    post("/api/v1/user") {
        aop(call, true) {
            val userDto: UserDto = call.receive<UserDto>()
            if (it!!.level >= UserLevel.ADMIN.no) {
                call.respond(userService.save(userDto))
            } else {
                throw SessionNotFoundException(null, "Wrong User Access")
            }
        }
    }

    put("/api/v1/user") {
        aop(call, true) {
            val userDto: UserDto = call.receive<UserDto>()
            if (it!!.id.equals(userDto.id) || it!!.level > UserLevel.ADMIN.no) {
                call.respond(userService.update(userDto))
            } else {
                throw SessionNotFoundException(null, "Wrong User Access")
            }
        }
    }
}
