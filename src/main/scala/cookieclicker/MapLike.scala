package cookieclicker

import scala.scalajs.js.annotation.{JSBracketAccess, JSGlobal}
import scala.scalajs.js

@js.native
@JSGlobal
class MapLike[I, A] extends js.Object {
  @JSBracketAccess
  def apply(i: I): A = js.native
}

object MapLike {
  extension [I, A](mapLike: MapLike[I, A])
    def toArray: js.Array[A] = js.Object.entries(mapLike).map(_._2.asInstanceOf[A])
}
