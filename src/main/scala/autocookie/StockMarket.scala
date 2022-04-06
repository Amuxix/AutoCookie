package autocookie

import autocookie.buyable.{Building, Buyable}
import autocookie.Helpers.*
import cookieclicker.stockmarket.{Good, StockMarket as GameStockMarket}
import cookieclicker.Game
import cookieclicker.global.Beautify
import org.scalajs.dom.{console, document}
import org.scalajs.dom.raw.HTMLElement

import scala.concurrent.duration.*
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}

@JSExportTopLevel("StockMarket")
@JSExportAll
object StockMarket extends AutoSaveable {
  val MAX_STD_DEV = 0.01
  val MAX_ABS_AVG = 0.003
  val STABILITY_MIN_PRICES = 5
  val STABILITY_MAX_PRICES = 10

  private def minigame: GameStockMarket = Building.bank.minigame.get.asInstanceOf[GameStockMarket]

  def sell(good: Good, amount: Int): Double =
    if minigame.sellGood(good.id, amount) then
      val price = good.price * amount
      Logger.log(s"Sold ${amount} ${good.name}($$${good.price.round(2)}) for ${Beautify(price)}")
      price
    else
      0

  def buy(good: Good, amount: Int): Double =
    if minigame.buyGood(good.id, amount) then
      val price = good.price * amount
      Logger.log(s"Bought ${amount} ${good.name}($$${good.price.round(2)}) for ${Beautify(price)}")
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

  private def isProfitable(good: Good, percentIncrease: Double): Boolean =
    val prices = good.prices.toSeq.slice(0, STABILITY_MAX_PRICES)
    val percentageDifferences = prices.sliding(2).collect {
      case Seq(newest, oldest) => newest / oldest - 1
    }.toSeq
    val weightedPercentageDifferences = percentageDifferences.zipWithIndex.map((diff, id) => diff * math.pow(.972, id))
    val stdDev = weightedPercentageDifferences.stdDev
    val avg = weightedPercentageDifferences.sum / weightedPercentageDifferences.length

    val isTrendingUp = percentageDifferences.count(_ >= 0) / percentageDifferences.length.toDouble > 0.65 && avg > 0
    val maxAverage = math.max(MAX_ABS_AVG, (percentIncrease - overhead) / 2)
    val isTrendingDownSlowlyAdnSteady = math.abs(avg) <= maxAverage && stdDev <= MAX_STD_DEV
    isTrendingUp || isTrendingDownSlowlyAdnSteady

  def investableGoods(buyable: Buyable): Seq[Good] = minigame.goods.filter { good =>
    good.active && good.prices.length >= STABILITY_MIN_PRICES && isProfitable(good, buyable.percentCpsIncrease)
  }.toSeq

  def isActive: Boolean = Building.bank.amount > 0 && Building.bank.level > 0 && Building.bank.minigameLoaded

  def moneyToCookies(money: Double, percentCpsIncrease: Double = 0): Double =
    val cps = if percentCpsIncrease == 0 then Game.cookiesPsRawHighest else Game.unbuffedCps * percentCpsIncrease
    money * cps

  //def moneyToCookies(money: Double, cps: Double = Game.cookiesPsRawHighest): Double = money * cps

  def timeToNextTick: FiniteDuration = 60.seconds - (StockMarket.minigame.tickT / Game.ticksPerSec).seconds

  def createInvestment(buyable: Buyable, cookies: Double): Investment =
    if StockMarket.isActive && investableGoods(buyable).nonEmpty && cookies > 0 then RealInvestment(buyable, cookies) else EmptyInvestment


  override val version: Float = 1

  override def autoSave: String = minigame.goods.map(_.prices.mkString(", ")).mkString("|")

  override def autoLoad(string: String, version: Float): Unit =
    import scalajs.js.JSConverters.*
    string.split("\\|").map(_.split(", ").map(_.toDouble)) match {
      case Array() => () //Nothing to load
      case prices =>
        minigame.goods.zip(prices).foreach {
          case (good, prices) => good.prices = prices.toJSArray
        }
    }
}