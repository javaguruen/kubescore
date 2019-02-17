package no.hamre.kubescore.backend.dao

import no.hamre.kubescore.backend.model.Person
import org.sql2o.data.Row

object DbMapper {
  def mapPerson(row: Row): Person = {
    Person(
      Some(row.getLong("id")),
      row.getString("fornavn"),
      row.getString("etternavn"),
      row.getString("epost"),
    )
  }
}
