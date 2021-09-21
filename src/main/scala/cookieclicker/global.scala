package cookieclicker

import cookieclicker.buyables.{GameAchievement, GameBuilding, GameBuyable, GameUpgrade}

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSBracketAccess, JSGlobal, JSGlobalScope, JSImport, JSName}

object global {

  @js.native
  @JSGlobal("Beautify")
  def Beautify(`val`: Double | Long | Int): String = js.native
  
  @js.native
  @JSGlobal("utf8_to_b64")
  def encode(string: String): String = js.native
  
  @js.native
  @JSGlobal("b64_to_utf8")
  def decode(encoded: String): String = js.native
}
