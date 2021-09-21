package cookieclicker

import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js

@js.native
trait Tooltip extends js.Object {
  var shouldHide: Boolean

  def draw(from: HTMLElement, text: String, origin: String): Unit
}
