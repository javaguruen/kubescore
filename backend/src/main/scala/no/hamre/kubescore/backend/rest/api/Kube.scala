package no.hamre.kubescore.backend.rest.api

import java.time.OffsetDateTime

case class Kube(id: Option[Long], navn: String)

case class Konkurranse(id: Option[Long], navn: String, sted: String, tidspunkt: OffsetDateTime, beskrivelse: Option[String])

case class PersonForm(id: Option[Long], fornavn: String, etternavn: String, epost: String, passord: String, passordConfirm: String)
case class Person(id: Option[Long], fornavn: String, etternavn: String, epost: String)

case class Serie(id: Option[Long], person: Person, navn: String,
                 tider: List[Tid], konkurranse: Konkurranse, gren: String, antall: Int)

case class Konkurransegren(id: Option[Long], navn: String, kube: Kube, beskrivelse: Option[String], konkurranse: Konkurranse,
                           serier: List[Serie])

case class Tid(minutter: Int, sekunder: Int, hundredeler: Int)
