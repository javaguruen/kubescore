package no.hamre.kubescore.backend

import org.slf4j.LoggerFactory

trait Slf4jLogger {
  val log = LoggerFactory.getLogger(this.getClass)
}
