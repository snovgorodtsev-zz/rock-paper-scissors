package net.slava.game

sealed trait Result

object Result {

  object Win extends Result

  object Fail extends Result

  object Draw extends Result

}

/**
 * Feel free to add more signs if necessary...
 */
sealed trait HandSign {
  def name: String

  def challenge(that: HandSign): Result
}

object HandSign {

  object Rock extends HandSign {
    override def name: String = "Rock"

    override def challenge(that: HandSign): Result = that match {
      case Rock => Result.Draw
      case Paper => Result.Fail
      case Scissors => Result.Win
    }
  }

  object Paper extends HandSign {
    override def name: String = "Paper"

    override def challenge(that: HandSign): Result = that match {
      case Rock => Result.Win
      case Paper => Result.Draw
      case Scissors => Result.Fail
    }
  }

  object Scissors extends HandSign {
    override def name: String = "Scissors"

    override def challenge(that: HandSign): Result = that match {
      case Rock => Result.Fail
      case Paper => Result.Win
      case Scissors => Result.Draw
    }
  }

  // Map of available hand signs. Should be kept in an up-to-date state.
  val Values: Map[Int, HandSign] =
    List(HandSign.Rock, HandSign.Paper, HandSign.Scissors).zipWithIndex.map(e => e._2 -> e._1).toMap

}