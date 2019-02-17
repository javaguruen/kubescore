package no.hamre.kubescore.backend.auth

import java.security.SecureRandom
import java.util.Base64

import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator

object PasswordEncrypter {
  def encryptPassword(plainText: String): String = {
    import org.bouncycastle.crypto.params.KeyParameter
    // tuning parameters// tuning parameters

    // these sizes are relatively arbitrary
    val seedBytes = 20
    val hashBytes = 20

    // increase iterations as high as your performance can tolerate
    // since this increases computational cost of password guessing
    // which should help security
    val iterations = 1000

    // to save a new password:

    val rng = new SecureRandom()
    val salt = rng.generateSeed(seedBytes)

    val kdf = new PKCS5S2ParametersGenerator()
    kdf.init(plainText.getBytes("UTF-8"), salt, iterations)

    val hash = kdf.generateDerivedMacParameters(8 * hashBytes).asInstanceOf[KeyParameter].getKey
    Base64.getEncoder.encodeToString(hash)
/*
    // now save salt and hash

    // to check a password, given the known previous salt and hash:

    kdf = new Nothing
    kdf.init(passwordToCheck.getBytes("UTF-8"), salt, iterations)

    val hashToCheck = kdf.generateDerivedMacParameters(8 * hashBytes).asInstanceOf[KeyParameter].getKey

    // if the bytes of hashToCheck don't match the bytes of hash
    // that means the password is invalid
*/
  }
}
