package com.emoldino.serenity.server.jpa.own.repository

import com.querydsl.core.types.dsl.PathBuilder
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.repository.Querydsl4RepositorySupport
import com.emoldino.serenity.server.jpa.own.entity.AgentDetail
import com.emoldino.serenity.server.jpa.own.entity.QAgentDetail
import kotlin.reflect.KClass


class AgentDetailRepository : Querydsl4RepositorySupport<AgentDetail>(Env.em, AgentDetail::class as KClass<Any>) {
  val path: PathBuilder<AgentDetail> = PathBuilder<AgentDetail>(AgentDetail::class.java, "agentDetail")
  val agentDetail: QAgentDetail = QAgentDetail.agentDetail

}
