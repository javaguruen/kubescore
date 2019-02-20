package no.hamre.kubescore.backend.security

import javax.ws.rs.HttpMethod
import no.hamre.kubescore.backend.security.SecurityConstants.SIGN_UP_URL
import no.hamre.kubescore.backend.service.PersonService
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.{CorsConfiguration, CorsConfigurationSource, UrlBasedCorsConfigurationSource}

@EnableWebSecurity
class WebSecurity(userDetailsService: UserDetailsService, bCryptPasswordEncoder: BCryptPasswordEncoder)
  extends WebSecurityConfigurerAdapter {

  override protected def configure(http: HttpSecurity): Unit = {
    http.cors().and().csrf().disable().authorizeRequests()
      .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
      .anyRequest().authenticated()
      .and()
      .addFilter(new JWTAuthenticationFilter(authenticationManager()))
      .addFilter(new JWTAuthorizationFilter(authenticationManager()))
      // this disables session creation on Spring Security
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }

  override def configure(auth: AuthenticationManagerBuilder): Unit = {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
  }

  @Bean
  def corsConfigurationSource(): CorsConfigurationSource = {
    val source: UrlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues())
    source
  }
}