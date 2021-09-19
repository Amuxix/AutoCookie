package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirement
import autocookie.buyable.traits.BuildingsRequired
import autocookie.buyable.upgrade.Upgrade

class BuildingUpgrade(
  override val name: String,
  override protected val buildingRequirementsSeq: BuildingRequirement*
) extends Upgrade with BuildingsRequired
