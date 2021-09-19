package autocookie.buyable.upgrade

import autocookie.buyable.traits.{CookiePower, ResearchTime, SingleUpgradeRequired}

class ResearchCookieUpgrade(
  override val name: String,
  override protected val upgradeRequirementName: String,
  override protected val power: Double,
) extends Upgrade with SingleUpgradeRequired with ResearchTime with CookiePower
