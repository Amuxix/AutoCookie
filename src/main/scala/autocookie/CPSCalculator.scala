package autocookie

import autocookie.Helpers.*
import autocookie.buyable.traits.CookiePower
import autocookie.buyable.{Building, BuildingRequirement}
import autocookie.buyable.upgrade.{CookieUpgrade, Upgrade}
import typings.cookieclicker.Game.GameObject as GameBuilding
import typings.cookieclicker.global.Game

object CPSCalculator {
  def godBuildingCpsMultiplier: Double =
    //val ages = 1 //TODO: considering if this can be implemented
    // format: off
    val decadence = List(1, .93, .95, .98)(getGodLevel("decadence"))
    val asceticism = List(1, 1.15, 1.1, 1.05)(getGodLevel("asceticism"))
    val industry = List(1, 1.1, 1.06, 1.03)(getGodLevel("industry"))
    val labor = List(1, .97, .98, .99)(getGodLevel("labor"))
    // format: on
    asceticism * decadence * industry * labor

  def hasOrIsInChoices(upgradeName: String, upgrades: Set[Upgrade]): Boolean =
    val upgrade = Upgrade.getByName(upgradeName)
    upgrade.owned || upgrades.contains(upgrade)

  def calculateBuildingCPS(
    upgrades: Set[Upgrade],
    level: Int,
    baseCps: Double,
    amount: Int,
    synergyMap: Map[String, Double],
    exponentialUpgrades: List[String],
    debug: Boolean,
    add: Double = 0
  ): Double =
    val exponentialMultiplier = math.pow(2, exponentialUpgrades.count(hasOrIsInChoices(_, upgrades)))
    val levelMultiplier = 1 + level * 0.01
    val synergyMultiplier = synergyMap.foldLeft(1D) {
      case (synergyMultiplier, (upgradeName, multiplier)) if hasOrIsInChoices(upgradeName, upgrades) => 
        synergyMultiplier * (1 + multiplier)
      case (synergyMultiplier, _)                                                                    => 
        synergyMultiplier
    }
    if debug then Logger.debug(s"base cps: $baseCps, exponentialMultiplier: $exponentialMultiplier, levelMultiplier: $levelMultiplier, synergyMultiplier: $synergyMultiplier, add: $add")
    (baseCps * exponentialMultiplier + add) * amount * levelMultiplier * synergyMultiplier

