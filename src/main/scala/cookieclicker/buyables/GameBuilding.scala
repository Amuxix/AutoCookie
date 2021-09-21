package cookieclicker.buyables

import cookieclicker.buyables.GameBuyable

import scala.scalajs.js

@js.native
trait GameBuilding extends GameBuyable {
  var amount: Double
  var level: Double
  var baseCps: Double
  var storedTotalCps: Double

  def getPrice(): Double
  def getSumPrice(missingAmount: Int): Double
  def buy(amount: Int): Unit
}
