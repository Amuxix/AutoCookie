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

object GoalNote extends NoteWithExtras {
  html.onmouseout = (mouseEvent) => Game.tooltip.shouldHide = true

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