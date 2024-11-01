package com.siksinhot.test.server.jpa.twin.enum

enum class OfferStatus(val displayName: String) {
    PENDING("대기중"),
    OFFERED("제안됨"),
    DONE("성사됨"),
    CANCEL("취소됨")
}