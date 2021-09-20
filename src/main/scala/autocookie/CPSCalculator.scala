package autocookie

import autocookie.Helpers.*
import autocookie.buyable.traits.CookiePower
import autocookie.buyable.upgrade.{CookieUpgrade, Upgrade}
import autocookie.buyable.{Building, BuildingRequirement}
import cookieclicker.buyables.GameBuilding
import cookieclicker.Game

import scala.collection.mutable

object CPSCalculator {
  def toUpgrade[A](t: (String, A)): (Upgrade, A) = (Upgrade.getByName(t._1), t._2)

  var cursors = 0
  var grandmas = 0
  var farms = 0
  var mines = 0
  var factories = 0
  var banks = 0
  var temples = 0
  var wizardTowers = 0
  var shipments = 0
  var alchemyLabs = 0
  var portals = 0
  var timeMachines = 0
  var antimatterCondensers = 0
  var prisms = 0
  var chancemakers = 0
  var fractalEngines = 0

  var extraCursors = 0
  var extraGrandmas = 0
  var extraFarms = 0
  var extraMines = 0
  var extraFactories = 0
  var extraBanks = 0
  var extraTemples = 0
  var extraWizardTowers = 0
  var extraShipments = 0
  var extraAlchemyLabs = 0
  var extraPortals = 0
  var extraTimeMachines = 0
  var extraAntimatterCondensers = 0
  var extraPrisms = 0
  var extraChancemakers = 0
  var extraFractalEngines = 0

  def totalExtra = extraCursors + extraGrandmas + extraFarms + extraMines + extraFactories + extraBanks + extraTemples +
    extraWizardTowers + extraShipments + extraAlchemyLabs + extraPortals + extraTimeMachines +
    extraAntimatterCondensers + extraPrisms + extraChancemakers + extraFractalEngines

  var extraNonCursors = 0

  lazy val fingerAdd: Map[Upgrade, Int] = Map(
    "Million fingers" -> 5,
    "Billion fingers" -> 10,
    "Trillion fingers" -> 20,
    "Quadrillion fingers" -> 20,
    "Quintillion fingers" -> 20,
    "Sextillion fingers" -> 20,
    "Septillion fingers" -> 20,
    "Octillion fingers" -> 20,
    "Nonillion fingers" -> 20,
  ).map(toUpgrade)

  val cursorSynergyMap: Map[Upgrade, () => Double] = Map(
    "Mice clicking mice" -> (() => fractalEngines * .05),
  ).map(toUpgrade)
  lazy val cursorExponentialUpgrades = List(
    "Reinforced index finger",
    "Carpal tunnel prevention cream",
    "Ambidextrous"
  ).map(Upgrade.getByName)

  lazy val grandmaExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val grandmaBaseIncreases = Map(
    "One mind" -> (() => grandmas * 0.02),
    "Communal brainsweep" -> (() => grandmas * 0.02),
    "Elder Pact" -> (() => portals * 0.05),
  ).map(toUpgrade)

  lazy val farmExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val farmSynergyMap: Map[Upgrade, () => Double] = Map(
    "Farmer grandmas" -> (() => grandmas * .01),
    "Future almanacs" -> (() => timeMachines * .05),
    "Rain prayer" -> (() => temples * .05),
    "Magical botany" -> (() => wizardTowers * .05),
    "Infernal crops" -> (() => portals * .05),
  ).map(toUpgrade)

  lazy val mineExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val mineSynergyMap: Map[Upgrade, () => Double] = Map(
    "Miner grandmas" -> (() => grandmas / 2D * .01),
    "Seismic magic" -> (() => wizardTowers * .05),
    "Asteroid mining" -> (() => shipments * .05),
    "Fossil fuels" -> (() => shipments * .05),
    "Primordial ores" -> (() => alchemyLabs * .05),
    "Gemmed talismans" -> (() => chancemakers * .05),
  ).map(toUpgrade)

