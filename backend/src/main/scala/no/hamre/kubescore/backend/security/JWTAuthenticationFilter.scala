package no.hamre.kubescore.backend.security

import java.util.Date

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.ObjectMapper
import javax.servlet.FilterChain
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import no.hamre.kubescore.backend.security.SecurityConstants.{EXPIRATION_TIME, HEADER_STRING, SECRET, TOKEN_PREFIX}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.{AuthenticationManager, UsernamePasswordAuthenticationToken}
import org.springframework.security.core.{Authentication, GrantedAuthority}
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

import scala.collection.JavaConverters._

class JWTAuthenticationFilter(@Autowired authenticationManager: AuthenticationManager) extends UsernamePasswordAuthenticationFilter {

  override def attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication = {
    val creds: ApplicationUser = new ObjectMapper()
      .readValue(req.getInputStream, classOf[ApplicationUser])

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
      creds.username,
      creds.password,
      List[GrantedAuthority]().asJava)
    )
  }

  override protected def successfulAuthentication(req: HttpServletRequest,
                                                  res: HttpServletResponse,
                                                  chain: FilterChain,
                                                  auth: Authentication): Unit = {

    val token: String = JWT.create()
      .withSubject(auth.getPrincipal.asInstanceOf[ApplicationUser].username)
      .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .sign(Algorithm.HMAC512(SECRET.getBytes()))

    res.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
  }
}