package cookieclicker.stockmarket

import cookieclicker.Minigame

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@js.native
trait StockMarket extends Minigame {
  val tickT: Int
  val brokers: Int
  @JSName("goodsById")
  val goods: js.Array[Good]

  def getMaxBrokers(): Int
  def getGoodMaxStock(good: Good): Int
  def getGoodPrice(good: Good): Double
  def buyGood(goodID: Int, amount: Int): Boolean
  def sellGood(goodID: Int, amount: Int): Boolean

}