  lazy val factoryExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val factorySynergyMap: Map[Upgrade, () => Double] = Map(
    "Worker grandmas" -> (() => grandmas / 3D * .01),
    "Quantum electronics" -> (() => antimatterCondensers * .05),
    "Temporal overclocking" -> (() => timeMachines * .05),
    "Printing presses" -> (() => banks * .05),
    "Shipyards" -> (() => shipments * .05),
  ).map(toUpgrade)

  lazy val bankExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val bankSynergyMap: Map[Upgrade, () => Double] = Map(
    "Banker grandmas" -> (() => grandmas / 4D * .01),
    "Contracts from beyond" -> (() => portals * .05),
    "Printing presses" -> (() => factories * .001),
    "Gold fund" -> (() => alchemyLabs * .05),
    "Extra physics funding" -> (() => antimatterCondensers * .05),
  ).map(toUpgrade)

  lazy val templeExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val templeSynergyMap: Map[Upgrade, () => Double] = Map(
    "Priestess grandmas" -> (() => grandmas / 5D * .01),
    "Rain prayer" -> (() => farms * .001),
    "Paganism" -> (() => portals * .05),
    "God particle" -> (() => antimatterCondensers * .05),
    "Mystical energies" -> (() => prisms * .05),
  ).map(toUpgrade)

  lazy val wizardTowerExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val wizardTowerSynergyMap: Map[Upgrade, () => Double] = Map(
    "Witch grandmas" -> (() => grandmas / 6D * .01),
    "Seismic magic" -> (() => mines * .001),
    "Arcane knowledge" -> (() => alchemyLabs * .05),
    "Magical botany" -> (() => farms * .001),
    "Light magic" -> (() => prisms * .05),
  ).map(toUpgrade)

  lazy val shipmentExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val shipmentSynergyMap: Map[Upgrade, () => Double] = Map(
    "Cosmic grandmas" -> (() => grandmas / 7D * .01),
    "Seismic magic" -> (() => mines * .001),
    "Arcane knowledge" -> (() => alchemyLabs * .05),
    "Magical botany" -> (() => farms * .001),
    "Light magic" -> (() => prisms * .05),
  ).map(toUpgrade)

  lazy val alchemyLabExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val alchemyLabSynergyMap: Map[Upgrade, () => Double] = Map(
    "Transmuted grandmas" -> (() => grandmas / 8D * .01),
    "Arcane knowledge" -> (() => wizardTowers * .001),
    "Primordial ores" -> (() => mines * .001),
    "Gold fund" -> (() => banks * .001),
    "Chemical proficiency" -> (() => antimatterCondensers * .05),
  ).map(toUpgrade)

  lazy val portalExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val portalSynergyMap: Map[Upgrade, () => Double] = Map(
    "Altered grandmas" -> (() => grandmas / 9D * .01),
    "Contracts from beyond" -> (() => banks * .001),
    "Paganism" -> (() => temples * .001),
    "Infernal crops" -> (() => farms * .001),
    "Abysmal glimmer" -> (() => prisms * .05),
  ).map(toUpgrade)

  lazy val timeMachineExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val timeMachineSynergyMap: Map[Upgrade, () => Double] = Map(
    "Grandmas\' grandmas" -> (() => grandmas / 10D * .01),
    "Future almanacs" -> (() => farms * .001),
    "Temporal overclocking" -> (() => factories * .001),
    "Relativistic parsec-skipping" -> (() => shipments * .001),
    "Primeval glow" -> (() => prisms * .05),
  ).map(toUpgrade)

  lazy val antimatterCondenserExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val antimatterCondenserSynergyMap: Map[Upgrade, () => Double] = Map(
    "Antigrandmas" -> (() => grandmas / 11D * .01),
    "Quantum electronics" -> (() => factories * .001),
    "God particle" -> (() => temples * .001),
    "Extra physics funding" -> (() => banks * .001),
    "Chemical proficiency" -> (() => alchemyLabs * .001),
    "Charm quarks" -> (() => chancemakers * .05),
  ).map(toUpgrade)

