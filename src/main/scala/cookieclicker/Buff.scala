package cookieclicker

import scalajs.js

@js.native
trait Buff extends js.Object {
  var multClick: js.UndefOr[Double]
  var multCpS: js.UndefOr[Double]
  var time: Double
}
