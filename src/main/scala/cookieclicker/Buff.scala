package cookieclicker

import scala.scalajs.js.annotation.JSName
import scalajs.js

@js.native
trait Buff extends js.Object {
  var multClick: js.UndefOr[Double]
  var multCpS: js.UndefOr[Double]
  @JSName("time")
  var remainingTicks: Double
}
