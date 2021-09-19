package autocookie.buyable.traits

import autocookie.buyable.{BuildingRequirement, Buyable}

trait SingleBuildingRequired extends BuildingsRequired {
  this: Buyable =>
  protected val buildingRequirement: BuildingRequirement
  override protected val buildingRequirementsSeq: Seq[BuildingRequirement] = Seq(buildingRequirement)
}
