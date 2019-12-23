package net.slava.game

import net.slava.game.Text._
import zio.console._
import zio.test.Assertion._
import zio.test.environment.{TestConsole, TestRandom}
import zio.test.{DefaultRunnableSpec, assert, suite, testM}

import scala.language.postfixOps

object GameSpec extends DefaultRunnableSpec({

  def n(s: String) = s"$s\n"

  suite("Game")(
    testM("has a menu") {
      for {
        _ <- TestConsole.feedLines("0", "1", "7", "x")
        playerVsComputerTxt = "This is a mock for a Player VS Computer game"
        computerVsComputerTxt = "This is a mock for a Computer VS Computer game"
        _ <- Game.gameMenu(putStrLn(playerVsComputerTxt), putStrLn(computerVsComputerTxt))
        intro0 <- TestConsole.output.map(_ (0))
        playerVsComputer <- TestConsole.output.map(_ (1))
        intro1 <- TestConsole.output.map(_ (2))
        computerVsComputer <- TestConsole.output.map(_ (3))
        intro2 <- TestConsole.output.map(_ (4))
        wrongMenuChoice <- TestConsole.output.map(_ (5))
        intro3 <- TestConsole.output.map(_ (6))
        bye <- TestConsole.output.map(_ (7))
        msgNum <- TestConsole.output.map(_.size)
      } yield assert(intro0, equalTo(n(IntroTxt))) && assert(playerVsComputer, equalTo(n(playerVsComputerTxt))) &&
        assert(intro1, equalTo(n(IntroTxt))) && assert(computerVsComputer, equalTo(n(computerVsComputerTxt))) &&
        assert(intro2, equalTo(n(IntroTxt))) && assert(wrongMenuChoice, equalTo(n(wrongMenuChoiceTxt("7")))) &&
        assert(intro3, equalTo(n(IntroTxt))) && assert(bye, equalTo(n(ByeTxt))) && assert(msgNum, equalTo(8))
    },

    testM("supports computer vs computer game") {
      for {
        _ <- TestRandom.feedInts(0, 0, 1, 2)
        _ <- Game.computerVsComputerGame
        computersAreDrawing1 <- TestConsole.output.map(_ (0))
        computersHaveSigns1 <- TestConsole.output.map(_ (1))
        draw <- TestConsole.output.map(_ (2))
        computersAreDrawing2 <- TestConsole.output.map(_ (3))
        computerHaveSigns2 <- TestConsole.output.map(_ (4))
        computerWins <- TestConsole.output.map(_ (5))
        msgNum <- TestConsole.output.map(_.size)
      } yield assert(computersAreDrawing1, equalTo(n(ComputersAreDrawingTxt))) &&
        assert(computersHaveSigns1, equalTo(n(computerVsComputerSignsTxt(HandSign.Rock, HandSign.Rock)))) &&
        assert(draw, equalTo(n(DrawTxt))) && assert(computersAreDrawing2, equalTo(n(ComputersAreDrawingTxt))) &&
        assert(computerHaveSigns2, equalTo(n(computerVsComputerSignsTxt(HandSign.Paper, HandSign.Scissors)))) &&
        assert(computerWins, equalTo(n(winsTxt(Computer2Txt)))) && assert(msgNum, equalTo(6))
    },

    testM("supports computer vs computer mode") {
      for {
        _ <- TestRandom.feedInts(0, 1)
        _ <- TestConsole.feedLines("7", "0", "2")
        _ <- Game.playerVsComputerGame
        drawSign0 <- TestConsole.output.map(_ (0))
        incorrectSelection <- TestConsole.output.map(_ (1))
        drawSign1 <- TestConsole.output.map(_ (2))
        playerVsComputerSigns0 <- TestConsole.output.map(_ (3))
        draw <- TestConsole.output.map(_ (4))
        drawSign2 <- TestConsole.output.map(_ (5))
        playerVsComputerSigns1 <- TestConsole.output.map(_ (6))
        playerWins <- TestConsole.output.map(_ (7))
        msgNum <- TestConsole.output.map(_.size)
      } yield assert(drawSign0, equalTo(n(DrawSignTxt))) &&
        assert(incorrectSelection, equalTo(n(IncorrectSelectionTxt))) && assert(drawSign1, equalTo(n(DrawSignTxt))) &&
        assert(playerVsComputerSigns0, equalTo(n(playerVsComputerSignsTxt(HandSign.Rock, HandSign.Rock)))) &&
        assert(draw, equalTo(n(DrawTxt))) && assert(drawSign2, equalTo(n(DrawSignTxt))) &&
        assert(playerVsComputerSigns1, equalTo(n(playerVsComputerSignsTxt(HandSign.Scissors, HandSign.Paper)))) &&
        assert(playerWins, equalTo(n(winsTxt(PlayerTxt)))) && assert(msgNum, equalTo(8))
    }
  )
})