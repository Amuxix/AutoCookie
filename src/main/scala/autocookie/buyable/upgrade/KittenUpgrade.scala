package autocookie.buyable.upgrade

import autocookie.Helpers
import autocookie.Helpers.getKittenMultiplier
import autocookie.buyable.{Achievement, BuildingRequirement}
import cookieclicker.Game

class KittenUpgrade(override val name: String, milkRequired: Double) extends Upgrade {
  override protected def calculateCpsIncrease(
    buildingRequirements: Set[BuildingRequirement],
    upgradeRequirements: Set[Upgrade],
    achievmentRequirements: Set[Achievement]
  ): Double =
    if Game.milkProgress < milkRequired then
      0D
    else
      val base_cps = Helpers.cps
      val multiplier = Game.globalCpsMult / getKittenMultiplier(Game.milkProgress) * getKittenMultiplier(Game.milkProgress, Some(name))
      return (base_cps * multiplier) - base_cps
}
