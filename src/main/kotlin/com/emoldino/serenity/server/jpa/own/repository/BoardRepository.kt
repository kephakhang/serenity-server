package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.Board
import com.emoldino.serenity.server.jpa.own.entity.QBoard
import kotlin.reflect.KClass


class BoardRepository : Querydsl4RepositorySupport<Board>(Env.em, Board::class as KClass<Any>) {
  val path: PathBuilder<Board> = PathBuilder<Board>(Board::class.java, "board")
  val board: QBoard = QBoard.board


  fun findById(id: String): Board? {
    return selectFrom(board).where(board.id.eq(id)).fetchOne()
  }
}
