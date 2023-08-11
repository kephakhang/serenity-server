package com.siksinhot.test.server.jpa.twin.enum

enum class PaymentMethod(val displayName: String) {
    CARD("신용카드"),
    BANK("계좌이체"),
    MYACCOUNT("내통장결제"),
    FREECOUPOUN("무료쿠폰") //무료쿠폰-제주도
}
