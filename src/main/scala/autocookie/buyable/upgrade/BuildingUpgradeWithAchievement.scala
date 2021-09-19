package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirement
import autocookie.buyable.traits.{SingleBuildingRequired, UnlocksAchievment}

class BuildingUpgradeWithAchievement(
  override val name: String,
  override protected val buildingRequirement: BuildingRequirement,
  override protected val achievmentName: String
) extends Upgrade with SingleBuildingRequired with UnlocksAchievment
