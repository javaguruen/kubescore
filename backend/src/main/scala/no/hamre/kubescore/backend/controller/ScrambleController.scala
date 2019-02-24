package no.hamre.kubescore.backend.controller

import javax.ws.rs._
import javax.ws.rs.core.Response
import no.hamre.kubescore.backend.core.Scrambler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@Path("/scrambles")
class ScrambleController {
  val log = LoggerFactory.getLogger(classOf[ScrambleController])

  @GET
  @Path("/3x3/")
  def scramble3x3(@QueryParam("rotations") @DefaultValue("20") rotations: Int): Response = {
    log.info(s"Generating a 3x3 scramble with $rotations moves")
    val seq = Scrambler.scramble3x3(rotations)
    Response.ok(seq.mkString(" ")).build()
  }

  @GET
  @Path("/pyraminx/")
  def pyraminx(@QueryParam("rotations") @DefaultValue("12") rotations: Int): Response = {
    log.info(s"Generating a pyraminx scramble with $rotations moves")
    val seq = Scrambler.scramblePyraminx(rotations)
    Response.ok(seq.mkString(" ")).build()
  }
}