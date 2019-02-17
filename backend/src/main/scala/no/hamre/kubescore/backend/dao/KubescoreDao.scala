package no.hamre.kubescore.backend.dao

import java.util.stream.Collectors

import javax.sql.DataSource
import no.hamre.kubescore.backend.model.{Person, Tid}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.sql2o.Sql2o
import org.sql2o.quirks.PostgresQuirks
import scala.collection.JavaConverters._

trait KubescoreDao {
  def addTime(time: Tid, personId: Long): Long

  def addPerson(person: Person): Long

  def findPerson(id: Long): Option[Person]
  def findPersonByEmail(email: String): Option[Person]
}

@Component
class KubescoreDaoImpl(@Autowired dataSource: DataSource) extends KubescoreDao {
  private val sql2o = new Sql2o(dataSource, new PostgresQuirks())


  override def findPersonByEmail(email: String): Option[Person] = {
    val selectQuery = "SELECT * FROM personer WHERE lower(epost) = :epost"

    val con = sql2o.beginTransaction
    try {
      val personer = con.createQuery(selectQuery)
        .addParameter("epost", email.toLowerCase.trim)
        .executeAndFetchTable().rows()
        .asScala
        .map(row => DbMapper.mapPerson(row))
        .toList
      personer match {
        case Nil => None
        case x :: Nil => Some(x)
        case xs => throw DuplicateException(s"Found ${xs.size} persons with email $email")
      }
    } catch {
      case e: Exception => throw DatabaseException(s"Error finding Person by id: $email", e)
    } finally {
      if (con != null) con.close()
    }
  }

  override def findPerson(id: Long): Option[Person] = {
    val selectQuery = "SELECT * FROM personer WHERE id = :id"

    val con = sql2o.beginTransaction
    try {
      val personer = con.createQuery(selectQuery)
        .addParameter("id", id)
        .executeAndFetchTable().rows()
        .asScala
        .map(row => DbMapper.mapPerson(row))
        .toList
      personer match {
        case Nil => None
        case x :: Nil => Some(x)
        case xs => throw DuplicateException(s"Found ${xs.size} persons with id $id")
      }
    } catch {
      case e: Exception => throw DatabaseException(s"Error finding Person by id: $id", e)
    } finally {
      if (con != null) con.close()
    }
  }

  override def addPerson(person: Person): Long = {
    val insertQuery =
      """
      INSERT INTO personer (fornavn, etternavn, epost)
      VALUES (:fnavn, :enavn, :epost)""".stripMargin
    val con = sql2o.beginTransaction
    try {
      val generatedId: Long = con.createQuery(insertQuery, true)
        .addParameter("fnavn", person.fornavn)
        .addParameter("enavn", person.etternavn)
        .addParameter("epost", person.epost)
        .executeUpdate().getKey().asInstanceOf[Long]
      con.commit
      generatedId
    } catch {
      case e: Exception => throw DatabaseException(s"Error inserting Person: $person", e)
    } finally {
      if (con != null) con.close()
    }
  }

  override def addTime(tid: Tid, personId: Long): Long = {
    val insertQuery =
      """
      INSERT INTO tid(minutter, sekunder, hundredeler)
      VALUES (:min, :sek, :hund)""".stripMargin
    val con = sql2o.beginTransaction
    try {
      val generatedId: Long = con.createQuery(insertQuery, true)
        .addParameter("min", tid.minutter)
        .addParameter("sek", tid.sekunder)
        .addParameter("hund", tid.hundredeler)
        .executeUpdate().getKey().asInstanceOf[Long]
      con.commit
      generatedId
    } catch {
      case e: Exception => throw DatabaseException(s"Error inserting Tid: $tid", e)
    } finally
      if (con != null) con.close()
  }
}

case class DatabaseException(message: String, cause: Throwable) extends RuntimeException(message, cause)

case class DuplicateException(message: String) extends RuntimeException(message)