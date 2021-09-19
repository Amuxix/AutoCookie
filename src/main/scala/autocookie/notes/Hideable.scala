package autocookie.notes

import org.scalajs.dom.raw.HTMLElement

class Hideable {
  val html: HTMLElement = Note.newDiv

  var shown = false

  def show(): this.type =
    if !shown then
      html.style.display = "block"
      shown = true
    this

  def hide(): this.type =
    if shown then
      html.style.display = "none"
      shown = false
    this
}
