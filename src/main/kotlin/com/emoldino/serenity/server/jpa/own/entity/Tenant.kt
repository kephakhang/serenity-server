package com.emoldino.serenity.server.jpa.own.entity

//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import com.emoldino.serenity.common.LocalDateTimeSerializer
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.entity.BaseEntity
import com.emoldino.serenity.server.jpa.own.dto.TenantDto
import com.fasterxml.jackson.annotation.JsonInclude
import org.eclipse.jdt.internal.compiler.parser.Parser.name
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "Tenant")
//@ApiModel("Tenant")
@Table(name = Env.tablePrefix + "tenant")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class Tenant (

  /**
   * 고유 ID(UUID)
   */
//  @ApiModelProperty("고유 UUID")
  @Id
  @Column(name = "id", columnDefinition = "char(36)")
  var id: String? = null,

  @Column(name = "name", nullable = false)
  var name: String = "",

  //  @ApiModelProperty("tenant type (0:unknown, 1:OEM, 2:MoldMaker, 3:Supply)")
  @Column(name = "type", nullable = false)
  var type: Int = 0,

  @Column(name = "description", nullable = true)
  var description: String = "",

  @Column(name = "jdbc_host", nullable = true)
  var jdbcHost: String? = null,

  @Column(name = "jdbc_user", nullable = true)
  var jdbcUser: String? = null,

  @Column(name = "jdbc_pass", nullable = true)
  var jdbcPass: String? = null,

  @Column(name = "country_id", nullable = false)
  var countryId: String = "",

  @Column(name = "host_url", nullable = false)
  var hostUrl: String = "",

  @Column(name = "enable", columnDefinition = "BIT", nullable = false)
//  @Type(type = "org.hibernate.type.NumericBooleanType")
  var enable: Boolean = true,

  /**
   * 등록시각
   */
//  @ApiModelProperty("등록시각")
  @CreationTimestamp
  @Convert(converter  = LocalDateTimeSerializer::class)
  @Column(name = "reg_datetime", columnDefinition = "DATETIME", nullable = false, updatable = false)
  var regDatetime: LocalDateTime = LocalDateTime.MIN,

  /**
   * 변경시각
   */
//  @ApiModelProperty("수정시각")
  @UpdateTimestamp
  @Convert(converter  = LocalDateTimeSerializer::class)
  @Column(name = "mod_datetime", columnDefinition = "DATETIME", nullable = false)
  var modDatetime: LocalDateTime = LocalDateTime.MIN

)  {

  fun toTenantDto(): TenantDto {
    return TenantDto(
      id = id,
      name = name,
      type = type,
      description = description,
      hostUrl = hostUrl,
      countryCode = countryId,
      regDatetime =  regDatetime,
      modDatetime = modDatetime
    )
  }
}
