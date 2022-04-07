package autocookie.notes.reserve

import autocookie.AutoCookie
import autocookie.notes.Hideable
import autocookie.reserve.{Reserve, ReserveGroup, ReserveLevel, CookieEffect}
import org.scalajs.dom.raw.HTMLAnchorElement
import org.scalajs.dom.document.createElement

class Button(group: ReserveGroup) extends Hideable {
  override val html: HTMLAnchorElement =
    val button = createElement("a").asInstanceOf[HTMLAnchorElement]
    button.textContent = group.icon
    button.style.textDecoration = "none"
    button.onclick = e => click()
    button

  override val displayType: String = "inline-block"

  var reserveLevelIndex = 0

  def unlocked: Boolean = group.reserveLevels.count(_.isUnlocked()) > 1

  def reserveLevel(index: Int): ReserveLevel =
    val unlockedReserveLevels = group.reserveLevels.filter(_.isUnlocked())
    index % unlockedReserveLevels.length
    unlockedReserveLevels(index)

  private def unlockedReserveLevels = group.reserveLevels.filter(_.isUnlocked())

  def setReverseLevelId(id: Int): Unit =
    val unlockedReserveLevels = this.unlockedReserveLevels
    val oldReserveLevel = unlockedReserveLevels(reserveLevelIndex)
    reserveLevelIndex = id % unlockedReserveLevels.length
    val reserveLevel = unlockedReserveLevels(reserveLevelIndex)
    html.style.textShadow = Button.glow(reserveLevel)
    Reserve.changeReserveLevels(oldReserveLevel, reserveLevel)


  def click() = setReverseLevelId(reserveLevelIndex + 1)
}

object Button:
  def glow(reserveLevel: ReserveLevel) =
    reserveLevel.effects match
      case effects if effects.isEmpty => ""
      case effects if Set(CookieEffect.Frenzy, CookieEffect.BuildingSpecial).subsetOf(effects) => "0px 0px 5px #bf0000" //Red glow
      case effects if Set(CookieEffect.Frenzy, CookieEffect.DragonHarvest).subsetOf(effects) => "0px 0px 5px #36a300" //Green glow
      case effects if effects.contains(CookieEffect.BuildingSpecial) => "0px 0px 5px #f54295" //Pinkish glow
      case effects if effects.contains(CookieEffect.Frenzy) => "0px 0px 5px #ffb340" //Orange glow
      case effects if effects.contains(CookieEffect.DragonHarvest) => "0px 0px 5px #68f" //Blueish glow
      case effects => "0px 0px 5px #aaa" //White glow
