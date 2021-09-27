package autocookie

import autocookie.Logger.*
import autocookie.Helpers.*
import autocookie.LoopReason.*
import autocookie.buyable.*
import autocookie.buyable.Building.*
import autocookie.buyable.upgrade.*
import autocookie.notes.reserve.ReserveNote
import autocookie.notes.*
import autocookie.reserve.Reserve
import org.scalajs.dom.raw.HTMLElement
import org.scalajs.dom.{console, document}
import cookieclicker.{Game, Mod}
import cookieclicker.global.Beautify

import scala.concurrent.duration.*
import scala.scalajs.js.annotation.*
import scala.scalajs.js.timers.{SetIntervalHandle, SetTimeoutHandle, clearInterval, clearTimeout, setInterval, setTimeout}
import scala.scalajs.js
import scala.collection.mutable.Map
import scala.collection.mutable
import scala.util.{Failure, Success, Try}

@JSExportTopLevel("AutoCookie")
@JSExportAll
object AutoCookie extends Mod with AutoSaveable {
  override val version = 6
  val CLICKS_PER_SEC = 3
  val NOTE_UPDATE_FREQUENCY = 300.millis

  //@JSExport
  var stopped = true
  var buyLocked = true
  var buying = false

  var bestBuyable: Buyable = new Building("Cursor")

  lazy val notes: Seq[Note] = Seq(GoldenCookieSpawnNote, ReserveNote, InvestmentNote, GoalNote, NextBuyNote)
  lazy val buildings: Map[String, Building] = Buyables.createBuildings
  lazy val upgrades: Map[String, Upgrade] = Buyables.createUpgrades
  lazy val achievements: Map[String, Achievement] = Buyables.createAchievements
  var buyables: Set[Buyable] = Set.empty

  var mainTimeout: Option[SetTimeoutHandle] = None
  var noteUpdateInterval: Option[SetIntervalHandle] = None

  var lastCps: Double = 0
  var lastNumberOfBuffs: Int = 0

  var notesShown: Int = 0

  def toggleBuyLock(): Unit =
    buyLocked = !buyLocked
    loop(BuyLockToggle(buyLocked))

  def createNotes(): Unit =
    val notes = document.getElementById("notes")
    notes.parentNode.insertBefore(NoteArea.html, notes)

    this.notes.foreach(note => NoteArea.html.appendChild(note.html))
    Logger.log("All notes created successfully.")

  def updateNotes(): Unit =
    val cps = Helpers.cps
    if lastCps != cps then
      lastCps = cps
      setTimeout(0)(loop(CPSChanged))

    val numberOfBuffs = Helpers.buffs.length
    if lastNumberOfBuffs > numberOfBuffs then
      setTimeout(0)(loop(BuffEnded))

    lastNumberOfBuffs = numberOfBuffs
    notes.foreach(_.update())
    //Move krumblor egg dialog
    document.getElementById("specialPopup").asInstanceOf[HTMLElement].style.bottom = s"${25 + 37 * notesShown}px"

  def engageHooks(): Unit =
    Game.registerHook("click", () => loop(BigCookieClicked))
    //document.getElementById("store").addEventListener("click", (e) => loop("Store clicked")) //Hook store items and buildings
    document.getElementById("shimmers")
      .addEventListener("click", (e) => setTimeout(50.millis)(loop(GoldenCookieClicked))) //Hook golden cookie clicks

  def loop(reason: LoopReason): Unit =
    debug(s"Loop: ${reason.message}")
    if mainTimeout.nonEmpty then mainTimeout.foreach(clearTimeout)
    if stopped then return
    reason match
      case reason if reason.shouldUpdate =>
        Reserve.update()
        profile("Update")(buyables.foreach(_.update()))
        val newBestBuyable = buyables.minBy(_.payback)
        if bestBuyable != newBestBuyable then
          newBestBuyable.resetOriginalBuyTime()
        bestBuyable = newBestBuyable
        bestBuyable.updateInvestmentAndBuyTime()
      case `ReserveLevelChanged`         =>
        bestBuyable.resetOriginalBuyTime()
        bestBuyable.updateInvestmentAndBuyTime()
      case _ =>
        bestBuyable.updateInvestmentAndBuyTime()

