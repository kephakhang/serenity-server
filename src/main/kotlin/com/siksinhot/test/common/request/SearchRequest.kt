package com.siksinhot.test.common.request

import com.siksinhot.test.server.jpa.common.repository.Sort
import com.siksinhot.test.server.jpa.common.repository.PageRequest
import java.time.LocalDateTime

/**
 * 거래 검색 모델
 */
data class OfferSearchRequest(
    val addressOne: String? = null,
    val addressTwo: String? = null,
    val addressThree: String? = null,
    val text: String? = null
)

/**
 * 회원 검색 모델
 */
data class UserSearchRequest(
    val nickname: String? = null,
    val email: String? = null
)

/**
 * 결제 내역 검색 모델
 */
data class PaymentLogSearchRequest(
    val trNo: String? = null,
    val userEmail:  String? = null,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null
)

/**
 * 폴리곤 정보 및 포함 셀 데이터 검색 모델
 */
data class PolygonCellDataSearchRequest(
    val district: String? = null,
    val name: String? = null,
    val sort: String? = null,
)


