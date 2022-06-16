package com.emoldino.serenity.server.route

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.TenantDto
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.jpa.own.enum.UserLevel
import com.emoldino.serenity.server.service.TenantService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.tenant(tenantService: TenantService) {

    get("/api/v1/tenant") {
        aop(call, true) {
            val tenantId = call.request.queryParameters["terminalId"]?.toString()?: throw SessionNotFoundException(null, "request parameter is missed : Tenant Id not found")
            val tenant = tenantService.getTenant(tenantId) ?: throw SessionNotFoundException(null, "Tenant Not Found")
            call.respond(HttpStatusCode.OK, tenant)
        }
    }

    get("/api/v1/tenant/list") {
        aop(call, true) {
//            val pageno = call.request.queryParameters["pageno"]?.toLong() ?: 1L
//            val size = call.request.queryParameters["size"]?.toLong() ?: 10L
            val list: List<TenantDto> = tenantService.getList()
            call.respond(HttpStatusCode.OK, list)
        }
    }

    get("/api/v1/tenant/test") {
        call.respond(HttpStatusCode.OK, mapOf("hello" to "tenant's world"))
    }

    post("/api/v1/tenant") {
        aop(call, true) {
            val tenantDto: TenantDto = call.receive<TenantDto>()
            if (it!!.level > UserLevel.ADMIN.no) {
                call.respond(tenantService.save(tenantDto))
            } else {
                throw SessionNotFoundException(null, "Wrong User Access")
            }
        }
    }

    put("/api/v1/tenant") {
        aop(call, true) {
            val tenantDto: TenantDto = call.receive<TenantDto>()
            if (it!!.level > UserLevel.ADMIN.no) {
                call.respond(tenantService.update(tenantDto))
            } else {
                throw SessionNotFoundException(null, "Wrong User Access")
            }
        }
    }
}
