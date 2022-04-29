package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.MemberDetail
import com.emoldino.serenity.server.jpa.own.entity.QMemberDetail
import kotlin.reflect.KClass


class MemberDetailRepository : Querydsl4RepositorySupport<MemberDetail>(Env.em, MemberDetail::class as KClass<Any>) {
  val path: PathBuilder<MemberDetail> = PathBuilder<MemberDetail>(MemberDetail::class.java, "memberDetail")
  val memberDetail: QMemberDetail = QMemberDetail.memberDetail
}
