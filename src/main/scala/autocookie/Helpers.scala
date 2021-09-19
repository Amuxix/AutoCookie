package autocookie

import autocookie.buyable.{Achievement, Building, BuildingRequirement}
import autocookie.buyable.upgrade.Upgrade
import autocookie.notes.Note
import org.scalajs.dom.console
import typings.cookieclicker.Game.{Buff, PseudoBoolean, Achievement as GameAchievement, GameObject as GameBuilding,
  Upgrade as GameUpgrade}
import typings.cookieclicker.cookieclickerBooleans.*
import typings.cookieclicker.{cookieclickerNumbers, cookieclickerStrings}
import typings.cookieclicker.global.Game

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import scala.concurrent.duration.FiniteDuration
import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.JSConverters.*
import scala.util.{Random, Try}

object Helpers:
  type GameBuyable = GameBuilding | GameUpgrade | GameAchievement

  given pseudoToBoolean: Conversion[PseudoBoolean, Boolean] = _.eq(PseudoBoolean.`1`)

  given Conversion[Boolean, PseudoBoolean] = b => if b then PseudoBoolean.`1` else PseudoBoolean.`0`

  implicit def toBoolean(b: Boolean | PseudoBoolean): Boolean =
    if b.isInstanceOf[Boolean] then
      b
    else
      pseudoToBoolean(b.asInstanceOf[PseudoBoolean])

  extension (number: Double)
    def round(precision: Int = 2): Double =
      val d = Math.pow(10, precision)
      Math.round(number * d) / d

  extension[T] (xs: Iterable[T])
    def sumBy[U](f: T => U)(implicit N: Numeric[U]): U = xs.foldLeft(N.zero)((acc, x) => N.plus(acc, f(x)))

  def unlockRequirements(): Unit = Game.UnlockAt.foreach { unlock =>
    val enoughCookies = Game.cookiesEarned >= unlock.cookies
    val notOwned = !unlock.require.fold(false)(req => !Game.Has(req) && !Game.HasAchiev(req))
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

  def amountOfNonCursors: Double = Game.ObjectsById.sumBy(_.amount) - Building.cursor.amount

  def hasOrIsChoice(upgradeName: String, choice: Option[String]): Boolean =
    val upgrade = Upgrade.getByName(upgradeName)
    upgrade.owned || choice.exists(_ == upgrade.name)

  def cps: Double = Game.cookiesPs * (1 - Game.cpsSucked)

  def getGodLevel(godName: String): Int =
    import cookieclickerNumbers._
    Game.hasGod.fold(0) { level =>
      Try(level.asInstanceOf[`false`]).map(_ => 0)
        .orElse(Try(level.asInstanceOf[`1`]).map(_ => 1))
        .orElse(Try(level.asInstanceOf[`2`]).map(_ => 2))
        .orElse(Try(level.asInstanceOf[`3`]).map(_ => 3))
        .getOrElse(0)
    }

  def getKittenMultiplier(milk: Double, choice: Option[String] = None): Double = {
    val kittenMultipliers: Map[String, Double] = Map(
      "helpers" -> 0.1,
      "workers" -> 0.125,
      "engineers" -> 0.15,
      "overseers" -> 0.175,
      "managers" -> 0.2,
      "accountants" -> 0.2,
      "specialists" -> 0.2,
      "experts" -> 0.2,
      "consultants" -> 0.2,
      "assistants to the regional manager" -> 0.175,
      "marketeers" -> 0.15,
      "analysts" -> 0.125,
      "angels" -> 0.1,
    )
    val allKittensMultiplier = kittenMultipliers.foldLeft(1d) {
      case (acc, (name, value)) if hasOrIsChoice(s"Kitten $name", choice) => acc * 1 + milk * value
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

  def profile(what: String)(f: () => Unit): Unit =
    val start = Date.now()
    f()
    Logger.debug(s"$what took ${Date.now() - start} millis to run")

  extension (gameAchievement: GameAchievement)
    def cloned: GameAchievement =
      GameAchievement(
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
      )

/*
implicit class StatisticalOps(seq: Seq[Double]) {

  def stdDev: Double = {
    val mean = seq.sum / seq.length
    val squared = seq.map(n => Math.pow(n - mean, 2))
    Math.sqrt(squared.sum / (squared.length - 1))
  }
}

def logMissingAchievements(): Unit = Game.AchievementsById.foreach(achievement => if (Achievement.findByName
(achievement.name).isEmpty) { console.log(achievement) })

def logMissingUpgrades(): Unit = Game.UpgradesById.foreach(upgrade => if (Upgrade.findByName(upgrade.name).isEmpty) {
 console.log(upgrade) })
*/
