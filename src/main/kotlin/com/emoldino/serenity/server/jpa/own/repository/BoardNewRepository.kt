package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.Board
import com.emoldino.serenity.server.jpa.own.entity.BoardNew
import com.emoldino.serenity.server.jpa.own.entity.QBoardNew
import kotlin.reflect.KClass


class BoardNewRepository : Querydsl4RepositorySupport<BoardNew>(Env.em, Board::class as KClass<Any>) {
  val path: PathBuilder<BoardNew> = PathBuilder<BoardNew>(BoardNew::class.java, "boardNew")
  val boardNew: QBoardNew = QBoardNew.boardNew


  fun findById(id: String): BoardNew? {
    return selectFrom(boardNew).where(boardNew.id.eq(id)).fetchOne()
  }

}
