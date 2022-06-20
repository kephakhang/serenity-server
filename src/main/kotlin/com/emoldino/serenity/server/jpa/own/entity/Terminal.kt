package com.emoldino.serenity.server.jpa.own.entity

//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import com.emoldino.serenity.common.DateUtil
import com.emoldino.serenity.common.LocalDateTimeSerializer
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.entity.BaseEntity
import com.emoldino.serenity.server.jpa.own.dto.TenantDto
import com.emoldino.serenity.server.jpa.own.dto.TerminalDto
import com.fasterxml.jackson.annotation.JsonInclude
import org.eclipse.jdt.internal.compiler.parser.Parser.name
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "Terminal")
@Table(name = Env.tablePrefix + "terminal")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Terminal (

  //Terimainl.Id should be lower than 16 bytes : H/W Team suggestion
  @Column(name = "country_id", nullable = false)
  var countryId: String = "",

  @Column(name = "tr__version", nullable = false)
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
      countryCode = countryId,
      version = trVersion,
      status = trStatus,
      ip = trIp,
      regDatetime =  DateUtil.localDatetimeToStr(regDatetime),
      modDatetime = DateUtil.localDatetimeToStr(modDatetime)
    )
  }
}
