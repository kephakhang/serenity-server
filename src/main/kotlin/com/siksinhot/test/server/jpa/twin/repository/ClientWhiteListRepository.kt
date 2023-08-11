package com.siksinhot.test.server.jpa.twin.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.siksinhot.test.server.env.Env
import com.siksinhot.test.server.jpa.common.repository.Querydsl4RepositorySupport
import com.siksinhot.test.server.jpa.twin.entity.*
import kotlin.reflect.KClass


class ClientWhiteListRepository : Querydsl4RepositorySupport<ClientWhiteList>(Env.em, ClientWhiteList::class as KClass<Any>) {
    val path: PathBuilder<ClientWhiteList> = PathBuilder<ClientWhiteList>(ClientWhiteList::class.java, "clientWhiteList")
    val clientWhiteList: QClientWhiteList = QClientWhiteList.clientWhiteList
    fun findByIp(ip: String): ClientWhiteList? {
        return selectFrom(clientWhiteList).where(clientWhiteList.ip.eq(ip)).fetchOne()
    }

//    @Query("SELECT awl FROM ClientIp awl ORDER BY awl.description ASC")
//    fun findAllWithPaging(pageRequest: Pageable): Page<ClientIp>

    fun findAllByActiveEqualsAndIp(active: Boolean, ip: String): ClientWhiteList? {
        return selectFrom(clientWhiteList).where(clientWhiteList.active.eq(true).and(clientWhiteList.ip.eq(ip))).fetchOne()
    }
}