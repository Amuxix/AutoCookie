package cookieclicker

import cookieclicker.buyables.*

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSGlobal, JSName}

@js.native
@JSGlobal("Game")
object Game extends js.Object {
  val UnlockAt: js.Array[GameUpgrade] = js.native
  val cookiesEarned: Double = js.native
  val season: String = js.native
  @JSName("ObjectsById")
  val buildings: js.Array[GameBuilding] = js.native
  @JSName("UpgradesById")
  val upgrades: js.Array[GameUpgrade] = js.native
  @JSName("AchievementsById")
  val achievements: js.Array[GameAchievement] = js.native
  val globalCpsMult: Double = js.native
  val fps: Double = js.native
  val cookies: Double = js.native
  val cookiesPs: Double = js.native
  val unbuffedCps: Double = js.native
  val cookiesPsRawHighest: Double = js.native
  val cpsSucked: Double = js.native
  var buyMode: Double = js.native
  val milkProgress: Double = js.native
  val prestige: Double = js.native
  val baseResearchTime: Double = js.native
  val researchT: Double = js.native
  val nextResearch: Double = js.native
  @JSName("Upgrades")
  val upgradesByName: MapLike[String, GameUpgrade] = js.native
  @JSName("Achievements")
  val achievementsByName:  MapLike[String, GameAchievement] = js.native
  @JSName("Objects")
  val buildingsByName:  MapLike[String, GameBuilding] = js.native
  val elderWrath: Double = js.native
  val tooltip: Tooltip = js.native
  val shimmers: js.Array[Shimmer] = js.native
  val shimmerTypes: ShimmerTypes = js.native
  val buffs: MapLike[String, Buff] = js.native
  //val wrinklers: js.Array[Wrinkler]= js.native
  val BuildingsOwned: Double = js.native

  @JSName("Has")
  def upgradeBought(upgradeName: String): 0 | 1 = js.native
  @JSName("HasAchiev")
  def achievementWon(what: String): 0 | 1 = js.native
  def Win(what: String | js.Array[String]): Boolean = js.native
  def Unlock(what: String | js.Array[String]): Boolean = js.native
  def ComputeCps(base: Double, mult: Double, bonus: Double): Double = js.native
  def ComputeCps(base: Double, mult: Double): Double = js.native
  def hasGod(name: String): false | 1 | 2 | 3 = js.native
  def getWrinklersMax(): Double = js.native
  def Notify(title: String, desc: String, pic: js.Array[Double]): Double = js.native
  def Notify(title: String, desc: String): Double = js.native
  def CalculateGains(): Unit = js.native
  def mouseCps(): Double = js.native
  def auraMult(name: String): Double = js.native
  def eff(name: String): Double = js.native
  def crateTooltip(`object`: GameBuyable, context: String): String = js.native
  def hasAura(name: String): Boolean = js.native

  def registerHook(hookId: String, f: js.Function): Unit = js.native
  def registerMod(id: String, mod: GameMod): Unit = js.native

  def removeHook(hookId: String, f: js.Function): Unit = js.native
}
