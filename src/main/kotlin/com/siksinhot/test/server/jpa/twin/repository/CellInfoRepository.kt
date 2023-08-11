package com.siksinhot.test.cellinfo.repository

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.jpa.common.repository.Page
import com.siksinhot.test.server.jpa.common.repository.Pageable
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.CellInfo
import com.siksinhot.test.server.jpa.twin.entity.*
import kotlin.reflect.KClass

class CellInfoRepository : Querydsl4RepositorySupport<CellInfo>(Env.em, CellInfo::class as KClass<Any>) {
    val path: PathBuilder<CellInfo> = PathBuilder<CellInfo>(CellInfo::class.java, "cellInfo")
    val cellInfo: QCellInfo = QCellInfo.cellInfo


    fun findAllByOrderByCellId(pageRequest: Pageable): Page {
        val data = selectFrom(cellInfo).orderBy(OrderSpecifier(Order.ASC, cellInfo.cellId))
            .offset(pageRequest.offset).limit(pageRequest.pageSize.toLong()).fetch()

        return toPage(cellInfo, pageRequest, data)
    }

    fun findAllByOwnerIdOrderByCellId(ownerId: Long, pageRequest: Pageable): Page {
        val data = selectFrom(cellInfo).where(cellInfo.owner.id.eq(ownerId))
            .orderBy(OrderSpecifier(Order.ASC, cellInfo.cellId))
            .offset(pageRequest.offset).limit(pageRequest.pageSize.toLong()).fetch()
        return toPage(cellInfo, pageRequest, data)
    }

    fun findAllByOwnerId(ownerId: Long): List<CellInfo> {
        return selectFrom(cellInfo).where(cellInfo.owner.id.eq(ownerId))
            .fetch()
    }

    fun findAllByCellId(cellId: Long): List<CellInfo> {
        return selectFrom(cellInfo).where(cellInfo.cellId.eq(cellId))
            .fetch()
    }
}