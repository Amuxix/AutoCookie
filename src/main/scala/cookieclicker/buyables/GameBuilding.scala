package cookieclicker.buyables

import cookieclicker.Minigame
import cookieclicker.buyables.GameBuyable

import scala.scalajs.js

@js.native
trait GameBuilding extends GameBuyable {
  val amount: Double
  val level: Double
  val baseCps: Double
  val storedTotalCps: Double
  val minigame: js.UndefOr[Minigame]
  val minigameLoaded: Boolean

  def getPrice(): Double
  def getSumPrice(missingAmount: Int): Double
  def buy(amount: Int): Unit
}
