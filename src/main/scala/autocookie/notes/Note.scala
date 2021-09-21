package autocookie.notes

import autocookie.buyable.Achievement
import autocookie.buyable.upgrade.Upgrade
import autocookie.notes.Note.newDiv
import autocookie.Helpers.*
import autocookie.Helpers.given
import org.scalajs.dom.raw.{HTMLDivElement, HTMLElement}
import org.scalajs.dom.document.createElement
import cookieclicker.Game
import cookieclicker.buyables.GameAchievement

import scala.language.implicitConversions

object Note {
  def newDiv: HTMLDivElement = createElement("div").asInstanceOf[HTMLDivElement]

}

abstract class Note extends Hideable {
  html.style.width = "350px"
  html.style.display = "none"
  html.classList.add("framed")
  html.classList.add("text")

  val topRow = newDiv
  html.appendChild(topRow)

  val bottomRow = newDiv
  html.appendChild(bottomRow)

  val title = createElement("h3").asInstanceOf[HTMLElement]
  title.style.display = "inline-block"
  topRow.appendChild(title)

  val description = createElement("h5").asInstanceOf[HTMLElement]
  description.classList.add("title")
  description.style.display = "inline-block"
  description.style.fontSize = "16px"
  bottomRow.appendChild(description)

  def setTitle(title: String): this.type =
    this.title.textContent = title
    this

  def setDescription(description: String): this.type =
    this.description.textContent = description
    this

  def drawTooltip(buyable: Upgrade | Achievement) =
    buyable match {
      case upgrade: Upgrade         =>
        Game.tooltip.draw(html, Game.crateTooltip(upgrade.gameBuyable, "stats"), "this")
      case achievement: Achievement =>
        /*val gameAchievement: GameAchievement = achievement.gameBuyable.cloned
        gameAchievement.won = true*/
        val gameAchievement: GameAchievement = achievement.gameBuyable
        Game.tooltip.draw(html, Game.crateTooltip(gameAchievement, "stats"), "this")
    }

  def update(): Unit
}
