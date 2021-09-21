package cookieclicker.stockmarket

import scala.scalajs.js.annotation.JSName
import scalajs.js

@js.native
trait Good extends js.Object {
  val id: Int
  val name: String
  @JSName("val")
  val value: Double
  val vals: js.Array[Double]
  val stock: Double
  val active: Boolean
}