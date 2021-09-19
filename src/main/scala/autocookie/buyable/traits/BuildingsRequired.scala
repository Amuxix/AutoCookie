package autocookie.buyable.traits

import autocookie.buyable.*
import autocookie.buyable.upgrade.Upgrade
import typings.cookieclicker.Game.GameObject as GameBuilding

object BuildingsRequired {
  extension (buildingRequirements: Set[BuildingRequirement])
    def toRequirementsMap: Map[GameBuilding, Int] = buildingRequirements
      .map(buildingRequirement => buildingRequirement.gameBuyable -> buildingRequirement.amount)
      .toMap

  extension (requirements: Map[GameBuilding, Int])
    def +++(buildingRequirements: Set[BuildingRequirement]): Map[GameBuilding, Int] =
      (requirements.toSeq ++ buildingRequirements.toRequirementsMap.toSeq)
        .groupBy(_._1)
        .view
        .mapValues(_.maxBy(_._2)._2)
        .toMap

  def flattenedBuildingRequirements(upgradeRequirements: Set[Upgrade], 
    buildingRequirements: Set[BuildingRequirement]): Set[BuildingRequirement] =
    upgradeRequirements.foldLeft(buildingRequirements.toRequirementsMap) {
      case (totalRequirements, buildingsRequired: BuildingsRequired) => totalRequirements +++ buildingsRequired
        .buildingRequirements
      case (totalRequirements, _)                                    => totalRequirements
    }.toSet.map((building, amount) => BuildingRequirement(building, amount))
}

trait BuildingsRequired {
  this: Buyable =>
  protected val buildingRequirementsSeq: Seq[BuildingRequirement]
  /**
   * The original requirements of this
   */
  lazy val originalBuildingRequirements: Set[BuildingRequirement] = buildingRequirementsSeq.toSet
  lazy val buildingRequirements: Set[BuildingRequirement] =
    val upgradeRequirements = this match {
      case upgradesRequired: UpgradesRequired => upgradesRequired.upgradeRequirements
      case _                                  => Set.empty
    }
    BuildingsRequired.flattenedBuildingRequirements(upgradeRequirements, originalBuildingRequirements)

  /**
   * Contains all building requirements of this buyable and any requirements it might have
   */
}
