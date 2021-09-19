package autocookie.buyable

import autocookie.{AutoCookie, CPSCalculator, EmptyInvestment, Helpers, Investment, Logger, StockMarket}
import autocookie.Helpers.*
import autocookie.Helpers.given
import autocookie.buyable.{Building, BuildingRequirement}
import autocookie.buyable.upgrade.Upgrade
import autocookie.buyable.traits.*
import autocookie.buyable.Achievement
import typings.cookieclicker.Game.{GameObject as GameBuilding, PseudoBoolean, Achievement as GameAchievement, Upgrade as GameUpgrade}
import typings.cookieclicker.global.Game

import scala.concurrent.duration.*
import scala.scalajs.js.{Date, undefined}

object Buyable {
  def calculatePayback(price: Double, cpsIncrease: Double): Double =
    val cps = Helpers.cps
    if cpsIncrease == 0 || cps == 0 then
      Double.PositiveInfinity
    else
      val payback = price / cpsIncrease + Math.max(0, price + AutoCookie.reserve.reserveAmount - Game.cookies) / cps
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

  def estimatedReturnPercent(newBrokers: Int): Double = ???

  /**
   * Calculates the price to buy this and all its requirements if any.
   */
  protected def calculatePrice(buildingRequirements: Set[BuildingRequirement], upgradeRequirements: Set[Upgrade])
  : Double =
    val buildingsPrice = buildingRequirements.sumBy { requirement =>
      requirement.gameBuyable.getSumPrice(requirement.missingAmount)
    }
    val upgradesPrice = upgradeRequirements.sumBy(_.gameBuyable.getPrice())
    buildingsPrice + upgradesPrice

  protected def calculateCpsIncrease(buildingRequirements: Set[BuildingRequirement],
    upgradeRequirements: Set[Upgrade], achievmentRequirements: Set[Achievement]): Double =
    val extraMilk = 0.04 * achievmentRequirements.size
    val multiplier = Game.globalCpsMult / Helpers.getKittenMultiplier(Game.milkProgress) * getKittenMultiplier(Game
      .milkProgress + extraMilk
    )
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

  def update(debug: Boolean = false): Unit =
    val upgradeRequirements: Set[Upgrade] = calculateUpgradeRequirements
    if debug then println(s"upgradeRequirements: $upgradeRequirements")
    val buildingRequirements: Set[BuildingRequirement] = calculateBuildingRequirements(upgradeRequirements)
    if debug then println(s"buildingRequirements: $buildingRequirements")
    val achievmentUnlocks: Set[Achievement] = calculateAchievmentUnlocks(upgradeRequirements)
    if debug then println(s"achievmentUnlocks: $achievmentUnlocks")

    val thisBuilding = this match {
      case building: Building => Set(BuildingRequirement(building.gameBuyable, building.amount + 1))
      case _                  => Seq.empty
    }

    requirements = upgradeRequirements ++ buildingRequirements ++ achievmentUnlocks
    price = calculatePrice(buildingRequirements ++ thisBuilding, upgradeRequirements)
    if debug then println(s"price: $price")
    cpsIncrease = calculateCpsIncrease(buildingRequirements ++ thisBuilding, upgradeRequirements, achievmentUnlocks)
    if debug then println(s"cpsIncrease: $cpsIncrease")
    payback = calculatePayback(price, cpsIncrease)
    if debug then println(s"payback: $payback")

  private def cookiesNeeded: Double =
    AutoCookie.reserve.reserveAmount + price - Game.cookies - investment.estimatedReturns

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

    var buySeconds = (cookiesNeeded / cps).seconds

    /*val cpsBuffs = getBuffs().filter(buff => typeof buff.multCpS != "undefined" && buff.multCpS !== 0)
    if (cpsBuffs.length > 0) {
      val buffedNextBuyTime = cookiesNeeded / cps
      val shortBuffs = cpsBuffs.filter(buff => buff.time / Game.fps < buffedNextBuyTime)
      if (shortBuffs.length > 0) {
        //Time the cpsBuffs will save us
        val shortBuffsTime = shortBuffs.reduce((acc, buff) => acc + (buff.time / Game.fps), 0)
        val shortBuffsPower = shortBuffs.reduce((acc, buff) => acc * buff.multCpS, 1)
        buySeconds = shortBuffsTime + (buySeconds - shortBuffsTime) * shortBuffsPower
      }
    }*/
    Date.now() + buySeconds.toMillis

  def resetOriginalBuyTime(): Unit =
    originalBuyTime = 0
    if (this.nextMilestone != this) {
      nextMilestone.resetOriginalBuyTime()
    }

  def updateInvestmentAndBuyTime(): Unit =
    investment = StockMarket.createInvestment(this, Game.cookies - price)
    buyTime = calculateBuyTime
    if originalBuyTime == 0 then originalBuyTime = buyTime
    if nextMilestone != this then nextMilestone.updateInvestmentAndBuyTime()

  def cpsIncreasePercentText =
    if this.percentCpsIncrease > 0 then s"(+${math.round(this.percentCpsIncrease * 100)}%)" else ""

  private def buyTimeDifference: Double =
    originalBuyTime - buyTime

  def timeSavedText: String =
    val millisChange = buyTimeDifference
    val absMillisChange = math.abs(millisChange)
    if absMillisChange < 1000 then
      return ""
    else
      val symbol = if millisChange >= 0 then "" else "-"
      s"($symbol${absMillisChange.millis.prettyPrint})"

  def timeSavedTextColour: String =
    if math.abs(buyTimeDifference) >= 0 then "#6F6" else "#F66"

  /*
  def estimatedReturnPercent(additionalBrokers: Double): Double = {
    val overhead = StockMarket.overhead * Math.pow(0.95, additionalBrokers)
    1 + (1 - STOCK_MARKET_STABILITY_THRESHOLD) * (this.percentCpsIncrease - overhead)
  }
  */
}

