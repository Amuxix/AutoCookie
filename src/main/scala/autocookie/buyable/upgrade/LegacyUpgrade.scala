package autocookie.buyable.upgrade

import autocookie.{AutoCookie, Logger}
import autocookie.buyable.traits.UpgradesRequired

class LegacyUpgrade(
  override val name: String,
  override protected val upgradeRequirementsNames: String*
) extends Upgrade with UpgradesRequired {
  override protected def innerBuy(): Unit =
    AutoCookie.stopped = true
    Logger.error(s"Trying to buy legacy upgrade $name")
    Logger.debug(s"canEventuallyGet $canEventuallyGet")
    Logger.debug(s"cpsIncrease $cpsIncrease")
    Logger.debug(s"price $price")
    Logger.debug(s"owned $owned")
}
