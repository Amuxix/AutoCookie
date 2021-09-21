package autocookie

import scala.concurrent.duration._

import scala.scalajs.js.timers._

object Main {
  def main(args: Array[String]): Unit = setTimeout(1000) {
    AutoCookie.register
  }
}
