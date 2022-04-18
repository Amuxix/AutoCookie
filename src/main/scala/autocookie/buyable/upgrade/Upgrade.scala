package autocookie.buyable.upgrade

import autocookie.{AutoCookie, Helpers, Logger}
import autocookie.buyable.*
import autocookie.Helpers.*
import autocookie.buyable.traits.UpgradesRequired
import cookieclicker.buyables.GameUpgrade
import cookieclicker.Game
import scala.scalajs.js.annotation.{JSExportTopLevel, JSExport}

@JSExportTopLevel("Upgrade")
object Upgrade:
  @JSExport
  def getByName(name: String): Upgrade = AutoCookie.upgrades.getOrElse(name, throw new Exception(s"Unable to find Upgrade named \"$name\""))

  def getGameUpgradeByName(name: String): GameUpgrade = Game.upgradesByName(name)

  def canEventuallyGet(upgrade: Upgrade): Boolean =
    lazy val canGet = upgrade match {
      //case legacyUpgrade: LegacyUpgrade if legacyUpgrade.upgradeRequirements.isEmpty => owned
      case legacyUpgrade: LegacyUpgrade => false
      case upgrade: UpgradesRequired                                                 =>
        //All legacy upgrade dependencies must be owned
        upgrade.upgradeRequirements.forall {
          case legacyUpgrade: LegacyUpgrade => legacyUpgrade.owned
          case _                            => true
        }
      case _                                                                         => true
    }
    !upgrade.owned && canGet

abstract class Upgrade extends Buyable:
  override type T = GameUpgrade
  override lazy val gameBuyable: GameUpgrade = Upgrade.getGameUpgradeByName(name)

  /**
   * Can this be bought in this ascension? ie if this depends on a legacy upgrade we don't have this ascension this
   * can't be bought.
   */
  override lazy val canEventuallyGet: Boolean = Upgrade.canEventuallyGet(this)

  def owned: Boolean = gameBuyable.bought

  override protected def innerBuy(): Unit =
    if !gameBuyable.unlocked then
      Logger.log(s"Trying to buy $name but its not unlocked yet!")
      unlockRequirements()
    if requirements.nonEmpty then
      AutoCookie.stopped = true
      Logger.error(s"Trying to buy upgrade $name but it still has requirements")
    else
      val text = s"Bought $name"
      Logger.log(text)
      Game.Notify(text, "")
      removeFromBuyables()
      gameBuyable.buy(false)