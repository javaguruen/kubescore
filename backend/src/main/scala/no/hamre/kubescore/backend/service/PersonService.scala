package no.hamre.kubescore.backend.service

import no.hamre.kubescore.backend.Slf4jLogger
import no.hamre.kubescore.backend.dao.{DuplicateException, KubescoreDao}
import no.hamre.kubescore.backend.model.PersonForm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

trait PersonService{
  def createNewPerson(personForm: PersonForm): Long
}

@Component
class PersonServiceImpl(@Autowired kubescoreDao: KubescoreDao) extends PersonService with Slf4jLogger {

  override def createNewPerson(personForm: PersonForm): Long = {
    val maybePersonExists = kubescoreDao.findPersonByEmail(personForm.epost)
    if( maybePersonExists.isDefined ){
      throw DuplicateException(s"Det finnes allerede en registrert person med epost ${personForm.epost}")
    }

    kubescoreDao.addPerson(personForm, encryptedPassword)
  }
}
