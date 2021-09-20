package autocookie.notes

import org.scalajs.dom.raw.HTMLElement

class Hideable {
  val html: HTMLElement = Note.newDiv
  val displayType = "block"

  var shown = false

  def show(): this.type =
    if !shown then
      html.style.display = displayType
      shown = true
    this

  def hide(): this.type =
    if shown || html.style.display != "none" then
      html.style.display = "none"
      shown = false
    this
}
