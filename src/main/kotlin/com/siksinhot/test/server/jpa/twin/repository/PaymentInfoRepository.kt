package com.siksinhot.test.paymentinfo.repository

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.jpa.common.repository.Page
import com.siksinhot.test.server.jpa.common.repository.Pageable
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.common.repository.Sort
import com.siksinhot.test.server.jpa.twin.entity.PaymentInfo
import com.siksinhot.test.server.jpa.twin.entity.*
import kotlin.reflect.KClass


class PaymentInfoRepository : Querydsl4RepositorySupport<PaymentInfo>(Env.em, PaymentInfo::class as KClass<Any>) {
    val path: PathBuilder<PaymentInfo> = PathBuilder<PaymentInfo>(PaymentInfo::class.java, "paymentInfo")
    val paymentInfo: QPaymentInfo = QPaymentInfo.paymentInfo

    fun findAllByOrderByTrNo(pageRequest: Pageable): Page {
        val data = selectFrom(paymentInfo).orderBy(OrderSpecifier(Order.ASC,paymentInfo.trNo)).offset(pageRequest.offset).limit(pageRequest.pageSize.toLong()).fetch()
        return toPage(paymentInfo, pageRequest, data)
    }

    fun findAllByUserIdOrderByTrNo(userId: Long, pageRequest: Pageable): Page {
        val data = selectFrom(paymentInfo).where(paymentInfo.user?.id?.eq(userId)).orderBy(OrderSpecifier(Order.ASC,paymentInfo.trNo)).offset(pageRequest.offset).limit(pageRequest.pageSize.toLong()).fetch()
        return toPage(paymentInfo, pageRequest, data)
    }
}