package com.emoldino.serenity.server.service

import com.emoldino.serenity.exception.EmolException
import com.emoldino.serenity.exception.ErrorCode
import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.extensions.isEmail
import com.emoldino.serenity.extensions.sha256
import com.emoldino.serenity.server.auth.BcryptHasher
import com.emoldino.serenity.server.auth.JwtConfig
import com.emoldino.serenity.server.jpa.own.dto.LoginDto
import com.emoldino.serenity.server.jpa.own.dto.SignupDto
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import com.emoldino.serenity.server.jpa.own.entity.Admin
import com.emoldino.serenity.server.jpa.own.enum.AdminStatus
import com.emoldino.serenity.server.jpa.own.enum.UserLevel
import com.emoldino.serenity.server.jpa.own.repository.AdminRepository
import java.time.Clock
import java.time.LocalDateTime

class AdminService(val db: AdminRepository) {

  @Throws(Exception::class)
  fun login(credentials: LoginDto): UserDto = credentials.let { (id, password) ->
    val admin = if (id.isEmail()) {
      db.findByEmail(id) ?: throw SessionNotFoundException(null, "User Not Found")
    } else {
      throw SessionNotFoundException(null, "User Not Found")
    }
    val user: UserDto = admin.toUserDto()
    BcryptHasher.checkPassword(password, user)
    user.copy(jwt = JwtConfig.makeToken(user))
    return user
  }

  fun register(signup: SignupDto): UserDto {
    var admin = Admin()
    val now = LocalDateTime.now(Clock.systemUTC())
    admin.apply {
      amEmailHash = signup.email.sha256()
      amName = signup.name
      amPassword = BcryptHasher.hashPassword(signup.password)
      amLevel = UserLevel.ADMIN.no
      amStatus = AdminStatus.CERTIFIED.no
      regDatetime = now
      modDatetime = now
    }

    db.transaction {
      admin = db.insert(admin) ?: throw EmolException(ErrorCode.E10003)
    }
    return admin.toUserDto()
  }

  fun updateAdmin(new: UserDto, current: UserDto): UserDto? {

    val curMember = db.findByUuid(new.uuid!!) ?: throw SessionNotFoundException(null, "Not Found User")
    var update = when {
      new.password != null -> new.copy(uuid = curMember.id.toString(), password = BcryptHasher.hashPassword(new.password))
      else -> new.copy(uuid = curMember.id.toString())
    }

    db.transaction {
      update = db.update(update.toAdmin()).toUserDto()
    }
    return update
  }
}

fun main() {
  print(BcryptHasher.hashPassword("osk3s#M!5ZYg-cz-h8uPH"))
}
