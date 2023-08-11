package com.siksinhot.test.cell.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.Offer
import com.siksinhot.test.server.jpa.twin.entity.*
import java.util.*
import kotlin.reflect.KClass

class OfferRepository : Querydsl4RepositorySupport<Offer>(Env.em, Offer::class as KClass<Any>) {
    val path: PathBuilder<Offer> = PathBuilder<Offer>(Offer::class.java, "offer")
    val offer: QOffer = QOffer.offer
    fun findById(id: Long): Offer? {
        return selectFrom(offer).where(offer.id.eq(id)).fetchOne()
    }
}
