package cookieclicker.stockmarket

import scala.scalajs.js.annotation.JSName
import scalajs.js

@js.native
trait Good extends js.Object {
  @JSName("vale")
  val value: Double
  val stock: Double
}
