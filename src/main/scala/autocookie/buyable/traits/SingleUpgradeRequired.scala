package autocookie.buyable.traits

import autocookie.buyable.Buyable

trait SingleUpgradeRequired extends UpgradesRequired {
  this: Buyable =>
  protected val upgradeRequirementName: String
  override protected val upgradeRequirementsNames: Seq[String] = Seq(upgradeRequirementName)
}
