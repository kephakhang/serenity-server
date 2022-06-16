package com.emoldino.serenity.server.jpa.own.entity

//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import com.emoldino.serenity.common.DateUtil
import com.emoldino.serenity.common.LocalDateTimeSerializer
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.entity.BaseEntity
import com.emoldino.serenity.server.jpa.own.dto.CounterDto
import com.emoldino.serenity.server.jpa.own.dto.TenantDto
import com.emoldino.serenity.server.jpa.own.dto.TerminalDto
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.eclipse.jdt.internal.compiler.parser.Parser.name
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "Counter")
@Table(name = Env.tablePrefix + "counter")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Counter (

  //Counter.Id should be 13 bytes : H/W Specification

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tr_id", insertable = false, updatable = false)
  var terminal: Terminal? = null,


  @Column(name = "tr_id", nullable = false)
  var trId: String? = "",


  @Column(name = "co__version", nullable = false)
  var coVersion: Int = 3,

  //  @ApiModelProperty("terminal type (0:available, 1:installed")
  @Column(name = "co_status", nullable = false)
  var coStatus: Int = 0,

): BaseEntity()  {

  fun toCounterDto(): CounterDto {
    return CounterDto(
      id = id,
      tenantId = teId,
      terminalId = trId,
      version = coVersion,
      status = coStatus,
      regDatetime =  DateUtil.localDatetimeToStr(regDatetime),
      modDatetime = DateUtil.localDatetimeToStr(modDatetime)
    )
  }
}
