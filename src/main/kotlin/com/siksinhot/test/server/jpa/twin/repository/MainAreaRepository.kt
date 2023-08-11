package com.siksinhot.test.paymentinfo.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.MainArea
import com.siksinhot.test.server.jpa.twin.entity.*
import kotlin.reflect.KClass


class MainAreaRepository : Querydsl4RepositorySupport<MainArea>(Env.em, MainArea::class as KClass<Any>) {
    val path: PathBuilder<MainArea> = PathBuilder<MainArea>(MainArea::class.java, "mainArea")
    val mainArea: QMainArea = QMainArea.mainArea

    fun findAll(): List<MainArea> {
        return selectFrom(mainArea).fetch()
    }
}