package autocookie

import autocookie.Logger.*
import autocookie.Helpers.*
import autocookie.LoopReason.*
import autocookie.buyable.*
import autocookie.buyable.Building.*
import autocookie.buyable.upgrade.*
import autocookie.notes.reserve.ReserveNote
import autocookie.notes.{GoalNote, NextBuyNote, Note, NoteArea}
import autocookie.reserve.Reserve
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.{console, document}
import cookieclicker.Game
import cookieclicker.Mod
import cookieclicker.global.Beautify

import scala.concurrent.duration.*
import scala.scalajs.js.annotation.*
import scala.scalajs.js.timers.{SetIntervalHandle, SetTimeoutHandle, clearInterval, clearTimeout, setInterval, setTimeout}
import scala.scalajs.js
import scala.collection.mutable.Map
import scala.collection.mutable

object AutoCookie extends Mod {
  val CLICKS_PER_SEC = 3
  val NOTE_UPDATE_FREQUENCY = 500.millis

  var stopped = true
  var buyLocked = true
  var buying = false

  var stockMarket: Option[StockMarket] = None
  var bestBuyable: Buyable = new Building("Cursor")

  //lazy val spawnWindowNote: GoldenCookieSpawnNote
  lazy val notes: Seq[Note] = Seq(GoalNote, NextBuyNote, ReserveNote)
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
    loop(BuyLockToggle(buyLocked))

  def createNotes(): Unit =
    val notes = document.getElementById("notes")
    notes.parentNode.insertBefore(NoteArea.html, notes)

    NoteArea.html.appendChild(ReserveNote.html)
    NoteArea.html.appendChild(GoalNote.html)
    NoteArea.html.appendChild(NextBuyNote.html)
    Logger.log("All notes created successfully.")

  def updateNotes(): Unit =
    val cps = Helpers.cps
    if lastCps != cps then
      lastCps = cps
      setTimeout(0)(loop(CPSChanged))
    notes.foreach(_.update())
  //document.getElementById("specialPopup").style.bottom = `${25 + 37 * this.notesShown}px`

  def engageHooks(): Unit =
    Game.registerHook("click", () => loop(BigCookieClicked))
    //document.getElementById("store").addEventListener("click", (e) => loop("Store clicked")) //Hook store items and buildings
    document.getElementById("shimmers")
      .addEventListener("click", (e) => setTimeout(50.millis)(loop(GoldenCookieClicked))) //Hook golden cookie clicks

  def loop(reason: LoopReason): Unit =
    debug(s"Loop: ${reason.message}")
    if stopped then return
    if mainTimeout.nonEmpty then mainTimeout.foreach(clearTimeout)
    reason match
      case reason if reason.CPSChanged =>
        Reserve.update()
        profile("Update")(buyables.foreach(_.update()))
        val newBestBuyable = buyables.minBy(_.payback)
        if bestBuyable != newBestBuyable then
          newBestBuyable.resetOriginalBuyTime()
        bestBuyable = newBestBuyable
        bestBuyable.updateInvestmentAndBuyTime()
      case `ReserveLevelChanged` =>
        bestBuyable.resetOriginalBuyTime()
        bestBuyable.updateInvestmentAndBuyTime()
      case _ =>
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
            loop(InvestmentSold)
          }
      else
        val message = nextMilestone match {
          case building: Building => s"Buying the ${convertNumeral(building.amount + 1)} ${building.name}"
          case _                  => s"Buying ${nextMilestone.name}"
        }
        mainTimeout = Some(setTimeout(nextMilestone.timeToBuy)(loop(Buying(message))))
    notes.foreach(_.update())

  def start(): Unit =
    if (stopped) {
      stopped = false
      val allBuyables = (buildings ++ upgrades ++ achievements).values.toSet
      buyables = allBuyables.filter(_.canEventuallyGet)
      //Update reserve note
      loop(Start)
      noteUpdateInterval = Some(setInterval(NOTE_UPDATE_FREQUENCY)(updateNotes()))
      engageHooks()
      NoteArea.show()
    }

  def load(save: String): Unit = ()

  def save(): String = ""

  def init(): Unit =
    Game.Notify("Auto Cookie started!", "")
    document.getElementById("versionNumber").asInstanceOf[HTMLElement].style.display = "none"
    createNotes()
    start()

}
