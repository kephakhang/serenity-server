package com.siksinhot.test.server.jpa.twin.dto

import java.time.LocalDateTime

data class PaymentInfoDto(
    var trNo: String,
    var trPrice: String,
    var paymentAt: LocalDateTime,
    var method: String,
    var cellIds: String?,
    var cellId: Long?,
    var userId: Long,
)