  lazy val prismExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val prismSynergyMap: Map[Upgrade, () => Double] = Map(
    "Rainbow grandmas" -> (() => grandmas / 12D * .01),
    "Abysmal glimmer" -> (() => portals * .001),
    "Primeval glow" -> (() => timeMachines * .001),
    "Light magic" -> (() => wizardTowers * .001),
    "Mystical energies" -> (() => temples * .001),
    "Recursive mirrors" -> (() => antimatterCondensers * .05),
  ).map(toUpgrade)

  lazy val chancemakerExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val chancemakerSynergyMap: Map[Upgrade, () => Double] = Map(
    "Lucky grandmas" -> (() => grandmas / 13D * .01),
    "Gemmed talismans" -> (() => mines * .001),
    "Charm quarks" -> (() => antimatterCondensers * .001),
  ).map(toUpgrade)

  lazy val fractalExponentialUpgrades = List(
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
  ).map(Upgrade.getByName)
  lazy val fractalSynergyMap: Map[Upgrade, () => Double] = Map(
    "Metagrandmas" -> (() => grandmas / 14D * .01),
    "Recursive mirrors" -> (() => prisms * .001),
    "Mice clicking mice" -> (() => cursors * .001),
  ).map(toUpgrade)


  def godBuildingCpsMultiplier: Double =
    //val ages = 1 //TODO: considering if this can be implemented
    // format: off
    val decadence = getGodMultiplier("decadence")(Seq(1, .93, .95, .98))
    val asceticism = getGodMultiplier("asceticism")(Seq(1, 1.15, 1.1, 1.05))
    val industry = getGodMultiplier("industry")(Seq(1, 1.1, 1.06, 1.03))
    val labor = getGodMultiplier("labor")(Seq(1, .97, .98, .99))
    // format: on
    asceticism * decadence * industry * labor

  def hasOrIsInChoices(upgrade: Upgrade, upgrades: Set[Upgrade]): Boolean = upgrade.owned || upgrades.contains(upgrade)

  def calculateBuildingCPS(
    upgrades: Set[Upgrade],
    level: Int,
    baseCps: Double,
    amount: Int,
    synergyMap: Map[Upgrade, () => Double],
    exponentialUpgrades: List[Upgrade],
    add: Double = 0
  ): Double =
    val exponentialMultiplier = math.pow(2, exponentialUpgrades.count(hasOrIsInChoices(_, upgrades)))
    val levelMultiplier = 1 + level * 0.01
    val synergyMultiplier = synergyMap.foldLeft(1D) {
      case (synergyMultiplier, (upgrade, multiplierF)) if hasOrIsInChoices(upgrade, upgrades) =>
        synergyMultiplier * (1 + multiplierF())
      case (synergyMultiplier, _)                                                             =>
        synergyMultiplier
    }
    (baseCps * exponentialMultiplier + add) * amount * levelMultiplier * synergyMultiplier

  def calculateCursorsCps(upgrades: Set[Upgrade], nonCursors: Int): Double =
    if totalExtra == 0 then
      return Building.cursor.storedTotalCps
    val fingersAdd =
      if hasOrIsInChoices(Upgrade.getByName("Thousand fingers"), upgrades) then
        nonCursors * fingerAdd.foldLeft(0.1) {
          case (totalAdd, (name, multiplier)) if hasOrIsInChoices(name, upgrades) => totalAdd * multiplier
          case (totalAdd, _)                                                      => totalAdd
        }
      else
        0

    //0.1 is the cursor base CPS
    calculateBuildingCPS(upgrades, Building.cursor.level.toInt, 0.1, cursors, cursorSynergyMap, cursorExponentialUpgrades, fingersAdd)

  def calculateGrandmasCps(upgrades: Set[Upgrade]): Double =
    if extraGrandmas == 0  && extraFractalEngines == 0 then
      return Building.cursor.storedTotalCps
    val baseCps = grandmaBaseIncreases.foldLeft(Building.grandma.baseCps) {
      case (baseIncrease, (upgrade, increaseF)) if hasOrIsInChoices(upgrade, upgrades) => baseIncrease + increaseF()
      case (baseIncrease, _)                                                           => baseIncrease
    }
    calculateBuildingCPS(upgrades, Building.grandma.level.toInt, baseCps, grandmas, Map.empty, grandmaExponentialUpgrades)

