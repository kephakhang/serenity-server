package com.emoldino.serenity.server.route

import com.emoldino.serenity.exception.EmolException
import com.emoldino.serenity.exception.ErrorCode
import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.service.UserService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.http.*
import io.ktor.server.locations.get
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.test() {

    get("/hello") {
        call.respond(mapOf("hello" to "world"))
    }
}
