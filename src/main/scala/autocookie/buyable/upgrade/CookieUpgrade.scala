package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirement
import autocookie.buyable.traits.{BuildingsRequired, CookiePower}

class CookieUpgrade(
  override val name: String, override protected val buildingRequirementsSeq: Seq[BuildingRequirement] = Seq
    .empty
) extends Upgrade with BuildingsRequired with CookiePower
