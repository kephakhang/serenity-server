package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.extensions.sha256
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.common.repository.WhereBuilder
import com.emoldino.serenity.server.jpa.own.entity.Tenant
import com.emoldino.serenity.server.jpa.own.entity.QTenant
import javax.persistence.TypedQuery
import kotlin.reflect.KClass

class TenantRepository : Querydsl4RepositorySupport<Tenant>(Env.em, Tenant::class as KClass<Any>) {
  val path: PathBuilder<Tenant> = PathBuilder<Tenant>(Tenant::class.java, "tenant")
  val tenant: QTenant = QTenant.tenant


  fun findById(uuid: String): Tenant? {
    return selectFrom(tenant).where(tenant.id.eq(uuid)).fetchOne()
  }

  fun findAll(): List<Tenant> {
    return selectFrom(tenant).where(tenant.enable.isTrue).fetch()
  }
}
