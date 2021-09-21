package cookieclicker.buyables

import cookieclicker.buyables.GameBuyable

import scala.scalajs.js

@js.native
trait GameAchievement extends GameBuyable {
  var won: 0 | 1
}
