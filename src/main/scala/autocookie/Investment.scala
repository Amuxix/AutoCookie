package autocookie

import autocookie.buyable.Buyable
import autocookie.Helpers.sumBy
import cookieclicker.Game
import cookieclicker.stockmarket.Good

object Investment {
  def apply(buyable: Buyable, cookies: Double): Investment =
    if (StockMarket.isActive && cookies > 0) {RealInvestment(buyable, cookies) }
    else {EmptyInvestment }
}

sealed trait Investment {
  def estimatedReturnPercent: Double
  /** Estimates the profit we would make if we ran this investment
   */
  def estimatedReturns: Double

  /** @returns {number} The total cookies invested
   */
  def invest(): Double

  /** @returns {number} The total cookies gotten from selling this investment
   */
  def liquidateInvestment(): Double
}

object EmptyInvestment extends Investment {

  override def estimatedReturnPercent: Double = 1

  /** Estimates the profit we would make if we ran this investment
   */
  override def estimatedReturns: Double = 0

  /** @returns {Double} The total cookies invested
   */
  override def invest(): Double = 0

  /** @returns {Double =} The total cookies gotten from selling this investment
   */
  override def liquidateInvestment(): Double = 0
}

case class GoodInvestment(good: Good, amount: Int, cost: Double) {
  lazy val icon = (good.icon(0), good.icon(1))
}

object RealInvestment {
  def apply(buyable: Buyable, cookies: Double): RealInvestment = {
    val goods = StockMarket.investableGoods(buyable).sortBy(_.price)(Ordering[Double].reverse)
    val fullStockPrice = goods.foldLeft(0D) { case (total, good) =>
      val price = good.price
      val stockToFillWarehouse = StockMarket.maxStock(good) - good.stock
      total + price * stockToFillWarehouse
    }
    val startingMoney = StockMarket.cookiesToMoney(cookies)
    val brokersToBuy = StockMarket.calculateBrokersToBuy(startingMoney min fullStockPrice)
    val brokersCost = brokersToBuy * 1200
    val money = startingMoney - brokersCost

    val zero = (money, Seq(() => StockMarket.buyBrokers(brokersToBuy)), Seq.empty[() => Double], Seq.empty[GoodInvestment])
    val (remainingMoney, buyFunctions, sellFunctions, goodsInvested) = goods.foldLeft(zero) {
      case (acc @ (remainingMoney, buyFunctions, sellFunctions, goodsInvested), good) =>
        val price = good.price
        val stockToFillWarehouse = StockMarket.maxStock(good) - good.stock.toInt
        val stockToBuy = stockToFillWarehouse min Math.floor(remainingMoney / price).toInt
        if stockToBuy > 0 then
          def buyFunction(): Unit = StockMarket.buy(good, stockToBuy)
          def sellFunction(): Double = StockMarket.sell(good, stockToBuy)
          val totalPrice = stockToBuy * price
          val goodInvestment = GoodInvestment(good, stockToBuy, totalPrice)
          (remainingMoney - totalPrice, buyFunctions :+ buyFunction, sellFunctions :+ sellFunction, goodsInvested :+ goodInvestment)
        else
          acc
    }
    new RealInvestment(
      buyable,
      moneyInvestedInStocks = money - remainingMoney,
      moneyInvestedInBrokers = brokersCost,
      brokersToBuy,
      buyFunctions,
      sellFunctions,
      goodsInvested
    )
  }
}

case class RealInvestment private(
  buyable: Buyable,
  moneyInvestedInStocks: Double,
  moneyInvestedInBrokers: Double,
  newBrokers: Int,
  buyFunctions: Seq[() => Unit],
  sellFunctions: Seq[() => Double],
  goodsInvested: Seq[GoodInvestment]
) extends Investment {
  lazy val totalMoneyInvestment: Double = moneyInvestedInStocks + moneyInvestedInBrokers
  lazy val totalCookieInvestment: Double = StockMarket.moneyToCookies(totalMoneyInvestment)

  override def estimatedReturnPercent: Double = buyable.estimatedReturnPercent(newBrokers)

  /** Estimates the profit we would make if we ran this investment
   */
  override def estimatedReturns: Double = StockMarket.moneyToCookies(moneyInvestedInStocks) * estimatedReturnPercent - totalCookieInvestment

  /** @returns {number} The total cookies invested
   */
  override def invest(): Double =
    buyFunctions.foreach(_())
    totalCookieInvestment

  /** @returns {number} The total cookies gotten from selling this investment
   */
  override def liquidateInvestment(): Double =
    StockMarket.moneyToCookies(sellFunctions.sumBy(_ ()))
}
