package no.hamre.kubescore.backend.security

import java.util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import javax.servlet.FilterChain
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import no.hamre.kubescore.backend.security.SecurityConstants.{HEADER_STRING, SECRET, TOKEN_PREFIX}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.{AuthenticationManager, UsernamePasswordAuthenticationToken}
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

class JWTAuthorizationFilter(@Autowired authManager: AuthenticationManager) extends BasicAuthenticationFilter(authManager) {

  override protected def doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain): Unit = {
    val header: String = req.getHeader(HEADER_STRING)

    if (header == null || !header.startsWith(TOKEN_PREFIX)) {
      chain.doFilter(req, res)
      return
    }

    val authentication: UsernamePasswordAuthenticationToken = getAuthentication(req)
    SecurityContextHolder.getContext.setAuthentication(authentication)
    chain.doFilter(req, res)
  }

  private def getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken = {
    val token = request.getHeader(HEADER_STRING)
    if (token != null) { // parse the token.
      val user = JWT.require(Algorithm.HMAC512(SECRET.getBytes)).build.verify(token.replace(TOKEN_PREFIX, "")).getSubject
      if (user != null) return new UsernamePasswordAuthenticationToken(user, null, new util.ArrayList[GrantedAuthority]())
      return null
    }
    null
  }
}
