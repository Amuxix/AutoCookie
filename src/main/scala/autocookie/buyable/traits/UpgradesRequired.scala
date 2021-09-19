package autocookie.buyable.traits

import autocookie.buyable.Buyable
import autocookie.buyable.upgrade.Upgrade

trait UpgradesRequired {
  this: Buyable =>
  protected val upgradeRequirementsNames: Seq[String]
  /**
   * The original requirements of this upgrade
   */
  lazy val originalUpgradeRequirements: Set[Upgrade] = upgradeRequirementsNames.map(Upgrade.getByName).toSet
  lazy val upgradeRequirements: Set[Upgrade] = {
    /*lazy val children = originalUpgradeRequirements.map(_.name).mkString(", ")
    lazy val childrenText = if children.nonEmpty then s" -> $children" else ""
    println(s"Calculating upgrade requirements of $name${childrenText}")*/
    originalUpgradeRequirements.flatMap {
      case upgradeRequired: UpgradesRequired => upgradeRequired.upgradeRequirements + upgradeRequired
      case upgradeRequired                   => Set(upgradeRequired)
    }
  }

  /**
   * Contains all building requirements of this buyable and any requirements it might have
   */

  /*protected def filteredBuildingRequirements: Set[BuildingRequirement] =
    originalBuildingRequirements.filter(req => req.amount > req.gameBuyable.amount)

  def hasRequirements: Boolean =
    filteredBuildingRequirements.length > 0*/
}
