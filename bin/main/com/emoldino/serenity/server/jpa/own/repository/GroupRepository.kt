package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.Group
import com.emoldino.serenity.server.jpa.own.entity.QGroup
import kotlin.reflect.KClass


class GroupRepository : Querydsl4RepositorySupport<Group>(Env.em, Group::class as KClass<Any>) {
  val path: PathBuilder<Group> = PathBuilder<Group>(Group::class.java, "group")
  val group: QGroup = QGroup.group


  fun findByUuid(uuid: String): Group? {
    return selectFrom(group).where(group.id.eq(uuid)).fetchOne()
  }
}
