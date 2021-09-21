package autocookie

import autocookie.buyable.{Building, Buyable}
import cookieclicker.stockmarket.Good
import cookieclicker.Game

import scala.concurrent.duration.*

object StockMarket {
  //private def game: StocksMinigame = Building.bank.minigame.get.asInstanceOf[StocksMinigame]

  def sell(good: Good, amount: Int): Double = ???

  def buy(good: Good, amount: Int): Unit = ???

  def buyBrokers(brokersToBuy: Int): Unit = ???

  def calculateBrokersToBuy(d: Double): Int = ???

  def cookiesToMoney(cookies: Double): Double = ???

  def maxStock(good: Good): Int = ???

  def price(good: Good): Double = ???

  def stableActiveGoods: Seq[Good] = ???

  def isActive: Boolean = false

  def moneyToCookies(totalInvestment: Double, cps: Double = Game.unbuffedCps): Double = ???

  //def timeToNextTick: FiniteDuration = 60.seconds - (StockMarket.game.tickT / Game.fps).seconds
  def timeToNextTick: FiniteDuration = ???

  def createInvestment(buyable: Buyable, cookies: Double): Investment =
    if StockMarket.isActive && cookies > 0 then RealInvestment(buyable, cookies) else EmptyInvestment

}

case class StockMarket()
