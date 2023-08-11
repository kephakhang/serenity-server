package com.siksinhot.test.server.route.twin

import com.siksinhot.test.area.dto.AreaMainDto
import com.siksinhot.test.area.dto.AreaSubDto
import com.siksinhot.test.exception.ErrorCode
import com.siksinhot.test.exception.TwinException
import com.siksinhot.test.server.service.twin.AreaService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.jvm.Throws


@Throws(Exception::class)
fun Route.area(areaService: AreaService) {

    get("/api/v1/area/main") {
        //aop(call, false) {
            val list: List<AreaMainDto> = areaService.getAreaMainList()
            call.respond(list)
        //}
    }

    get("/api/v1/area/sub") {
        //aop(call, false) {
            val mainAreaId = call.parameters["mainAreaId"]?.toLong() ?: throw TwinException(ErrorCode.E10010)
            val list: List<AreaSubDto> = areaService.getAreaSubList(mainAreaId)
            call.respond(list)
        //}
    }
}


class AreaNotes {
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
