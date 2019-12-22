package net.slava.game

import java.io.IOException

import net.slava.game.Texts._
import zio.console._
import zio.random.Random
import zio.{App, URIO, ZIO, random}

import scala.util.Try

object Game extends App {

  private type Env = Console with Random

  def run(args: List[String]): URIO[Env, Int] = gameMenu.fold(_ => 1, _ => 0)

  private val computerVsComputer: ZIO[Env, IOException, Unit] = for {
    _ <- putStrLn("Computers are drawing hand signs...")
    sign1 <- drawSign
    sign2 <- drawSign
    _ <- putStrLn(s"computer1 hand sign is ${sign1.name}, computer2 hand sign is ${sign2.name}!!!")
    _ <- sign1.compare(sign2) match {
      case Result.Win => putStrLn("computer1 wins!")
      case Result.Fail => putStrLn("computer2 wins!")
      case Result.Draw => putStrLn("It's a draw, a replay needed.") *> computerVsComputer
    }
  } yield ()

  private val playerVsComputer: ZIO[Env, IOException, Unit] = for {
    _ <- putStrLn("Please, draw a hand sign. Enter \"1\" for Rock, \"2\" for Paper or \"3\" for Scissors.")
    n <- getStrLn
    _ <- Try(n.toInt).toOption.flatMap(HandSign.Values.get) match {
      case None => putStrLn("Incorrect hand sign selection") *> playerVsComputer
      case Some(sign) =>
        for {
          computerSign <- drawSign
          _ <- putStrLn(s"computer hand sign is ${computerSign.name}, player hand sign is ${sign.name}!!!")
          _ <- sign.compare(computerSign) match {
            case Result.Fail => putStrLn("Computer wins!")
            case Result.Win => putStrLn("Player wins!")
            case Result.Draw => putStrLn("It's a draw, a replay needed.") *> playerVsComputer
          }
        } yield ()
    }
  } yield ()

  private val gameMenu: ZIO[Env, IOException, Unit] = for {
    _ <- putStrLn(introStr)
    number <- getStrLn
    _ <- number match {
      case "1" => playerVsComputer *> gameMenu
      case "2" => putStrLn("Computer vs computer game starts") *> computerVsComputer *> gameMenu
      case "x" =>
        putStrLn(byeStr)
      case x => putStrLn(s"You could choose either 1 or 2, but you chose $x, please try again") *> gameMenu
    }
  } yield ()

  private def drawSign = random.nextInt(HandSign.Values.size).map(HandSign.Values(_))

}