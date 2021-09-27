package cookieclicker.stockmarket

import scala.scalajs.js.annotation.JSName
import scalajs.js

@js.native
trait Good extends js.Object {
  val id: Int
  val name: String
  @JSName("val")
  val price: Double
  @JSName("vals")
  var prices: js.Array[Double]
  val stock: Double
  val active: Boolean
  val icon: js.Array[Int]
}
