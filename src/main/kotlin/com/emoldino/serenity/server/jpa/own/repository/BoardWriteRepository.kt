package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.BoardWrite
import com.emoldino.serenity.server.jpa.own.entity.QBoardWrite
import kotlin.reflect.KClass


class BoardWriteRepository : Querydsl4RepositorySupport<BoardWrite>(Env.em, BoardWrite::class as KClass<Any>) {
  val path: PathBuilder<BoardWrite> = PathBuilder<BoardWrite>(BoardWrite::class.java, "boardWrite")
  val boardWrite: QBoardWrite = QBoardWrite.boardWrite


  fun findById(id: String): BoardWrite? {
    return selectFrom(boardWrite).where(boardWrite.id.eq(id)).fetchOne()
  }

}
