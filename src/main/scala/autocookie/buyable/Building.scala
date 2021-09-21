package autocookie.buyable

import autocookie.Helpers.{convertNumeral, unlockRequirements}
import autocookie.{AutoCookie, Helpers, Logger}
import cookieclicker.buyables.GameBuilding
import cookieclicker.Game

import scala.scalajs.js

object Building {
  def findByName(name: String): Option[Building] =
    AutoCookie.buildings.get(name)

  def getGameBuildingByName(name: String): GameBuilding =
    Game.buildings
      .find(_.name == name)
      .getOrElse(throw new Exception(s"Unable to find Game Building named \"$name\""))

  lazy val cursor = Game.buildingsByName("Cursor")
  lazy val grandma = Game.buildingsByName("Grandma")
  lazy val farm = Game.buildingsByName("Farm")
  lazy val mine = Game.buildingsByName("Mine")
  lazy val factory = Game.buildingsByName("Factory")
  lazy val bank = Game.buildingsByName("Bank")
  lazy val temple = Game.buildingsByName("Temple")
  lazy val wizardTower = Game.buildingsByName("Wizard tower")
  lazy val shipment = Game.buildingsByName("Shipment")
  lazy val alchemyLab = Game.buildingsByName("Alchemy lab")
  lazy val portal = Game.buildingsByName("Portal")
  lazy val timeMachine = Game.buildingsByName("Time machine")
  lazy val antimatterCondenser = Game.buildingsByName("Antimatter condenser")
  lazy val prism = Game.buildingsByName("Prism")
  lazy val chancemaker = Game.buildingsByName("Chancemaker")
  lazy val fractalEngine = Game.buildingsByName("Fractal engine")
  lazy val javascriptConsole = Game.buildingsByName("Javascript console")
  lazy val idleverse = Game.buildingsByName("Idleverse")

  lazy val all: Seq[GameBuilding] = Game.buildings.toSeq
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
    gameBuyable.buy(1)
    Game.buyMode = oldBuyMode
}
