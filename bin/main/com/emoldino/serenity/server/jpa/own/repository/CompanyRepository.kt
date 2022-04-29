package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.Company
import com.emoldino.serenity.server.jpa.own.entity.QCompany
import kotlin.reflect.KClass


class CompanyRepository : Querydsl4RepositorySupport<Company>(Env.em, Company::class as KClass<Any>) {
  val path: PathBuilder<Company> = PathBuilder<Company>(Company::class.java, "company")
  val company: QCompany = QCompany.company

  fun findByUuid(uuid: String): Company? {
    return selectFrom(company).where(company.id.eq(uuid)).fetchOne()
  }

}