  def calculateFarmsCps(upgrades: Set[Upgrade]): Double =
    if extraFarms == 0 && extraGrandmas == 0 && extraTimeMachines == 0 && extraTemples == 0 && extraWizardTowers == 0 && extraPortals == 0 then
      return Building.farm.storedTotalCps
    calculateBuildingCPS(upgrades, Building.farm.level.toInt, Building.farm.baseCps, farms, farmSynergyMap, farmExponentialUpgrades)

  def calculateMinesCps(upgrades: Set[Upgrade]): Double =
    if extraMines == 0 && extraGrandmas == 0 && extraWizardTowers == 0 && extraShipments == 0 && extraAlchemyLabs == 0 && extraChancemakers == 0 then
      return Building.mine.storedTotalCps
    calculateBuildingCPS(upgrades, Building.mine.level.toInt, Building.mine.baseCps, mines, mineSynergyMap, mineExponentialUpgrades)

  def calculateFactoriesCps(upgrades: Set[Upgrade]): Double =
    if extraFactories == 0 && extraGrandmas == 0 && extraAntimatterCondensers == 0 && extraTimeMachines == 0 && extraBanks == 0 && extraShipments == 0 then
      return Building.factory.storedTotalCps
    calculateBuildingCPS(upgrades, Building.factory.level.toInt, Building.factory.baseCps, factories, factorySynergyMap, factoryExponentialUpgrades)

  def calculateBanksCps(upgrades: Set[Upgrade]): Double =
    if extraBanks == 0 && extraGrandmas == 0 && extraPortals == 0 && extraFactories == 0 && extraAlchemyLabs == 0 && extraAntimatterCondensers == 0 then
      return Building.bank.storedTotalCps
    calculateBuildingCPS(upgrades, Building.bank.level.toInt, Building.bank.baseCps, banks, bankSynergyMap, bankExponentialUpgrades)

  def calculateTemplesCps(upgrades: Set[Upgrade]): Double =
    if extraTemples == 0 && extraGrandmas == 0 && extraFarms == 0 && extraPortals == 0 && extraAntimatterCondensers == 0 && extraPrisms == 0 then
      return Building.temple.storedTotalCps
    calculateBuildingCPS(upgrades, Building.temple.level.toInt, Building.temple.baseCps, temples, templeSynergyMap, templeExponentialUpgrades)

  def calculateWizardTowersCps(upgrades: Set[Upgrade]): Double =
    if extraWizardTowers == 0 && extraGrandmas == 0 && extraMines == 0 && extraAlchemyLabs == 0 && extraFarms == 0 && extraPrisms == 0 then
      return Building.wizardTower.storedTotalCps
    calculateBuildingCPS(upgrades, Building.wizardTower.level.toInt, Building.wizardTower.baseCps, wizardTowers, wizardTowerSynergyMap, wizardTowerExponentialUpgrades)

  def calculateShipmentsCps(upgrades: Set[Upgrade]): Double =
    if extraShipments == 0 && extraGrandmas == 0 && extraMines == 0 && extraAlchemyLabs == 0 && extraFarms == 0 && extraPrisms == 0 then
      return Building.shipment.storedTotalCps
    calculateBuildingCPS(upgrades, Building.shipment.level.toInt, Building.shipment.baseCps, shipments, shipmentSynergyMap, shipmentExponentialUpgrades)

  def calculateAlchemyLabsCps(upgrades: Set[Upgrade]): Double =
    if extraAlchemyLabs == 0 && extraGrandmas == 0 && extraWizardTowers == 0 && extraMines == 0 && extraBanks == 0 && extraAntimatterCondensers == 0 then
      return Building.alchemyLab.storedTotalCps
    calculateBuildingCPS(upgrades, Building.alchemyLab.level.toInt, Building.alchemyLab.baseCps, alchemyLabs, alchemyLabSynergyMap, alchemyLabExponentialUpgrades)

