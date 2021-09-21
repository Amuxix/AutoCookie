package autocookie.buyable.traits

import autocookie.buyable.{Achievement, BuildingRequirement, Buyable}
import cookieclicker.Game
import autocookie.Helpers.toBoolean
import autocookie.{EmptyInvestment, Investment}
import autocookie.buyable.upgrade.Upgrade

object ResearchTime {
  def fullResearchTime: Int =
    val researchTime = Game.baseResearchTime
    if Game.upgradeBought("Persistent memory") then
      math.ceil(researchTime / 10).toInt
    else
      researchTime.toInt

  def nextResearch: Option[Upgrade] =
    val nextResearchId = Game.nextResearch.toInt
    Option.when(nextResearchId > 0)(Upgrade.getByName(Game.upgrades(nextResearchId).name))
}

trait ResearchTime { this: Buyable =>
  override protected def calculatePayback(price: Double, cpsIncrease: Double): Double =
    if Game.researchT == 0 && Achievement.getByName("Elder").won && ResearchTime.nextResearch.forall(_ != this) then
      Buyable.calculatePayback(price, cpsIncrease)
    else
      Double.PositiveInfinity
}
