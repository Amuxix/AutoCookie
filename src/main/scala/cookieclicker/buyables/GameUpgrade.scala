package cookieclicker.buyables

import cookieclicker.buyables.GameBuyable

import scala.scalajs.js

@js.native
trait GameUpgrade extends GameBuyable {
  var unlocked: 0 | 1
  var cookies: Double
  var require: js.UndefOr[String]
  var season: js.UndefOr[String]
  var bought: 0 | 1
  var power: Double | js.Function1[GameUpgrade, Double]

  def getPrice(): Double
  def buy(boolean: Boolean): Unit
}
