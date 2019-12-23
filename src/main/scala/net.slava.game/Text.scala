package net.slava.game

/**
 * The object contains game messages
 */
object Text {

  // menu messages
  val IntroTxt: String =
    """
      |Hello! What kind of game you would like to have?
      |     - press "0" for a Player vs Computer game
      |     - press "1" for a Computer vs Computer game
      |     - press "x" to exit the program
      |""".stripMargin
  val ByeTxt: String = "Good bye!"

  def wrongMenuChoiceTxt(choice: String) = s"You could choose either 0 or 1, but you chose $choice, please try again"

  // computer vs computer messages
  val ComputersAreDrawingTxt: String = "Computers are drawing hand signs..."
  val Computer1Txt = "Computer1"
  val Computer2Txt = "Computer2"

  def computerVsComputerSignsTxt(sign1: HandSign, sign2: HandSign): String =
    signsTxt(Computer1Txt, sign1, Computer2Txt, sign2)

  // player vs computer messages
  val DrawSignTxt = "Please, draw a hand sign. Enter \"0\" for Rock, \"1\" for Paper or \"2\" for Scissors."
  val IncorrectSelectionTxt = "Incorrect hand sign selection"
  val PlayerTxt = "Player"
  val ComputerTxt = "Computer"

  def playerVsComputerSignsTxt(playerSign: HandSign, computerSign: HandSign): String =
    signsTxt(PlayerTxt, playerSign, ComputerTxt, computerSign)

  // common game messages
  def winsTxt(player: String) = s"$player wins!"

  private def signsTxt(player1: String, sign1: HandSign, player2: String, sign2: HandSign): String =
    s"$player1 hand sign is ${sign1.name}, $player2 hand sign is ${sign2.name}!!!"

  val DrawTxt: String = "It's a draw, a replay needed."
}
