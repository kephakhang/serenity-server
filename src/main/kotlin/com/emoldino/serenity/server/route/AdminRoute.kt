package com.emoldino.serenity.server.route

import com.emoldino.serenity.server.jpa.own.dto.LoginDto
import com.emoldino.serenity.server.jpa.own.dto.SignupDto
import com.emoldino.serenity.server.service.AdminService
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.routing.Route.*
import kotlin.jvm.Throws


@Throws(Exception::class)
fun Route.admin(adminService: AdminService) {

    post("/sso/admin/login") {
        val login = call.receive<LoginDto>()
        val user = adminService.login(login)
        call.respond(user)
    }

    post("/sso/admin/signup") {
        val signup = call.receive<SignupDto>()
        val user = adminService.register(signup)
        call.respond(user)
    }
}
