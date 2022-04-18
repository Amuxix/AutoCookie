package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirement
import autocookie.buyable.BuildingRequirements.BuildingRequirements
import autocookie.buyable.traits.BuildingsRequired
import autocookie.buyable.upgrade.Upgrade

class BuildingUpgrade(
  override val name: String,
  override protected val originalBuildingRequirements: BuildingRequirements
) extends Upgrade with BuildingsRequired
