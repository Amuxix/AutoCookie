package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirement
import autocookie.buyable.BuildingRequirements.BuildingRequirements
import autocookie.buyable.traits.{BuildingsRequired, UnlocksAchievment}

class BuildingUpgradeWithAchievement(
  override val name: String,
  override protected val originalBuildingRequirements: BuildingRequirements,
  override protected val achievmentName: String
) extends Upgrade with BuildingsRequired with UnlocksAchievment
