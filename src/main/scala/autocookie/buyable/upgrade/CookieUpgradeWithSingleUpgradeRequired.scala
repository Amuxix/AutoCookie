package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirement
import autocookie.buyable.traits.{SingleUpgradeRequired, CookiePower}

class CookieUpgradeWithSingleUpgradeRequired(
  override val name: String,
  override protected val upgradeRequirementName: String
) extends Upgrade with SingleUpgradeRequired with CookiePower
