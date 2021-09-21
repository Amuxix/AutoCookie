package autocookie.buyable.traits

import autocookie.buyable.upgrade.Upgrade
import cookieclicker.buyables.GameUpgrade

import scala.scalajs.js

trait CookiePower {
  this: Upgrade =>
  protected def power: Double =
    if gameBuyable.power.isInstanceOf[Double] then
      gameBuyable.power.asInstanceOf[Double]
    else
      gameBuyable.power.asInstanceOf[js.Function1[GameUpgrade, Double]](gameBuyable)

  lazy val powerMultiplier: Double = 1 + power / 100
}
