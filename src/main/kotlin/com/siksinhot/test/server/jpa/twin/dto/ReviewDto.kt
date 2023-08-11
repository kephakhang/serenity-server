package com.siksinhot.test.server.jpa.twin.dto

import java.time.LocalDateTime


class ReviewDto(

)

class ReviewDeletedDto(
    val createdAt: LocalDateTime,
    val reviewId: Long,
    val code: String,
    val storeName: String?,
    val deleteReason: String,
    val deletedAt: LocalDateTime,
    val point: Double? = 0.0,
)
