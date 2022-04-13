package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.CompanyMember
import com.emoldino.serenity.server.jpa.own.entity.QCompanyMember
import kotlin.reflect.KClass


class CompanyMemberRepository : Querydsl4RepositorySupport<CompanyMember>(Env.em, CompanyMember::class as KClass<Any>) {
  val path: PathBuilder<CompanyMember> = PathBuilder<CompanyMember>(CompanyMember::class.java, "companyMember")
  val companyMember: QCompanyMember = QCompanyMember.companyMember

  fun findByUuid(uuid: String): CompanyMember? {
    return selectFrom(companyMember).where(companyMember.id.eq(uuid)).fetchOne()
  }

  fun findByCoIdAndMbId(coId: String, mbId: String): MutableList<CompanyMember>? {
    return selectFrom(companyMember)
      .where(
        companyMember.coId.eq(coId)
          .and(
            companyMember.mbId.eq(mbId)
          )
      ).fetch() as MutableList<CompanyMember>?
  }

}
