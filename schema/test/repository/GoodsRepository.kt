package com.emoldino.serenity.server.jpa.test.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.test.entity.Goods
import com.emoldino.serenity.server.jpa.test.entity.QGoods
import kotlin.reflect.KClass


class GoodsRepository : Querydsl4RepositorySupport<Goods>(Env.em, Goods::class as KClass<Any>) {
  val path: PathBuilder<Goods> = PathBuilder<Goods>(Goods::class.java, "goods")
  val goods: QGoods = QGoods.goods


  fun findBySeqNo(seqNo: Long): Goods? {
    return selectFrom(goods).where(goods.seqNo.eq(seqNo)).fetchOne() as Goods?
  }

}
