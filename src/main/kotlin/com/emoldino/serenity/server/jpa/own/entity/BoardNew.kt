package com.emoldino.serenity.server.jpa.own.entity


import com.fasterxml.jackson.annotation.JsonInclude
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.entity.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity(name = "BoardNew")
@Table(name = Env.tablePrefix + "board_new")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class BoardNew(

  @Column(name = "bo_id", nullable = false)
  var boId: String = "",

  @Column(name = "wr_id", nullable = false)
  var wrId: String = "",

  @Column(name = "wr_parent", nullable = false)
  var wrParent: String = "",

  @Column(name = "mb_id", nullable = false)
  var mbId: String = ""

) : BaseEntity()
