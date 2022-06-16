package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.common.repository.WhereBuilder
import com.emoldino.serenity.server.jpa.own.entity.Counter
import com.emoldino.serenity.server.jpa.own.entity.QCounter
import kotlin.reflect.KClass

class CounterRepository : Querydsl4RepositorySupport<Counter>(Env.em, Counter::class as KClass<Any>) {
  val path: PathBuilder<Counter> = PathBuilder<Counter>(Counter::class.java, "counter")
  val counter: QCounter = QCounter.counter


  fun findById(uuid: String): Counter? {
    return selectFrom(counter).where(counter.id.eq(uuid)).fetchOne()
  }

  fun findAll(tenantId: String?, terminalId: String?): List<Counter> {
    return selectFrom(counter).where(WhereBuilder()
              .optionalAnd(tenantId, { counter.teId.eq(tenantId) })
              .optionalAnd(tenantId, { counter.trId.eq(terminalId) })
    ).fetch()
  }
}
