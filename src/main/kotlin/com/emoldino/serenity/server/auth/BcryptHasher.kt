package com.emoldino.serenity.server.auth

import com.emoldino.serenity.exception.SessionNotFoundException
import com.emoldino.serenity.server.jpa.own.dto.UserDto
import org.mindrot.jbcrypt.BCrypt

object BcryptHasher {

  /**
   * Check if the password matches the User's password
   */
  fun checkPassword(attempt: String, user: UserDto) = if (BCrypt.checkpw(attempt, user.password)) Unit
  else throw SessionNotFoundException(null, "Wrong Password")

  /**
   * Returns the hashed version of the supplied password
   */
  fun hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

}
