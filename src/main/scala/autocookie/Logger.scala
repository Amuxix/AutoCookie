package autocookie

import org.scalajs.dom.console

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import scala.scalajs.js
import scala.scalajs.js.Date

object Logger {
  private def formatNumber(number: Double, digits: Int = 2): String =
    val string = number.toString()
    "0" * (digits - string.length) + string

  private def time: String =
    val currentTime = new Date()
    val hours = formatNumber(currentTime.getHours())
    val minutes = formatNumber(currentTime.getMinutes())
    val seconds = formatNumber(currentTime.getSeconds())
    val millis = formatNumber(currentTime.getMilliseconds(), 4)
    s"${hours}:${minutes}:${seconds}:${millis}"

  private def autoCookieTime: String = s"[AutoCookie $time]"

  def log(obj: js.Any): Unit = console.log(autoCookieTime, obj)

  def debug(obj: js.Any): Unit = console.debug(autoCookieTime, obj)

  def error(obj: js.Any): Unit = console.error(autoCookieTime, obj)

  def apply(obj: js.Any): Unit = log(obj)
}
