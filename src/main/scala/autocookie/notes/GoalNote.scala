package autocookie.notes

import autocookie.AutoCookie
import org.scalajs.dom.raw.HTMLSpanElement
import org.scalajs.dom.document.createElement
import cookieclicker.Game
import cookieclicker.buyables.GameAchievement
import autocookie.Helpers.*
import autocookie.Helpers.given
import autocookie.buyable.upgrade.Upgrade
import autocookie.buyable.{Achievement, Building}

import scala.concurrent.duration.*
import scala.language.implicitConversions

object GoalNote extends NoteWithExtras with HideOnMouseout {
  override def update(): Unit =
    val bestBuyable = AutoCookie.bestBuyable
    if bestBuyable == bestBuyable.nextMilestone then
      hideNote()
      return
    else
      setDescription(bestBuyable.name)
      showNote()

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