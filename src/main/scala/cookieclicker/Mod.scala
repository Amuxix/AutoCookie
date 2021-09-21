package cookieclicker

import cookieclicker.Game

trait Mod extends Named {
  def init(): Unit
  def save(): String
  def load(string: String): Unit

  private def toGameMod: GameMod =
    val gameMod = GameMod()
    gameMod.setInit(init)
    gameMod.setSave(save)
    gameMod.setLoad(load)
    gameMod

  final def register = Game.registerMod(className, toGameMod)
}
