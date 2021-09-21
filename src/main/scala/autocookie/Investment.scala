package autocookie

import autocookie.buyable.Buyable
import autocookie.Helpers.sumBy
import cookieclicker.Game

object Investment {
  def apply(buyable: Buyable, cookies: Double): Investment =
    if (StockMarket.isActive && cookies > 0) {RealInvestment(buyable, cookies) }
    else {EmptyInvestment }
}

sealed trait Investment {
  /** Estimates the profit we would make if we ran this investment
   */
  def estimatedReturns: Double

  /** @returns {number} The total cookies invested
   */
  def invest(): Double

  /** @returns {number} The total cookies gotten from selling this investment
   */
  def sellInvestment(): Double
}

object EmptyInvestment extends Investment {
  /** Estimates the profit we would make if we ran this investment
   */
  def estimatedReturns: Double = 0

  /** @returns {Double} The total cookies invested
   */
  def invest(): Double = 0

  /** @returns {Double =} The total cookies gotten from selling this investment
   */
  def sellInvestment(): Double = 0
}

object RealInvestment {
  def apply(buyable: Buyable, cookies: Double): RealInvestment = {
    val goods = StockMarket.stableActiveGoods.sortBy(_.value)(Ordering[Double].reverse)
    val fullStockPrice = goods.foldLeft(0D) { case (total, good) =>
      val price = StockMarket.price(good)
      val stockToFillWarehouse = StockMarket.maxStock(good) - good.stock
      total + price * stockToFillWarehouse
    }
    val startingMoney = StockMarket.cookiesToMoney(cookies)
    val brokersToBuy = StockMarket.calculateBrokersToBuy(startingMoney min fullStockPrice)
    val brokersCost = brokersToBuy * 1200
    val money = startingMoney - brokersCost

    val zero = (money, Seq(() => StockMarket.buyBrokers(brokersToBuy)), Seq.empty[() => Double])
    val (remainingMoney, buyFunctions, sellFunctions) = goods.foldLeft(zero) {
      case (acc@(remainingMoney, buyFunctions, sellFunctions), good) =>
        val price = StockMarket.price(good)
        val stockToFillWarehouse = StockMarket.maxStock(good) - good.stock.toInt
        val stockToBuy = stockToFillWarehouse min Math.floor(remainingMoney / price).toInt
        if (stockToBuy > 0) {
          val buyFunction = () => StockMarket.buy(good, stockToBuy)
          val sellFunction = () => StockMarket.sell(good, stockToBuy)
          (remainingMoney - stockToBuy * price, buyFunctions :+ buyFunction, sellFunctions :+ sellFunction)
        } else {
          acc
        }
    }
    new RealInvestment(
      buyable,
      moneyInvestedInStocks = money - remainingMoney,
      moneyInvestedInBrokers = brokersCost,
      brokersToBuy,
      buyFunctions,
      sellFunctions,
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
) extends Investment {
  lazy val totalInvestment: Double = moneyInvestedInStocks + moneyInvestedInBrokers
  lazy val cookieInvestment: Double = StockMarket.moneyToCookies(totalInvestment)

  /** Estimates the profit we would make if we ran this investment
   */
  def estimatedReturns: Double = {
    val estimatedReturnPercent = buyable.estimatedReturnPercent(newBrokers)
    val cpsAfterBuying = Game.unbuffedCps * buyable.percentCpsIncrease
    val returns = StockMarket.moneyToCookies(moneyInvestedInStocks, cpsAfterBuying) * estimatedReturnPercent
    returns - this.cookieInvestment
  }

  /** @returns {number} The total cookies invested
   */
  def invest(): Double = {
    buyFunctions.foreach(_ ())
    cookieInvestment
  }

  /** @returns {number} The total cookies gotten from selling this investment
   */
  def sellInvestment(): Double =
    StockMarket.moneyToCookies(sellFunctions.sumBy(_ ()))
}
