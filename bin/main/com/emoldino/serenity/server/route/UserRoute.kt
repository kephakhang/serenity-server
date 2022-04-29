package com.emoldino.serenity.server.route

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.user(userService: UserService) {

    get("/api/v1/user") {
        val session: UserDto = call.authentication.principal<UserDto>() ?: throw SessionNotFoundException(null, "User Not Found")
        call.respond(HttpStatusCode.OK, session)
    }

    get("/api/v1/user/list") {
        val pageno = call.request.queryParameters["pageno"]?.toLong() ?: 1L
        val size = call.request.queryParameters["size"]?.toLong() ?: 10L
        val list: List<UserDto> = userService.getList(pageno, size)
        call.respond(HttpStatusCode.OK, list)
    }

    get("/api/v1/user/test"){
         call.respond(HttpStatusCode.OK, mapOf("hello" to "user's world"))
    }

    put("/api/v1/user") {
        val userDto: UserDto = call.receive<UserDto>()
        val session: UserDto = call.authentication.principal<UserDto>() ?: throw SessionNotFoundException(null, "User Not Found")
        if (session.uuid.equals(userDto.uuid)) {
            call.respond(userService.update(userDto))
        } else {
            throw SessionNotFoundException(null, "Wrong User Access")
        }
    }

}
