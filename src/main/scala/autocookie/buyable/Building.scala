package autocookie.buyable

import autocookie.Helpers.{convertNumeral, unlockRequirements}
import autocookie.{AutoCookie, Helpers, Logger}
import cookieclicker.buyables.GameBuilding
import cookieclicker.Game

import scala.scalajs.js

object GameBuildings:
  def getByName(name: String): GameBuilding =
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

object Building:
  def getByName(name: String): Building =
    all.find(_.name == name).getOrElse(throw new Exception(s"Unable to find Building named \"$name\""))

  lazy val cursor = Building("Cursor")
  lazy val grandma = Building("Grandma")
  lazy val farm = Building("Farm")
  lazy val mine = Building("Mine")
  lazy val factory = Building("Factory")
  lazy val bank = Building("Bank")
  lazy val temple = Building("Temple")
  lazy val wizardTower = Building("Wizard tower")
  lazy val shipment = Building("Shipment")
  lazy val alchemyLab = Building("Alchemy lab")
  lazy val portal = Building("Portal")
  lazy val timeMachine = Building("Time machine")
  lazy val antimatterCondenser = Building("Antimatter condenser")
  lazy val prism = Building("Prism")
  lazy val chancemaker = Building("Chancemaker")
  lazy val fractalEngine = Building("Fractal engine")
  lazy val javascriptConsole = Building("Javascript console")
  lazy val idleverse = Building("Idleverse")

  lazy val all: Seq[Building] =
    Seq(cursor, grandma, farm, mine, factory, bank, temple, wizardTower, shipment, alchemyLab, portal, timeMachine,
      antimatterCondenser, prism, chancemaker, fractalEngine, javascriptConsole, idleverse)
    
  def innerBuy(building: Building): Unit =
    val oldBuyMode = Game.buyMode
    Game.buyMode = 1 //Make sure we are not selling
    val text = s"Bought the ${convertNumeral(building.amount + 1)} ${building.name}"
    Logger.log(text)
    Game.Notify(text, "")
    building.gameBuyable.buy(1)
    Game.buyMode = oldBuyMode

class Building(val name: String) extends Buyable:
  override type T = GameBuilding
  override lazy val gameBuyable: GameBuilding = GameBuildings.getByName(name)

  def amount: Int = gameBuyable.amount.toInt

  override protected def innerBuy(): Unit = Building.innerBuy(this)
