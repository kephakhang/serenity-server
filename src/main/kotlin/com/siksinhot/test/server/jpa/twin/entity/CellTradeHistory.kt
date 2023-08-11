package com.siksinhot.test.server.jpa.twin.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.siksinhot.test.cellinfo.dto.CellTradeHistoryDto
import com.siksinhot.test.common.DateUtil
import javax.persistence.*

@Entity
@Table(name="cell_trade_history")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CellTradeHistory(
    @Id
    @Column(name = "id")
    var id: String? = null,

    @Column(name = "cell_id", nullable = false)
    var cellId: Long = 0,

    @Column(name = "from_ci", nullable = false)
    var fromCi: String = "",

    @Column(name = "to_ci", nullable = false)
    var toCi: String = "",

    @Column(name = "price", nullable = false)
    var price: Float = 0.0f,

    @Column(name = "currency", nullable = false)
    var currency: String = "KRW"

) : BaseEntity() {
    fun toDto(): CellTradeHistoryDto {
        return CellTradeHistoryDto(
            id = id!!,
            cellId = cellId,
            fromCi = fromCi,
            toCi = toCi,
            price = price,
            currency = currency,
            createdAt = DateUtil.localDatetimeToStr(createdAt),
            updatedAt = DateUtil.localDatetimeToStr(updatedAt),
        )
    }
}