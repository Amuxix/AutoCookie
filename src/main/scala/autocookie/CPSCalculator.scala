package autocookie

import autocookie.Helpers.*
import autocookie.buyable.BuildingRequirements.BuildingRequirements
import autocookie.buyable.traits.CookiePower
import autocookie.buyable.upgrade.{CookieUpgrade, Upgrade}
import autocookie.buyable.{Building, BuildingRequirement, GameBuildings}
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
  var javascriptConsoles = 0
  var idleverses = 0

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
  var extraJavascriptConsoles = 0
  var extraIdleverses = 0

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
    "Binary grandmas",
    "Alternate grandmas",
    "Bingo center/Research facility",
    "Bingo center/Research facility", //This is duplicated on purpose as bingo center increases grandma cps by 4x
    "Ritual rolling pins",
  ).map(Upgrade.getByName)
  lazy val grandmaBaseIncreases = Map(
    "One mind" -> (() => grandmas * 0.02),
    "Communal brainsweep" -> (() => grandmas * 0.02),
    "Elder Pact" -> (() => portals * 0.05),
  ).map(toUpgrade)
  lazy val grandmaSynergyMap: Map[Upgrade, () => Double] = Map(
    "Script grannies" -> (() => javascriptConsoles * .05),
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
    "Perforated mille-feuille cosmos" -> (() => idleverses * .05),
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
    "Tombola computing" -> (() => javascriptConsoles * .05),
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
    "Infraverses and superverses" -> (() => fractalEngines * .05),
  ).map(toUpgrade)

  lazy val javascriptConsoleExponentialUpgrades = List(
    "The JavaScript console for dummies",
    "64bit arrays",
    "Stack overflow",
    "Enterprise compiler",
    "Syntactic sugar",
    "A nice cup of coffee",
    "Just-in-time baking",
    "cookies++",
    "Software updates",
    "Game.Loop",
    "eval()",
    "Your biggest fans",
    "Hacker shades",
  ).map(Upgrade.getByName)
  lazy val javascriptConsoleSynergyMap: Map[Upgrade, () => Double] = Map(
    "Binary grandmas" -> (() => grandmas / 15D * .01),
    "Script grannies" -> (() => grandmas * .001),
    "Tombola computing" -> (() => chancemakers * .001),
  ).map(toUpgrade)

  lazy val idleverseExponentialUpgrades = List(
    "Manifest destiny",
    "The multiverse in a nutshell",
    "All-conversion",
    "Multiverse agents",
    "Escape plan",
    "Game design",
    "Sandbox universes",
    "Multiverse wars",
    "Mobile ports",
    "Encapsulated realities",
    "Extrinsic clicking",
    "Universal idling",
    "Break the fifth wall",
  ).map(Upgrade.getByName)
  lazy val idleverseSynergyMap: Map[Upgrade, () => Double] = Map(
    "Alternate grandmas" -> (() => grandmas / 16D * .01),
    "Perforated mille-feuille cosmos" -> (() => portals * .001),
    "Infraverses and superverses" -> (() => fractalEngines * .001),
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
    if upgrades.isEmpty && totalExtra == 0 then
      return GameBuildings.cursor.storedTotalCps
    val fingersAdd =
      if hasOrIsInChoices(Upgrade.getByName("Thousand fingers"), upgrades) then
        nonCursors * fingerAdd.foldLeft(0.1) {
          case (totalAdd, (name, multiplier)) if hasOrIsInChoices(name, upgrades) => totalAdd * multiplier
          case (totalAdd, _)                                                      => totalAdd
        }
      else
        0

    //0.1 is the cursor base CPS
    calculateBuildingCPS(upgrades, GameBuildings.cursor.level.toInt, 0.1, cursors, cursorSynergyMap, cursorExponentialUpgrades, fingersAdd)

  def calculateGrandmasCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraGrandmas == 0  && extraFractalEngines == 0 then
      return GameBuildings.grandma.storedTotalCps
    val baseCps = grandmaBaseIncreases.foldLeft(GameBuildings.grandma.baseCps) {
      case (baseIncrease, (upgrade, increaseF)) if hasOrIsInChoices(upgrade, upgrades) => baseIncrease + increaseF()
      case (baseIncrease, _)                                                           => baseIncrease
    }
    calculateBuildingCPS(upgrades, GameBuildings.grandma.level.toInt, baseCps, grandmas, grandmaSynergyMap, grandmaExponentialUpgrades)

  def calculateFarmsCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraFarms == 0 && extraGrandmas == 0 && extraTimeMachines == 0 && extraTemples == 0 && extraWizardTowers == 0 && extraPortals == 0 then
      return GameBuildings.farm.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.farm.level.toInt, GameBuildings.farm.baseCps, farms, farmSynergyMap, farmExponentialUpgrades)

  def calculateMinesCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraMines == 0 && extraGrandmas == 0 && extraWizardTowers == 0 && extraShipments == 0 && extraAlchemyLabs == 0 && extraChancemakers == 0 then
      return GameBuildings.mine.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.mine.level.toInt, GameBuildings.mine.baseCps, mines, mineSynergyMap, mineExponentialUpgrades)

  def calculateFactoriesCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraFactories == 0 && extraGrandmas == 0 && extraAntimatterCondensers == 0 && extraTimeMachines == 0 && extraBanks == 0 && extraShipments == 0 then
      return GameBuildings.factory.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.factory.level.toInt, GameBuildings.factory.baseCps, factories, factorySynergyMap, factoryExponentialUpgrades)

  def calculateBanksCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraBanks == 0 && extraGrandmas == 0 && extraPortals == 0 && extraFactories == 0 && extraAlchemyLabs == 0 && extraAntimatterCondensers == 0 then
      return GameBuildings.bank.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.bank.level.toInt, GameBuildings.bank.baseCps, banks, bankSynergyMap, bankExponentialUpgrades)

  def calculateTemplesCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraTemples == 0 && extraGrandmas == 0 && extraFarms == 0 && extraPortals == 0 && extraAntimatterCondensers == 0 && extraPrisms == 0 then
      return GameBuildings.temple.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.temple.level.toInt, GameBuildings.temple.baseCps, temples, templeSynergyMap, templeExponentialUpgrades)

  def calculateWizardTowersCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraWizardTowers == 0 && extraGrandmas == 0 && extraMines == 0 && extraAlchemyLabs == 0 && extraFarms == 0 && extraPrisms == 0 then
      return GameBuildings.wizardTower.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.wizardTower.level.toInt, GameBuildings.wizardTower.baseCps, wizardTowers, wizardTowerSynergyMap, wizardTowerExponentialUpgrades)

  def calculateShipmentsCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraShipments == 0 && extraGrandmas == 0 && extraMines == 0 && extraAlchemyLabs == 0 && extraFarms == 0 && extraPrisms == 0 then
      return GameBuildings.shipment.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.shipment.level.toInt, GameBuildings.shipment.baseCps, shipments, shipmentSynergyMap, shipmentExponentialUpgrades)

  def calculateAlchemyLabsCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraAlchemyLabs == 0 && extraGrandmas == 0 && extraWizardTowers == 0 && extraMines == 0 && extraBanks == 0 && extraAntimatterCondensers == 0 then
      return GameBuildings.alchemyLab.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.alchemyLab.level.toInt, GameBuildings.alchemyLab.baseCps, alchemyLabs, alchemyLabSynergyMap, alchemyLabExponentialUpgrades)

  def calculatePortalsCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraPortals == 0 && extraGrandmas == 0 && extraBanks == 0 && extraTemples == 0 && extraFarms == 0 && extraPrisms == 0 then
      return GameBuildings.portal.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.portal.level.toInt, GameBuildings.portal.baseCps, portals, portalSynergyMap, portalExponentialUpgrades)

  def calculateTimeMachinesCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraTimeMachines == 0 && extraGrandmas == 0 && extraFarms == 0 && extraFactories == 0 && extraShipments == 0 && extraPrisms == 0 then
      return GameBuildings.timeMachine.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.timeMachine.level.toInt, GameBuildings.timeMachine.baseCps, timeMachines, timeMachineSynergyMap, timeMachineExponentialUpgrades)

  def calculateAntimatterCondensersCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraAntimatterCondensers == 0 && extraGrandmas == 0 && extraFactories == 0 && extraTemples == 0 && extraBanks == 0 && extraAlchemyLabs == 0 && extraChancemakers == 0 then
      return GameBuildings.antimatterCondenser.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.antimatterCondenser.level.toInt, GameBuildings.antimatterCondenser.baseCps, antimatterCondensers, antimatterCondenserSynergyMap, antimatterCondenserExponentialUpgrades)

  def calculatePrismsCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraPrisms == 0 && extraGrandmas == 0 && extraPortals == 0 && extraTimeMachines == 0 && extraWizardTowers == 0 && extraTemples == 0 && extraAntimatterCondensers == 0 then
      return GameBuildings.prism.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.prism.level.toInt, GameBuildings.prism.baseCps, prisms, prismSynergyMap, prismExponentialUpgrades)

  def calculateChancemakersCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraChancemakers == 0 && extraGrandmas == 0 && extraMines == 0 && extraAntimatterCondensers == 0 then
      return GameBuildings.chancemaker.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.chancemaker.level.toInt, GameBuildings.chancemaker.baseCps, chancemakers, chancemakerSynergyMap, chancemakerExponentialUpgrades)

  def calculateFractalEnginesCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraFractalEngines == 0 && extraGrandmas == 0 && extraPrisms == 0 && extraCursors == 0 then
      return GameBuildings.fractalEngine.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.fractalEngine.level.toInt, GameBuildings.fractalEngine.baseCps, fractalEngines, fractalSynergyMap, fractalExponentialUpgrades)

  def calculateJavascriptConsolesCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && extraJavascriptConsoles == 0 && extraGrandmas == 0 && extraChancemakers == 0 then
      return GameBuildings.javascriptConsole.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.javascriptConsole.level.toInt, GameBuildings.javascriptConsole.baseCps, javascriptConsoles, javascriptConsoleSynergyMap, javascriptConsoleExponentialUpgrades)

  def calculateIdleversesCps(upgrades: Set[Upgrade]): Double =
    if upgrades.isEmpty && idleverses == 0 && extraGrandmas == 0 && extraPortals == 0 && extraFractalEngines == 0 then
      return GameBuildings.idleverse.storedTotalCps
    calculateBuildingCPS(upgrades, GameBuildings.idleverse.level.toInt, GameBuildings.idleverse.baseCps, idleverses, idleverseSynergyMap, idleverseExponentialUpgrades)

  def apply(buildingRequirements: BuildingRequirements = BuildingRequirements.empty, upgrades: Set[Upgrade] = Set.empty, debug: Boolean = false): Double =
    val buildMult = godBuildingCpsMultiplier

    extraCursors = buildingRequirements.getMissingAmount(Building.cursor)
    extraGrandmas = buildingRequirements.getMissingAmount(Building.grandma)
    extraFarms = buildingRequirements.getMissingAmount(Building.farm)
    extraMines = buildingRequirements.getMissingAmount(Building.mine)
    extraFactories = buildingRequirements.getMissingAmount(Building.factory)
    extraBanks = buildingRequirements.getMissingAmount(Building.bank)
    extraTemples = buildingRequirements.getMissingAmount(Building.temple)
    extraWizardTowers = buildingRequirements.getMissingAmount(Building.wizardTower)
    extraShipments = buildingRequirements.getMissingAmount(Building.shipment)
    extraAlchemyLabs = buildingRequirements.getMissingAmount(Building.alchemyLab)
    extraPortals = buildingRequirements.getMissingAmount(Building.portal)
    extraTimeMachines = buildingRequirements.getMissingAmount(Building.timeMachine)
    extraAntimatterCondensers = buildingRequirements.getMissingAmount(Building.antimatterCondenser)
    extraPrisms = buildingRequirements.getMissingAmount(Building.prism)
    extraChancemakers = buildingRequirements.getMissingAmount(Building.chancemaker)
    extraFractalEngines = buildingRequirements.getMissingAmount(Building.fractalEngine)
    extraJavascriptConsoles = buildingRequirements.getMissingAmount(Building.javascriptConsole)
    extraIdleverses = buildingRequirements.getMissingAmount(Building.idleverse)

    cursors = GameBuildings.cursor.amount.toInt + extraCursors
    grandmas = GameBuildings.grandma.amount.toInt + extraGrandmas
    farms = GameBuildings.farm.amount.toInt + extraFarms
    mines = GameBuildings.mine.amount.toInt + extraMines
    factories = GameBuildings.factory.amount.toInt + extraFactories
    banks = GameBuildings.bank.amount.toInt + extraBanks
    temples = GameBuildings.temple.amount.toInt + extraTemples
    wizardTowers = GameBuildings.wizardTower.amount.toInt + extraWizardTowers
    shipments = GameBuildings.shipment.amount.toInt + extraShipments
    alchemyLabs = GameBuildings.alchemyLab.amount.toInt + extraAlchemyLabs
    portals = GameBuildings.portal.amount.toInt + extraPortals
    timeMachines = GameBuildings.timeMachine.amount.toInt + extraTimeMachines
    antimatterCondensers = GameBuildings.antimatterCondenser.amount.toInt + extraAntimatterCondensers
    prisms = GameBuildings.prism.amount.toInt + extraPrisms
    chancemakers = GameBuildings.chancemaker.amount.toInt + extraChancemakers
    fractalEngines = GameBuildings.fractalEngine.amount.toInt + extraFractalEngines
    javascriptConsoles = GameBuildings.javascriptConsole.amount.toInt + extraJavascriptConsoles
    idleverses = GameBuildings.idleverse.amount.toInt + extraIdleverses

    val nonCursors = grandmas + farms + mines + factories + banks + temples + wizardTowers + shipments + alchemyLabs + portals + timeMachines + antimatterCondensers + prisms + chancemakers + fractalEngines + javascriptConsoles + idleverses
    extraNonCursors = extraGrandmas + extraFarms + extraMines + extraFactories + extraBanks + extraTemples + extraWizardTowers + extraShipments + extraAlchemyLabs + extraPortals + extraTimeMachines + extraAntimatterCondensers + extraPrisms + extraChancemakers + extraFractalEngines + extraJavascriptConsoles + extraIdleverses

    val oldBuildingCps: Double = GameBuildings.all.sumBy(_.storedTotalCps)

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
    val javascriptConsoleCps = calculateJavascriptConsolesCps(upgrades)
    val idleverseCps = calculateIdleversesCps(upgrades)

    val increase = cursorsCps + grandmasCps + farmsCps + minesCps + factoriesCps + banksCps + templesCps + wizardCps + shipmentsCps + alchemyCps + portalsCps + timeCps + antimatterCps + prismsCps + chancemakersCps + fractalCps + javascriptConsoleCps + idleverseCps

    val cpsIncrease = ((increase * godBuildingCpsMultiplier * cookieUpgradesMultiplier - oldBuildingCps) * Game.globalCpsMult).round(4) max 0D

    if debug || cpsIncrease < 0 then
      Logger.debug(s"Cursors ${calculateCursorsCps(upgrades, nonCursors) - GameBuildings.cursor.storedTotalCps}")
      Logger.debug(s"Grandmas ${calculateGrandmasCps(upgrades) - GameBuildings.grandma.storedTotalCps}")
      Logger.debug(s"Farms ${calculateFarmsCps(upgrades) - GameBuildings.farm.storedTotalCps}")
      Logger.debug(s"Mines ${calculateMinesCps(upgrades) - GameBuildings.mine.storedTotalCps}")
      Logger.debug(s"Factories ${calculateFactoriesCps(upgrades) - GameBuildings.factory.storedTotalCps}")
      Logger.debug(s"Banks ${calculateBanksCps(upgrades) - GameBuildings.bank.storedTotalCps}")
      Logger.debug(s"Temples ${calculateTemplesCps(upgrades) - GameBuildings.temple.storedTotalCps}")
      Logger.debug(s"WizardTowers ${calculateWizardTowersCps(upgrades) - GameBuildings.wizardTower.storedTotalCps}")
      Logger.debug(s"Shipments ${calculateShipmentsCps(upgrades) - GameBuildings.shipment.storedTotalCps}")
      Logger.debug(s"AlchemyLabs ${calculateAlchemyLabsCps(upgrades) - GameBuildings.alchemyLab.storedTotalCps}")
      Logger.debug(s"Portals ${calculatePortalsCps(upgrades) - GameBuildings.portal.storedTotalCps}")
      Logger.debug(s"TimeMachines ${calculateTimeMachinesCps(upgrades) - GameBuildings.timeMachine.storedTotalCps}")
      Logger.debug(s"AntimatterCondensers ${calculateAntimatterCondensersCps(upgrades) - GameBuildings.antimatterCondenser.storedTotalCps}")
      Logger.debug(s"Prisms ${calculatePrismsCps(upgrades) - GameBuildings.prism.storedTotalCps}")
      Logger.debug(s"Chancemakers ${calculateChancemakersCps(upgrades) - GameBuildings.chancemaker.storedTotalCps}")
      Logger.debug(s"FractalEngines ${calculateFractalEnginesCps(upgrades) - GameBuildings.fractalEngine.storedTotalCps}")
      Logger.debug(s"JavascriptConsoles ${calculateJavascriptConsolesCps(upgrades) - GameBuildings.javascriptConsole.storedTotalCps}")
      Logger.debug(s"Idleverses ${calculateIdleversesCps(upgrades) - GameBuildings.idleverse.storedTotalCps}")
      Logger.debug(s"godBuildingCpsMultiplier $godBuildingCpsMultiplier")
      Logger.debug(s"cookieUpgradesMultiplier $cookieUpgradesMultiplier")
      Logger.debug(s"diff ${increase * godBuildingCpsMultiplier * cookieUpgradesMultiplier - oldBuildingCps}")
      Logger.debug(s"not rounded ${(increase * godBuildingCpsMultiplier * cookieUpgradesMultiplier - oldBuildingCps) * Game.globalCpsMult}")

    cpsIncrease
}
