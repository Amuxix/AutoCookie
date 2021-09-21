package cookieclicker

import scala.scalajs.js

@js.native
sealed trait Shimmer extends js.Object {
  var life: Double
}

@js.native
trait GoldenShimmer extends Shimmer {
  var minTime: Double
  var maxTime: Double
  var time: Double
}