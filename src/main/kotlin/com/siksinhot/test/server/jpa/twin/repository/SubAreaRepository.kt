package com.siksinhot.test.paymentinfo.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.*
import kotlin.reflect.KClass


class SubAreaRepository : Querydsl4RepositorySupport<SubArea>(Env.em, SubArea::class as KClass<Any>) {
    val path: PathBuilder<SubArea> = PathBuilder<SubArea>(SubArea::class.java, "subArea")
    val subArea: QSubArea = QSubArea.subArea

    fun findAllByMainAreaId(mainAreaId: Long): List<SubArea> {
        return selectFrom(subArea).where(subArea.mainArea.areaId.eq(mainAreaId.toInt())).fetch()
    }

}