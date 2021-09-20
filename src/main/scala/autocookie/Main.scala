package autocookie

import scala.concurrent.duration.*
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}
import scala.scalajs.js.timers.*

object Main {
  def main(args: Array[String]): Unit = setTimeout(1000) {
    AutoCookie.register
  }
}
