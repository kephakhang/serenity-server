package com.emoldino.serenity.server.service

import com.sultanofcardio.models.Email
import com.emoldino.serenity.exception.EmolException
import com.emoldino.serenity.exception.ErrorCode
import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.extensions.isEmail
import com.emoldino.serenity.extensions.isPhoneNumber
import com.emoldino.serenity.extensions.sha256
import com.emoldino.serenity.server.auth.BcryptHasher
import com.emoldino.serenity.server.auth.JwtConfig
import com.emoldino.serenity.server.env.Env
import com.emoldino.serenity.server.jpa.own.dto.LoginDto
import com.emoldino.serenity.server.jpa.own.dto.SignupDto
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.jpa.own.entity.Member
import com.emoldino.serenity.server.jpa.own.entity.MemberDetail
import com.emoldino.serenity.server.jpa.own.enum.UserLevel
import com.emoldino.serenity.server.jpa.own.enum.UserStatus
import com.emoldino.serenity.server.jpa.own.repository.MemberDetailRepository
import com.emoldino.serenity.server.jpa.own.repository.MemberRepository
import mu.KotlinLogging
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset

private val logger = KotlinLogging.logger {}

class SsoService(val db: MemberRepository, val detailDB: MemberDetailRepository) {

  @Throws(Exception::class)
  fun login(credentials: LoginDto): UserDto = credentials.let {
    val member = if (it.id.isEmail()) {
      db.findByEmail(it.id) ?: throw SessionNotFoundException(null, "User Not Found")
    } else if (it.id.isPhoneNumber()) {
      db.findByMobile(it.id) ?: throw SessionNotFoundException(null, "User Not Found")
    } else {
      throw SessionNotFoundException(null, "User Not Found")
    }
    val user = member.toUserDto()
    BcryptHasher.checkPassword(it.password, user)
    val jwt = JwtConfig.makeToken(user)
    user.jwt = jwt
    return user
  }

  @Throws(Exception::class)
  fun checkAvailable(loginId: String): Boolean {
    return if (loginId.isEmail()) {
      if (db.findByEmail(loginId) != null) false else true
    } else if (loginId.isPhoneNumber()) {
      if (db.findByMobile(loginId) != null) false else true
    } else {
      false
    }
  }


  @Throws(Exception::class)
  fun register(signup: SignupDto): UserDto {
    var member = Member()
    member.apply {
      mbEmailHash = signup.email.sha256()
      mbMobileHash = signup.mobile.sha256()
      mbName = signup.name
      mbPassword = BcryptHasher.hashPassword(signup.password)
      mbLevel = UserLevel.USER.no
      mbLevel = UserStatus.WAIT.no
    }

    var memberDetail = MemberDetail()
    db.transaction {
      member = db.insert(member) ?: throw EmolException(ErrorCode.E10003)
      memberDetail.apply {
        mbId = member.id!!
        mbEmail = signup.email
        mbMobile = signup.mobile
        mbEmailCertify = LocalDateTime.now(Clock.systemUTC())
        mbEmailCertify2 = com.emoldino.serenity.common.KeyGenerator.generateMobileAuth()
      }
      memberDetail = detailDB.insert(memberDetail)
      member.detail = memberDetail
    }

    val content = Env.confirmEmailContent.replace("{{action_url}}", Env.apiHostUrl + "/sso/confirm/email?id=${member.id}&confirm=${memberDetail.mbEmailCertify2}")
    Env.mailSender.sendEmail(Email(Env.email, "[eMoldino] Thank you for singup ::: Email Certification", content, memberDetail.mbEmail!!))
    return member.toUserDto()
  }

  @Throws(Exception::class)
  fun confirmEmail(uid: String, confirm: String): String? {
    try {
      val member = db.findByUuid(uid) ?: return "User Not Found"
      val now = Env.now()
      if ((now.toEpochSecond(ZoneOffset.UTC) - member.detail?.mbEmailCertify!!.toEpochSecond(ZoneOffset.UTC)) > Env.confirmExpireTime) {
        return "Confirmation time was expired !!!"
      } else if (member.detail?.mbEmailCertify2 != confirm) {
        return "Confirmation code is wrong : Please find the confirmation email in your email inbox"
      } else {
        member.mbStatus =
          if (member.mbStatus === UserStatus.MOBILE_ONLY.no) UserStatus.BOTH.no else UserStatus.EMAIL_ONLY.no
        db.transaction {
          db.update(member)
        }
        return null //정상
      }
    } catch(ex: Exception) {
      logger.error("sso.confirmEmail","Unknown Error : ${ex}")
      return ex.localizedMessage
    }
  }

  fun updateUser(new: UserDto, current: UserDto): UserDto? {

    val curMember = db.findByUuid(new.uuid!!) ?: throw SessionNotFoundException(null, "Not Found User")
    var update = when {
      new.password != null -> new.copy(uuid = curMember.id.toString(), password = BcryptHasher.hashPassword(new.password))
      else -> new.copy(uuid = curMember.id.toString())
    }

    db.transaction {
      update = db.update(update.toMember()).toUserDto()
    }
    return update
  }
}

fun main() {
  print(BcryptHasher.hashPassword("osk3s#M!5ZYg-cz-h8uPH"))
}
