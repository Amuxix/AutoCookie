package cookieclicker

import scala.scalajs.js

@js.native
trait GameMod extends js.Object {
  def save(): String
  def load(string: String): Unit
  def init(): Unit
}

object GameMod {
  def apply(): GameMod = js.Dynamic.literal().asInstanceOf[GameMod]

  extension (mod: GameMod)
    def setInit(value: () => Unit) = mod.asInstanceOf[js.Dynamic].updateDynamic("init")(value)
    def setSave(value: () => String) = mod.asInstanceOf[js.Dynamic].updateDynamic("save")(value)
    def setLoad(value: String => Unit) = mod.asInstanceOf[js.Dynamic].updateDynamic("load")(value)
}
