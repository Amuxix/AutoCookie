package autocookie.buyable.upgrade

import autocookie.buyable.traits.{HeavenlyPowerUnlock, UpgradesRequired}

class HeavenlyChipUpgrade(
  override val name: String,
  override protected val percentUnlock: Int,
  override protected val upgradeRequirementsNames: String*
) extends Upgrade with UpgradesRequired with HeavenlyPowerUnlock
