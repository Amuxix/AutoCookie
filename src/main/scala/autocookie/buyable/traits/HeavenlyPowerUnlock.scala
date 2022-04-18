package autocookie.buyable.traits

import autocookie.Helpers.cps
import autocookie.buyable.BuildingRequirements.BuildingRequirements
import autocookie.buyable.{Achievement, BuildingRequirement}
import autocookie.buyable.upgrade.Upgrade
import cookieclicker.Game

trait HeavenlyPowerUnlock:
  this: Upgrade =>
  protected val percentUnlock: Int

  lazy val percentIncrease: Double = Game.prestige * percentUnlock * 0.0001

  override protected def calculateCpsIncrease(
    buildingRequirements: BuildingRequirements,
    upgradeRequirements: Set[Upgrade],
    achievementRequirements: Set[Achievement],
    debug: Boolean,
  ): Double =
    cps * percentIncrease
