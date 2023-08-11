package com.siksinhot.test.cellinfo.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.siksinhot.test.common.DateUtil
import com.siksinhot.test.server.jpa.twin.entity.CellTradeHistory
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CellTradeHistoryDto(
    var id: String?,
    var cellId: Long,
    var fromCi: String,
    var toCi: String,
    var price: Float,
    var currency: String?,
    var createdAt: String,
    var updatedAt: String,
) {
    fun toEntity(): CellTradeHistory {
        val dto = this
        val entity = CellTradeHistory()
        return entity.apply {
            id = if (dto.id == null) UUID.randomUUID().toString() else dto.id
            cellId = dto.cellId
            fromCi = dto.fromCi
            toCi = dto.toCi
            price = dto.price
            currency = if (dto.currency !== "KRW" && dto.currency !== "USD") "KRW" else dto.currency!!
            createdAt = DateUtil.parseLocalDatetime(dto.createdAt)
            updatedAt = DateUtil.parseLocalDatetime(dto.updatedAt)
        }
    }
}
