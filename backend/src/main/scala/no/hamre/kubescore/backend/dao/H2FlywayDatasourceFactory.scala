package no.hamre.kubescore.backend.dao

import java.util.concurrent.atomic.AtomicInteger

import javax.sql.DataSource
import no.hamre.kubescore.backend.dao.H2FlywayDatasourceFactory.DB_NUMBER
import org.flywaydb.core.Flyway

object H2FlywayDatasourceFactory {
  val DB_NUMBER = new AtomicInteger(0)

  def createDataSource(s: String) =
    new H2FlywayDatasourceFactory(s)
      .createDataSource()

}

class H2FlywayDatasourceFactory(val schemaName: String) {
  def createDataSource(): DataSource = {
    val url = s"jdbc:h2:mem:cube${DB_NUMBER.incrementAndGet()}"
    var options = ";MODE=PostgreSQL;INIT=CREATE SCHEMA IF NOT EXISTS " + schemaName

    if (url.contains(":mem:")) {
      options = options + ";DB_CLOSE_DELAY=-1"
    }
    try {
      val flyway = Flyway.configure().dataSource(s"$url$options", "sa", null).load()
      flyway.migrate()
      flyway.getConfiguration().getDataSource
    } catch {
      case e: Exception => throw new RuntimeException("Error bootstrapping H2-datasource using liquibase", e)
    }
  }
}