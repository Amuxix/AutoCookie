package autocookie.buyable

import autocookie.Helpers
import cookieclicker.Game
import cookieclicker.buyables.GameBuilding

object BuildingRequirement {
  def generateRequirementsForAllBuildings(amount: Int): Seq[BuildingRequirement] =
    Game.buildings.map(building => new BuildingRequirement(building, amount)).toSeq
}

case class BuildingRequirement(buyable: GameBuilding, requiredAmount: Int) extends Building(buyable.name) {

  override lazy val gameBuyable: GameBuilding = buyable

  def missingAmount: Int = 0 max requiredAmount - gameBuyable.amount.toInt

  lazy val building: Building = Building.getByName(name)

  override def update(debug: Boolean = false): Unit = throw new Exception("Trying to update BuildingRequirement")
}
