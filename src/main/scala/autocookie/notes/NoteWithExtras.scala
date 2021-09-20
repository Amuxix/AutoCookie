package autocookie.notes

import org.scalajs.dom.raw.HTMLSpanElement
import org.scalajs.dom.document.createElement

abstract class NoteWithExtras extends Note {
  lazy val extraTitle = createElement("span").asInstanceOf[HTMLSpanElement]
  topRow.appendChild(extraTitle)

  lazy val extraDescription = createElement("span").asInstanceOf[HTMLSpanElement]
  extraDescription.style.color = "#6F6"
  bottomRow.appendChild(extraDescription)

  def setExtraDescription(extra: String): this.type =
    extraDescription.textContent = extra
    this

  def setExtraTitle(extra: String, color: String): this.type =
    extraTitle.textContent = extra
    extraTitle.style.color = color
    this
}
