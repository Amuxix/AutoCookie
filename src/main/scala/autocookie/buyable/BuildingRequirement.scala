package autocookie.buyable

import autocookie.Helpers
import typings.cookieclicker.global.Game
import typings.cookieclicker.Game.GameObject as GameBuilding

object BuildingRequirement {
  def generateRequirementsForAllBuildings(amount: Int): Seq[BuildingRequirement] =
    Game.ObjectsById.map(building => new BuildingRequirement(building, amount)).toSeq
}

case class BuildingRequirement(buyable: GameBuilding, override val amount: Int) extends Building(buyable.name) {

  override lazy val gameBuyable: GameBuilding = buyable

  def missingAmount: Double = 0D max amount - gameBuyable.amount

  override def update(debug: Boolean = false): Unit = throw new Exception("Trying to update BuildingRequirement")
}
