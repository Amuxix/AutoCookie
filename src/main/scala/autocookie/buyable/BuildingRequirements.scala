package autocookie.buyable

import autocookie.Helpers.{sumBy => sumByF}

object BuildingRequirements:
  opaque type BuildingRequirements = Map[Building, BuildingRequirement]

  object BuildingRequirements:
    def apply(requirements: Iterable[BuildingRequirement]): BuildingRequirements = requirements.groupBy(_.building).view.mapValues(_.maxBy(_.requiredAmount)).toMap
    def apply(requirement: BuildingRequirement): BuildingRequirements = Map(requirement.building -> requirement)
    def apply(t: (Building, Int)*): BuildingRequirements = t.map((building, amount) => building -> BuildingRequirement(building, amount)).toMap
    lazy val empty: BuildingRequirements = Map.empty

    def generateRequirementsForAllBuildings(amount: Int): BuildingRequirements =
      Building.all.map(building => building -> BuildingRequirement(building, amount)).toMap

  extension (requirements: BuildingRequirements)
    def getMissingAmount(building: Building): Int = requirements.get(building).fold(0)(_.missingAmount)
    def toSet: Set[BuildingRequirement] = requirements.values.toSet
    def +(buildingRequirement: BuildingRequirement): BuildingRequirements =
      requirements.updatedWith(buildingRequirement.building) { requirement =>
        Some {
          requirement.fold(buildingRequirement) { existingRequirement =>
            if existingRequirement.requiredAmount >= buildingRequirement.requiredAmount then existingRequirement else buildingRequirement
          }
        }
      }
    def ++(buildingRequirements: BuildingRequirements): BuildingRequirements = BuildingRequirements((requirements ++ buildingRequirements).values)
    def isEmpty: Boolean = requirements.isEmpty
    def nonEmpty: Boolean = requirements.nonEmpty
    def filter(predicate: BuildingRequirement => Boolean): BuildingRequirements = requirements.filter((_, requirement) => predicate(requirement))
    def sumBy[U: Numeric](f: BuildingRequirement => U): U = requirements.values.sumByF(f)
