package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.extensions.sha256
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.Admin
import com.emoldino.serenity.server.jpa.own.entity.QAdmin
import kotlin.reflect.KClass


class AdminRepository : Querydsl4RepositorySupport<Admin>(Env.em, Admin::class as KClass<Any>) {
  val path: PathBuilder<Admin> = PathBuilder<Admin>(Admin::class.java, "admin")
  val admin: QAdmin = QAdmin.admin


  fun findByUuid(uuid: String): Admin? {
    return selectFrom(admin).where(admin.id.eq(uuid)).fetchOne()
  }

  fun findByEmail(email: String): Admin? {
    return selectFrom(admin).where(admin.amEmailHash.eq(email.sha256())).fetchOne()
  }
}
