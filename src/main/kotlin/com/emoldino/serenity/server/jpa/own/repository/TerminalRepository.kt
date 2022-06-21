package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.common.repository.WhereBuilder
import com.emoldino.serenity.server.jpa.own.entity.Terminal
import com.emoldino.serenity.server.jpa.own.entity.QTerminal
import kotlin.reflect.KClass

class TerminalRepository : Querydsl4RepositorySupport<Terminal>(Env.em, Terminal::class as KClass<Any>) {
  val path: PathBuilder<Terminal> = PathBuilder<Terminal>(Terminal::class.java, "terminal")
  val terminal: QTerminal = QTerminal.terminal


  fun findById(id: String): Terminal? {
    return selectFrom(terminal).where(terminal.id.eq(id)).fetchOne()
  }

  fun findAll(tenantId: String?): List<Terminal> {
    return selectFrom(terminal).where(WhereBuilder()
              .optionalAnd(tenantId, { terminal.teId.eq(tenantId) })
            ).fetch()
  }
}
