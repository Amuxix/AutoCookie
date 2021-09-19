package autocookie.buyable.upgrade

import autocookie.AutoCookie
import autocookie.Helpers.toBoolean
import autocookie.buyable.{Achievement, BuildingRequirement}
import typings.cookieclicker.global.Game

class GoldenCookieUpgrade(override val name: String) extends Upgrade {
  override def estimatedReturnPercent(newBrokers: Int): Double = 1D

  override protected def calculateCpsIncrease(
    buildingRequirements: Set[BuildingRequirement],
    upgradeRequirements: Set[Upgrade],
    achievmentRequirements: Set[Achievement]
  ): Double =
    if (gameBuyable.unlocked && AutoCookie.reserve.reserveAmount + this.price <= Game.cookies) then Double
      .PositiveInfinity else 0
}
