package com.emoldino.serenity.server.route

import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.dto.LoginDto
import com.emoldino.serenity.server.jpa.own.dto.SignupDto
import com.emoldino.serenity.server.jpa.own.dto.ConfirmDto
import com.emoldino.serenity.server.model.NOK
import com.emoldino.serenity.server.model.OK
import com.emoldino.serenity.server.service.SsoService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.jvm.Throws

@Throws(Exception::class)
fun Route.sso(ssoService: SsoService) {

    get("/sso/hello"){
      call.respond(mapOf("/api/sso/hello" to "world"))
    }

    post("/sso/login"){
      val login: LoginDto = call.receive<LoginDto>()
      val user = ssoService.login(login)
      call.respond(user)
    }

    get("/sso/check") {
      val loginId: String = call.parameters["loginId"].toString()
      if (ssoService.checkAvailable(loginId)) {
        call.respond(OK)
      } else {
        call.respond(HttpStatusCode.Found, NOK)
      }
    }

    post("/sso/signup") {
      val signup = call.receive<SignupDto>()
      logger.debug("/sso/signup", Env.gson.toJson(signup))
      val user = ssoService.register(signup)
      call.respond(user)
    }

    get("/sso/confirm/email"){
      val email: ConfirmDto = call.receive<ConfirmDto>()
      val uid = email.uid
      val confirm = email.confirm
      val ret: String? = ssoService.confirmEmail(uid, confirm)
      if (ret == null) {
        call.respondRedirect(Env.confirmSuccessUrl)
      } else {
        call.respondRedirect(Env.confirmFailureUrl)
      }
    }
}