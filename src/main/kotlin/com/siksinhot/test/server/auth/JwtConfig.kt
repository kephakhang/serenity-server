package com.siksinhot.test.server.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {

  const val authHeader = "Authorization"
  private const val secret = "xE1x1o1x8qflc1iYtcRd"
  private const val subject = "sso:::authentication"
  private const val issuer = "siksinhot.com"
  private const val audience = "siksinhot:::web:::mobile:::client"
  const val realm = issuer
  private const val validityInMs = 36_000_000 // 10 hours
  private val algorithm = Algorithm.HMAC256(secret)

  val verifier: JWTVerifier = JWT
    .require(algorithm)
    .withAudience(audience)
    .withIssuer(issuer)
    .build()

  /**
   * Produce a token for this combination of User and Account
   */
//  fun makeToken(user: UserDto, jti: String? = null): String = JWT.create()
//    .withSubject(subject)
//    .withAudience(audience)
//    .withIssuer(issuer)
//    .withJWTId(if (jti == null) UUID.randomUUID().toString() else jti)
//    .withClaim("uid", user.id)
//    .withClaim("name", user.name)
//    .withClaim("level", user.level)
//    .withClaim("status", user.status)
//    .withExpiresAt(getExpiration())
//    .sign(algorithm)

  /**
   * Calculate the expiration Date based on current time + the given validity
   */
  private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)

}
