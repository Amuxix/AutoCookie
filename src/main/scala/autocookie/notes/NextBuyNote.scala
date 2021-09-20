package autocookie.notes

import autocookie.Helpers.*
import autocookie.buyable.Building
import autocookie.buyable.upgrade.Upgrade
import autocookie.notes.GoalNote.html
import autocookie.{AutoCookie, Helpers}
import org.scalajs.dom.raw.{HTMLAnchorElement, HTMLDivElement, HTMLElement}
import org.scalajs.dom.document.createElement
import org.scalajs.dom.console
import cookieclicker.Game

import scala.concurrent.duration.*
import scala.scalajs.js.Date

object NextBuyNote extends NoteWithExtras {
  show()
  html.onmouseout = (mouseEvent) => Game.tooltip.shouldHide = true
  lazy val button: HTMLAnchorElement =
    val button = createElement("a").asInstanceOf[HTMLAnchorElement]
    button.style.fontSize = "15px"
    button.style.cssFloat = "right"
    button.style.textDecoration = "none"
    //updateLockedIcon() //TODO: Check
    button.onclick = (mouseEvent) => AutoCookie.toggleBuyLock()
    topRow.appendChild(button)
    button

  private def updateLockedIcon(): this.type =
    if AutoCookie.buyLocked then
      button.textContent = "🔒"
    else
      button.textContent = "🗸"
    this

  override def update(): Unit =
    val nextMilestone = AutoCookie.bestBuyable.nextMilestone
    val timeToBuy = nextMilestone.timeToBuy
    val title =
      if Game.cookiesPs == 0 then
        "No production, click cookie to buy"
      else if timeToBuy == 0.seconds then
        if AutoCookie.buyLocked then
          "Best buy is"
        else
          nextMilestone match {
            case building: Building => "Buying the " + convertNumeral(building.amount)
            case _                  => "Buying "
          }
      else if AutoCookie.buyLocked then
        s"Can buy in ${timeToBuy.prettyPrint}"
      else
        s"Next buy in ${timeToBuy.prettyPrint}"

    if Game.cookiesPs != 0 && timeToBuy != 0.seconds then
      setExtraTitle(nextMilestone.timeSavedText, nextMilestone.timeSavedTextColour)

    nextMilestone match {
      case upgrade: Upgrade => html.onmouseover = (m) => drawTooltip(upgrade)
      case _                => html.onmouseover = null
    }
    if nextMilestone.cpsIncrease != Double.PositiveInfinity then
      setExtraDescription(nextMilestone.cpsIncreasePercentText)

    setTitle(title)
      .setDescription(nextMilestone.name)
      .updateLockedIcon()
}
