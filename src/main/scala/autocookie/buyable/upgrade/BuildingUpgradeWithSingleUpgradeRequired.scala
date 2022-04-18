package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirement
import autocookie.buyable.BuildingRequirements.BuildingRequirements
import autocookie.buyable.traits.{BuildingsRequired, SingleUpgradeRequired}

class BuildingUpgradeWithSingleUpgradeRequired(
  override val name: String,
  override protected val upgradeRequirementName: String,
  override protected val originalBuildingRequirements: BuildingRequirements
) extends Upgrade with BuildingsRequired with SingleUpgradeRequired
