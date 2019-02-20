package no.hamre.kubescore.backend.service

import no.hamre.kubescore.backend.Slf4jLogger
import no.hamre.kubescore.backend.dao.{DuplicateException, KubescoreDao}
import no.hamre.kubescore.backend.model.PersonForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.{User, UserDetails, UserDetailsService, UsernameNotFoundException}
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

import scala.collection.JavaConverters._

trait PersonService {
  def createNewPerson(personForm: PersonForm): Long
}

@Service
class PersonServiceImpl(@Autowired kubescoreDao: KubescoreDao,
                        @Autowired bCryptPasswordEncoder: BCryptPasswordEncoder
                       ) extends PersonService with Slf4jLogger with UserDetailsService{


  override def loadUserByUsername(s: String): UserDetails = {
    val maybePerson = kubescoreDao.findAuthUserByEmail(s)
    maybePerson match {
      case Some(p) => new User(p.username, p.username, List().asJava);
      case None =>       throw new UsernameNotFoundException(s);
    }
  }

  override def createNewPerson(personForm: PersonForm): Long = {
    val maybePersonExists = kubescoreDao.findPersonByEmail(personForm.epost)
    if (maybePersonExists.isDefined) {
      throw DuplicateException(s"Det finnes allerede en registrert person med epost ${personForm.epost}")
    }

    kubescoreDao.addPerson(personForm, bCryptPasswordEncoder.encode(personForm.passord))
  }
}
