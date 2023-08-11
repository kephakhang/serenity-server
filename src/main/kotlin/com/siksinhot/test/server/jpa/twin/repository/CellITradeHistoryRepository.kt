package com.siksinhot.test.cellinfo.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.CellTradeHistory
import com.siksinhot.test.server.jpa.twin.entity.*
import java.util.*
import kotlin.reflect.KClass


class CellITradeHistoryRepository : Querydsl4RepositorySupport<CellTradeHistory>(Env.em, CellTradeHistory::class as KClass<Any>) {
    val path: PathBuilder<CellTradeHistory> = PathBuilder<CellTradeHistory>(CellTradeHistory::class.java, "cellTradeHistory")
    val cellTradeHistory: QCellTradeHistory = QCellTradeHistory.cellTradeHistory

    fun findById(id: String): CellTradeHistory? {
        return selectFrom(cellTradeHistory).where(cellTradeHistory.id.eq(id)).fetchOne()
    }
}