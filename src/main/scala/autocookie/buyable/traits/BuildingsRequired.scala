package autocookie.buyable.traits

import autocookie.buyable.*
import autocookie.buyable.upgrade.Upgrade
import autocookie.buyable.BuildingRequirements.BuildingRequirements
import cookieclicker.buyables.GameBuilding

object BuildingsRequired:
  def flattenedBuildingRequirements(
    upgradeRequirements: Set[Upgrade],
    buildingRequirements: BuildingRequirements,
  ): BuildingRequirements =
    upgradeRequirements.foldLeft(buildingRequirements) {
      case (totalRequirements, buildingsRequired: BuildingsRequired) => totalRequirements ++ buildingsRequired.buildingRequirements
      case (totalRequirements, _)                                    => totalRequirements
    }

trait BuildingsRequired:
  this: Buyable =>
  /**
   * The original requirements of this
   */
  protected val originalBuildingRequirements: BuildingRequirements

  /**
   * Contains all building requirements of this buyable and any requirements it might have
   */
  lazy val buildingRequirements: BuildingRequirements =
    val upgradeRequirements = this match {
      case upgradesRequired: UpgradesRequired => upgradesRequired.upgradeRequirements
      case _                                  => Set.empty
    }
    BuildingsRequired.flattenedBuildingRequirements(upgradeRequirements, originalBuildingRequirements)
