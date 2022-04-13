package com.emoldino.serenity.server.jpa.own.dto

import com.emoldino.serenity.server.jpa.own.entity.MemberDetail
import java.time.LocalDate
import java.time.LocalDateTime

data class UserDetailDto(
  val mobile: String? = null,
  val email: String? = null,
  val image: String? = null,
  val homepage: String? = null,
  val gender: String? = null,
  val birthday: LocalDate? = null,
  val adult: Boolean = false,
  val married: Boolean = false,
  val zip: String? = null,
  val addr1: String? = null,
  val addr2: String? = null,
  val addr3: String? = null,
  val addrJibeon: String? = null,
  val latitude: Double? = null,
  val longitude: Double? = null,
  val signature: String? = null,
  val recommend: String? = null,
  val todayLogin: LocalDateTime? = null,
  val loginIp: String? = null,
  val ip: String? = null,
  val leaveDate: LocalDate? = null,
  val interceptDate: LocalDate? = null,
  val emailCertify: LocalDateTime? = null,
  val emailCertify2: String? = null,
  val mobileCertify: LocalDateTime? = null,
  val mobileCertify2: String? = null,
  val memo: String? = null,
  val lostCertify: String? = null,
  val mailling: Boolean = false,
  val sms: Boolean = false,
  val open: Boolean = false,
  val openDate: LocalDate? = null,
  val greeting: String? = null,
  val memoCall: String? = null,
  val memoCnt: Int? = 0,
  val scrapCnt: Int? = 0,
  val ext1: String? = null,
  val ext2: String? = null,
  val ext3: String? = null,
  val ext4: String? = null,
  val ext5: String? = null,
  val ext6: String? = null,
  val ext7: String? = null,
  val ext8: String? = null,
  val ext9: String? = null,
  val ext10: String? = null
) {

  fun toMemberDetail(): MemberDetail {
    val memberDetail = MemberDetail()
    return memberDetail.apply {
      mbEmail = email
      mbMobile = mobile
      mbImage = image
      mbHomepage = homepage
      mbGender = gender
      mbBirthday = birthday
      mbAdult = adult
      mbMarried = married
      mbZip = zip
      mbAddr1 = addr1
      mbAddr2 = addr2
      mbAddr3 = addr3
      mbAddrJibeon = addrJibeon
      mbSignature = signature
      mbRecommend = recommend
      mbTodayLogin = todayLogin
      mbLoginIp = loginIp
      mbIp = ip
      mbLeaveDate = leaveDate
      mbInterceptDate = interceptDate
      mbEmailCertify = emailCertify
      mbEmailCertify2 = emailCertify2
      mbMemo = memo
      mbLostCertify = lostCertify
      mbMailling = mailling
      mbSms = sms
      mbOpen = open
      mbOpenDate = openDate
      mbGreeting = greeting
      mbMemoCall = memoCall
      mbMemoCnt = memoCnt
      mbScrapCnt = scrapCnt
      mb1 = ext1
      mb2 = ext2
      mb3 = ext3
      mb4 = ext4
      mb5 = ext5
      mb6 = ext6
      mb7 = ext7
      mb8 = ext8
      mb9 = ext9
      mb10 = ext10
    }
  }
}

class UserDetailWrapper(val userDetail: UserDetailDto)
