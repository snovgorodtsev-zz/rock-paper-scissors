package net.slava.game

import java.io.IOException

import net.slava.game.Text._
import zio.console._
import zio.random.Random
import zio.{App, URIO, ZIO, random}

import scala.util.Try

object Game extends App {

  private type Env = Console with Random

  private def drawRandomSign = random.nextInt(HandSign.Values.size).map(HandSign.Values(_))

  val computerVsComputerGame: ZIO[Env, IOException, Unit] = for {
    _ <- putStrLn(ComputersAreDrawingTxt)
    computer1Sign <- drawRandomSign
    computer2Sign <- drawRandomSign
    _ <- putStrLn(computerVsComputerSignsTxt(computer1Sign, computer2Sign))
    _ <- computer1Sign.challenge(computer2Sign) match {
      case Result.Win => putStrLn(winsTxt(Computer1Txt))
      case Result.Fail => putStrLn(winsTxt(Computer2Txt))
      case Result.Draw => putStrLn(DrawTxt) *> computerVsComputerGame
    }
  } yield ()

  val playerVsComputerGame: ZIO[Env, IOException, Unit] = for {
    _ <- putStrLn(DrawSignTxt)
    n <- getStrLn
    _ <- Try(n.toInt).toOption.flatMap(HandSign.Values.get) match {
      case None => putStrLn(IncorrectSelectionTxt) *> playerVsComputerGame
      case Some(playerSign) =>
        for {
          computerSign <- drawRandomSign
          _ <- putStrLn(playerVsComputerSignsTxt(playerSign, computerSign))
          _ <- playerSign.challenge(computerSign) match {
            case Result.Fail => putStrLn(winsTxt(ComputerTxt))
            case Result.Win => putStrLn(winsTxt(PlayerTxt))
            case Result.Draw => putStrLn(DrawTxt) *> playerVsComputerGame
          }
        } yield ()
    }
  } yield ()

  def gameMenu(
                playerVsComputer: ZIO[Env, IOException, Unit],
                computerVsComputer: ZIO[Env, IOException, Unit]
              ): ZIO[Env, IOException, Unit] =
    for {
      _ <- putStrLn(IntroTxt)
      number <- getStrLn
      menu = gameMenu(playerVsComputer, computerVsComputer)
      _ <- number match {
        case "0" => playerVsComputer *> menu
        case "1" => computerVsComputer *> menu
        case "x" => putStrLn(ByeTxt)
        case c => putStrLn(wrongMenuChoiceTxt(c)) *> menu
      }
    } yield ()

  def run(args: List[String]): URIO[Env, Int] =
    gameMenu(playerVsComputerGame, computerVsComputerGame).fold(_ => 1, _ => 0)

}