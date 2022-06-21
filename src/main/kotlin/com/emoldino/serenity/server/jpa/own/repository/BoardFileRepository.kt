package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.Board
import com.emoldino.serenity.server.jpa.own.entity.BoardFile
import com.emoldino.serenity.server.jpa.own.entity.QBoardFile
import kotlin.reflect.KClass


class BoardFileRepository : Querydsl4RepositorySupport<BoardFile>(Env.em, Board::class as KClass<Any>) {
  val path: PathBuilder<BoardFile> = PathBuilder<BoardFile>(BoardFile::class.java, "boardFile")
  val boardFile: QBoardFile = QBoardFile.boardFile


  fun findById(id: String): BoardFile? {
    return selectFrom(boardFile).where(boardFile.id.eq(id)).fetchOne()
  }

}
