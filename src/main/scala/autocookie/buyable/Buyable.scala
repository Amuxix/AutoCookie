package autocookie.buyable

import autocookie.*
import autocookie.Helpers.*
import autocookie.buyable.{Building, BuildingRequirement}
import autocookie.buyable.upgrade.Upgrade
import autocookie.buyable.traits.*
import autocookie.buyable.Achievement
import autocookie.reserve.Reserve
import cookieclicker.buyables.*
import cookieclicker.Game

import scala.concurrent.duration.*
import scala.scalajs.js.{Date, undefined}

object Buyable {
  def calculatePayback(price: Double, cpsIncrease: Double): Double =
    val cps = Helpers.cps
    if cpsIncrease == 0 || cps == 0 then
      Double.PositiveInfinity
    else
      val payback = price / cpsIncrease + Math.max(0, price + Reserve.amount - Game.cookies) / cps
      payback.round(6)
}

abstract class Buyable {
  type T <: GameBuyable
  def gameBuyable: T
  val name: String

  lazy val canEventuallyGet: Boolean = true
  var price: Double = 0
  var cpsIncrease: Double = 0
  var payback: Double = 0
  var investment: Investment = EmptyInvestment
  var buyTime: Double = 0
  var originalBuyTime: Double = 0
  var requirements: Set[Buyable] = Set.empty

  def estimatedReturnPercent(newBrokers: Int): Double =
    val overhead = StockMarket.overhead * math.pow(0.95, newBrokers)
    1 + (1 - StockMarket.MAX_ABS_AVG - 2 * StockMarket.MAX_STD_DEV) * (percentCpsIncrease - overhead)

  /**
   * Calculates the price to buy this and all its requirements if any.
   */
  protected def calculatePrice(buildingRequirements: Set[BuildingRequirement], upgradeRequirements: Set[Upgrade]): Double =
    val buildingsPrice = buildingRequirements.sumBy { requirement =>
      requirement.gameBuyable.getSumPrice(requirement.missingAmount)
    }
    val upgradesPrice = upgradeRequirements.sumBy(_.gameBuyable.getPrice())
    buildingsPrice + upgradesPrice

  protected def calculateCpsIncrease(
    buildingRequirements: Set[BuildingRequirement],
    upgradeRequirements: Set[Upgrade],
    achievmentRequirements: Set[Achievement]
  ): Double =
    val extraMilk = 0.04 * achievmentRequirements.size
    val multiplier = Game.globalCpsMult / Helpers.getKittenMultiplier(Game.milkProgress) * getKittenMultiplier(Game.milkProgress + extraMilk)
    val cps = Helpers.cps
    val baseCps = cps / Game.globalCpsMult
    val achievmentCpsIncrease = ((baseCps * multiplier) - cps)
    (CPSCalculator(buildingRequirements, upgradeRequirements) + achievmentCpsIncrease).round(6)

  protected def calculatePayback(price: Double, cpsIncrease: Double): Double =
    Buyable.calculatePayback(price, cpsIncrease)

  private def calculateUpgradeRequirements: Set[Upgrade] =
    val thisUpgrade: Set[Upgrade] = this match {
      case upgrade: Upgrade => Set(upgrade)
      case _                => Set.empty
    }
    val upgradesRequired: Set[Upgrade] = this match {
      case upgradesRequired: UpgradesRequired => upgradesRequired.upgradeRequirements
      case _                                  => Set.empty
    }
    (thisUpgrade ++ upgradesRequired).filter(!_.owned)

  private def calculateBuildingRequirements(upgradeRequirements: Set[Upgrade]): Set[BuildingRequirement] =
    val thisRequirements: Set[BuildingRequirement] = this match {
      case buildingsRequirement: BuildingRequirement =>
        Set(buildingsRequirement)
      case buildingsRequired: BuildingsRequired      =>
        buildingsRequired.buildingRequirements
      case _                                         =>
        Set.empty
    }
    val fromUpgrades: Set[BuildingRequirement] = upgradeRequirements.collect {
      case buildingsRequired: BuildingsRequired => buildingsRequired.buildingRequirements
    }.flatten
    (thisRequirements ++ fromUpgrades).filter(req => req.gameBuyable.amount < req.amount)

  private def calculateAchievmentUnlocks(upgradeRequirements: Set[Upgrade]): Set[Achievement] =
    val thisAchievement: Set[Achievement] = this match {
      case achievement: Achievement => Set(achievement)
      case _                        => Set.empty
    }

    val fromUpgrades: Set[Achievement] = upgradeRequirements.collect {
      case unlocksAchievment: UnlocksAchievment => unlocksAchievment.achievmentsUnlocked
    }.flatten
    (thisAchievement ++ fromUpgrades).filter(!_.won)

  lazy val semiFinalUpgradeRequirements: Set[Upgrade] = calculateUpgradeRequirements
  lazy val semiFinalBuildingRequirements: Set[BuildingRequirement] = calculateBuildingRequirements(semiFinalUpgradeRequirements)
  lazy val semiFinalAchievmentUnlocks: Set[Achievement] = calculateAchievmentUnlocks(semiFinalUpgradeRequirements)