  def calculatePortalsCps(upgrades: Set[Upgrade]): Double =
    if extraPortals == 0 && extraGrandmas == 0 && extraBanks == 0 && extraTemples == 0 && extraFarms == 0 && extraPrisms == 0 then
      return Building.portal.storedTotalCps
    calculateBuildingCPS(upgrades, Building.portal.level.toInt, Building.portal.baseCps, portals, portalSynergyMap, portalExponentialUpgrades)

  def calculateTimeMachinesCps(upgrades: Set[Upgrade]): Double =
    if extraTimeMachines == 0 && extraGrandmas == 0 && extraFarms == 0 && extraFactories == 0 && extraShipments == 0 && extraPrisms == 0 then
      return Building.timeMachine.storedTotalCps
    calculateBuildingCPS(upgrades, Building.timeMachine.level.toInt, Building.timeMachine.baseCps, timeMachines, timeMachineSynergyMap, timeMachineExponentialUpgrades)

  def calculateAntimatterCondensersCps(upgrades: Set[Upgrade]): Double =
    if extraAntimatterCondensers == 0 && extraGrandmas == 0 && extraFactories == 0 && extraTemples == 0 && extraBanks == 0 && extraAlchemyLabs == 0 && extraChancemakers == 0 then
      return Building.antimatterCondenser.storedTotalCps
    calculateBuildingCPS(upgrades, Building.antimatterCondenser.level.toInt, Building.antimatterCondenser.baseCps, antimatterCondensers, antimatterCondenserSynergyMap, antimatterCondenserExponentialUpgrades)

  def calculatePrismsCps(upgrades: Set[Upgrade]): Double =
    if extraPrisms == 0 && extraGrandmas == 0 && extraPortals == 0 && extraTimeMachines == 0 && extraWizardTowers == 0 && extraTemples == 0 && extraAntimatterCondensers == 0 then
      return Building.prism.storedTotalCps
    calculateBuildingCPS(upgrades, Building.prism.level.toInt, Building.prism.baseCps, prisms, prismSynergyMap, prismExponentialUpgrades)

  def calculateChancemakersCps(upgrades: Set[Upgrade]): Double =
    if extraChancemakers == 0 && extraGrandmas == 0 && extraMines == 0 && extraAntimatterCondensers == 0 then
      return Building.chancemaker.storedTotalCps
    calculateBuildingCPS(upgrades, Building.chancemaker.level.toInt, Building.chancemaker.baseCps, chancemakers, chancemakerSynergyMap, chancemakerExponentialUpgrades)

  def calculateFractalEnginesCps(upgrades: Set[Upgrade]): Double =
    if extraFractalEngines == 0 && extraGrandmas == 0 && extraPrisms == 0 && extraCursors == 0 then
      return Building.fractalEngine.storedTotalCps
    calculateBuildingCPS(upgrades, Building.fractalEngine.level.toInt, Building.fractalEngine.baseCps, fractalEngines, fractalSynergyMap, fractalExponentialUpgrades)

  def apply(buildingRequirements: Set[BuildingRequirement] = Set.empty, upgrades: Set[Upgrade] = Set.empty): Double =
    val buildMult = godBuildingCpsMultiplier
    val requirementMap = buildingRequirements.map(req => req.gameBuyable -> req.missingAmount).toMap
    
    extraCursors = requirementMap.getOrElse(Building.cursor, 0)
    extraGrandmas = requirementMap.getOrElse(Building.grandma, 0)
    extraFarms = requirementMap.getOrElse(Building.farm, 0)
    extraMines = requirementMap.getOrElse(Building.mine, 0)
    extraFactories = requirementMap.getOrElse(Building.factory, 0)
    extraBanks = requirementMap.getOrElse(Building.bank, 0)
    extraTemples = requirementMap.getOrElse(Building.temple, 0)
    extraWizardTowers = requirementMap.getOrElse(Building.wizardTower, 0)
    extraShipments = requirementMap.getOrElse(Building.shipment, 0)
    extraAlchemyLabs = requirementMap.getOrElse(Building.alchemyLab, 0)
    extraPortals = requirementMap.getOrElse(Building.portal, 0)
    extraTimeMachines = requirementMap.getOrElse(Building.timeMachine, 0)
    extraAntimatterCondensers = requirementMap.getOrElse(Building.antimatterCondenser, 0)
    extraPrisms = requirementMap.getOrElse(Building.prism, 0)
    extraChancemakers = requirementMap.getOrElse(Building.chancemaker, 0)
    extraFractalEngines = requirementMap.getOrElse(Building.fractalEngine, 0)

