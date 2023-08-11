package com.siksinhot.test.server.route.twin

import com.siksinhot.test.server.jpa.common.repository.PageRequest
import com.siksinhot.test.server.jpa.common.repository.Sort
import com.siksinhot.test.server.service.twin.CellInfoService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.jvm.Throws


@Throws(Exception::class)
fun Route.cell(cellService: CellInfoService) {

    post("/api/v1/cell/info") {
        //aop(call, false) {
            val pageNo = call.parameters["page"]?.toInt() ?: 0
            val size = call.parameters["size"]?.toInt() ?: 20
            val page = cellService.getAll(PageRequest.of(page = pageNo, size = size, sort = Sort.by("id:asc")))
            call.respond(page)
        //}
    }
}


class CellInfoNotes {
    companion object {
        const val getAllNote =
            "<h1>셀정보 리스트</h1>"

        const val getUserCellNote =
            "<h1>유저별 셀정보 리스트</h1>" +
                    "<h3>파라미터 설명</h3>" +
                    "__본 API 는 Path Parameter 로 확인하고자 하는 유저의 ID 를 받습니다.__"

        const val tradeCellNote =
            "<h1>셀 매매</h1>" +
                    "<h3>파라미터 설명</h3>" +
                    "__본 API 는 RequestBody 로 셀 매매정보를 받습니다.__"

        const val getCellTradeHistoryNote =
            "<h1>셀 매매 히스토리 정보</h1>" +
                    "<h3>파라미터 설명</h3>" +
                    "__본 API 는 Path Parameter 로 확인하고자 셀 매매 히스토리 ID(tradeId)를 받습니다.__"

        const val getCellTradeHistoryBySellerNote =
            "<h1>판매자 ci 기준 셀 매매 히스토리 정보</h1>" +
                    "<h3>파라미터 설명</h3>" +
                    "__본 API 는 Path Parameter 로 확인하고자 셀 매매 판매자 ci 를 받습니다.__"

        const val getCellTradeHistoryByBuyerNote =
            "<h1>구매자 ci 기준 셀 매매 히스토리 정보</h1>" +
                    "<h3>파라미터 설명</h3>" +
                    "__본 API 는 Path Parameter 로 확인하고자 셀 매매 구매자 ci 를 받습니다.__"
    }
}