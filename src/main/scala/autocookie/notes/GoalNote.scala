package autocookie.notes

import autocookie.AutoCookie
import org.scalajs.dom.raw.HTMLSpanElement
import org.scalajs.dom.document.createElement
import typings.cookieclicker.global.Game
import typings.cookieclicker.Game.{AchievementPool, PseudoBoolean, Achievement as GameAchievement}
import typings.cookieclicker.cookieclickerStrings
import autocookie.Helpers.*
import autocookie.Helpers.given
import autocookie.buyable.upgrade.Upgrade
import autocookie.buyable.{Achievement, Building}

import scala.concurrent.duration.*
import scala.language.implicitConversions

class GoalNote extends Note {
  html.onmouseout = (mouseEvent) => Game.tooltip.shouldHide = true

  val extraTitle = createElement("span").asInstanceOf[HTMLSpanElement]
  topRow.appendChild(extraTitle)

  val extraDescription = createElement("span").asInstanceOf[HTMLSpanElement]
  extraDescription.style.color = "#6F6"
  bottomRow.appendChild(extraDescription)

  def setExtraDescription(extra: String): this.type =
    extraDescription.textContent = extra
    this

  def setExtraTitle(extra: String, color: String): this.type =
    extraTitle.textContent = extra
    extraTitle.style.color = color
    this

  override def update(): Unit =
    val bestBuyable = AutoCookie.bestBuyable
    if bestBuyable == bestBuyable.nextMilestone then
      AutoCookie.notesShown = 3
      hide()
      return
    else
      AutoCookie.notesShown = 4
      setDescription(bestBuyable.name).show()

    val unlock = bestBuyable match {
      case _: Achievement => "Achieving"
      case _ => "Unlocking"
    }
    val title = s"Goal: $unlock in ${bestBuyable.timeToBuy.prettyPrint}"
    setExtraTitle(bestBuyable.timeSavedText, bestBuyable.timeSavedTextColour)

    bestBuyable match {
      case achievement: Achievement =>
        html.onmouseover = (m) => drawTooltip(achievement)
      case upgrade: Upgrade     =>
        html.onmouseover = (m) => drawTooltip(upgrade)
      case building: Building    =>
        html.onmouseover = null
    }
    setTitle(title).setExtraDescription(bestBuyable.cpsIncreasePercentText)

}