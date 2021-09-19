package autocookie

import autocookie.buyable.{Building, Buyable}
import typings.cookieclicker.Game.StocksGood
import typings.cookieclicker.global.Game
import typings.cookieclicker.Game.StocksMinigame

import scala.concurrent.duration.*

object StockMarket {
  private def game: StocksMinigame = Building.bank.minigame.get.asInstanceOf[StocksMinigame]

  def sell(good: StocksGood, amount: Int): Double = ???

  def buy(good: StocksGood, amount: Int): Unit = ???

  def buyBrokers(brokersToBuy: Int): Unit = ???

  def calculateBrokersToBuy(d: Double): Int = ???

  def cookiesToMoney(cookies: Double): Double = ???

  def maxStock(good: StocksGood): Int = ???

  def price(good: StocksGood): Double = ???

  def stableActiveGoods: Seq[StocksGood] = ???

  def isActive: Boolean = false

  def moneyToCookies(totalInvestment: Double, cps: Double = Game.unbuffedCps): Double = ???

  def timeToNextTick: FiniteDuration = 60.seconds - (StockMarket.game.tickT / Game.fps).seconds

  def createInvestment(buyable: Buyable, cookies: Double): Investment =
    if StockMarket.isActive && cookies > 0 then RealInvestment(buyable, cookies) else EmptyInvestment

}

case class StockMarket()
