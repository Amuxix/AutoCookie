package autocookie

import autocookie.Logger.*
import autocookie.Helpers.*
import autocookie.buyable.*
import autocookie.buyable.Building.*
import autocookie.buyable.upgrade.*
import autocookie.notes.reserve.ReserveNote
import autocookie.notes.{GoalNote, NextBuyNote, Note, NoteArea}
import autocookie.reserve.Reserve
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.{console, document}
import typings.cookieclicker.global.Game
import typings.cookieclicker.global.Beautify

import scala.concurrent.duration.*
import scala.scalajs.js.annotation.*
import scala.scalajs.js.timers.{SetIntervalHandle, SetTimeoutHandle, clearInterval, clearTimeout, setInterval, setTimeout}
import scala.scalajs.js
import scala.collection.mutable.Map
import scala.collection.mutable

object AutoCookie extends Named {
  val CLICKS_PER_SEC = 3
  val NOTE_UPDATE_FREQUENCY = 500.millis

  val STOCK_MARKET_MAX_STD_DEV = 0.01
  val STOCK_MARKET_STABILITY_THRESHOLD = 0.03
  val STOCK_MARKET_STABILITY_MIN_PRICES = 5
  val STOCK_MARKET_STABILITY_MAX_PRICES = 10

  var stopped = true
  var buyLocked = true
  var buying = false

  val reserve: Reserve = new Reserve()
  var stockMarket: Option[StockMarket] = None
  var bestBuyable: Buyable = new Building("Cursor")

  lazy val noteArea = new NoteArea
  //lazy val spawnWindowNote: GoldenCookieSpawnNote
  lazy val reserveNote: ReserveNote = new ReserveNote
  lazy val goalNote: GoalNote = new GoalNote
  lazy val nextBuyNote: NextBuyNote = new NextBuyNote
  lazy val notes: Seq[Note] = Seq(goalNote, nextBuyNote, reserveNote)
  val buildings: Map[String, Building] = Buyables.createBuildings
  val upgrades: Map[String, Upgrade] = Buyables.createUpgrades
  val achievements: Map[String, Achievement] = Buyables.createAchievements
  var buyables: Set[Buyable] = Set.empty

  var mainTimeout: Option[SetTimeoutHandle] = None
  var noteUpdateInterval: Option[SetIntervalHandle] = None

  var lastCps: Double = 0
  var notesShown: Int = 3

  def toggleBuyLock(): Unit =
    buyLocked = !buyLocked
    loop(s"Set buy lock to $buyLocked")

  def createNotes(): Unit =
    val notes = document.getElementById("notes")
    notes.parentNode.insertBefore(noteArea.html, notes)

    noteArea.html.appendChild(reserveNote.html)
    noteArea.html.appendChild(goalNote.html)
    noteArea.html.appendChild(nextBuyNote.html)
    Logger.log("All notes created successfully.")

  def updateNotes(): Unit =
    val cps = Helpers.cps
    if lastCps != cps then
      lastCps = cps
      setTimeout(0)(loop("Cookies per second changed"))
    notes.foreach(_.update())
  //document.getElementById("specialPopup").style.bottom = `${25 + 37 * this.notesShown}px`

  def engageHooks(): Unit =
    Game.registerHook("click", () => loop("Big Cookie clicked"))
    document.getElementById("store")
      .addEventListener("click", (e) => loop("Store clicked")) //Hook store items and buildings
    document.getElementById("shimmers")
      .addEventListener("click", (e) => setTimeout(50.millis)(loop("Golden Cookie clicked"))) //Hook golden cookie clicks

  def loop(message: String): Unit =
    debug(s"Loop: $message")
    if stopped then return
    if mainTimeout.nonEmpty then mainTimeout.foreach(clearTimeout)
    reserve.update()
    profile("Update")(buyables.foreach(_.update()))
    val newBestBuyable = buyables.minBy(_.payback)
    if bestBuyable != newBestBuyable then
      newBestBuyable.resetOriginalBuyTime()
      bestBuyable = newBestBuyable
    bestBuyable.updateInvestmentAndBuyTime()

    if !buyLocked then
      val nextMilestone = bestBuyable.nextMilestone
      if nextMilestone.timeToBuy <= 0.millis then
        val invested = nextMilestone.investment.invest()
        nextMilestone.buy()
        if invested > 0 then
          setTimeout(StockMarket.timeToNextTick + 50.millis) {
            val profit = nextMilestone.investment.sellInvestment() - invested
            log(s"Investments earned ${Beautify(profit)} or ${(profit / Game.unbuffedCps).seconds.prettyPrint} of production")
            loop("Investment Sold")
          }
      else
        val message = nextMilestone match {
          case building: Building => s"Buying the ${convertNumeral(building.amount + 1)} ${building.name}"
          case _                  => s"Buying ${nextMilestone.name}"
        }
        mainTimeout = Some(setTimeout(nextMilestone.timeToBuy)(loop(message)))
    notes.foreach(_.update())

  def start(): Unit =
    if (stopped) {
      stopped = false
      val allBuyables = (buildings ++ upgrades ++ achievements).values.toSet
      buyables = allBuyables.filter(_.canEventuallyGet)
      //Update reserve note
      loop("start")
      noteUpdateInterval = Some(setInterval(NOTE_UPDATE_FREQUENCY)(updateNotes()))
      engageHooks()
      noteArea.show()
    }

  def load(save: String): Unit = ()

  def save(): String = ""

  def init(): Unit =
    Game.Notify("Auto Cookie started!", "", (), 5)
    document.getElementById("versionNumber").asInstanceOf[HTMLElement].style.display = "none"
    createNotes()
    start()

}
