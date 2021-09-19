package autocookie

import typings.cookieclicker.Game.Mod
import typings.cookieclicker.Game.Mod._
import typings.cookieclicker.global.Game
import scala.concurrent.duration._

import scala.scalajs.js.timers._

object Main {
  def main(args: Array[String]): Unit = setTimeout(1000) {
    val mod = Mod()
    mod.setInit(AutoCookie.init)
    mod.setSave(AutoCookie.save)
    mod.setLoad(AutoCookie.load)
    Game.registerMod(AutoCookie.className, mod)
  }
}