    if !buyLocked then
      val nextMilestone = bestBuyable.nextMilestone
      val investment = nextMilestone.investment

      if nextMilestone.timeToBuy <= 0.millis then
        val invested = if investment.estimatedReturns > 0 then
          println(s"Highest CPS at investment ${Beautify(Game.cookiesPsRawHighest)}")
          investment.invest()
        else
          0
        nextMilestone.buy()
        if invested > 0 then
          setTimeout(StockMarket.timeToNextTick + 50.millis) {
            println(s"Highest CPS at liquidation ${Beautify(Game.cookiesPsRawHighest)}")
            val profit = investment.liquidateInvestment() - invested
            val title = s"Investment Returns for ${nextMilestone.name}"
            val message = s"${Beautify(profit)} or ${(profit / Game.unbuffedCps).seconds.prettyPrint} of production"
            log(title + " " + message)
            Game.Notify(title, message)
            loop(InvestmentSold)
          }
        lastCps = Helpers.cps
        setTimeout(0)(loop(AfterBuy))
      else
        def buildingBuyMessage(building: Building) = s"Buying the ${convertNumeral(building.amount + 1)} ${building.name}"
        val message = nextMilestone match {
          case requirement: BuildingRequirement => buildingBuyMessage(requirement.building)
          case building: Building               => buildingBuyMessage(building)
          case _                  => s"Buying ${nextMilestone.name}"
        }
        mainTimeout = Some(setTimeout(nextMilestone.timeToBuy)(loop(Buying(message))))
    notes.foreach(_.update())

  def start(): Unit =
    if stopped then
      stopped = false
      val allBuyables = (buildings ++ upgrades ++ achievements).values.toSet
      buyables = allBuyables.filter(_.canEventuallyGet)
      //Update reserve note
      loop(Start)
      noteUpdateInterval = Some(setInterval(NOTE_UPDATE_FREQUENCY)(updateNotes()))
      engageHooks()
      NoteArea.show()
      //CPSCalculator(debug = true)

  def stop(): Unit =
    if !stopped then
      stopped = true
      noteUpdateInterval.foreach(clearInterval)
      noteUpdateInterval = None
      NoteArea.hide()
      //diesngageHooks()

  def emptyCalc() = CPSCalculator(debug = true)

  override def autoLoad(save: String, version: Float): Unit =
    def load() =
      val split = save.split("\\|")
      for {
        reserveSave <- Try(split(0))
        stockMarketSave <- Try(split(1))
        buyLocked <- Try(split(2)).map(_.toBoolean)
        _ = ReserveNote.load(reserveSave)
        _ = StockMarket.load(stockMarketSave)
        _ = this.buyLocked = buyLocked
        _ = log("Load Successful")
      } yield ()

    var interval: Option[SetIntervalHandle] = None
    //At this points buildings have already loaded but cookies per second has not
    if Game.BuildingsOwned == 0 then //We have no buildings, we are ready to go
      debug("Loading with no buildings")
      load()
    else
      interval = Some(
        setInterval(50.millis) {
          if Game.unbuffedCps > 0 then
            load() //Wait for Game.unbuffedCps to be updated
            interval.foreach(clearInterval)
        }
      )

  override def autoSave: String =
    Seq(
      ReserveNote.save,
      StockMarket.save,
      buyLocked,
    ).mkString("|")

  def init(): Unit =
    Game.Notify("Auto Cookie started!", "")
    document.getElementById("versionNumber").asInstanceOf[HTMLElement].style.display = "none"
    createNotes()
    start()

}
