package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirement
import autocookie.buyable.traits.{ResearchTime, UpgradesRequired}

class ResearchUpgrade(
  override val name: String,
  override protected val upgradeRequirementsNames: String*
) extends Upgrade with UpgradesRequired with ResearchTime
