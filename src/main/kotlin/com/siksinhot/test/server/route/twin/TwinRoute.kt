package com.siksinhot.test.server.route.twin

import com.siksinhot.test.server.env.Env
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mu.KotlinLogging
import kotlin.jvm.Throws

private val logger = KotlinLogging.logger {}
@Throws(Exception::class)
fun Route.twin() {

    post("/exchange-api/callback.do") {
        //aop(call, false) {
            val body = call.receive<Any>()
            logger.debug { "/exchange-api/callback.do : ${Env.gson.toJson(body)}" }
            call.respond(HttpStatusCode.OK, body)
        //}
    }

    post("/wapi/v1/tk/callback") {
        //aop(call, false) {
        val body = call.receive<Any>()
        logger.debug { "/api/twinkorea/callback : ${Env.gson.toJson(body)}" }
        call.respond(HttpStatusCode.OK, body)
        //}
    }

    post("/wapi/v1/tk/add") {
        //aop(call, false) {
        val body = call.receive<Any>()
        logger.debug { "/api/twinkorea/add : ${Env.gson.toJson(body)}" }
        call.respond(HttpStatusCode.OK, body)
        //}
    }

}


class TwinNotes {
    companion object {
        const val getAreaMainGroupListNote =
            "<h1>대지역 리스트</h1>"

        const val getAreaSubGroupListNote =
            "<h1>소지역 리스트</h1>" +
                    "<h3>파라미터 설명</h3>" +
                    "__본 API 는 Query Parameter 로 option 을 받습니다.__\n" +
                    "|파라미터 명|타입|필수여부|내용|\n" +
                    "|--------|---|---|---|\n" +
                    "|mainAreaId|Long(Number)|Y|지역 대분류 고유번호|\n"
    }
}
