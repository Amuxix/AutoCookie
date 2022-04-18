package autocookie.buyable

import autocookie.{Helpers, Logger}
import autocookie.Helpers.convertNumeral
import cookieclicker.Game
import cookieclicker.buyables.GameBuilding

case class BuildingRequirement(building: Building, requiredAmount: Int) extends Buyable:
  override type T = GameBuilding
  override lazy val gameBuyable: GameBuilding = building.gameBuyable
  override val name: String = building.name

  override protected def innerBuy(): Unit = Building.innerBuy(building)

  def missingAmount: Int = 0 max requiredAmount - gameBuyable.amount.toInt