  def update(debug: Boolean = false): Unit =
    val upgradeRequirements: Set[Upgrade] = semiFinalUpgradeRequirements.filter(!_.owned)
    val buildingRequirements: Set[BuildingRequirement] = semiFinalBuildingRequirements.filter(req => req.gameBuyable.amount < req.amount)
    val achievmentUnlocks: Set[Achievement] = semiFinalAchievmentUnlocks.filter(!_.won)

    val thisBuilding = this match {
      case building: Building => Set(BuildingRequirement(building.gameBuyable, building.amount + 1))
      case _                  => Set.empty
    }

    requirements = upgradeRequirements ++ buildingRequirements ++ achievmentUnlocks
    price = calculatePrice(buildingRequirements ++ thisBuilding, upgradeRequirements)
    if debug then println(s"price: $price")
    cpsIncrease = calculateCpsIncrease(buildingRequirements ++ thisBuilding, upgradeRequirements, achievmentUnlocks)
    if debug then println(s"cpsIncrease: $cpsIncrease")
    payback = calculatePayback(price, cpsIncrease)
    if debug then println(s"payback: $payback")

  private def cookiesNeeded: Double =
    Reserve.amount + price - Game.cookies - (investment.estimatedReturns max 0)

  def percentCpsIncrease: Double = this.cpsIncrease / Game.cookiesPs

  final def nextMilestone: Buyable = if requirements.isEmpty then this else requirements.minBy(_.payback)

  protected def removeFromBuyables(): Unit = AutoCookie.buyables = AutoCookie.buyables - this

  protected def innerBuy(): Unit

  final def buy(): Unit =
    AutoCookie.buying = true
    innerBuy()
    resetOriginalBuyTime()
    Game.CalculateGains()
    AutoCookie.buying = false

  def timeToBuy: FiniteDuration = ((buyTime - Date.now()) max 0).millis

  private def calculateBuyTime: Double =
    val cookiesNeeded = this.cookiesNeeded
    if (cookiesNeeded <= 0) return Date.now()

    val cpsBuffs = Helpers.buffs.filter(_.multCpS.exists(_ != 0))
    val buySeconds = if cpsBuffs.length > 0 then
      val sortedBuffs = cpsBuffs.map(buff => (buff.remainingTicks / Game.ticksPerSec, buff.multCpS.get)).sortBy(_._1)
      val (unbuffedCPS, remainingCookiesNeeded, currentBuySeconds) = sortedBuffs.foldLeft((cps, cookiesNeeded, 0D)) {
        case ((currentCPS, cookiesNeeded, buySeconds), (remainingBuffSeconds, buffPower)) if cookiesNeeded > 0 =>
          val CPSwithoutThisBuff = currentCPS / buffPower
          val remainingCookiesNeeded = cookiesNeeded - remainingBuffSeconds * currentCPS
          if remainingCookiesNeeded < 0 then
            val remainingBuySeconds = buySeconds + cookiesNeeded / currentCPS
            (currentCPS, 0, remainingBuySeconds)
          else
            (CPSwithoutThisBuff, remainingCookiesNeeded, buySeconds + remainingBuffSeconds)
        case (t, _) => t
      }
      currentBuySeconds + remainingCookiesNeeded / unbuffedCPS
    else
      cookiesNeeded / cps

    Date.now() + buySeconds * 1000

  def resetOriginalBuyTime(): Unit =
    originalBuyTime = 0
    if (this.nextMilestone != this) {
      nextMilestone.resetOriginalBuyTime()
    }

  protected def createInvestment: Investment = StockMarket.createInvestment(this, Game.cookies - price)

  def updateInvestmentAndBuyTime(): Unit =
    investment = createInvestment
    buyTime = calculateBuyTime
    if originalBuyTime == 0 then originalBuyTime = buyTime
    if nextMilestone != this then nextMilestone.updateInvestmentAndBuyTime()

  def cpsIncreasePercentText =
    if this.percentCpsIncrease > 0 then s" (+${(this.percentCpsIncrease * 100).round(2)}%)" else ""

  private def buyTimeDifference: Double = originalBuyTime - buyTime

  def timeSavedText: String =
    val millisChange = buyTimeDifference
    val absMillisChange = math.abs(millisChange)
    if absMillisChange < 1000 then
      return ""
    else
      val symbol = if millisChange >= 0 then "" else "-"
      s" ($symbol${absMillisChange.millis.prettyPrint})"

  def timeSavedTextColour: String =
    if buyTimeDifference >= 0 then "#6F6" else "#F66"

  /*
  def estimatedReturnPercent(additionalBrokers: Double): Double = {
    val overhead = StockMarket.overhead * Math.pow(0.95, additionalBrokers)
    1 + (1 - STOCK_MARKET_MAX_ABS_AVG) * (this.percentCpsIncrease - overhead)
  }
  */
}

