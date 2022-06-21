package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.extensions.sha256
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.common.repository.WhereBuilder
import com.emoldino.serenity.server.jpa.own.entity.Member
import com.emoldino.serenity.server.jpa.own.entity.QMember
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

class MemberRepository : Querydsl4RepositorySupport<Member>(Env.em, Member::class as KClass<Any>) {
  val path: PathBuilder<Member> = PathBuilder<Member>(Member::class.java, "member")
  val member: QMember = QMember.member


  fun findById(id: String): Member? {
    return selectFrom(member).where(member.id.eq(id)).fetchOne()
  }

  fun findByEmail(email: String): Member? {
    return selectFrom(member).where(member.mbEmailHash.eq(email.sha256())).fetchOne()
  }

  fun findByMobile(mobile: String): Member? {
    val m = mobile.replace("-", "")
    return selectFrom(member).where(member.mbMobileHash.eq(m.sha256())).fetchOne()
  }

  fun findAll(offset: Long, limit: Long): List<Member> {
    return selectFrom(member).offset(offset).limit(limit).fetch()
  }
}
