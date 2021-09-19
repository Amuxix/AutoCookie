package autocookie.buyable

import autocookie.{AutoCookie, Helpers, Logger}
import typings.cookieclicker.Game.{AchievementPool, PseudoBoolean, Achievement as GameAchievement}
import typings.cookieclicker.global.Game
import autocookie.Helpers.*
import org.scalajs.dom.console
import autocookie.buyable.traits.{BuildingsRequired, UnlocksAchievment}

object Achievement {
  def getByName(name: String): Achievement = AutoCookie.achievements
    .getOrElse(name, throw new Exception(s"Unable to find Achievement named \"$name\""))

  def getGameAchievementByName(name: String): GameAchievement =
    Game.AchievementsById
      .find(_.name.equalsIgnoreCase(name))
      .getOrElse(throw new Exception(s"Unable to find Game Achievement named \"$name\""))
}

class Achievement(
  val name: String,
  override protected val buildingRequirementsSeq: BuildingRequirement*
) extends Buyable with BuildingsRequired with UnlocksAchievment {
  override type T = GameAchievement
  override lazy val gameBuyable: GameAchievement = Achievement.getGameAchievementByName(name)
  override protected val achievmentName: String = name

  def won: Boolean = gameBuyable.won

  override lazy val canEventuallyGet: Boolean =
  //If an Achievement has no requirements it means we can't purchase our way to get it.
  //It comes from baking a certain amount of cookies for example, or having a certain amount of different grandmas
    !won && buildingRequirements.nonEmpty

  override protected def innerBuy(): Unit =
    if won then
      removeFromBuyables()
    else
      Logger.error(s"Trying to buy Achievment $name")

}
