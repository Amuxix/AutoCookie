package autocookie.notes

import org.scalajs.dom.document

object NoteArea extends Hideable {
  html.style.position = "absolute"
  html.style.bottom = "0px"
  html.style.left = "auto"
  html.style.width = "350px"
  html.style.zIndex = "6"
  hide()
}
