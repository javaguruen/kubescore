package no.hamre.kubescore.backend.controller

import java.net.URI

import javax.validation.Valid
import javax.ws.rs._
import javax.ws.rs.core.Response
import no.hamre.kubescore.backend.Slf4jLogger
import no.hamre.kubescore.backend.rest.api.{PersonForm, ToModelMapper}
import no.hamre.kubescore.backend.service.PersonService
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.stereotype.Component

@Component
@Path("/personer")
class PersonerController(@Autowired personService: PersonService, @Value("baseUrl") baseUrl: String) extends Slf4jLogger{

  @POST
  @Path("/")
  def createPerson(@Valid personForm: PersonForm): Response = {
    log.info(s"Creating new Person $personForm")
    val modelPersonForm = ToModelMapper.mapToPersonForm(personForm)
    val id = personService.createNewPerson(modelPersonForm)
    Response.created(new URI(s"$baseUrl/personer/$id")).build()
  }
}