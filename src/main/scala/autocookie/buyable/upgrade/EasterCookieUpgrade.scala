package autocookie.buyable.upgrade

import autocookie.Helpers.season
import autocookie.Season
import autocookie.buyable.traits.CookiePower
import cookieclicker.Game

class EasterCookieUpgrade(
  override val name: String,
) extends Upgrade with CookiePower:
  override protected val power: Double = 1D
  /**
   * Can this be bought in this ascension? ie if this depends on a legacy upgrade we don't have this ascension this
   * can't be bought.
   */
  override lazy val canEventuallyGet: Boolean =
    Upgrade.canEventuallyGet(this) &&
      (Game.season.contains(Season.Easter) || Upgrade.getByName("Season switcher").owned)
