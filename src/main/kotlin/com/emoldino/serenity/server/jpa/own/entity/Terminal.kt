package com.emoldino.serenity.server.jpa.own.entity

import com.emoldino.serenity.common.DateUtil
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.entity.BaseEntity
import com.emoldino.serenity.server.jpa.own.dto.TerminalDto
import com.fasterxml.jackson.annotation.JsonInclude
import javax.persistence.*

@Entity(name = "Terminal")
@Table(name = Env.tablePrefix + "terminal")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Terminal (

  //Terimainl.Id should be lower than 16 bytes : H/W Team suggestion

  @Column(name = "tr_version", nullable = false)
  var trVersion: Int = 3,

  //  @ApiModelProperty("terminal type (0:available, 1:installed)")
  @Column(name = "tr_status", nullable = false)
  var trStatus: Int = 0,

  @Column(name = "tr_ip", nullable = true)
  var trIp: String = "",

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "terminal", cascade = [CascadeType.ALL])
  var countList: MutableList<Counter> = arrayListOf()


): BaseEntity()  {

  fun toTerminalDto(): TerminalDto {
    return TerminalDto(
      id = id,
      tenantId = teId,
      version = trVersion,
      status = trStatus,
      ip = trIp,
      regDatetime =  DateUtil.localDatetimeToStr(regDatetime),
      modDatetime = DateUtil.localDatetimeToStr(modDatetime)
    )
  }
}
