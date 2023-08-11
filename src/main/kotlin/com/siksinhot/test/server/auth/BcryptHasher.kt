package com.siksinhot.test.server.auth

import com.siksinhot.test.exception.SessionNotFoundException
import org.mindrot.jbcrypt.BCrypt

object BcryptHasher {

  /**
   * Check if the password matches the User's password
   */
//  fun checkPassword(attempt: String, user: UserDto) = if (BCrypt.checkpw(attempt, user.password)) Unit
//  else throw SessionNotFoundException(null, "Wrong Password")

  /**
   * Returns the hashed version of the supplied password
   */
  fun hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

}
