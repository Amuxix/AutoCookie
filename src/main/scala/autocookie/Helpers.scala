package autocookie

import autocookie.buyable.{Achievement, Building, BuildingRequirement}
import autocookie.buyable.upgrade.Upgrade
import autocookie.notes.Note
import org.scalajs.dom.console
import cookieclicker.buyables.*
import cookieclicker.Game

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import scala.concurrent.duration.FiniteDuration
import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.JSConverters.*
import scala.util.{Random, Try}

object Helpers:
  //type GameBuyable = GameBuilding | GameUpgrade | GameAchievement

  //given pseudoToBoolean: Conversion[PseudoBoolean, Boolean] = _.eq(PseudoBoolean.`1`)

  //given Conversion[Boolean, PseudoBoolean] = b => if b then PseudoBoolean.`1` else PseudoBoolean.`0`

  /*implicit def toBoolean(b: Boolean | PseudoBoolean): Boolean =
    if b.isInstanceOf[Boolean] then
      b
    else
      pseudoToBoolean(b.asInstanceOf[PseudoBoolean])*/

  implicit def toBoolean(b: 0 | 1): Boolean = b match {
    case 0 => false
    case 1 => true
  }

  extension (number: Double)
    def round(precision: Int = 2): Double =
      val d = Math.pow(10, precision)
      Math.round(number * d) / d

  extension[T] (xs: Iterable[T])
    def sumBy[U](f: T => U)(implicit N: Numeric[U]): U = xs.foldLeft(N.zero)((acc, x) => N.plus(acc, f(x)))

  def unlockRequirements(): Unit = Game.UnlockAt.foreach { unlock =>
    val enoughCookies = Game.cookiesEarned >= unlock.cookies
    val notOwned = !unlock.require.fold(false)(req => !Game.upgradeBought(req) && !Game.achievementWon(req))
    val inSeason = !unlock.season.fold(false)(Game.season != _)
    if (enoughCookies && notOwned && inSeason) {
      Game.Unlock(unlock.name)
      Game.Win(unlock.name)
    }
  }

  extension (d: FiniteDuration)
    def prettyPrint: String =
      import scala.concurrent.duration._
      extension (string: String)
        def add(added: Option[String]): String =
          if string.isEmpty then added.getOrElse("") else added.fold(string)(added => s"$string $added")

      val days = d.toDays
      var duration = d - days.days
      val hours = duration.toHours
      duration -= hours.hours
      val minutes = duration.toMinutes
      duration -= minutes.minutes
      val seconds = duration.toSeconds
      "".add(Option.when(days > 0)(s"$days${if days == 0 then "day" else "days"}"))
        .add(Option.when(hours > 0)(s"${hours}h"))
        .add(Option.when(minutes > 0)(s"${minutes}m"))
        .add(Option.when(seconds >= 0)(s"${seconds}s"))

  def convertNumeral(number: Int): String =
    if number == 0 then return ""
    val numberString = number.toString
    val termination = numberString.dropRight(1).lastOption match {
      case Some('1') => "th"
      case _         =>
        numberString.lastOption match {
          case Some('1') => "st"
          case Some('2') => "nd"
          case Some('3') => "rd"
          case _         => "th"
        }
    }
    numberString + termination

  def amountOfNonCursors: Int = (Game.buildings.sumBy(_.amount) - Building.cursor.amount).toInt

  def hasOrIsChoice(upgradeName: String, choice: Option[String]): Boolean =
    val upgrade = Upgrade.getByName(upgradeName)
    upgrade.owned || choice.exists(_ == upgrade.name)

  def cps: Double = Game.cookiesPs * (1 - Game.cpsSucked)

