package no.hamre.kubescore.backend.auth

import org.mindrot.jbcrypt.BCrypt

object PasswordEncrypter {

  def hashPassword(plainText: String) = BCrypt.hashpw(plainText, BCrypt.gensalt())

  def checkPassword(plainText: String, hashed: String) = BCrypt.checkpw(plainText, hashed)

}
