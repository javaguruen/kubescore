package no.hamre.kubescore.backend.core

import org.scalatest.FunSuite

class ScramblerTest extends FunSuite {

  test("Finner motsatte sider"){
    assert( Scrambler.isMotsatt("B", "F") )
    assert( Scrambler.isMotsatt("F", "B") )
    assert( !Scrambler.isMotsatt("F", "U") )
    assert( !Scrambler.isMotsatt("F", "") )
  }
}
