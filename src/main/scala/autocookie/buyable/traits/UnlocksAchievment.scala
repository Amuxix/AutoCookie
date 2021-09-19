package autocookie.buyable.traits

import autocookie.buyable.{Achievement, Buyable}

trait UnlocksAchievment {
  this: Buyable =>
  protected val achievmentName: String

  lazy val originalAchievmentUnlocked: Achievement = Achievement.getByName(achievmentName)
  lazy val achievmentsUnlocked: Set[Achievement] =
    val fromUpgrades = this match {
      case upgradesRequired: UpgradesRequired =>
        upgradesRequired.upgradeRequirements.collect {
          case unlocksAchievment: UnlocksAchievment => unlocksAchievment.originalAchievmentUnlocked
        }
      case _                                  => Set.empty
    }
    fromUpgrades + this.originalAchievmentUnlocked


}
