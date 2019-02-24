package no.hamre.kubescore.backend.core

import scala.annotation.tailrec
import scala.util.Random

object Scrambler {
  private val sides3x3 = Array("F", "B", "U", "D", "R", "L")
  private val directions3x3 = Array(" ", "' ", "2 ")
  private val sidesPyraminx = Array("L", "R", "U", "B")
  private val directionsPyraminx = Array(" ", "' ")

  private val randomGenerator: Random = scala.util.Random

  def isMotsatt(sideOne: String, sideTwo: String): Boolean = {
    (sideOne, sideTwo) match {
      case ("", _) | (_, "") => false
      case ("F", "B") | ("B", "F") => true
      case ("U", "D") | ("D", "U") => true
      case ("L", "R") | ("R", "L") => true
      case _ => false
    }
  }

  def scramble3x3(requestedSize: Int): Array[String] = {

    @tailrec
    def generate(sequence: List[String], previousMove: String, twoPrevioues: String): List[String] = {
      if( sequence.size >= requestedSize ) sequence
      else{
        val side = sides3x3(randomGenerator.nextInt(6))
        val rotation = directions3x3(randomGenerator.nextInt(3))
        side match {
          case s if s == previousMove => generate(sequence, previousMove, twoPrevioues)
          case s if s == twoPrevioues && isMotsatt(s, previousMove) => generate(sequence, previousMove, twoPrevioues)
          case _ => generate( (side+rotation) :: sequence, side, previousMove)
        }
      }
    }
    generate(List(), "", "").toArray
  }

  private val PYRAMINX_MINIMIUM_TIPMOVES = 2

  def scramblePyraminx(requestedSize: Int): Array[String] = {
    val tipMoves = PYRAMINX_MINIMIUM_TIPMOVES + randomGenerator.nextInt(4 - PYRAMINX_MINIMIUM_TIPMOVES)
    val sideMoves = requestedSize - tipMoves

    @tailrec
    def generate(sequence: List[String], previousMove: String, length: Int): List[String] = {
      if( sequence.size >= length ) sequence
      else{
        val side = sidesPyraminx(randomGenerator.nextInt(4))
        val rotation = directionsPyraminx(randomGenerator.nextInt(2))

        side match {
          case s if s == previousMove => generate(sequence, previousMove, length)
          case _ => generate( (side+rotation) :: sequence, side, length)
        }
      }
    }

    val sides = generate(List(), "", sideMoves)
    val tips = generate(List(), "", tipMoves).map{ s => s.toLowerCase()}
    (sides ::: tips).toArray
  }

}