  def calculateCursorsCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], nonCursors: Int, debug: Boolean = false): Double =
    lazy val fingerAdd: Map[String, Double] = Map(
      "Million fingers" -> 5,
      "Billion fingers" -> 10,
      "Trillion fingers" -> 20,
      "Quadrillion fingers" -> 20,
      "Quintillion fingers" -> 20,
      "Sextillion fingers" -> 20,
      "Septillion fingers" -> 20,
      "Octillion fingers" -> 20,
      "Nonillion fingers" -> 20,
    )
    val fingersAdd =
      if hasOrIsInChoices("Thousand fingers", upgrades) then
        if debug then Logger.debug(s"nonCursors $nonCursors")
        nonCursors * fingerAdd.foldLeft(0.1) {
          case (totalAdd, (name, multiplier)) if hasOrIsInChoices(name, upgrades) =>
            if debug then Logger.debug(s"Adding $name total: ${totalAdd * multiplier}")
            totalAdd * multiplier
          case (totalAdd, _)                                                      => totalAdd
        }
      else
        0

    val synergyMap: Map[String, Double] = Map(
      "Mice clicking mice" -> (buildings(Building.fractalEngine) * .05)
    )
    val exponentialUpgrades = List(
      "Reinforced index finger",
      "Carpal tunnel prevention cream",
      "Ambidextrous"
    )
    //0.1 is the cursor base CPS
    calculateBuildingCPS(upgrades, Building.cursor.level.toInt, 0.1, buildings(Building.cursor), synergyMap, exponentialUpgrades, debug, fingersAdd)

  def calculateGrandmasCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Forwards from grandma",
      "Steel-plated rolling pins",
      "Lubricated dentures",
      "Prune juice",
      "Double-thick glasses",
      "Aging agents",
      "Xtreme walkers",
      "The Unbridling",
      "Reverse dementia",
      "Timeproof hair dyes",
      "Good manners",
      "Farmer grandmas",
      "Miner grandmas",
      "Worker grandmas",
      "Banker grandmas",
      "Priestess grandmas",
      "Witch grandmas",
      "Cosmic grandmas",
      "Transmuted grandmas",
      "Altered grandmas",
      "Grandmas' grandmas",
      "Antigrandmas",
      "Rainbow grandmas",
      "Lucky grandmas",
      "Metagrandmas",
      "Bingo center/Research facility",
      "Bingo center/Research facility", //This is duplicated on purpose as bingo center increases grandma cps by 4x
      "Ritual rolling pins",
    )
    val baseIncreases = Map(
      "One mind" -> buildings(Building.grandma) * 0.02,
      "Communal brainsweep" -> buildings(Building.grandma) * 0.02,
      "Elder Pact" -> buildings(Building.portal) * 0.05,
    )
    val baseCps = baseIncreases.foldLeft(Building.grandma.baseCps) {
      case (baseIncrease, (upgradeName, increase)) if hasOrIsInChoices(upgradeName, upgrades) => baseIncrease + increase
      case (baseIncrease, _)                                                                  => baseIncrease
    }
    calculateBuildingCPS(upgrades, Building.grandma.level.toInt, baseCps, buildings(Building.grandma), Map.empty, exponentialUpgrades, debug)

  def calculateFarmsCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Cheap hoes",
      "Fertilizer",
      "Cookie trees",
      "Genetically-modified cookies",
      "Gingerbread scarecrows",
      "Pulsar sprinklers",
      "Fudge fungus",
      "Wheat triffids",
      "Humane pesticides",
      "Barnstars",
      "Lindworms",
    )
    val synergyMap: Map[String, Double] = Map(
      "Farmer grandmas" -> (buildings(Building.grandma) * .01),
      "Future almanacs" -> (buildings(Building.timeMachine) * .05),
      "Rain prayer" -> (buildings(Building.temple) * .05),
      "Magical botany" -> (buildings(Building.wizardTower) * .05),
      "Infernal crops" -> (buildings(Building.portal) * .05),
    )
    calculateBuildingCPS(upgrades, Building.farm.level.toInt, Building.farm.baseCps, buildings(Building.farm), synergyMap, exponentialUpgrades, debug)

  def calculateMinesCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Sugar gas",
      "Megadrill",
      "Ultradrill",
      "Ultimadrill",
      "H-bomb mining",
      "Coreforge",
      "Planetsplitters",
      "Canola oil wells",
      "Mole people",
      "Mine canaries",
      "Bore again",
    )
    val synergyMap: Map[String, Double] = Map(
      "Miner grandmas" -> (buildings(Building.grandma) / 2D * .01),
      "Seismic magic" -> (buildings(Building.wizardTower) * .05),
      "Asteroid mining" -> (buildings(Building.shipment) * .05),
      "Fossil fuels" -> (buildings(Building.shipment) * .05),
      "Primordial ores" -> (buildings(Building.alchemyLab) * .05),
      "Gemmed talismans" -> (buildings(Building.chancemaker) * .05),
    )
    calculateBuildingCPS(upgrades, Building.mine.level.toInt, Building.mine.baseCps, buildings(Building.mine), synergyMap, exponentialUpgrades, debug)

  def calculateFactoriesCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Sturdier conveyor belts",
      "Child labor",
      "Sweatshop",
      "Radium reactors",
      "Recombobulators",
      "Deep-bake process",
      "Cyborg workforce",
      "78-hour days",
      "Machine learning",
      "Brownie point system",
      "\"Volunteer\" interns",
    )
    val synergyMap: Map[String, Double] = Map(
      "Worker grandmas" -> (buildings(Building.grandma) / 3D * .01),
      "Quantum electronics" -> (buildings(Building.antimatterCondenser) * .05),
      "Temporal overclocking" -> (buildings(Building.timeMachine) * .05),
      "Printing presses" -> (buildings(Building.bank) * .05),
      "Shipyards" -> (buildings(Building.shipment) * .05),
    )
    calculateBuildingCPS(upgrades, Building.factory.level.toInt, Building.factory.baseCps, buildings(Building.factory), synergyMap, exponentialUpgrades, debug)

  def calculateBanksCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Taller tellers",
      "Scissor-resistant credit cards",
      "Acid-proof vaults",
      "Chocolate coins",
      "Exponential interest rates",
      "Financial zen",
      "Way of the wallet",
      "The stuff rationale",
      "Edible money",
      "Grand supercycles",
      "Rules of acquisition",
    )
    val synergyMap: Map[String, Double] = Map(
      "Banker grandmas" -> (buildings(Building.grandma) / 4D * .01),
      "Contracts from beyond" -> (buildings(Building.portal) * .05),
      "Printing presses" -> (buildings(Building.factory) * .001),
      "Gold fund" -> (buildings(Building.alchemyLab) * .05),
      "Extra physics funding" -> (buildings(Building.antimatterCondenser) * .05),
    )
    calculateBuildingCPS(upgrades, Building.bank.level.toInt, Building.bank.baseCps, buildings(Building.bank), synergyMap, exponentialUpgrades, debug)

  def calculateTemplesCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Golden idols",
      "Sacrifices",
      "Delicious blessing",
      "Sun festival",
      "Enlarged pantheon",
      "Great Baker in the sky",
      "Creation myth",
      "Theocracy",
      "Sick rap prayers",
      "Psalm-reading",
      "War of the gods",
    )
    val synergyMap: Map[String, Double] = Map(
      "Priestess grandmas" -> (buildings(Building.grandma) / 5D * .01),
      "Rain prayer" -> (buildings(Building.farm) * .001),
      "Paganism" -> (buildings(Building.portal) * .05),
      "God particle" -> (buildings(Building.antimatterCondenser) * .05),
      "Mystical energies" -> (buildings(Building.prism) * .05),
    )
    calculateBuildingCPS(upgrades, Building.temple.level.toInt, Building.temple.baseCps, buildings(Building.temple), synergyMap, exponentialUpgrades, debug)

  def calculateWizardTowersCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Pointier hats",
      "Beardlier beards",
      "Ancient grimoires",
      "Kitchen curses",
      "School of sorcery",
      "Dark formulas",
      "Cookiemancy",
      "Rabbit trick",
      "Deluxe tailored wands",
      "Immobile spellcasting",
      "Electricity",
    )
    val synergyMap: Map[String, Double] = Map(
      "Witch grandmas" -> (buildings(Building.grandma) / 6D * .01),
      "Seismic magic" -> (buildings(Building.mine) * .001),
      "Arcane knowledge" -> (buildings(Building.alchemyLab) * .05),
      "Magical botany" -> (buildings(Building.farm) * .001),
      "Light magic" -> (buildings(Building.prism) * .05),
    )
    calculateBuildingCPS(upgrades, Building.wizardTower.level.toInt, Building.wizardTower.baseCps, buildings(Building.wizardTower), synergyMap, exponentialUpgrades, debug)

  def calculateShipmentsCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Vanilla nebulae",
      "Wormholes",
      "Frequent flyer",
      "Warp drive",
      "Chocolate monoliths",
      "Generation ship",
      "Dyson sphere",
      "The final frontier",
      "Autopilot",
      "Restaurants at the end of the universe",
      "Universal alphabet",
    )
    val synergyMap: Map[String, Double] = Map(
      "Cosmic grandmas" -> (buildings(Building.grandma) / 7D * .01),
      "Seismic magic" -> (buildings(Building.mine) * .001),
      "Arcane knowledge" -> (buildings(Building.alchemyLab) * .05),
      "Magical botany" -> (buildings(Building.farm) * .001),
      "Light magic" -> (buildings(Building.prism) * .05),
    )
    calculateBuildingCPS(upgrades, Building.shipment.level.toInt, Building.shipment.baseCps, buildings(Building.shipment), synergyMap, exponentialUpgrades, debug)

  def calculateAlchemyLabsCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Antimony",
      "Essence of dough",
      "True chocolate",
      "Ambrosia",
      "Aqua crustulae",
      "Origin crucible",
      "Theory of atomic fluidity",
      "Beige goo",
      "The advent of chemistry",
      "On second thought",
      "Public betterment",
    )
    val synergyMap: Map[String, Double] = Map(
      "Transmuted grandmas" -> (buildings(Building.grandma) / 8D * .01),
      "Arcane knowledge" -> (buildings(Building.wizardTower) * .001),
      "Primordial ores" -> (buildings(Building.mine) * .001),
      "Gold fund" -> (buildings(Building.bank) * .001),
      "Chemical proficiency" -> (buildings(Building.antimatterCondenser) * .05),
    )
    calculateBuildingCPS(upgrades, Building.alchemyLab.level.toInt, Building.alchemyLab.baseCps, buildings(Building.alchemyLab), synergyMap, exponentialUpgrades, debug)

  def calculatePortalsCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Ancient tablet",
      "Insane oatling workers",
      "Soul bond",
      "Sanity dance",
      "Brane transplant",
      "Deity-sized portals",
      "End of times back-up plan",
      "Maddening chants",
      "The real world",
      "Dimensional garbage gulper",
      "Embedded microportals",
    )
    val synergyMap: Map[String, Double] = Map(
      "Altered grandmas" -> (buildings(Building.grandma) / 9D * .01),
      "Contracts from beyond" -> (buildings(Building.bank) * .001),
      "Paganism" -> (buildings(Building.temple) * .001),
      "Infernal crops" -> (buildings(Building.farm) * .001),
      "Abysmal glimmer" -> (buildings(Building.prism) * .05),
    )
    calculateBuildingCPS(upgrades, Building.portal.level.toInt, Building.portal.baseCps, buildings(Building.portal), synergyMap, exponentialUpgrades, debug)

  def calculateTimeMachinesCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false)
  : Double =
    val exponentialUpgrades = List(
      "Flux capacitors",
      "Time paradox resolver",
      "Quantum conundrum",
      "Causality enforcer",
      "Yestermorrow comparators",
      "Far future enactment",
      "Great loop hypothesis",
      "Cookietopian moments of maybe",
      "Second seconds",
      "Additional clock hands",
      "Nostalgia",
    )
    val synergyMap: Map[String, Double] = Map(
      "Grandmas\' grandmas" -> (buildings(Building.grandma) / 10D * .01),
      "Future almanacs" -> (buildings(Building.farm) * .001),
      "Temporal overclocking" -> (buildings(Building.factory) * .001),
      "Relativistic parsec-skipping" -> (buildings(Building.shipment) * .001),
      "Primeval glow" -> (buildings(Building.prism) * .05),
    )
    calculateBuildingCPS(upgrades, Building.timeMachine.level.toInt, Building.timeMachine.baseCps, buildings(Building.timeMachine), synergyMap, exponentialUpgrades, debug)

  def calculateAntimatterCondensersCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Sugar bosons",
      "String theory",
      "Large macaron collider",
      "Big bang bake",
      "Reverse cyclotrons",
      "Nanocosmics",
      "The Pulse",
      "Some other super-tiny fundamental particle? Probably?",
      "Quantum comb",
      "Baking Nobel prize",
      "The definite molecule",
    )
    val synergyMap: Map[String, Double] = Map(
      "Antigrandmas" -> (buildings(Building.grandma) / 11D * .01),
      "Quantum electronics" -> (buildings(Building.factory) * .001),
      "God particle" -> (buildings(Building.temple) * .001),
      "Extra physics funding" -> (buildings(Building.bank) * .001),
      "Chemical proficiency" -> (buildings(Building.alchemyLab) * .001),
      "Charm quarks" -> (buildings(Building.chancemaker) * .05),
    )
    calculateBuildingCPS(upgrades, Building.antimatterCondenser.level.toInt, Building.antimatterCondenser.baseCps, buildings(Building.antimatterCondenser), synergyMap, exponentialUpgrades, debug)

  def calculatePrismsCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Gem polish",
      "9th color",
      "Chocolate light",
      "Grainbow",
      "Pure cosmic light",
      "Glow-in-the-dark",
      "Lux sanctorum",
      "Reverse shadows",
      "Crystal mirrors",
      "Reverse theory of light",
      "Light capture measures",
    )
    val synergyMap: Map[String, Double] = Map(
      "Rainbow grandmas" -> (buildings(Building.grandma) / 12D * .01),
      "Abysmal glimmer" -> (buildings(Building.portal) * .001),
      "Primeval glow" -> (buildings(Building.timeMachine) * .001),
      "Light magic" -> (buildings(Building.wizardTower) * .001),
      "Mystical energies" -> (buildings(Building.temple) * .001),
      "Recursive mirrors" -> (buildings(Building.antimatterCondenser) * .05),
    )
    calculateBuildingCPS(upgrades, Building.prism.level.toInt, Building.prism.baseCps, buildings(Building.prism), synergyMap, exponentialUpgrades, debug)

  def calculateChancemakersCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Your lucky cookie",
      "\"All Bets Are Off\" magic coin",
      "Winning lottery ticket",
      "Four-leaf clover field",
      "A recipe book about books",
      "Leprechaun village",
      "Improbability drive",
      "Antisuperstistronics",
      "Bunnypedes",
      "Revised probabilistics",
      "0-sided dice",
    )
    val synergyMap: Map[String, Double] = Map(
      "Lucky grandmas" -> (buildings(Building.grandma) / 13D * .01),
      "Gemmed talismans" -> (buildings(Building.mine) * .001),
      "Charm quarks" -> (buildings(Building.antimatterCondenser) * .001),
    )
    calculateBuildingCPS(upgrades, Building.chancemaker.level.toInt, Building.chancemaker.baseCps, buildings(Building.chancemaker), synergyMap, exponentialUpgrades, debug)

  def calculateFractalEnginesCps(upgrades: Set[Upgrade], buildings: Map[GameBuilding, Int], debug: Boolean = false): Double =
    val exponentialUpgrades = List(
      "Metabakeries",
      "Mandelbrown sugar",
      "Fractoids",
      "Nested universe theory",
      "Menger sponge cake",
      "One particularly good-humored cow",
      "Chocolate ouroboros",
      "Nested",
      "Space-filling fibers",
      "Endless book of prose",
      "The set of all sets",
    )
    val synergyMap: Map[String, Double] = Map(
      "Metagrandmas" -> (buildings(Building.grandma) / 14D * .01),
      "Recursive mirrors" -> (buildings(Building.prism) * .001),
      "Mice clicking mice" -> (buildings(Building.cursor) * .001),
    )
    calculateBuildingCPS(upgrades, Building.fractalEngine.level.toInt, Building.fractalEngine.baseCps, buildings(Building.fractalEngine), synergyMap, exponentialUpgrades, debug)

  def apply(buildingRequirements: Set[BuildingRequirement] = Set.empty, upgrades: Set[Upgrade] = Set.empty, debug: Boolean = false): Double =
    val buildMult = godBuildingCpsMultiplier
    val requirementMap = buildingRequirements.map(req => req.gameBuyable -> req.amount).toMap
    val buildings = Building.all.map { building =>
      building -> (building.amount.toInt max requirementMap.getOrElse(building, 0))
    }.toMap
    val nonCursors = buildings.view.filterKeys(_ != Building.cursor).values.sum
    val oldBuildingCps: Double = Building.all.sumBy(_.storedTotalCps)

    val cookieUpgradesMultiplier = upgrades.foldLeft(1D) {
      case (multiplier, upgrade: CookiePower) => multiplier * upgrade.powerMultiplier
      case (multiplier, _)                    => multiplier
    }
    val increases: Seq[Double] = Seq(
      calculateCursorsCps(upgrades, buildings, nonCursors),
      calculateGrandmasCps(upgrades, buildings),
      calculateFarmsCps(upgrades, buildings),
      calculateMinesCps(upgrades, buildings),
      calculateFactoriesCps(upgrades, buildings),
      calculateBanksCps(upgrades, buildings),
      calculateTemplesCps(upgrades, buildings),
      calculateWizardTowersCps(upgrades, buildings),
      calculateShipmentsCps(upgrades, buildings),
      calculateAlchemyLabsCps(upgrades, buildings),
      calculatePortalsCps(upgrades, buildings),
      calculateTimeMachinesCps(upgrades, buildings),
      calculateAntimatterCondensersCps(upgrades, buildings),
      calculatePrismsCps(upgrades, buildings),
      calculateChancemakersCps(upgrades, buildings),
      calculateFractalEnginesCps(upgrades, buildings),
    )

    if debug then
      Logger.debug(s"Cursors ${calculateCursorsCps(upgrades, buildings, nonCursors, debug) - Building.cursor.storedTotalCps}")
      Logger.debug(s"Grandmas ${calculateGrandmasCps(upgrades, buildings, debug) - Building.grandma.storedTotalCps}")
      Logger.debug(s"Farms ${calculateFarmsCps(upgrades, buildings, debug) - Building.farm.storedTotalCps}")
      Logger.debug(s"Mines ${calculateMinesCps(upgrades, buildings, debug) - Building.mine.storedTotalCps}")
      Logger.debug(s"Factories ${calculateFactoriesCps(upgrades, buildings, debug) - Building.factory.storedTotalCps}")
      Logger.debug(s"Banks ${calculateBanksCps(upgrades, buildings, debug) - Building.bank.storedTotalCps}")
      Logger.debug(s"Temples ${calculateTemplesCps(upgrades, buildings, debug) - Building.temple.storedTotalCps}")
      Logger.debug(s"WizardTowers ${calculateWizardTowersCps(upgrades, buildings, debug) - Building.wizardTower.storedTotalCps}")
      Logger.debug(s"Shipments ${calculateShipmentsCps(upgrades, buildings, debug) - Building.shipment.storedTotalCps}")
      Logger.debug(s"AlchemyLabs ${calculateAlchemyLabsCps(upgrades, buildings, debug) - Building.alchemyLab.storedTotalCps}")
      Logger.debug(s"Portals ${calculatePortalsCps(upgrades, buildings, debug) - Building.portal.storedTotalCps}")
      Logger.debug(s"TimeMachines ${calculateTimeMachinesCps(upgrades, buildings, debug) - Building.timeMachine.storedTotalCps}")
      Logger.debug(s"AntimatterCondensers ${calculateAntimatterCondensersCps(upgrades, buildings, debug) - Building.antimatterCondenser.storedTotalCps}")
      Logger.debug(s"Prisms ${calculatePrismsCps(upgrades, buildings, debug) - Building.prism.storedTotalCps}")
      Logger.debug(s"Chancemakers ${calculateChancemakersCps(upgrades, buildings, debug) - Building.chancemaker.storedTotalCps}")
      Logger.debug(s"FractalEngines ${calculateFractalEnginesCps(upgrades, buildings, debug) - Building.fractalEngine.storedTotalCps}")

    ((increases.sum * godBuildingCpsMultiplier * cookieUpgradesMultiplier - oldBuildingCps) * Game.globalCpsMult).round(6)
}
