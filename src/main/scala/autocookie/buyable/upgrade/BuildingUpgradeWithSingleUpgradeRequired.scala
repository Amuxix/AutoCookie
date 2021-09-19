package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirement
import autocookie.buyable.traits.{BuildingsRequired, SingleUpgradeRequired}

class BuildingUpgradeWithSingleUpgradeRequired(
  override val name: String,
  override protected val upgradeRequirementName: String,
  override protected val buildingRequirementsSeq: BuildingRequirement*
) extends Upgrade with BuildingsRequired with SingleUpgradeRequired