/*  def getGodLevel(godName: String): Int =
    import cookieclickerNumbers._
    Game.hasGod.fold(0) { level =>
      Try(level.asInstanceOf[`false`]).map(_ => 0)
        .orElse(Try(level.asInstanceOf[`1`]).map(_ => 1))
        .orElse(Try(level.asInstanceOf[`2`]).map(_ => 2))
        .orElse(Try(level.asInstanceOf[`3`]).map(_ => 3))
        .getOrElse(0)
    }*/

  def getGodLevel(godName: String): Int =
    Game.hasGod(godName) match {
      case false => 0
      case 1 => 1
      case 2 => 2
      case 3 => 3
    }

  def getGodMultiplier(godName: String)(multipliers: Seq[Double]): Double =
    multipliers(getGodLevel(godName))

  lazy val kittenMultipliers: Map[String, Double] = Map(
    "Kitten helpers" -> 0.1,
    "Kitten workers" -> 0.125,
    "Kitten engineers" -> 0.15,
    "Kitten overseers" -> 0.175,
    "Kitten managers" -> 0.2,
    "Kitten accountants" -> 0.2,
    "Kitten specialists" -> 0.2,
    "Kitten experts" -> 0.2,
    "Kitten consultants" -> 0.2,
    "Kitten assistants to the regional manager" -> 0.175,
    "Kitten marketeers" -> 0.15,
    "Kitten analysts" -> 0.125,
    "Kitten angels" -> 0.1,
  )

  def getKittenMultiplier2(milk: Double, choice: Option[String] = None): Double = {
    val allKittensMultiplier = kittenMultipliers.foldLeft(1d) {
      case (acc, (name, value)) if hasOrIsChoice(name, choice) => acc * 1 + milk * value
      case (acc, _)                                                       => acc
    }
    val godMultiplier = getGodLevel("mother") match {
      case 1 => 1.1
      case 2 => 1.05
      case 3 => 1.03
      case _ => 1
    }
    allKittensMultiplier * godMultiplier
  }

  //Implementing this with a map and a fold is twice as slow
  def getKittenMultiplier(milk: Double, choice: Option[String] = None): Double =
    var multiplier = 1D;
    if hasOrIsChoice("Kitten helpers", choice) then multiplier *= 1 + milk * 0.1
    if hasOrIsChoice("Kitten workers", choice) then multiplier *= 1 + milk * 0.125
    if hasOrIsChoice("Kitten engineers", choice) then multiplier *= 1 + milk * 0.15
    if hasOrIsChoice("Kitten overseers", choice) then multiplier *= 1 + milk * 0.175
    if hasOrIsChoice("Kitten managers", choice) then multiplier *= 1 + milk * 0.2
    if hasOrIsChoice("Kitten accountants", choice) then multiplier *= 1 + milk * 0.2
    if hasOrIsChoice("Kitten specialists", choice) then multiplier *= 1 + milk * 0.2
    if hasOrIsChoice("Kitten experts", choice) then multiplier *= 1 + milk * 0.2
    if hasOrIsChoice("Kitten consultants", choice) then multiplier *= 1 + milk * 0.2
    if hasOrIsChoice("Kitten assistants to the regional manager", choice) then multiplier *= 1 + milk * 0.175
    if hasOrIsChoice("Kitten marketeers", choice) then multiplier *= 1 + milk * 0.15
    if hasOrIsChoice("Kitten analysts", choice) then multiplier *= 1 + milk * 0.125
    if hasOrIsChoice("Kitten angels", choice) then multiplier *= 1 + milk * 0.1

    multiplier * getGodMultiplier("decadence")(Seq(1, 1.1, 1.05, 1.03))
    /*val godLevel = getGodLevel("decadence")
    if (godLevel == 1) multiplier *= 1.1
    else if (godLevel == 2) multiplier *= 1.05
    else if (godLevel == 3) multiplier *= 1.03
    multiplier*/

  def timedRunWithResult[A](f: => A): (A, Double) =
    val start = Date.now()
    (f, Date.now() - start)

  def timedRun(f: => Unit): Double =
    timedRunWithResult(f)._2

  def profile[A](what: String)(f: => A): A =
    val (result, time) = timedRunWithResult(f)
    Logger.debug(s"$what took $time millis to run")
    result

  extension (gameAchievement: GameAchievement)
    def cloned: GameAchievement = ???
      /*GameAchievement(
        gameAchievement.baseDesc,
        gameAchievement.click,
        gameAchievement.desc,
        gameAchievement.disabled,
        gameAchievement.icon,
        gameAchievement.id,
        gameAchievement.name,
        gameAchievement.order,
        gameAchievement.pool,
        gameAchievement.toggle,
        gameAchievement.vanilla,
        gameAchievement.won,
      )*/


  extension (seq: Seq[Double])
    def stdDev: Double =
      val mean = seq.sum / seq.length
      val squared = seq.map(n => math.pow(n - mean, 2))
      math.sqrt(squared.sum / (squared.length - 1))

/*


def logMissingAchievements(): Unit = Game.AchievementsById.foreach(achievement => if (Achievement.findByName
(achievement.name).isEmpty) { console.log(achievement) })

def logMissingUpgrades(): Unit = Game.UpgradesById.foreach(upgrade => if (Upgrade.findByName(upgrade.name).isEmpty) {
 console.log(upgrade) })
*/
