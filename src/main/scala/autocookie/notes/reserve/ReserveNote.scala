package autocookie.notes.reserve

import autocookie.{AutoCookie, AutoSaveable}
import autocookie.notes.Note
import autocookie.notes.reserve.ReserveNote.buttons
import autocookie.reserve.{Reserve, ReserveGroup, ReserveLevel}
import org.scalajs.dom.document.createElement
import org.scalajs.dom.raw.{HTMLAnchorElement, HTMLDivElement}
import cookieclicker.global.Beautify

object ReserveNote extends Note with AutoSaveable {
  val version: Float = 1
  lazy val buttons: Seq[Button] = ReserveGroup.values.toSeq.map(new Button(_))

  lazy val buttonDiv: HTMLDivElement =
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

  override def autoSave: String =
    buttons.map(_.reserveLevelIndex).mkString(", ")

  override def autoLoad(string: String, version: Float): Unit =
    buttons.zip(string.split(", ").map(_.toInt)).foreach((button, state) => button.setReverseLevelId(state))

  override def update(): Unit =
    buttons.foreach { button =>
      if button.unlocked then button.show() else button.hide()
    }

    val reserveLevel = Reserve.level
    topRow.style.color = if reserveLevel == ReserveLevel.Disabled then "#F66" else "#6F6"
    setDescription(Beautify(Reserve.amount.toDouble))
      .setTitle(s"Reserve (${reserveLevel.title})")
}
