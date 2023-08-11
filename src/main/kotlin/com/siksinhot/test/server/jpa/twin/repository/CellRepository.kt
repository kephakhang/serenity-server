package com.siksinhot.test.cell.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.jpa.common.repository.Page
import com.siksinhot.test.server.jpa.common.repository.Pageable
import com.siksinhot.test.server.jpa.common.repository.WhereBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.Cell
import com.siksinhot.test.server.jpa.twin.entity.User
import com.siksinhot.test.server.jpa.twin.entity.*
import java.time.LocalDateTime
import java.util.*
import kotlin.reflect.KClass

class CellRepository : Querydsl4RepositorySupport<Cell>(Env.em, Cell::class as KClass<Any>) {
    val path: PathBuilder<Cell> = PathBuilder<Cell>(Cell::class.java, "cell")
    val cell: QCell = QCell.cell
    fun findById(id: Long): Cell? {
        return selectFrom(cell).where(cell.id.eq(id)).fetchOne()
    }

    fun findAllByAreaId(areaId: Long): MutableList<Cell> {
        return selectFrom(cell).where(cell.areaId.eq(areaId)).fetch()
    }

    // 구매 가능한 셀 갯수
    fun countAllByAreaIdAndReservedIsTrue(areaId: Long): Long {
        return selectFrom(cell).where(cell.areaId.eq(areaId).and(cell.reserved.eq(true))).fetchCount()
    }
    // 결제 상태로 10분이상 지속된 셀
    fun findAllByOnPaymentIsTrueAndUpdatedAtIsBefore(updatedAtBefore: LocalDateTime): MutableList<Cell> {
        return selectFrom(cell).where(cell.onPayment.eq(true).and(cell.updatedAt.lt(updatedAtBefore))).fetch()
    }
}