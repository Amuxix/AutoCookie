package autocookie

import autocookie.buyable.{Building, Buyable}
import autocookie.StockMarket.*
import autocookie.Helpers.*
import cookieclicker.stockmarket.{Good, StockMarket as GameStockMarket}
import cookieclicker.Game
import cookieclicker.global.Beautify
import org.scalajs.dom.document
import org.scalajs.dom.raw.HTMLElement

import scala.concurrent.duration.*

object StockMarket {
  val MAX_STD_DEV = 0.01
  val STABILITY_THRESHOLD = 0.03
  val STABILITY_MIN_PRICES = 5
  val STABILITY_MAX_PRICES = 10

  private def minigame: GameStockMarket = Building.bank.minigame.get.asInstanceOf[GameStockMarket]

  def sell(good: Good, amount: Int): Double =
    if minigame.sellGood(good.id, amount) then
      val price = StockMarket.price(good) * amount
      Logger.log(s"Sold ${amount} ${good.name} for ${Beautify(price)}")
      price
    else
      0

  def buy(good: Good, amount: Int): Double =
    if minigame.buyGood(good.id, amount) then
      val price = StockMarket.price(good) * amount
      Logger.log(s"Bought ${amount} ${good.name} for ${Beautify(price)}")
      price
    else
      0
      
  def brokers: Int = minigame.brokers

  def maxBrokers: Int = minigame.getMaxBrokers()

  def buyBrokers(brokersToBuy: Int): Unit =
    (0 to brokersToBuy).foreach { _ =>
      document.getElementById("bankBrokersBuy").asInstanceOf[HTMLElement].click()
    }

  def calculateOverhead(brokers: Int): Double = 0.2 * math.pow(0.95, brokers)

  def overhead: Double = calculateOverhead(brokers)

  def calculateBrokersToBuy(stockPrice: Double): Int =
    val increase = .25 //This simulates an upgrade, this can be any value above the current overhead
    if (StockMarket.brokers == StockMarket.maxBrokers) return 0
    val maxNewBrokers = StockMarket.maxBrokers - StockMarket.brokers

    def calculateProfit(brokers: Int): Double =
      stockPrice * (increase - StockMarket.calculateOverhead(StockMarket.brokers + brokers)) - brokers * 1200

    (0 to maxBrokers).maxBy(calculateProfit)

  def cookiesToMoney(cookies: Double): Double = cookies / Game.cookiesPsRawHighest

  def maxStock(good: Good): Int = minigame.getGoodMaxStock(good)

  def price(good: Good): Double = minigame.getGoodPrice(good)

  private def isGoodStableOrTrendingUp(good: Good): Boolean =
    val prices = good.vals.toSeq//.slice(0, STABILITY_MAX_PRICES)
    val percentageDifferences = prices.sliding(2).collect {
      case Seq(v1, v2) => (v2 - v1) / v1
    }.toSeq
    val weightedPercentageDifferences = percentageDifferences.zipWithIndex.map((diff, id) => diff * math.pow(.972, id))
    val stdDev = weightedPercentageDifferences.stdDev
    val avg = weightedPercentageDifferences.sum / weightedPercentageDifferences.length

    val isTrendingUp = percentageDifferences.count(_ >= 0) / percentageDifferences.length.toDouble > 0.8
    val isStable = avg >= 0 && avg < STABILITY_THRESHOLD && stdDev < MAX_STD_DEV
    return isTrendingUp || isStable

  def stableActiveGoods: Seq[Good] = minigame.goods.filter { good =>
    good.active && good.vals.length >= STABILITY_MIN_PRICES && isGoodStableOrTrendingUp(good)
  }.toSeq

  def isActive: Boolean = Building.bank.amount > 0 && Building.bank.level > 0 && Building.bank.minigameLoaded

  def moneyToCookies(money: Double, cps: Double = Game.cookiesPsRawHighest): Double = money * cps

  def timeToNextTick: FiniteDuration = 60.seconds - (StockMarket.minigame.tickT / Game.fps).seconds

  def createInvestment(buyable: Buyable, cookies: Double): Investment =
    if StockMarket.isActive && cookies > 0 then RealInvestment(buyable, cookies) else EmptyInvestment

}

case class StockMarket()
