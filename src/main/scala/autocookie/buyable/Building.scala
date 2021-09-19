package autocookie.buyable

import autocookie.Helpers.{convertNumeral, unlockRequirements}
import autocookie.{AutoCookie, Helpers, Logger}
import typings.cookieclicker.Game.GameObject as GameBuilding
import typings.cookieclicker.global.Game

import scala.scalajs.js

object Building {
  def findByName(name: String): Option[Building] =
    AutoCookie.buildings.get(name)

  def getGameBuildingByName(name: String): GameBuilding =
    Game.ObjectsById
      .find(_.name == name)
      .getOrElse(throw new Exception(s"Unable to find Game Building named \"$name\""))

  lazy val cursor = Game.Objects("Cursor")
  lazy val grandma = Game.Objects("Grandma")
  lazy val farm = Game.Objects("Farm")
  lazy val mine = Game.Objects("Mine")
  lazy val factory = Game.Objects("Factory")
  lazy val bank = Game.Objects("Bank")
  lazy val temple = Game.Objects("Temple")
  lazy val wizardTower = Game.Objects("Wizard tower")
  lazy val shipment = Game.Objects("Shipment")
  lazy val alchemyLab = Game.Objects("Alchemy lab")
  lazy val portal = Game.Objects("Portal")
  lazy val timeMachine = Game.Objects("Time machine")
  lazy val antimatterCondenser = Game.Objects("Antimatter condenser")
  lazy val prism = Game.Objects("Prism")
  lazy val chancemaker = Game.Objects("Chancemaker")
  lazy val fractalEngine = Game.Objects("Fractal engine")
  lazy val javascriptConsole = Game.Objects("Javascript console")
  lazy val idleverse = Game.Objects("Idleverse")

  lazy val all: Seq[GameBuilding] = Game.ObjectsById.toSeq
}

class Building(val name: String) extends Buyable {
  override type T = GameBuilding
  override lazy val gameBuyable: GameBuilding = Building.getGameBuildingByName(name)

  def amount: Int = gameBuyable.amount.toInt

  override protected def innerBuy(): Unit =
    val oldBuyMode = Game.buyMode
    Game.buyMode = 1 //Make sure we are not selling
    val text = s"Bought the ${convertNumeral(amount + 1)} ${name}"
    Logger.log(text)
    Game.Notify(text, "")
    gameBuyable.buy(1D)
    Game.buyMode = oldBuyMode
}
