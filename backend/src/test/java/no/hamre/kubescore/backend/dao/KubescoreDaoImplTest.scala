package no.hamre.kubescore.backend.dao

import no.hamre.kubescore.backend.model.{Person, Tid}
import org.scalatest.FunSuite

class KubescoreDaoImplTest extends FunSuite with DaoTestdata{

  test("Insert new tid should give an id > 0"){
    new DaoTestdata {
      val id = dao.addTime(Tid(None, 0, 30, 100), 42)
      println(s"Generated ID is $id")
      assert(id > 0)
    }
  }

  test("Insert new person should give an id > 0"){
    new DaoTestdata {
      val id = dao.addPerson(person)
      println(s"Generated ID is $id")
      assert(id > 0)
    }
  }

  test("Load testdata when running tests"){
    new DaoTestdata {
      val testdataPerson = dao.findPerson(1)
      assert( testdataPerson.isDefined )
      assert( testdataPerson.get.fornavn == "Speed")
      assert( testdataPerson.get.etternavn == "Cuber")
      assert( testdataPerson.get.epost == "speed.cuber@gmail.com")
    }
  }

  test("Finding a person after creation should work"){
    new DaoTestdata {
      val id = dao.addPerson(person)
      val maybePerson = dao.findPerson(id)
      assert(maybePerson.isDefined)
    }
  }

  test("Matching of lists"){
    val list = List("String")
    val size = list match {
      case Nil => 0
      case x :: Nil => 1
      case xs => xs.size
    }
    assert( size == 1)
  }
}

trait DaoTestdata{
  val person = Person(None, "New", "Person", "new.person@mail.com")
  val datasource = H2FlywayDatasourceFactory.createDataSource("cubescore")
  val dao = new KubescoreDaoImpl(datasource)
}
