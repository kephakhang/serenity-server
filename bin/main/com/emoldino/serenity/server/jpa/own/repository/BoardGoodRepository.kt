package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.Board
import com.emoldino.serenity.server.jpa.own.entity.BoardGood
import com.emoldino.serenity.server.jpa.own.entity.QBoardGood
import kotlin.reflect.KClass


class BoardGoodRepository : Querydsl4RepositorySupport<BoardGood>(Env.em, Board::class as KClass<Any>) {
  val path: PathBuilder<BoardGood> = PathBuilder<BoardGood>(BoardGood::class.java, "boardGood")
  val boardGood: QBoardGood = QBoardGood.boardGood


  fun findByUuid(uuid: String): BoardGood? {
    return selectFrom(boardGood).where(boardGood.id.eq(uuid)).fetchOne()
  }

}
