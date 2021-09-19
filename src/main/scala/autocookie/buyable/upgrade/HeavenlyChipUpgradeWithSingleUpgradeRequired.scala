package autocookie.buyable.upgrade

import autocookie.buyable.traits.{HeavenlyPowerUnlock, UpgradesRequired, SingleUpgradeRequired}

class HeavenlyChipUpgradeWithSingleUpgradeRequired(
  override val name: String,
  override protected val percentUnlock: Int,
  override protected val upgradeRequirementName: String,
) extends Upgrade with HeavenlyPowerUnlock with SingleUpgradeRequired
