package autocookie.notes.reserve

import autocookie.AutoCookie
import autocookie.notes.Note
import autocookie.notes.reserve.ReserveNote.buttons
import autocookie.reserve.{Reserve, ReserveGroup, ReserveLevel}
import org.scalajs.dom.document.createElement
import org.scalajs.dom.raw.{HTMLAnchorElement, HTMLDivElement}
import typings.cookieclicker.global.Beautify

class ReserveNote extends Note {
  val buttonDiv: HTMLDivElement =
    val buttonDiv = createElement("div").asInstanceOf[HTMLDivElement]
    buttonDiv.style.fontSize = "15px"
    buttonDiv.style.cssFloat = "right"
    topRow.appendChild(buttonDiv)
    buttonDiv

  buttons.foreach { button =>
    buttonDiv.appendChild(button.html)
    if button.unlocked then button.show() else button.hide()
  }
  show()

  def getButtonStates: Seq[Int] = buttons.map(_.reserveLevelIndex)

  override def update(): Unit =
    buttons.foreach { button =>
      if button.unlocked then button.show() else button.hide()
    }

    val reserveLevel = Reserve.level
    topRow.style.color = if reserveLevel == ReserveLevel.Disabled then "#F66" else "#6F6"
    setDescription(Beautify(Reserve.amount.toDouble))
      .setTitle(s"Reserve (${reserveLevel.title})")
}

object ReserveNote {
  lazy val buttons: Seq[Button] = ReserveGroup.values.toSeq.map(new Button(_))
}
