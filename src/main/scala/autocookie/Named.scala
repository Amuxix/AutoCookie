package autocookie

import scala.scalajs.js

trait Named {
  lazy val className: String = getClass.getSimpleName.split("\\$").last.replaceAll("([a-z])([A-Z])", "$1 $2")
}
