package no.hamre.kubescore.backend.rest.api

import no.hamre.kubescore.backend.model

object ToModelMapper {
  def mapToPersonForm(apiPersonForm: PersonForm): model.PersonForm = {
    model.PersonForm(
      apiPersonForm.id,
      apiPersonForm.fornavn,
      apiPersonForm.etternavn,
      apiPersonForm.epost,
      apiPersonForm.passord,
      apiPersonForm.passordConfirm
    )
  }
}
