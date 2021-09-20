package autocookie.notes.reserve

import autocookie.AutoCookie
import autocookie.notes.Hideable
import autocookie.reserve.{CookieEffect, ReserveGroup, ReserveLevel}
import org.scalajs.dom.raw.HTMLAnchorElement
import org.scalajs.dom.document.createElement

class Button(group: ReserveGroup, index: Int = 0) extends Hideable {
  override val html: HTMLAnchorElement =
    val button = createElement("a").asInstanceOf[HTMLAnchorElement]
    button.textContent = group.icon
    button.style.textDecoration = "none"
    button.onclick = (e) => click()
    button

  override val displayType: String = "inline-block"

  var reserveLevelIndex = index

  def unlocked: Boolean = group.reserveLevels.count(_.isUnlocked()) > 1

  def reserveLevel(index: Int): ReserveLevel =
    val unlockedReserveLevels = group.reserveLevels.filter(_.isUnlocked())
    index % unlockedReserveLevels.length
    unlockedReserveLevels(index)

  def click() =
    val unlockedReserveLevels = group.reserveLevels.filter(_.isUnlocked())
    val oldReserveLevel = unlockedReserveLevels(reserveLevelIndex)
    reserveLevelIndex = (reserveLevelIndex + 1) % unlockedReserveLevels.length
    val reserveLevel = unlockedReserveLevels(reserveLevelIndex)
    html.style.textShadow = Button.glow(reserveLevel)
    AutoCookie.reserve.changeReserveLevels(oldReserveLevel, reserveLevel)
}

object Button {
  def glow(reserveLevel: ReserveLevel) =
    reserveLevel.effects match {
      case Seq() => ""
      case effects if effects.contains(CookieEffect.Frenzy) => "0px 0px 5px #ffb340" //Orange glow
      case effects if effects.contains(CookieEffect.DragonHarvest) => "0px 0px 5px #68f" //Blueish glow
      case effects => "0px 0px 5px #fff" //White glow
    }
}
