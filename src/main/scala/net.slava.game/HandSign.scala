package net.slava.game

sealed trait HandSign {
  def name: String

  def compare(that: HandSign): Result
}

object HandSign {

  object Rock extends HandSign {
    override def name: String = "Rock"

    override def compare(that: HandSign): Result = that match {
      case Rock => Result.Draw
      case Paper => Result.Fail
      case Scissors => Result.Win
    }
  }

  object Paper extends HandSign {
    override def name: String = "Paper"

    override def compare(that: HandSign): Result = that match {
      case Rock => Result.Win
      case Paper => Result.Draw
      case Scissors => Result.Fail
    }
  }

  object Scissors extends HandSign {
    override def name: String = "Scissors"

    override def compare(that: HandSign): Result = that match {
      case Rock => Result.Fail
      case Paper => Result.Win
      case Scissors => Result.Draw
    }
  }

  val Values: Map[Int, HandSign] =
    List(HandSign.Rock, HandSign.Paper, HandSign.Scissors).zipWithIndex.map(e => e._2 -> e._1).toMap

}