package autocookie.buyable.upgrade

import autocookie.AutoCookie
import autocookie.Helpers
import autocookie.Helpers
import autocookie.Helpers.{amountOfNonCursors, hasOrIsChoice, toBoolean}
import autocookie.buyable.{Achievement, BuildingRequirement}
import org.scalajs.dom.console
import cookieclicker.Buff
import cookieclicker.Game

import scala.language.implicitConversions
import scala.scalajs.js
import scala.util.Try

object MouseUpgrade {
  def clickBuffs = Game.buffs.toArray.filter(_.multCpS.exists(_ > 0))
}

class MouseUpgrade(override val name: String) extends Upgrade {
  override protected def calculateCpsIncrease(
    buildingRequirements: Set[BuildingRequirement],
    upgradeRequirements: Set[Upgrade],
    achievmentRequirements: Set[Achievement]
  ): Double =
    0 //TODO fix this when I figgure out buffs or fix the ts definitions
  /*val hasFrenzy = Try(Game.hasBuff("Click frenzy").asInstanceOf[cookieclickerNumbers.`0`]).fold(_ => true, _ => false)
  println(s"Has Click frenzy $hasFrenzy")
  if (!hasFrenzy) return Double.PositiveInfinity //Only consider these upgrades if we have a buff
  println("fingerAdd")
  lazy val fingerAdd: Map[String, Double] = Map(
    //"Thousand" -> 0.1,
    "Million fingers" -> 5,
    "Billion fingers" -> 10,
    "Trillion fingers" -> 20,
    "Quadrillion fingers" -> 20,
    "Quintillion fingers" -> 20,
    "Sextillion fingers" -> 20,
    "Septillion fingers" -> 20,
    "Octillion fingers" -> 20,
    "Nonillion fingers" -> 20,
  )
  println("fingersAdd")
  val fingersAdd = if (Game.Has("Thousand fingers")) then
    amountOfNonCursors * fingerAdd.foldLeft(0.1) {
      case (totalAdd, (name, multiplier)) if Game.Has(name) => totalAdd * multiplier
      case (totalAdd, _) => totalAdd
    }
  else
    0

  val mouses = List(
    "Plastic mouse",
    "Iron mouse",
    "Titanium mouse",
    "Adamantium mouse",
    "Unobtainium mouse",
    "Eludium mouse",
    "Wishalloy mouse",
    "Fantasteel mouse",
    "Nevercrack mouse",
    "Armythril mouse",
    "Technobsidian mouse",
    "Plasmarble mouse",
  )
  println("mousesAdd")
  val mousesAdd = mouses.count(mouse => hasOrIsChoice(mouse, Some(name))) * Game.cookiesPs * 0.01
  val multipliers = List(
    "Reinforced index finger",
    "Carpal tunnel prevention cream",
    "Ambidextrous"
  )
  println("clickMultiplier")
  val clickMultiplier = math.pow(2, multipliers.count(Game.Has(_)))
  println("buffMultipliers")
  val buffMultipliers = MouseUpgrade.clickBuffs.foldLeft(1D)((total, buff) => total * buff.arg1.getOrElse(1D))

  (clickMultiplier + fingersAdd) * buffMultipliers - Game.mouseCps() * AutoCookie.CLICKS_PER_SEC*/
}
