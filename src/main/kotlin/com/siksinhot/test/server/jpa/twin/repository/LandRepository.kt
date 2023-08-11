package com.siksinhot.test.cell.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.*
import java.util.*
import kotlin.reflect.KClass


class LandRepository : Querydsl4RepositorySupport<Land>(Env.em, Land::class as KClass<Any>) {
    val path: PathBuilder<Land> = PathBuilder<Land>(Land::class.java, "land")
    val land: QLand = QLand.land
}