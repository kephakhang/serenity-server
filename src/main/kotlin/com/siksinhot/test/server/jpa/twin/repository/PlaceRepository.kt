package com.siksinhot.test.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.*
import java.util.*
import kotlin.reflect.KClass

class PlaceRepository : Querydsl4RepositorySupport<Place>(Env.em, Place::class as KClass<Any>) {
    val path: PathBuilder<Place> = PathBuilder<Place>(Place::class.java, "place")
    val place: QPlace = QPlace.place
    
    fun findById(id: Long): Place? {
        return select().where(place.id.eq(id)).fetchOne()
    }
}
