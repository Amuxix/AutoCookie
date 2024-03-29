package autocookie.buyable.upgrade

import autocookie.{AutoCookie, EmptyInvestment, Investment}
import autocookie.Helpers.toBoolean
import autocookie.buyable.BuildingRequirements.BuildingRequirements
import autocookie.buyable.{Achievement, BuildingRequirement}
import autocookie.reserve.Reserve
import cookieclicker.Game

class GoldenCookieUpgrade(override val name: String) extends Upgrade:
  override def estimatedReturnPercent(newBrokers: Int): Double = 1D
  override protected def createInvestment: Investment = EmptyInvestment

  override protected def calculateCpsIncrease(
    buildingRequirements: BuildingRequirements,
    upgradeRequirements: Set[Upgrade],
    achievementRequirements: Set[Achievement],
    debug: Boolean,
  ): Double =
    if gameBuyable.unlocked && Reserve.amount + this.price <= Game.cookies then Double.PositiveInfinity else 0
