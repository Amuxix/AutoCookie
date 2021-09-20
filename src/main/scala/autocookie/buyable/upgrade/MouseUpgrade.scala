package autocookie.buyable.upgrade

import autocookie.{AutoCookie, CPSCalculator, EmptyInvestment, Helpers, Investment}
import autocookie.Helpers.{amountOfNonCursors, hasOrIsChoice, toBoolean}
import autocookie.buyable.upgrade.MouseUpgrade.{mouses, multipliers}
import autocookie.buyable.{Achievement, BuildingRequirement}
import cookieclicker.Buff
import cookieclicker.Game

import scala.language.implicitConversions
import scala.scalajs.js
import scala.util.Try

object MouseUpgrade {
  lazy val mouses = List(
    "Plastic mouse",
    "Iron mouse",
    "Titanium mouse",
    "Adamantium mouse",
    "Unobtainium mouse",
    "Eludium mouse",
    "Wishalloy mouse",
    "Fantasteel mouse",
    "Nevercrack mouse",
    "Armythril mouse",
    "Technobsidian mouse",
    "Plasmarble mouse",
    "Miraculite mouse",
  )

  lazy val multipliers = List(
    "Reinforced index finger",
    "Carpal tunnel prevention cream",
    "Ambidextrous"
  )
}

class MouseUpgrade(override val name: String) extends Upgrade {
  override def estimatedReturnPercent(newBrokers: Int): Double = 1D
  override protected def createInvestment: Investment = EmptyInvestment

  override protected def calculateCpsIncrease(
    buildingRequirements: Set[BuildingRequirement],
    upgradeRequirements: Set[Upgrade],
    achievmentRequirements: Set[Achievement]
  ): Double =
    val clickBuffs = Helpers.buffs.filter(_.multClick.exists(_ > 0))
    if clickBuffs.isEmpty then return 0 //Only consider these upgrades if we have a buff
    val fingersAdd =
      if (Game.upgradeBought("Thousand fingers")) then
        amountOfNonCursors * CPSCalculator.fingerAdd.foldLeft(0.1) {
          case (totalAdd, (upgrade, multiplier)) if upgrade.owned => totalAdd * multiplier
          case (totalAdd, _)                                                    => totalAdd
        }
      else
        0

    val mousesAdd = mouses.count(mouse => hasOrIsChoice(mouse, Some(name))) * Game.cookiesPs * 0.01
    val clickMultiplier = math.pow(2, multipliers.count(Game.upgradeBought(_)))
    val buffMultipliers = clickBuffs.flatMap(_.multClick.toOption).foldLeft(1D)((total, buffPower) => total * buffPower)
    val cookiesPerClick = (clickMultiplier + fingersAdd + mousesAdd) * buffMultipliers
    val cpsIncrease = (cookiesPerClick - Game.mouseCps()) * AutoCookie.CLICKS_PER_SEC
    val longestBuffRemainingSeconds = clickBuffs.maxBy(_.time).time / Game.fps
    if cpsIncrease * longestBuffRemainingSeconds > price then cpsIncrease else 0
}
