package autocookie.buyable.upgrade

import autocookie.buyable.traits.UpgradesRequired

class LegacyUpgrade(
  override val name: String,
  override protected val upgradeRequirementsNames: String*
) extends Upgrade with UpgradesRequired
