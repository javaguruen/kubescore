package no.hamre.kubescore.backend.core

import scala.annotation.tailrec
import scala.util.Random

object Scrambler {
  private val sides3x3 = Array("F", "B", "U", "D", "R", "L")
  private val directions3x3 = Array(" ", "' ", "2 ")
  private val randomGenerator: Random = scala.util.Random

/*
  const scramble = (moves) => {
    let ms = ''
    let count = 0
    let lastMove = undefined
    do{
      const rand = random(6)
      const rot = random(3)
      const m = moves3x3[rand]
      const r = times3x3[rot]
      if( lastMove !== m ){
        ms = ms + m + r
        lastMove = m
        count++
      }
    } while ( count < moves)
    return ms
  };

  
*/

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

}
