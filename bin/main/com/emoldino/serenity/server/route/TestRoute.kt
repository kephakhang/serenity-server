package com.emoldino.serenity.server.route

import com.emoldino.serenity.exception.EmolException
import com.emoldino.serenity.exception.ErrorCode
import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.get
import io.ktor.response.*
import io.ktor.routing.*

fun Route.test() {

  get("/hello") {
    call.respond(mapOf("hello" to "world"))
  }
}
