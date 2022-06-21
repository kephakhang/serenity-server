package com.emoldino.serenity.server.jpa.own.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.common.entity.BaseEntity
import javax.persistence.*

@Entity(name = "CompanyMember")
@Table(name = Env.tablePrefix + "company_member")
@JsonInclude(JsonInclude.Include.NON_NULL)
open class CompanyMember(


  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "co_id", insertable = false, updatable = false)
  open var company: Company? = null,


  /**
   * 가입회사 대행사 고유 ID
   */
//  @ApiModelProperty("가입회사 대행사 고유 ID")
  @Column(name = "co_id", nullable = true, updatable = false)
  var coId: String = "",


  @JsonIgnore
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mb_id", insertable = false, updatable = false)
  var member: Member? = null,

  /**
   * 가입회사 사용자 ID
   */
//  @ApiModelProperty("사용자 ID")
  @Column(name = "mb_id", nullable = true, updatable = false)
  var mbId: String = "",

  /**
   * OTP 암호
   */
  @Column(name = "cm_otp")
//  @ApiModelProperty("OTP 암호")
  var cmOtp: String? = null,

  /**
   * 상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:company-otp-ceritified)
   */
  @Column(name = "cm_status", nullable = false)
//  @ApiModelProperty("상태 flag(-3: blocked, -2: withdrawal, -1: dormant, 0:wait, 1:company-otp-ceritified)")
  var cmStatus: Int = 0,

  /**
   * 레벨 flag(0:guest, 10:co member, 11:co manager, 12:co admin)
   */
  @Column(name = "cm_level", nullable = false)
//  @ApiModelProperty("레벨 flag(0:guest, 10:co member, 11:co manager, 12:co admin)")
  var cmLevel: Int = 10,

  ) : BaseEntity() {
  fun copy(company: Company) {
    this.company = company
  }
}
