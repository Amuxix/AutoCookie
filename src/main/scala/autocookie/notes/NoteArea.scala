package autocookie.notes

import org.scalajs.dom.document

class NoteArea extends Hideable {
  html.style.position = "absolute"
  html.style.bottom = "0px"
  html.style.left = "auto"
  html.style.width = "350px"
  html.style.zIndex = "100000001"
  hide()
}
