package com.emoldino.serenity.server.route

import com.emoldino.serenity.server.jpa.own.dto.LoginDto
import com.emoldino.serenity.server.jpa.own.dto.SignupDto
import com.emoldino.serenity.server.service.AdminService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.jvm.Throws


@Throws(Exception::class)
fun Route.admin(adminService: AdminService) {

    post("/sso/admin/login") {
        aop(call, false) {
            val login = call.receive<LoginDto>()
            val user = adminService.login(login)
            call.respond(user)
        }
    }

    post("/sso/admin/signup") {
        aop(call, false) {
            val signup = call.receive<SignupDto>()
            val user = adminService.register(signup)
            call.respond(user)
        }
    }
}