    cursors = Building.cursor.amount.toInt + extraCursors
    grandmas = Building.grandma.amount.toInt + extraGrandmas
    farms = Building.farm.amount.toInt + extraFarms
    mines = Building.mine.amount.toInt + extraMines
    factories = Building.factory.amount.toInt + extraFactories
    banks = Building.bank.amount.toInt + extraBanks
    temples = Building.temple.amount.toInt + extraTemples
    wizardTowers = Building.wizardTower.amount.toInt + extraWizardTowers
    shipments = Building.shipment.amount.toInt + extraShipments
    alchemyLabs = Building.alchemyLab.amount.toInt + extraAlchemyLabs
    portals = Building.portal.amount.toInt + extraPortals
    timeMachines = Building.timeMachine.amount.toInt + extraTimeMachines
    antimatterCondensers = Building.antimatterCondenser.amount.toInt + extraAntimatterCondensers
    prisms = Building.prism.amount.toInt + extraPrisms
    chancemakers = Building.chancemaker.amount.toInt + extraChancemakers
    fractalEngines = Building.fractalEngine.amount.toInt + extraFractalEngines

    val nonCursors = grandmas + farms + mines + factories + banks + temples + wizardTowers + shipments + alchemyLabs + portals + timeMachines + antimatterCondensers + prisms + chancemakers + fractalEngines
    extraNonCursors = extraGrandmas + extraFarms + extraMines + extraFactories + extraBanks + extraTemples + extraWizardTowers + extraShipments + extraAlchemyLabs + extraPortals + extraTimeMachines + extraAntimatterCondensers + extraPrisms + extraChancemakers + extraFractalEngines

    val oldBuildingCps: Double = Building.all.sumBy(_.storedTotalCps)

    val cookieUpgradesMultiplier = upgrades.foldLeft(1D) {
      case (multiplier, upgrade: CookiePower) => multiplier * upgrade.powerMultiplier
      case (multiplier, _)                    => multiplier
    }

    val cursorsCps = calculateCursorsCps(upgrades, nonCursors)
    val grandmasCps = calculateGrandmasCps(upgrades)
    val farmsCps = calculateFarmsCps(upgrades)
    val minesCps = calculateMinesCps(upgrades)
    val factoriesCps = calculateFactoriesCps(upgrades)
    val banksCps = calculateBanksCps(upgrades)
    val templesCps = calculateTemplesCps(upgrades)
    val wizardCps = calculateWizardTowersCps(upgrades)
    val shipmentsCps = calculateShipmentsCps(upgrades)
    val alchemyCps = calculateAlchemyLabsCps(upgrades)
    val portalsCps = calculatePortalsCps(upgrades)
    val timeCps = calculateTimeMachinesCps(upgrades)
    val antimatterCps = calculateAntimatterCondensersCps(upgrades)
    val prismsCps = calculatePrismsCps(upgrades)
    val chancemakersCps = calculateChancemakersCps(upgrades)
    val fractalCps = calculateFractalEnginesCps(upgrades)

    val increase = cursorsCps + grandmasCps + farmsCps + minesCps + factoriesCps + banksCps + templesCps + wizardCps + shipmentsCps + alchemyCps + portalsCps + timeCps + antimatterCps + prismsCps + chancemakersCps + fractalCps

    ((increase * godBuildingCpsMultiplier * cookieUpgradesMultiplier - oldBuildingCps) * Game.globalCpsMult).round(6)
}
