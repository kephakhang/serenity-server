package com.emoldino.serenity.server.jpa.test.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.test.entity.Page
import com.emoldino.serenity.server.jpa.test.entity.QPage
import kotlin.reflect.KClass


class PageRepository : Querydsl4RepositorySupport<Page>(Env.em, Page::class as KClass<Any>) {
  val path: PathBuilder<Page> = PathBuilder<Page>(Page::class.java, "page")
  val page: QPage = QPage.page


  fun findBySeqNo(seqNo: Long): Page? {
    return selectFrom(page).where(page.seqNo.eq(seqNo)).fetchOne() as Page?
  }


}
