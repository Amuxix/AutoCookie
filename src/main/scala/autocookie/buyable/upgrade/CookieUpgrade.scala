package autocookie.buyable.upgrade

import autocookie.buyable.BuildingRequirements.BuildingRequirements
import autocookie.buyable.traits.{BuildingsRequired, CookiePower}

class CookieUpgrade(
  override val name: String,
  override protected val originalBuildingRequirements: BuildingRequirements = BuildingRequirements.empty,
) extends Upgrade with BuildingsRequired with CookiePower
