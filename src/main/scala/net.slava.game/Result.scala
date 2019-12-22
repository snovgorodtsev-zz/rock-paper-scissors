package net.slava.game

sealed trait Result

object Result {

  object Win extends Result

  object Fail extends Result

  object Draw extends Result

}