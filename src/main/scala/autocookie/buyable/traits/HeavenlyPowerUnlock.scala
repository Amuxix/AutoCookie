package autocookie.buyable.traits

import autocookie.Helpers.cps
import autocookie.buyable.{Achievement, BuildingRequirement}
import autocookie.buyable.upgrade.Upgrade
import typings.cookieclicker.global.Game

trait HeavenlyPowerUnlock {
  this: Upgrade =>
  protected val percentUnlock: Int

  lazy val percentIncrease: Double = Game.prestige * percentUnlock * 0.0001

  override protected def calculateCpsIncrease(
    buildingRequirements: Set[BuildingRequirement],
    upgradeRequirements: Set[Upgrade],
    achievmentRequirements: Set[Achievement]
  ): Double =
    cps * percentIncrease
}
