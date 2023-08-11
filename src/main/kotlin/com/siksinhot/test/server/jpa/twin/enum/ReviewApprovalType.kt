package com.siksinhot.test.server.jpa.twin.enum

enum class ReviewApprovalType(val displayName: String) {
    CERTIFIED("인증"),
    CLOSED("비공개"),
    NONE("미처리")
}