package com.emoldino.serenity.server.jpa.own.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.entity.BaseEntity
import com.emoldino.serenity.server.jpa.own.dto.TenantDto
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
//import io.swagger.annotations.ApiModel
//import io.swagger.annotations.ApiModelProperty
import javax.persistence.*

@Entity
//@ApiModel("Tenant")
@Table(name = Env.tablePrefix + "tenant")
@JsonInclude(JsonInclude.Include.NON_NULL)
class Tenant (

  /**
   * 고유 ID(UUID)
   */
//  @ApiModelProperty("고유 UUID")
  @Id
  @GeneratedValue(generator = "id")
  @GenericGenerator(name = "id", strategy = "com.emoldino.serenity.server.jpa.own.id.UuidGenerator")
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

  @Column(name = "host_url", nullable = true)
  var hostUrl: String? = null,

  @Column(name = "enable", nullable = false)
  var enable: Boolean = true,

  /**
   * 등록시각
   */
//  @ApiModelProperty("등록시각")
  @CreationTimestamp
  @Column(name = "reg_datetime", nullable = false, updatable = false)
  var regDatetime: LocalDateTime,

  /**
   * 변경시각
   */
//  @ApiModelProperty("수정시각")
  @UpdateTimestamp
  @Column(name = "mod_datetime", nullable = false)
  var modDatetime: LocalDateTime

)  : Serializable {

  fun toTenantDto(): TenantDto {
    return TenantDto(
      id = id,
      name = name,
      description = description,
      countryCode = countryId,
      regDatetime =  regDatetime,
      modDatetime = modDatetime
    )
  }
}
