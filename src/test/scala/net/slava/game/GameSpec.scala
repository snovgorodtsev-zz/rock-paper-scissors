package net.slava.game

import zio.test.Assertion.equalTo
import zio.test.environment.TestConsole
import zio.test.{DefaultRunnableSpec, assert, suite, testM}
import Texts._

object GameSpec extends DefaultRunnableSpec({

  suite("Game")(
    testM("exits") {
      for {
        _ <- TestConsole.feedLines("x")
        code <- Game.run(Nil)
        intro <- TestConsole.output.map(_.head)
        bye <- TestConsole.output.map(_.last)
      } yield assert(code, equalTo(0)) && assert(intro, equalTo(s"$introStr\n")) && assert(bye, equalTo(s"$byeStr\n"))
    }
  )
})