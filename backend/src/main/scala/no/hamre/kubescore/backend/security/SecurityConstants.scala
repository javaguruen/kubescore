package no.hamre.kubescore.backend.security

object SecurityConstants {
  val SECRET = "SecretKeyToGenJWTs"
  val EXPIRATION_TIME: Long = 864000000 // 10 days

  val TOKEN_PREFIX = "Bearer "
  val HEADER_STRING = "Authorization"
  val SIGN_UP_URL = "/users/sign-up"
}
