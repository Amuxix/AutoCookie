package cookieclicker.buyables

import autocookie.Season
import cookieclicker.buyables.GameBuyable

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@js.native
trait GameUpgrade extends GameBuyable {
  val unlocked: 0 | 1
  val cookies: Double
  val require: js.UndefOr[String]
  @JSName("season")
  val gameSeason: js.UndefOr[String]
  val bought: 0 | 1
  val power: Double | js.Function1[GameUpgrade, Double]

  def getPrice(): Double
  def buy(boolean: Boolean): Unit
}
