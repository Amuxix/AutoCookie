const MOD_ID = "Auto Cookie"

const AUTO_COOKIE_VERSION = 6

const CLICKS_PER_SEC = 3;
const NOTE_UPDATE_FREQUENCY = 500;

const STOCK_MARKET_STABILITY_THRESHOLD = 0.05
const STOCK_MARKET_STABILITY_MIN_PRICES = 5
const STOCK_MARKET_STABILITY_MAX_PRICES = 10

function unlockRequirements(): void {
  for (let i in Game.UnlockAt) {
    let unlock = Game.UnlockAt[i];
    if (Game.cookiesEarned >= unlock.cookies) {
      let pass = 1;
      if (unlock.require && !Game.Has(unlock.require) && !Game.HasAchiev(unlock.require)) pass = 0;
      if (unlock.season && Game.season !== unlock.season) pass = 0;
      if (pass) {
        Game.Unlock(unlock.name);
        Game.Win(unlock.name);
      }
    }
  }
}

function formatNumber(number: number, digits: number = 2): string {
  const string = number.toString();
  return "0".repeat(digits - string.length) + string;
}

/**
 * Creates a string with the local time
 * @return {string} Text with local time
 */
function getTime(): string {
  const currentTime = new Date();
  const hours = formatNumber(currentTime.getHours());
  const minutes = formatNumber(currentTime.getMinutes());
  const seconds = formatNumber(currentTime.getSeconds());
  const millis = formatNumber(currentTime.getMilliseconds(), 4);
  return `${hours}:${minutes}:${seconds}:${millis}`;
}

function consoleMessage(string: string): string {
  const time = getTime();
  return "[AutoCookie " + time + "] " + string;
}

/**
 * Logs text to console with the local time
 * @param {string} string Text to log
 **/
function log(string: string): void {
  console.log(consoleMessage(string))
}

function debug(string: string): void {
  console.debug(consoleMessage(string))
}

function error(string: string): void {
  console.error(consoleMessage(string))
}

function timeString(seconds: number): string {
  //Writes a string saying time
  let remainingSeconds, showString = "";
  function add(string) {
    if (showString === "") {
      return string;
    } else {
      return showString + " " + string;
    }
  }
  remainingSeconds = seconds;
  const years = Math.floor(remainingSeconds / (365 * 24 * 60 * 60));
  if (years > 0) {
    let yearString = 'years';
    if (years === 1) yearString = 'year';
    showString = add(years + yearString);
    remainingSeconds -= years * 365 * 24 * 60 * 60;
  }

  const months = Math.floor(remainingSeconds / (30 * 24 * 60 * 60));
  if (months > 0) {
    let monthString = 'months';
    if (months === 1) monthString = 'month';
    showString = add(months + monthString);
    remainingSeconds -= months * 30 * 24 * 60 * 60;
  }

  const weeks = Math.floor(remainingSeconds / (7 * 24 * 60 * 60));
  if (weeks > 0) {
    showString = add(weeks + "w");
    remainingSeconds -= weeks * 7 * 24 * 60 * 60;
  }

  const days = Math.floor(remainingSeconds / (24 * 60 * 60));
  if (days > 0) {
    showString = add(days + "d");
    remainingSeconds -= days * 24 * 60 * 60;
  }

  const hours = Math.floor(remainingSeconds / (60 * 60));
  if (hours > 0) {
    showString = add(hours + "h");
    remainingSeconds -= hours * 60 * 60;
  }

  const minutes = Math.floor(remainingSeconds / 60);
  if (minutes > 0) {
    showString = add(minutes + "m");
    remainingSeconds -= minutes * 60;
  }

  if (remainingSeconds > 1 || (minutes + hours + days === 0))
    showString = add(Math.floor(remainingSeconds) + "s");
  return showString;
}

function convertNumeral(number: number): string {
  const last_digit = number.toString()[number.toString().length - 1];
  if (number === 0) return "";
  let string = "th";
  if (last_digit === "1") string = "st";
  if (last_digit === "2") string = "nd";
  if (last_digit === "3") string = "rd";
  if (number > 10) {
    const last_two_digits = parseInt(number.toString()[number.toString().length - 2] + last_digit, 10);
    if (last_two_digits > 10 && last_two_digits < 14) string = "th";
  }
  return number + string;
}

function round(number: number, digits: number): number {
  return Math.round(number * Math.pow(10, digits)) / Math.pow(10, digits);
}

/**
 * @returns {number} Amount of non cursor buildings.
 */
function getAmountOfNonCursors(): number {
  return Game.ObjectsById.reduce((acc, b) => acc + b.amount, 0) - Game.Objects.Cursor.amount;
}

function hasOrIsChoice(upgradeName, choice): boolean {
  const upgrade = Upgrade.getByName(upgradeName);
  return upgrade.owned || upgrade.name === choice;
}

function getGodLevel(godName: string): number {
  if (Game.hasGod === undefined) {
    return 0;
  } else {
    return Game.hasGod(godName);
  }
}

function getKittenMultiplier(milk: number, choice: string = ""): number {
  let multiplier = 1;
  if (hasOrIsChoice("Kitten helpers", choice)) multiplier *= 1 + milk * 0.1;
  if (hasOrIsChoice("Kitten workers", choice)) multiplier *= 1 + milk * 0.125;
  if (hasOrIsChoice("Kitten engineers", choice)) multiplier *= 1 + milk * 0.15;
  if (hasOrIsChoice("Kitten overseers", choice)) multiplier *= 1 + milk * 0.175;
  if (hasOrIsChoice("Kitten managers", choice)) multiplier *= 1 + milk * 0.2;
  if (hasOrIsChoice("Kitten accountants", choice)) multiplier *= 1 + milk * 0.2;
  if (hasOrIsChoice("Kitten specialists", choice)) multiplier *= 1 + milk * 0.2;
  if (hasOrIsChoice("Kitten experts", choice)) multiplier *= 1 + milk * 0.2;
  if (hasOrIsChoice("Kitten consultants", choice)) multiplier *= 1 + milk * 0.2;
  if (hasOrIsChoice("Kitten assistants to the regional manager", choice)) multiplier *= 1 + milk * 0.175;
  if (hasOrIsChoice("Kitten marketeers", choice)) multiplier *= 1 + milk * 0.15;
  if (hasOrIsChoice("Kitten analysts", choice)) multiplier *= 1 + milk * 0.125;
  if (hasOrIsChoice("Kitten angels", choice)) multiplier *= 1 + milk * 0.1;
  const godLvl = getGodLevel('mother');
  if (godLvl === 1) multiplier *= 1.1;
  else if (godLvl === 2) multiplier *= 1.05;
  else if (godLvl === 3) multiplier *= 1.03;
  return multiplier;
}

/**
 * Calculates the CPS increase from getting all building requirements and all given upgrades
 * @param {Array<BuildingRequirement>} buildingRequirements
 * @param {Array<Upgrade>} upgrades
 * @param {boolean} debug
 */
function calculateCpsIncrease(buildingRequirements: Array<BuildingRequirement> = [], upgrades: Array<Upgrade> = [], debug: boolean = false): number {
  const buildMult = (1 - Math.floor(getGodLevel('decadence') * 25) / 100) * (1 + Math.floor(getGodLevel('industry') * (10 / 3)) / 100) * (1 - getGodLevel('labor') * .01);

  /**
   * Checks if the game has the given upgrade of if this is one of the choices
   * @param {string} upgradeName
   * @param {Array<Upgrade>} upgrades
   * @return {boolean}
   */
  function hasOrIsInChoices(upgradeName: string, upgrades: Array<Upgrade>): boolean {
    const upgrade = Upgrade.getByName(upgradeName);
    return upgrade.owned || upgrades.includes(upgrade);
  }

  function calculateCursorsCps(upgrades: Array<Upgrade>, cursors: number, nonCursors: number, fractalEngines: number): number {
    let add = 0;
    if (hasOrIsInChoices("Thousand fingers", upgrades)) add += 0.1;
    if (hasOrIsInChoices("Million fingers", upgrades)) add *= 5;
    if (hasOrIsInChoices("Billion fingers", upgrades)) add *= 10;
    if (hasOrIsInChoices("Trillion fingers", upgrades)) add *= 20;
    if (hasOrIsInChoices("Quadrillion fingers", upgrades)) add *= 20;
    if (hasOrIsInChoices("Quintillion fingers", upgrades)) add *= 20;
    if (hasOrIsInChoices("Sextillion fingers", upgrades)) add *= 20;
    if (hasOrIsInChoices("Septillion fingers", upgrades)) add *= 20;
    if (hasOrIsInChoices("Octillion fingers", upgrades)) add *= 20;
    if (hasOrIsInChoices("Nonillion fingers", upgrades)) add *= 20;
    add *= nonCursors;
    let multiplier = 1 + Game.Objects.Cursor.level * 0.01;
    if (hasOrIsInChoices("Mice clicking mice", upgrades)) multiplier *= 1 + fractalEngines * .05;
    let exponent = 0;
    if (hasOrIsInChoices("Reinforced index finger", upgrades)) exponent++;
    if (hasOrIsInChoices("Carpal tunnel prevention cream", upgrades)) exponent++;
    if (hasOrIsInChoices("Ambidextrous", upgrades)) exponent++;

    //0.1 is the cursor base CPS
    return Game.ComputeCps(0.1, exponent, add) * cursors * multiplier * buildMult;
  }

  function calculateGrandmasCps(upgrades: Array<Upgrade>, grandmas: number, portals: number): number {
    let exponent = 0;
    if (hasOrIsInChoices("Forwards from grandma", upgrades)) exponent++;
    if (hasOrIsInChoices("Steel-plated rolling pins", upgrades)) exponent++;
    if (hasOrIsInChoices("Lubricated dentures", upgrades)) exponent++;
    if (hasOrIsInChoices("Prune juice", upgrades)) exponent++;
    if (hasOrIsInChoices("Double-thick glasses", upgrades)) exponent++;
    if (hasOrIsInChoices("Aging agents", upgrades)) exponent++;
    if (hasOrIsInChoices("Xtreme walkers", upgrades)) exponent++;
    if (hasOrIsInChoices("The Unbridling", upgrades)) exponent++;
    if (hasOrIsInChoices("Reverse dementia", upgrades)) exponent++;
    if (hasOrIsInChoices("Timeproof hair dyes", upgrades)) exponent++;
    if (hasOrIsInChoices("Good manners", upgrades)) exponent++;

    if (hasOrIsInChoices("Farmer grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Miner grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Worker grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Banker grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Priestess grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Witch grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Cosmic grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Transmuted grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Altered grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Grandmas\' grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Antigrandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Rainbow grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Lucky grandmas", upgrades)) exponent++;
    if (hasOrIsInChoices("Metagrandmas", upgrades)) exponent++;

    if (hasOrIsInChoices("Bingo center/Research facility", upgrades)) exponent += 2;
    if (hasOrIsInChoices("Ritual rolling pins", upgrades)) exponent++;
    let baseIncrease = 0;
    if (hasOrIsInChoices("One mind", upgrades)) baseIncrease += grandmas * 0.02;
    if (hasOrIsInChoices("Communal brainsweep", upgrades)) baseIncrease += grandmas * 0.02;
    if (hasOrIsInChoices("Elder Pact", upgrades)) baseIncrease += portals * 0.05;
    const baseCps = Game.Objects.Grandma.baseCps + baseIncrease;
    const multiplier = 1 + Game.Objects.Grandma.level * 0.01;
    return Game.ComputeCps(baseCps, exponent) * grandmas * multiplier * buildMult;
  }

  function calculateFarmsCps(upgrades: Array<Upgrade>, farms: number, grandmas: number, timeMachines: number, temples: number, wizardTowers: number, portals: number): number {
    let multiplier = 1 + Game.Objects.Farm.level * 0.01;
    if (hasOrIsInChoices("Farmer grandmas", upgrades)) {
      multiplier *= 1 + grandmas * .01;
    }
    if (hasOrIsInChoices("Future almanacs", upgrades)) {
      multiplier *= 1 + timeMachines * .05;
    }
    if (hasOrIsInChoices("Rain prayer", upgrades)) {
      multiplier *= 1 + temples * .05;
    }
    if (hasOrIsInChoices("Magical botany", upgrades)) {
      multiplier *= 1 + wizardTowers * .05;
    }
    if (hasOrIsInChoices("Infernal crops", upgrades)) {
      multiplier *= 1 + portals * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Cheap hoes", upgrades)) exponent++;
    if(hasOrIsInChoices("Fertilizer", upgrades)) exponent++;
    if(hasOrIsInChoices("Cookie trees", upgrades)) exponent++;
    if(hasOrIsInChoices("Genetically-modified cookies", upgrades)) exponent++;
    if(hasOrIsInChoices("Gingerbread scarecrows", upgrades)) exponent++;
    if(hasOrIsInChoices("Pulsar sprinklers", upgrades)) exponent++;
    if(hasOrIsInChoices("Fudge fungus", upgrades)) exponent++;
    if(hasOrIsInChoices("Wheat triffids", upgrades)) exponent++;
    if(hasOrIsInChoices("Humane pesticides", upgrades)) exponent++;
    if(hasOrIsInChoices("Barnstars", upgrades)) exponent++;
    if(hasOrIsInChoices("Lindworms", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects.Farm.baseCps, exponent) * farms * multiplier * buildMult;
  }

  function calculateMinesCps(upgrades: Array<Upgrade>, mines: number, grandmas: number, wizardTowers: number, shipments: number, alchemyLabs: number, chancemakers: number): number {
    let multiplier = 1 + Game.Objects.Mine.level * 0.01;
    if (hasOrIsInChoices("Miner grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 2 * .01;
    }
    if (hasOrIsInChoices("Seismic magic", upgrades)) {
      multiplier *= 1 + wizardTowers * .05;
    }
    if (hasOrIsInChoices("Asteroid mining", upgrades)) {
      multiplier *= 1 + shipments * .05;
    }
    if (hasOrIsInChoices("Fossil fuels", upgrades)) {
      multiplier *= 1 + shipments * .05;
    }
    if (hasOrIsInChoices("Primordial ores", upgrades)) {
      multiplier *= 1 + alchemyLabs * .05;
    }
    if (hasOrIsInChoices("Gemmed talismans", upgrades)) {
      multiplier *= 1 + chancemakers * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Sugar gas", upgrades)) exponent++;
    if(hasOrIsInChoices("Megadrill", upgrades)) exponent++;
    if(hasOrIsInChoices("Ultradrill", upgrades)) exponent++;
    if(hasOrIsInChoices("Ultimadrill", upgrades)) exponent++;
    if(hasOrIsInChoices("H-bomb mining", upgrades)) exponent++;
    if(hasOrIsInChoices("Coreforge", upgrades)) exponent++;
    if(hasOrIsInChoices("Planetsplitters", upgrades)) exponent++;
    if(hasOrIsInChoices("Canola oil wells", upgrades)) exponent++;
    if(hasOrIsInChoices("Mole people", upgrades)) exponent++;
    if(hasOrIsInChoices("Mine canaries", upgrades)) exponent++;
    if(hasOrIsInChoices("Bore again", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects.Mine.baseCps, exponent) * mines * multiplier * buildMult;
  }

  function calculateFactoriesCps(upgrades: Array<Upgrade>, factories: number, grandmas: number, antimatterCondensers: number, timeMachines: number, banks: number, shipments: number): number {
    let multiplier = 1 + Game.Objects.Factory.level * 0.01;
    if (hasOrIsInChoices("Worker grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 3 * .01;
    }
    if (hasOrIsInChoices("Quantum electronics", upgrades)) {
      multiplier *= 1 + antimatterCondensers * .05;
    }
    if (hasOrIsInChoices("Temporal overclocking", upgrades)) {
      multiplier *= 1 + timeMachines * .05;
    }
    if (hasOrIsInChoices("Printing presses", upgrades)) {
      multiplier *= 1 + banks * .05;
    }
    if (hasOrIsInChoices("Shipyards", upgrades)) {
      multiplier *= 1 + shipments * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Sturdier conveyor belts", upgrades)) exponent++;
    if(hasOrIsInChoices("Child labor", upgrades)) exponent++;
    if(hasOrIsInChoices("Sweatshop", upgrades)) exponent++;
    if(hasOrIsInChoices("Radium reactors", upgrades)) exponent++;
    if(hasOrIsInChoices("Recombobulators", upgrades)) exponent++;
    if(hasOrIsInChoices("Deep-bake process", upgrades)) exponent++;
    if(hasOrIsInChoices("Cyborg workforce", upgrades)) exponent++;
    if(hasOrIsInChoices("78-hour days", upgrades)) exponent++;
    if(hasOrIsInChoices("Machine learning", upgrades)) exponent++;
    if(hasOrIsInChoices("Brownie point system", upgrades)) exponent++;
    if(hasOrIsInChoices("\"Volunteer\" interns", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects.Factory.baseCps, exponent) * factories * multiplier * buildMult;
  }

  function calculateBanksCps(upgrades: Array<Upgrade>, banks: number, grandmas: number, portals: number, factories: number, alchemyLabs: number, antimatterCondensers: number): number {
    let multiplier = 1 + Game.Objects.Bank.level * 0.01;
    if (hasOrIsInChoices("Banker grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 4 * .01;
    }
    if (hasOrIsInChoices("Contracts from beyond", upgrades)) {
      multiplier *= 1 + portals * .05;
    }
    if (hasOrIsInChoices("Printing presses", upgrades)) {
      multiplier *= 1 + factories * .001;
    }
    if (hasOrIsInChoices("Gold fund", upgrades)) {
      multiplier *= 1 + alchemyLabs * .05;
    }
    if (hasOrIsInChoices("Extra physics funding", upgrades)) {
      multiplier *= 1 + antimatterCondensers * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Taller tellers", upgrades)) exponent++;
    if(hasOrIsInChoices("Scissor-resistant credit cards", upgrades)) exponent++;
    if(hasOrIsInChoices("Acid-proof vaults", upgrades)) exponent++;
    if(hasOrIsInChoices("Chocolate coins", upgrades)) exponent++;
    if(hasOrIsInChoices("Exponential interest rates", upgrades)) exponent++;
    if(hasOrIsInChoices("Financial zen", upgrades)) exponent++;
    if(hasOrIsInChoices("Way of the wallet", upgrades)) exponent++;
    if(hasOrIsInChoices("The stuff rationale", upgrades)) exponent++;
    if(hasOrIsInChoices("Edible money", upgrades)) exponent++;
    if(hasOrIsInChoices("Grand supercycles", upgrades)) exponent++;
    if(hasOrIsInChoices("Rules of acquisition", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects.Bank.baseCps, exponent) * banks * multiplier * buildMult;
  }

  function calculateTemplesCps(upgrades: Array<Upgrade>, temples: number, grandmas: number, farms: number, portals: number, antimatterCondensers: number, prisms: number): number {
    let multiplier = 1 + Game.Objects.Temple.level * 0.01;
    if (hasOrIsInChoices("Priestess grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 5 * .01;
    }
    if (hasOrIsInChoices("Rain prayer", upgrades)) {
      multiplier *= 1 + farms * .001;
    }
    if (hasOrIsInChoices("Paganism", upgrades)) {
      multiplier *= 1 + portals * .05;
    }
    if (hasOrIsInChoices("God particle", upgrades)) {
      multiplier *= 1 + antimatterCondensers * .05;
    }
    if (hasOrIsInChoices("Mystical energies", upgrades)) {
      multiplier *= 1 + prisms * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Golden idols", upgrades)) exponent++;
    if(hasOrIsInChoices("Sacrifices", upgrades)) exponent++;
    if(hasOrIsInChoices("Delicious blessing", upgrades)) exponent++;
    if(hasOrIsInChoices("Sun festival", upgrades)) exponent++;
    if(hasOrIsInChoices("Enlarged pantheon", upgrades)) exponent++;
    if(hasOrIsInChoices("Great Baker in the sky", upgrades)) exponent++;
    if(hasOrIsInChoices("Creation myth", upgrades)) exponent++;
    if(hasOrIsInChoices("Theocracy", upgrades)) exponent++;
    if(hasOrIsInChoices("Sick rap prayers", upgrades)) exponent++;
    if(hasOrIsInChoices("Psalm-reading", upgrades)) exponent++;
    if(hasOrIsInChoices("War of the gods", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects.Temple.baseCps, exponent) * temples * multiplier * buildMult;
  }

  function calculateWizardTowersCps(upgrades: Array<Upgrade>, wizardTowers: number, grandmas: number, mines: number, alchemyLabs: number, farms: number, prisms: number): number {
    let multiplier = 1 + Game.Objects["Wizard tower"].level * 0.01;
    if (hasOrIsInChoices("Witch grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 6 * .01;
    }
    if (hasOrIsInChoices("Seismic magic", upgrades)) {
      multiplier *= 1 + mines * .001;
    }
    if (hasOrIsInChoices("Arcane knowledge", upgrades)) {
      multiplier *= 1 + alchemyLabs * .05;
    }
    if (hasOrIsInChoices("Magical botany", upgrades)) {
      multiplier *= 1 + farms * .001;
    }
    if (hasOrIsInChoices("Light magic", upgrades)) {
      multiplier *= 1 + prisms * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Pointier hats", upgrades)) exponent++;
    if(hasOrIsInChoices("Beardlier beards", upgrades)) exponent++;
    if(hasOrIsInChoices("Ancient grimoires", upgrades)) exponent++;
    if(hasOrIsInChoices("Kitchen curses", upgrades)) exponent++;
    if(hasOrIsInChoices("School of sorcery", upgrades)) exponent++;
    if(hasOrIsInChoices("Dark formulas", upgrades)) exponent++;
    if(hasOrIsInChoices("Cookiemancy", upgrades)) exponent++;
    if(hasOrIsInChoices("Rabbit trick", upgrades)) exponent++;
    if(hasOrIsInChoices("Deluxe tailored wands", upgrades)) exponent++;
    if(hasOrIsInChoices("Immobile spellcasting", upgrades)) exponent++;
    if(hasOrIsInChoices("Electricity", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects["Wizard tower"].baseCps, exponent) * wizardTowers * multiplier * buildMult;
  }

  function calculateShipmentsCps(upgrades: Array<Upgrade>, shipments: number, grandmas: number, mines: number, alchemyLabs: number, farms: number, prisms: number): number {
    let multiplier = 1 + Game.Objects.Shipment.level * 0.01;
    if (hasOrIsInChoices("Cosmic grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 7 * .01;
    }
    if (hasOrIsInChoices("Seismic magic", upgrades)) {
      multiplier *= 1 + mines * .001;
    }
    if (hasOrIsInChoices("Arcane knowledge", upgrades)) {
      multiplier *= 1 + alchemyLabs * .05;
    }
    if (hasOrIsInChoices("Magical botany", upgrades)) {
      multiplier *= 1 + farms * .001;
    }
    if (hasOrIsInChoices("Light magic", upgrades)) {
      multiplier *= 1 + prisms * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Vanilla nebulae", upgrades)) exponent++;
    if(hasOrIsInChoices("Wormholes", upgrades)) exponent++;
    if(hasOrIsInChoices("Frequent flyer", upgrades)) exponent++;
    if(hasOrIsInChoices("Warp drive", upgrades)) exponent++;
    if(hasOrIsInChoices("Chocolate monoliths", upgrades)) exponent++;
    if(hasOrIsInChoices("Generation ship", upgrades)) exponent++;
    if(hasOrIsInChoices("Dyson sphere", upgrades)) exponent++;
    if(hasOrIsInChoices("The final frontier", upgrades)) exponent++;
    if(hasOrIsInChoices("Autopilot", upgrades)) exponent++;
    if(hasOrIsInChoices("Restaurants at the end of the universe", upgrades)) exponent++;
    if(hasOrIsInChoices("Universal alphabet", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects.Shipment.baseCps, exponent) * shipments * multiplier * buildMult;
  }

  function calculateAlchemyLabsCps(upgrades: Array<Upgrade>, alchemyLabs: number, grandmas: number, wizardTowers: number, mines: number, banks: number, antimatterCondensers: number): number {
    let multiplier = 1 + Game.Objects["Alchemy lab"].level * 0.01;
    if (hasOrIsInChoices("Transmuted grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 8 * .01;
    }
    if (hasOrIsInChoices("Arcane knowledge", upgrades)) {
      multiplier *= 1 + wizardTowers * .001;
    }
    if (hasOrIsInChoices("Primordial ores", upgrades)) {
      multiplier *= 1 + mines * .001;
    }
    if (hasOrIsInChoices("Gold fund", upgrades)) {
      multiplier *= 1 + banks * .001;
    }
    if (hasOrIsInChoices("Chemical proficiency", upgrades)) {
      multiplier *= 1 + antimatterCondensers * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Antimony", upgrades)) exponent++;
    if(hasOrIsInChoices("Essence of dough", upgrades)) exponent++;
    if(hasOrIsInChoices("True chocolate", upgrades)) exponent++;
    if(hasOrIsInChoices("Ambrosia", upgrades)) exponent++;
    if(hasOrIsInChoices("Aqua crustulae", upgrades)) exponent++;
    if(hasOrIsInChoices("Origin crucible", upgrades)) exponent++;
    if(hasOrIsInChoices("Theory of atomic fluidity", upgrades)) exponent++;
    if(hasOrIsInChoices("Beige goo", upgrades)) exponent++;
    if(hasOrIsInChoices("The advent of chemistry", upgrades)) exponent++;
    if(hasOrIsInChoices("On second thought", upgrades)) exponent++;
    if(hasOrIsInChoices("Public betterment", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects["Alchemy lab"].baseCps, exponent) * alchemyLabs * multiplier * buildMult;
  }

  function calculatePortalsCps(upgrades: Array<Upgrade>, portals: number, grandmas: number, banks: number, temples: number, farms: number, prisms: number): number {
    let multiplier = 1 + Game.Objects.Portal.level * 0.01;
    if (hasOrIsInChoices("Altered grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 9 * .01;
    }
    if (hasOrIsInChoices("Contracts from beyond", upgrades)) {
      multiplier *= 1 + banks * .001;
    }
    if (hasOrIsInChoices("Paganism", upgrades)) {
      multiplier *= 1 + temples * .001;
    }
    if (hasOrIsInChoices("Infernal crops", upgrades)) {
      multiplier *= 1 + farms * .001;
    }
    if (hasOrIsInChoices("Abysmal glimmer", upgrades)) {
      multiplier *= 1 + prisms * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Ancient tablet", upgrades)) exponent++;
    if(hasOrIsInChoices("Insane oatling workers", upgrades)) exponent++;
    if(hasOrIsInChoices("Soul bond", upgrades)) exponent++;
    if(hasOrIsInChoices("Sanity dance", upgrades)) exponent++;
    if(hasOrIsInChoices("Brane transplant", upgrades)) exponent++;
    if(hasOrIsInChoices("Deity-sized portals", upgrades)) exponent++;
    if(hasOrIsInChoices("End of times back-up plan", upgrades)) exponent++;
    if(hasOrIsInChoices("Maddening chants", upgrades)) exponent++;
    if(hasOrIsInChoices("The real world", upgrades)) exponent++;
    if(hasOrIsInChoices("Dimensional garbage gulper", upgrades)) exponent++;
    if(hasOrIsInChoices("Embedded microportals", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects.Portal.baseCps, exponent) * portals * multiplier * buildMult;
  }

  function calculateTimeMachinesCps(upgrades: Array<Upgrade>, timeMachines: number, grandmas: number, farms: number, factories: number, shipments: number, prisms: number): number {
    let multiplier = 1 + Game.Objects["Time machine"].level * 0.01;
    if (hasOrIsInChoices("Grandmas\' grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 10 * .01;
    }
    if (hasOrIsInChoices("Future almanacs", upgrades)) {
      multiplier *= 1 + farms * .001;
    }
    if (hasOrIsInChoices("Temporal overclocking", upgrades)) {
      multiplier *= 1 + factories * .001;
    }
    if (hasOrIsInChoices("Relativistic parsec-skipping", upgrades)) {
      multiplier *= 1 + shipments * .001;
    }
    if (hasOrIsInChoices("Primeval glow", upgrades)) {
      multiplier *= 1 + prisms * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Flux capacitors", upgrades)) exponent++;
    if(hasOrIsInChoices("Time paradox resolver", upgrades)) exponent++;
    if(hasOrIsInChoices("Quantum conundrum", upgrades)) exponent++;
    if(hasOrIsInChoices("Causality enforcer", upgrades)) exponent++;
    if(hasOrIsInChoices("Yestermorrow comparators", upgrades)) exponent++;
    if(hasOrIsInChoices("Far future enactment", upgrades)) exponent++;
    if(hasOrIsInChoices("Great loop hypothesis", upgrades)) exponent++;
    if(hasOrIsInChoices("Cookietopian moments of maybe", upgrades)) exponent++;
    if(hasOrIsInChoices("Second seconds", upgrades)) exponent++;
    if(hasOrIsInChoices("Additional clock hands", upgrades)) exponent++;
    if(hasOrIsInChoices("Nostalgia", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects["Time machine"].baseCps, exponent) * timeMachines * multiplier * buildMult;
  }

  function calculateAntimatterCondensersCps(upgrades: Array<Upgrade>, antimatterCondensers: number, grandmas: number, factories: number, temples: number, banks: number, alchemyLabs: number, chancemakers: number): number {
    let multiplier = 1 + Game.Objects["Antimatter condenser"].level * 0.01;
    if (hasOrIsInChoices("Antigrandmas", upgrades)) {
      multiplier *= 1 + grandmas / 11 * .01;
    }
    if (hasOrIsInChoices("Quantum electronics", upgrades)) {
      multiplier *= 1 + factories * .001;
    }
    if (hasOrIsInChoices("God particle", upgrades)) {
      multiplier *= 1 + temples * .001;
    }
    if (hasOrIsInChoices("Extra physics funding", upgrades)) {
      multiplier *= 1 + banks * .001;
    }
    if (hasOrIsInChoices("Chemical proficiency", upgrades)) {
      multiplier *= 1 + alchemyLabs * .001;
    }
    if (hasOrIsInChoices("Charm quarks", upgrades)) {
      multiplier *= 1 + chancemakers * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Sugar bosons", upgrades)) exponent++;
    if(hasOrIsInChoices("String theory", upgrades)) exponent++;
    if(hasOrIsInChoices("Large macaron collider", upgrades)) exponent++;
    if(hasOrIsInChoices("Big bang bake", upgrades)) exponent++;
    if(hasOrIsInChoices("Reverse cyclotrons", upgrades)) exponent++;
    if(hasOrIsInChoices("Nanocosmics", upgrades)) exponent++;
    if(hasOrIsInChoices("The Pulse", upgrades)) exponent++;
    if(hasOrIsInChoices("Some other super-tiny fundamental particle? Probably?", upgrades)) exponent++;
    if(hasOrIsInChoices("Quantum comb", upgrades)) exponent++;
    if(hasOrIsInChoices("Baking Nobel prize", upgrades)) exponent++;
    if(hasOrIsInChoices("The definite molecule", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects["Antimatter condenser"].baseCps, exponent) * antimatterCondensers * multiplier * buildMult;
  }

  function calculatePrismsCps(upgrades: Array<Upgrade>, prisms: number, grandmas: number, portals: number, timeMachines: number, wizardTowers: number, temples: number, antimatterCondensers: number): number {
    let multiplier =  1 + Game.Objects.Prism.level * 0.01;
    if (hasOrIsInChoices("Rainbow grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 12 * .01;
    }
    if (hasOrIsInChoices("Abysmal glimmer", upgrades)) {
      multiplier *= 1 + portals * .001;
    }
    if (hasOrIsInChoices("Primeval glow", upgrades)) {
      multiplier *= 1 + timeMachines * .001;
    }
    if (hasOrIsInChoices("Light magic", upgrades)) {
      multiplier *= 1 + wizardTowers * .001;
    }
    if (hasOrIsInChoices("Mystical energies", upgrades)) {
      multiplier *= 1 + temples * .001;
    }
    if (hasOrIsInChoices("Recursive mirrors", upgrades)) {
      multiplier *= 1 + antimatterCondensers * .05;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Gem polish", upgrades)) exponent++;
    if(hasOrIsInChoices("9th color", upgrades)) exponent++;
    if(hasOrIsInChoices("Chocolate light", upgrades)) exponent++;
    if(hasOrIsInChoices("Grainbow", upgrades)) exponent++;
    if(hasOrIsInChoices("Pure cosmic light", upgrades)) exponent++;
    if(hasOrIsInChoices("Glow-in-the-dark", upgrades)) exponent++;
    if(hasOrIsInChoices("Lux sanctorum", upgrades)) exponent++;
    if(hasOrIsInChoices("Reverse shadows", upgrades)) exponent++;
    if(hasOrIsInChoices("Crystal mirrors", upgrades)) exponent++;
    if(hasOrIsInChoices("Reverse theory of light", upgrades)) exponent++;
    if(hasOrIsInChoices("Light capture measures", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects.Prism.baseCps, exponent) * prisms * multiplier * buildMult;
  }

  function calculateChancemakersCps(upgrades: Array<Upgrade>, chancemakers: number, grandmas: number, mines: number, antimatterCondensers: number): number {
    let multiplier =  1 + Game.Objects.Chancemaker.level * 0.01;
    if (hasOrIsInChoices("Lucky grandmas", upgrades)) {
      multiplier *= 1 + grandmas / 13 * .01;
    }
    if (hasOrIsInChoices("Gemmed talismans", upgrades)) {
      multiplier *= 1 + mines * .001;
    }
    if (hasOrIsInChoices("Charm quarks", upgrades)) {
      multiplier *= 1 + antimatterCondensers * .001;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Your lucky cookie", upgrades)) exponent++;
    if(hasOrIsInChoices("\"All Bets Are Off\" magic coin", upgrades)) exponent++;
    if(hasOrIsInChoices("Winning lottery ticket", upgrades)) exponent++;
    if(hasOrIsInChoices("Four-leaf clover field", upgrades)) exponent++;
    if(hasOrIsInChoices("A recipe book about books", upgrades)) exponent++;
    if(hasOrIsInChoices("Leprechaun village", upgrades)) exponent++;
    if(hasOrIsInChoices("Improbability drive", upgrades)) exponent++;
    if(hasOrIsInChoices("Antisuperstistronics", upgrades)) exponent++;
    if(hasOrIsInChoices("Bunnypedes", upgrades)) exponent++;
    if(hasOrIsInChoices("Revised probabilistics", upgrades)) exponent++;
    if(hasOrIsInChoices("0-sided dice", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects.Chancemaker.baseCps, exponent) * chancemakers * multiplier * buildMult;
  }

  function calculateFractalEnginesCps(upgrades: Array<Upgrade>, fractalEngines: number, grandmas: number, prisms: number, cursors: number): number {
    let multiplier =  1 + Game.Objects["Fractal engine"].level * 0.01;
    if (hasOrIsInChoices("Metagrandmas", upgrades)) {
      multiplier *= 1 + grandmas / 14 * .01;
    }
    if (hasOrIsInChoices("Recursive mirrors", upgrades)) {
      multiplier *= 1 + prisms * .001;
    }
    if (hasOrIsInChoices("Mice clicking mice", upgrades)) {
      multiplier *= 1 + cursors * .001;
    }

    let exponent = 0;
    if(hasOrIsInChoices("Metabakeries", upgrades)) exponent++;
    if(hasOrIsInChoices("Mandelbrown sugar", upgrades)) exponent++;
    if(hasOrIsInChoices("Fractoids", upgrades)) exponent++;
    if(hasOrIsInChoices("Nested universe theory", upgrades)) exponent++;
    if(hasOrIsInChoices("Menger sponge cake", upgrades)) exponent++;
    if(hasOrIsInChoices("One particularly good-humored cow", upgrades)) exponent++;
    if(hasOrIsInChoices("Chocolate ouroboros", upgrades)) exponent++;
    if(hasOrIsInChoices("Nested", upgrades)) exponent++;
    if(hasOrIsInChoices("Space-filling fibers", upgrades)) exponent++;
    if(hasOrIsInChoices("Endless book of prose", upgrades)) exponent++;
    if(hasOrIsInChoices("The set of all sets", upgrades)) exponent++;

    return Game.ComputeCps(Game.Objects["Fractal engine"].baseCps, exponent) * fractalEngines * multiplier * buildMult;
  }

  const requirements = buildingRequirements.reduce((acc, requirement) => {
    acc[requirement.name] = requirement.amount;
    return acc;
  }, {});

  const cursors = requirements[Game.Objects.Cursor.name] || Game.Objects.Cursor.amount;
  const grandmas = requirements[Game.Objects.Grandma.name] || Game.Objects.Grandma.amount;
  const farms = requirements[Game.Objects.Farm.name] || Game.Objects.Farm.amount;
  const mines = requirements[Game.Objects.Mine.name] || Game.Objects.Mine.amount;
  const factories = requirements[Game.Objects.Factory.name] || Game.Objects.Factory.amount;
  const banks = requirements[Game.Objects.Bank.name] || Game.Objects.Bank.amount;
  const temples = requirements[Game.Objects.Temple.name] || Game.Objects.Temple.amount;
  const wizardTowers = requirements[Game.Objects["Wizard tower"].name] || Game.Objects["Wizard tower"].amount;
  const shipments = requirements[Game.Objects.Shipment.name] || Game.Objects.Shipment.amount;
  const alchemyLabs = requirements[Game.Objects["Alchemy lab"].name] || Game.Objects["Alchemy lab"].amount;
  const portals = requirements[Game.Objects.Portal.name] || Game.Objects.Portal.amount;
  const timeMachines = requirements[Game.Objects["Time machine"].name] || Game.Objects["Time machine"].amount;
  const antimatterCondensers = requirements[Game.Objects["Antimatter condenser"].name] || Game.Objects["Antimatter condenser"].amount;
  const prisms = requirements[Game.Objects.Prism.name] || Game.Objects.Prism.amount;
  const chancemakers = requirements[Game.Objects.Chancemaker.name] || Game.Objects.Chancemaker.amount;
  const fractalEngines = requirements[Game.Objects["Fractal engine"].name] || Game.Objects["Fractal engine"].amount;
  const nonCursors = grandmas + farms + mines + factories + banks + temples + wizardTowers + shipments + alchemyLabs + portals + timeMachines + antimatterCondensers + prisms + chancemakers + fractalEngines;

  const cursorsCps = calculateCursorsCps(upgrades, cursors, nonCursors, fractalEngines);
  const grandmasCps = calculateGrandmasCps(upgrades, grandmas, portals);
  const farmsCps = calculateFarmsCps(upgrades, farms, grandmas, timeMachines, temples, wizardTowers, portals);
  const minesCps = calculateMinesCps(upgrades, mines, grandmas, wizardTowers, shipments, alchemyLabs, chancemakers);
  const factoriesCps = calculateFactoriesCps(upgrades, factories, grandmas, antimatterCondensers, timeMachines, banks, shipments);
  const banksCps = calculateBanksCps(upgrades, banks, grandmas, portals, factories, alchemyLabs, antimatterCondensers);
  const templesCps = calculateTemplesCps(upgrades, temples, grandmas, farms, portals, antimatterCondensers, prisms);
  const wizardTowersCps = calculateWizardTowersCps(upgrades, wizardTowers, grandmas, mines, alchemyLabs, farms, prisms);
  const shipmentsCps = calculateShipmentsCps(upgrades, shipments, grandmas, mines, alchemyLabs, farms, prisms);
  const alchemyLabsCps = calculateAlchemyLabsCps(upgrades, alchemyLabs, grandmas, wizardTowers, mines, banks, antimatterCondensers);
  const portalsCps = calculatePortalsCps(upgrades, portals, grandmas, banks, temples, farms, prisms);
  const timeMachinesCps = calculateTimeMachinesCps(upgrades, timeMachines, grandmas, farms, factories, shipments, prisms);
  const antimatterCondensersCps = calculateAntimatterCondensersCps(upgrades, antimatterCondensers, grandmas, factories, temples, banks, alchemyLabs, chancemakers);
  const prismsCps = calculatePrismsCps(upgrades, prisms, grandmas, portals, timeMachines, wizardTowers, temples, antimatterCondensers);
  const chancemakersCps = calculateChancemakersCps(upgrades, chancemakers, grandmas, mines, antimatterCondensers);
  const fractalEnginesCps = calculateFractalEnginesCps(upgrades, fractalEngines, grandmas, prisms, cursors);

  const oldBuildingCps = Game.Objects.Cursor.storedTotalCps + Game.Objects.Grandma.storedTotalCps + Game.Objects.Farm.storedTotalCps + Game.Objects.Mine.storedTotalCps + Game.Objects.Factory.storedTotalCps
    + Game.Objects.Bank.storedTotalCps + Game.Objects.Temple.storedTotalCps + Game.Objects["Wizard tower"].storedTotalCps + Game.Objects.Shipment.storedTotalCps + Game.Objects["Alchemy lab"].storedTotalCps + Game.Objects.Portal.storedTotalCps
    + Game.Objects["Time machine"].storedTotalCps + Game.Objects["Antimatter condenser"].storedTotalCps + Game.Objects.Prism.storedTotalCps + Game.Objects.Chancemaker.storedTotalCps + Game.Objects["Fractal engine"].storedTotalCps;

  if (debug) {
    console.log(round(cursorsCps - Game.Objects.Cursor.storedTotalCps, 3));
    console.log(round(grandmasCps - Game.Objects.Grandma.storedTotalCps, 3));
    console.log(round(farmsCps - Game.Objects.Farm.storedTotalCps, 3));
    console.log(round(minesCps - Game.Objects.Mine.storedTotalCps, 3));
    console.log(round(factoriesCps - Game.Objects.Factory.storedTotalCps, 3));
    console.log(round(banksCps - Game.Objects.Bank.storedTotalCps, 3));
    console.log(round(templesCps - Game.Objects.Temple.storedTotalCps, 3));
    console.log(round(wizardTowersCps - Game.Objects["Wizard tower"].storedTotalCps, 3));
    console.log(round(shipmentsCps - Game.Objects.Shipment.storedTotalCps, 3));
    console.log(round(alchemyLabsCps - Game.Objects["Alchemy lab"].storedTotalCps, 3));
    console.log(round(portalsCps - Game.Objects.Portal.storedTotalCps, 3));
    console.log(round(timeMachinesCps - Game.Objects["Time machine"].storedTotalCps, 3));
    console.log(round(antimatterCondensersCps - Game.Objects["Antimatter condenser"].storedTotalCps, 3));
    console.log(round(prismsCps - Game.Objects.Prism.storedTotalCps, 3));
    console.log(round(chancemakersCps - Game.Objects.Chancemaker.storedTotalCps, 3));
    console.log(round(fractalEnginesCps - Game.Objects["Fractal engine"].storedTotalCps, 3));
  }

  const cookieUpgrades = upgrades.filter(upgrade => upgrade instanceof CookieUpgrade || upgrade instanceof ResearchCookieUpgrade);
  const cookieUpgradesMultiplier = cookieUpgrades.reduce((acc, cookie) => acc * (<CookieUpgrade | ResearchCookieUpgrade>cookie).powerMultiplier, 1);

  const buildingsCps = cursorsCps + grandmasCps + farmsCps + minesCps + factoriesCps + banksCps + templesCps
    + wizardTowersCps + shipmentsCps + alchemyLabsCps + portalsCps + timeMachinesCps
    + antimatterCondensersCps + prismsCps + chancemakersCps + fractalEnginesCps;

  //console.log("Calculating CPS Increase");

  return round((buildingsCps * cookieUpgradesMultiplier - oldBuildingCps) * Game.globalCpsMult, 4);
}

/**
 * Returns the best thing to buy
 * @param {Array<Buyable>} buyableList List of buyable objects
 * @return {Buyable} The best thing to buy
 */
function minByPayback<T extends GameObject>(buyableList: Array<Buyable<T>>): Buyable<T> {
  return buyableList.reduce((min, current) => (current.payback < min.payback ) ? current : min);
}

abstract class Buyable<T extends GameObject> {
  gameObject: T;
  name: string;
  price: number;
  cpsIncrease: number;
  payback: number;
  investment: Investment;
  buyMillis: number;
  originalBuyMillis: number = 0;
  needsUpdate: boolean = true;

  protected constructor(gameObject: T) {
    this.gameObject = gameObject;
    this.name = gameObject.name;
  }

  update(): void {
    if (this.needsUpdate) {
      const price = this.calculatePrice();
      const cpsIncrease = this.calculateCpsIncrease();
      if (price !== undefined && !isNaN(price) && cpsIncrease !== undefined && !isNaN(cpsIncrease)) {
        this.price = price;
        this.cpsIncrease = cpsIncrease;
        this.payback = this.calculatePayback()
        this.needsUpdate = false;
      }
    }
  }

  updateInvestmentAndBuyMillis(): void {
    this.investment = AUTO_COOKIE.stockMarket.createInvestment(Game.cookies - this.price)
    this.buyMillis = this.calculateBuyMillis()
    if (this.originalBuyMillis === 0) {
      this.originalBuyMillis = this.buyMillis
    }
    if (this.nextMilestone !== this) {
      this.nextMilestone.updateInvestmentAndBuyMillis()
    }
  }

  resetOriginalBuyMillis(): void {
    this.originalBuyMillis = 0
    if (this.nextMilestone !== this) {
      this.nextMilestone.resetOriginalBuyMillis()
    }
  }

  get cookiesNeeded(): number {
    return AUTO_COOKIE.reserve.reserveAmount + this.price - Game.cookies - this.investment.estimateReturns(this)
  }

  calculateBuyMillis(): number {
    const cookiesNeeded = this.cookiesNeeded
    if (cookiesNeeded <= 0) return Date.now()

    const cps = getCps()
    let buySeconds = cookiesNeeded / cps

    /*const maxWrinklers = Game.getWrinklersMax();
    const activeWrinklers = Game.wrinklers.reduce((acc, wrinkler) => acc + wrinkler.close, 0);
    if (maxWrinklers === activeWrinklers) {
      const bestWrinkler = getBestWrinkler();
      const suckMultiplier = calculateWrinklerSuckMultiplier();
      const sucked = bestWrinkler.sucked * suckMultiplier;

      const cookiesLostDuringWrinklerRespawn = calculateCookiesLostDuringWrinklerRespawn();
      if (sucked >= cookiesNeeded && sucked >= cookiesLostDuringWrinklerRespawn) {
        //We're gonna pop a wrinkler to buy
        debug("Popping wrinkler");
        buySeconds = 0;
      } else if (sucked >= cookiesLostDuringWrinklerRespawn) {
        //We can pop wrinkler now but we won't have enough cookies to buy
        debug("Popping wrinkler as soon as it covers buying cost");
        //How much do we lack considering we pop the wrinkler?
        const cookiesNeededAfterPopping = cookiesNeeded - sucked;
        //How many cookies are we producing per second counting with the wrinkler we're gonna pop
        const cpsWithWrinkler = Game.cookiesPs * ((1 - Game.cpsSucked) + (Game.cpsSucked * suckMultiplier));
        buySeconds = cookiesNeededAfterPopping / cpsWithWrinkler
      } else {
        //We can't pop wrinkler yet
        const timeToPopWrinkler = (cookiesLostDuringWrinklerRespawn - sucked) / suckMultiplier;
        if (timeToPopWrinkler <= buySeconds) {
          //We can pop a wrinkler before we buy
          if (cookiesLostDuringWrinklerRespawn >= cookiesNeeded) {
            //Pop wrinkler as soon as we can as it will allow us to buy
            debug("Popping wrinkler as soon as possible");
            buySeconds = timeToPopWrinkler
          } else {
            debug("Gonna take a while");
            const cookiesNeededAfterPopping = cookiesNeeded - cookiesLostDuringWrinklerRespawn;
            const cpsWithWrinkler = Game.cookiesPs * ((1 - Game.cpsSucked) + (Game.cpsSucked * suckMultiplier));
            buySeconds = cookiesNeededAfterPopping / cpsWithWrinkler
          }
        }
      }
    }*/

    const cpsBuffs = getBuffs().filter(buff => typeof buff.multCpS !== "undefined" && buff.multCpS !== 0)
    if (cpsBuffs.length > 0) {
      const buffedNextBuyTime = cookiesNeeded / cps
      const shortBuffs = cpsBuffs.filter(buff => buff.time / Game.fps < buffedNextBuyTime)
      if (shortBuffs.length > 0) {
        //Time the cpsBuffs will save us
        const shortBuffsTime = shortBuffs.reduce((acc, buff) => acc + (buff.time / Game.fps), 0)
        const shortBuffsPower = shortBuffs.reduce((acc, buff) => acc * buff.multCpS, 1)
        buySeconds = shortBuffsTime + (buySeconds - shortBuffsTime) * shortBuffsPower
      }
    }
    return Date.now() + buySeconds * 1000
  }

  get millisToBuy(): number {
    return Math.max(0, this.buyMillis - Date.now())
  }

  /**
   * Can this be bought in this ascension? ie if this depends on a legacy upgrade we don't have this ascension this can't be bought.
   */
  abstract get canEventuallyGet(): boolean

  /**
   * Calculates the cps increase of getting this considering all requirements are already met.
   */
  abstract calculateCpsIncrease(): number

  /**
   * Calculates the price to buy this and all its requirements if any.
   */
  abstract calculatePrice(): number

  get nextMilestone(): Buyable<GameObject> {
    return this;
  }

  buy(): void {
    if (this.gameObject === undefined) {
      error("Buyable has no gameObject");
      AUTO_COOKIE.stopped = true;
      return;
    }
    if (this instanceof Upgrade && this.gameObject.unlocked === 0) {
      log(`Trying to buy ${this.name} but its not unlocked yet!`);
      unlockRequirements();
    }
    if (this instanceof Achievement) {
      error(`Trying to buy an Achievement (${this.name}, cpsIncrease: ${this.calculateCpsIncrease()})!`);
      AUTO_COOKIE.stopped = true;
      return;
    }
    AUTO_COOKIE.buying = true;
    const oldBuyMode = Game.buyMode;
    Game.buyMode = 1; //Make sure we are not selling
    if (this.gameObject instanceof Game.Object) {
      // @ts-ignore
      const amount: string = convertNumeral((<GameBuilding>this.gameObject).amount + 1)
      const text = `Bought the ${amount} ${this.name}`
      log(text)
      Game.Notify(text, "")
    } else {
      const text = `Bought ${this.name}`
      log(text)
      Game.Notify(text, "")
    }
    if (this instanceof Upgrade) {
      log(`Removing upgrade ${this.name}`);
      AUTO_COOKIE.buyables = AUTO_COOKIE.buyables.filter(upgrade => upgrade !== this)
    }
    this.gameObject.buy(1);
    this.resetOriginalBuyMillis()
    Game.buyMode = oldBuyMode;
    Game.CalculateGains();
    AUTO_COOKIE.buying = false;
  }

  calculatePayback(): number {
    const cps = getCps();
    if (this.cpsIncrease === 0 || cps === 0) return Infinity;
    const payback = this.price / this.cpsIncrease + Math.max(0, this.price + AUTO_COOKIE.reserve.reserveAmount - Game.cookies) / cps;
    return round(payback, 6);
  }

  get percentCpsIncrease(): number {
    return this.cpsIncrease / Game.cookiesPs
  }

  get cpsIncreasePercentText(): string {
    return this.percentCpsIncrease > 0 && this.percentCpsIncrease !== Infinity ? ` (+${round(this.percentCpsIncrease * 100, 2)}%)` : ""
  }

  estimatedReturnPercent(additionalBrokers: number): number {
    const overhead = StockMarket.overhead * Math.pow(0.95, additionalBrokers)
    return 1 + (1 - STOCK_MARKET_STABILITY_THRESHOLD) * (this.percentCpsIncrease - overhead)
  }

  get millisChange(): number {
    return this.originalBuyMillis - this.buyMillis
  }

  get timeSavedText(): string {
    const millisChange = this.millisChange
    const absMillisChange = Math.abs(millisChange);
    if (absMillisChange < 1000) {
      return "";
    } else {
      const timeText = timeString(Math.ceil(absMillisChange / 1000));
      if (millisChange >= 1) {
        return ` (${timeText})`
      } else {
        return ` (-${timeText})`
      }
    }
  }

  get timeSavedTextColour(): string {
    if (Math.sign(this.millisChange) === 1)  {
      return "#6F6"
    } else {
      return "#F66"
    }
  }
}

abstract class BuyableWithBuildingRequirements<T extends GameObject> extends Buyable<T> {
  buildingRequirements: Array<BuildingRequirement>;

  /**
   * @param gameObject This is the object we are wrapping, it can be the game version of a Building, an Upgrade or an Achievement.
   * @param {Array<BuildingRequirement>} buildingRequirements
   */
  constructor(gameObject: T, buildingRequirements: Array<BuildingRequirement>) {
    super(gameObject);
    this.buildingRequirements = buildingRequirements;
  }

  get filteredBuildingRequirements(): Array<BuildingRequirement> {
    return this.buildingRequirements.filter(req => req.amount > req.gameObject.amount);
  }

  get hasRequirements(): boolean {
    return this.filteredBuildingRequirements.length > 0;
  }

  get bestRequirement(): Buyable<GameBuilding | GameUpgrade> {
    return minByPayback(this.filteredBuildingRequirements);
  }

  get nextMilestone(): Buyable<GameObject> {
    if (this.hasRequirements) {
      return this.bestRequirement.nextMilestone;
    } else {
      return this;
    }
  }
}

class Building extends Buyable<GameBuilding> {
  constructor(name: string) {
    super(Building.getGameObjectByName(name));
  }

  get canEventuallyGet(): boolean {
    return true;
  }

  calculateCpsIncrease(): number {
    return calculateCpsIncrease([new BuildingRequirement(this.gameObject, this.gameObject.amount + 1)]);
  }

  calculatePrice(): number {
    return this.gameObject.getPrice();
  }

  static getByName(name): Building {
    const building = AUTO_COOKIE.buildings[name];
    if (building === undefined) {
      throw new Error(`Unable to find Building with name ${name}`)
    } else {
      return building
    }
  }

  static getGameObjectByName(name: string): GameBuilding {
    const building = Game.Objects[name];
    if (building === undefined) {
      throw new Error(`Unable to find Building with name ${name}`)
    } else {
      return building
    }
  }
}

class BuildingRequirement extends Building {
  amount: number;

  /**
   * @param {Game.Object} gameObject Building required
   * @param {number} amount Number of buildings required
   */
  constructor(gameObject: GameBuilding, amount: number) {
    super(gameObject.name);
    this.amount = amount;
  }

  update(): void {
    throw new Error("Trying to update BuildingRequirement");
  }

  get missingAmount(): number {
    return Math.max(0, this.amount - this.gameObject.amount);
  }

  calculateCpsIncrease(): number {
    return calculateCpsIncrease([this]);
  }

  calculatePrice(): number {
    return this.gameObject.getSumPrice(this.missingAmount);
  }

  get nextMilestone(): Buyable<GameBuilding> {
    return Building.getByName(this.name);
  }

  static generateRequirementsForAllBuildings(amount: number): Array<BuildingRequirement> {
    return Game.ObjectsById.map(building => new BuildingRequirement(building, amount))
  }
}

/**
 * @abstract
 */
abstract class Upgrade extends BuyableWithBuildingRequirements<GameUpgrade> {
  achievementsUnlocked: Array<Achievement>;
  upgradeRequirements: Array<Upgrade>;
  flattenedUpgradeRequirements: Array<Upgrade>;
  flattenedBuildingRequirements: Array<BuildingRequirement>;

  constructor(gameUpgrade: GameUpgrade, buildingRequirements: Array<BuildingRequirement> = [], upgradeRequirements: Array<Upgrade> = [], achievementsUnlocked: Array<Achievement> = []) {
    super(gameUpgrade, buildingRequirements);
    this.achievementsUnlocked = achievementsUnlocked;
    this.upgradeRequirements = upgradeRequirements;
    this.flattenedUpgradeRequirements = this.getFlattenedUpgradeRequirements();
    this.flattenedBuildingRequirements = this.getFattenBuildingRequirements();
    AUTO_COOKIE.upgrades[this.name] = this;
  }

  get canEventuallyGet(): boolean {
    return !this.owned && this.filteredUpgradeRequirements.reduce((acc, upgrade) => acc && upgrade.canEventuallyGet, true);
  }

  get filteredBuildingRequirements(): Array<BuildingRequirement> {
    return this.flattenedBuildingRequirements.filter(req => req.amount > req.gameObject.amount);
  }

  get filteredUpgradeRequirements(): Array<Upgrade> {
    return this.flattenedUpgradeRequirements.filter(upgrade => !upgrade.owned);
  }

  get filteredAchievementRequirements(): Array<Achievement> {
    return this.achievementsUnlocked.filter(achievement => !achievement.won);
  }

  /**
   * All upgrade requirements of this upgrade.
   */
  getFlattenedUpgradeRequirements(): Array<Upgrade> {
    const upgradeRequirements = this.upgradeRequirements.filter(upgrade => !upgrade.owned);
    return upgradeRequirements.concat(upgradeRequirements.flatMap(upgrade => upgrade.getFlattenedUpgradeRequirements()));
  }

  /**
   * Calculates the building requirements to unlock all required upgrades
   * @return {Array<BuildingRequirement>}
   */
  getFattenBuildingRequirements(): Array<BuildingRequirement> {
    const upgradeRequirements = this.upgradeRequirements.filter(upgrade => !upgrade.owned).flatMap(upgrade => upgrade.getFattenBuildingRequirements());
    return Game.ObjectsById.reduce<Array<BuildingRequirement>>((totalBuildingRequirements, building) => {
      const buildingRequirementsOfUpgrades = upgradeRequirements.filter(req => req.gameObject === building);
      const buildingRequirementsOfThis = this.buildingRequirements.filter(req => req.amount > req.gameObject.amount && req.gameObject === building);
      const amounts = buildingRequirementsOfUpgrades.concat(buildingRequirementsOfThis).map(req => req.amount);
      if (amounts.length > 0) {
        totalBuildingRequirements.push(new BuildingRequirement(building, Math.max(...amounts)))
      }
      return totalBuildingRequirements;
    }, []);
  }

  get upgradeRequirementsTotalCostWithoutBuildings(): number {
    return this.filteredUpgradeRequirements.reduce((acc, upgrade) => acc + upgrade.gameObject.getPrice(), 0);
  }

  get hasRequirements(): boolean {
    const needsBuilding = this.filteredBuildingRequirements.length > 0;
    const needsUpgrade = this.filteredUpgradeRequirements.length > 0;
    return needsBuilding || needsUpgrade
  }

  get bestRequirement(): Buyable<GameBuilding | GameUpgrade> {
    // @ts-ignore
    const requirements: Array<Buyable<GameBuilding | GameUpgrade>> = this.filteredBuildingRequirements.concat(this.filteredUpgradeRequirements)
    return minByPayback(requirements)
  }

  /**
   * Calculates the cps increase of getting this considering all requirements are already met.
   */
  calculateCpsIncrease(): number {
    const achievementsCpsIncrease = this.filteredAchievementRequirements.reduce((acc, achievement) => acc + achievement.cpsIncreaseWithoutRequirements, 0);
    return calculateCpsIncrease(this.filteredBuildingRequirements, this.filteredUpgradeRequirements.concat(this)) + achievementsCpsIncrease;
  }

  /**
   * Calculates the price to buy this and all its requirements if any.
   */
  calculatePrice(): number {
    const buildingRequirementsPrice = this.filteredBuildingRequirements.reduce((acc, b) => acc + b.calculatePrice(), 0);
    const upgradeRequirementsPrice = this.upgradeRequirementsTotalCostWithoutBuildings;
    return this.gameObject.getPrice() + buildingRequirementsPrice + upgradeRequirementsPrice;
  }

  get owned (): boolean {
    return this.gameObject.bought === 1;
  }

  /**
   * Looks for an upgrade with the given name
   * @param {String} name
   * @return {Upgrade}
   */
  static findByName(name: string): Upgrade {
    return AUTO_COOKIE.upgrades[name];
  }

  /**
   * Looks for an upgrade with the given name
   * @param {String} name
   * @return {Upgrade}
   */
  static getByName(name: string): Upgrade {
    const upgrade = Upgrade.findByName(name);
    if (upgrade === undefined) {
      throw new Error(`Unable to find Upgrade with name ${name}`)
    } else {
      return upgrade
    }
  }

  /**
   * Looks for an Upgrade with the given name
   */
  static getGameUpgradeByName(name: string): GameUpgrade {
    const achievement = Game.Upgrades[name];
    if (achievement === undefined) throw new Error(`Failed to find upgrade named ${name}`);
    return achievement;
  }
}

class LegacyUpgrade extends Upgrade {
  constructor(name: string, ...upgradeRequirementNames: Array<string>) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    const upgradesRequirements = upgradeRequirementNames.map(name => Upgrade.getByName(name));
    super(gameUpgrade, [], upgradesRequirements);
  }

  get canEventuallyGet(): boolean {
    return !this.owned && this.gameObject.unlocked === 1; //If this is not unlocked it won't be unlocked for this whole ascension
  }
}

class BuildingUpgrade extends Upgrade {
  constructor(name: string, ...requiredBuildings: Array<BuildingRequirement>) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    super(
      gameUpgrade,
      requiredBuildings,
    );
  }
}

class BuildingUpgradeWithAchievement extends Upgrade {
  constructor(name: string, requiredBuilding: BuildingRequirement, achievementName: string) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    const achievement = Achievement.getByName(achievementName);
    super(
      gameUpgrade,
      [requiredBuilding],
      [],
      [achievement]
    );
  }
}

class BuildingUpgradeWithUpgradeRequirement extends Upgrade {
  constructor(name: string, upgradeRequirementName: string, ...requiredBuildings: Array<BuildingRequirement>) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    const upgradeRequirement = Upgrade.getByName(upgradeRequirementName);
    super(
      gameUpgrade,
      requiredBuildings,
      [upgradeRequirement]
    );
  }
}

class CookieUpgrade extends Upgrade {
  constructor(name: string, buildingRequirements: Array<BuildingRequirement> = [], upgradeRequirementNames: Array<string> = []) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    const upgradesRequirements = upgradeRequirementNames.map(name => Upgrade.getByName(name));
    super(
      gameUpgrade,
      buildingRequirements,
      upgradesRequirements
    );
  }

  get powerMultiplier(): number {
    return 1 + this.gameObject.power / 100;
  }
}

class CookieUpgradeWithUpgradeRequirement extends CookieUpgrade {
  constructor(name: string, requiredUpgradeName: string) {
    super(
      name,
      [],
      [requiredUpgradeName]
    )
  }
}

class MouseUpgrade extends Upgrade {
  /**
   * @param {String} name Name of the upgrade
   */
  constructor(name: string) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    super(gameUpgrade);
  }

  calculateCpsIncrease(): number {
    let add = 0; //Increase to base cursor cps and mouse clicks per non cursor gameObject
    if (Game.Has("Thousand fingers")) add += 0.1;
    if (Game.Has("Million fingers")) add *= 5;
    if (Game.Has("Billion fingers")) add *= 5;
    if (Game.Has("Trillion fingers")) add *= 10;
    if (Game.Has("Quadrillion fingers")) add *= 20;
    if (Game.Has("Quintillion fingers")) add *= 20;
    if (Game.Has("Sextillion fingers")) add *= 20;
    if (Game.Has("Septillion fingers")) add *= 20;
    if (Game.Has("Octillion fingers")) add *= 20;
    if (Game.Has("Nonillion fingers")) add *= 20;
    add *= getAmountOfNonCursors();
    const cps = Game.cookiesPs;
    if (hasOrIsChoice("Plastic mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Iron mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Titanium mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Adamantium mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Unobtainium mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Eludium mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Wishalloy mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Fantasteel mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Nevercrack mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Armythril mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Technobsidian mouse", this.name)) add += cps * 0.01;
    if (hasOrIsChoice("Plasmarble mouse", this.name)) add += cps * 0.01;
    let cookiesPerClick = 1;
    if (Game.Has("Reinforced index finger")) cookiesPerClick *= 2;
    if (Game.Has("Carpal tunnel prevention cream")) cookiesPerClick *= 2;
    if (Game.Has("Ambidextrous")) cookiesPerClick *= 2;
    const clickBuffs = MouseUpgrade.clickBuffs
    if (clickBuffs.length > 0) {
      const clickMultiplier = clickBuffs.reduce<number>((multiplier, buff) => multiplier * buff.multClick, 1)
      return ((cookiesPerClick + add) * clickMultiplier - Game.mouseCps()) * CLICKS_PER_SEC;
    } else {
      return 0;
    }
  }

  static get clickBuffs(): Array<Buff> {
    return getBuffs().filter(buff => buff.multClick > 1)
  }
}

class GoldenCookieUpgrade extends Upgrade {
  /**
   * @param {String} name Name of the upgrade
   */
  constructor(name: string) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    super(gameUpgrade);
  }

  calculateCpsIncrease(): number {
    if (this.gameObject.unlocked === 1 && AUTO_COOKIE.reserve.reserveAmount + this.price <= Game.cookies) {
      return Infinity;
    } else {
      return 0;
    }
  }

  estimatedReturnPercent(): number {
    return 1
  }
}

class KittenUpgrade extends Upgrade {
  milkRequired: number;
  /**
   * @param {String} name Name of the upgrade
   * @param {number} milkRequired Milk progress that unlocks this kitten upgrade
   */
  constructor(name: string, milkRequired: number) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    super(gameUpgrade);
    this.milkRequired = milkRequired;
  }

  calculateCpsIncrease(): number {
    if (Game.milkProgress >= this.milkRequired) {
      const cps = getCps();
      const base_cps = cps / Game.globalCpsMult;
      let multiplier = Game.globalCpsMult / getKittenMultiplier(Game.milkProgress);
      multiplier *= getKittenMultiplier(Game.milkProgress, this.name);
      return (base_cps * multiplier) - cps;
    } else {
      return 0;
    }
  }
}

class HeavenlyChipUpgrade extends Upgrade {
  unlockPercentage: number;

  constructor(name: string, percentUnlock: number, ...requiredUpgradeNames: Array<string>) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    //const requirement = requirementNames.map(name => Upgrade.getGameUpgradeByName(name));
    const upgradeRequirements = requiredUpgradeNames.map(name => Upgrade.getByName(name));
    super(gameUpgrade, [], upgradeRequirements);
    this.unlockPercentage = percentUnlock;
  }

  calculateCpsIncrease(): number {
    const multiplier = Game.prestige * 0.01 * this.unlockPercentage / 100;
    return getCps() * multiplier
  }
}

class HeavenlyChipUpgradeWithUpgradeRequirement extends Upgrade {
  constructor(name: string, percentUnlocked: number, requiredUpgradeName: string) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    const requiredUpgrade = Upgrade.getByName(requiredUpgradeName);
    super(
      gameUpgrade,
      [],
      [requiredUpgrade]
    );
  }
}

class ResearchUpgrade extends Upgrade {

  constructor(name: string, buildingRequirements: Array<BuildingRequirement>, ...upgradeRequirementNames: Array<string>) {
    const gameUpgrade = Upgrade.getGameUpgradeByName(name);
    const upgradesRequirements = upgradeRequirementNames.map(name => Upgrade.getByName(name));
    super(gameUpgrade, buildingRequirements, upgradesRequirements);
  }

  calculateCpsIncrease(): number {
    if (Achievement.getByName("Elder").won && ResearchUpgrade.nextResearch() !== this) {
      return super.calculateCpsIncrease();
    } else {
      return 0;
    }
  }

  calculatePayback(): number {
    if (Game.researchT > 0) { //We must wait for the research to finish researching
      return Infinity;
    } else {
      return super.calculatePayback()
    }
  }

  static fullResearchTime(): number {
    const researchTime = Game.baseResearchTime;
    if (Game.Has('Persistent memory')) {
      return Math.ceil(researchTime / 10);
    } else {
      return researchTime;
    }
  }

  static nextResearch(): ResearchUpgrade | undefined {
    const nextResearchId = Game.nextResearch;
    if (nextResearchId > 0) {
      return AUTO_COOKIE.upgrades[Game.UpgradesById[nextResearchId].name]
    }
  }
}

class ResearchCookieUpgrade extends ResearchUpgrade {
  power: number;

  constructor(name: string, upgradeRequirementName: string, power: number) {
    super(name, [], upgradeRequirementName);
    this.power = power;
  }

  get powerMultiplier(): number {
    return 1 + this.power / 100;
  }
}

class Achievement extends BuyableWithBuildingRequirements<GameAchievement> {

  constructor(name: string, ...requiredBuildings: Array<BuildingRequirement>) {
    const achievement = Achievement.getGameAchievementByName(name);
    super(achievement, requiredBuildings);
    AUTO_COOKIE.achievements[this.name] = this;
  }

  get canEventuallyGet(): boolean {
    //If an Achievement has no requirements it means we can't purchase our way to get it.
    //It comes from baking a certain amount of cookies for example, or having a certain amount of different grandmas
    return this.gameObject.won === 0 && this.buildingRequirements.length > 0;
  }

  get cpsIncreaseWithoutRequirements(): number {
    if (this.gameObject.won === 1) {
      return 0; //We already have this achievement
    } else {
      let multiplier = Game.globalCpsMult / getKittenMultiplier(Game.milkProgress);
      multiplier *= getKittenMultiplier(Game.milkProgress + 0.04);
      const cps = getCps();
      const baseCps = cps / Game.globalCpsMult;
      return round((baseCps * multiplier) - cps, 4);
    }
  }

  calculateCpsIncrease(): number {
    return calculateCpsIncrease(this.filteredBuildingRequirements) + this.cpsIncreaseWithoutRequirements;
  }

  calculatePrice(): number {
    return this.filteredBuildingRequirements.reduce((acc, b) => acc + b.calculatePrice(), 0);
  }

  get won(): boolean {
    return this.gameObject.won === 1
  }

  static findByName(name): Achievement {
    return AUTO_COOKIE.achievements[name];
  }

  static getByName(name): Achievement {
    const achievement = Achievement.findByName(name);
    if (achievement === undefined) {
      throw new Error(`Unable to find Achievement with name ${name}`)
    } else {
      return achievement
    }
  }

  static getGameAchievementByName(name): GameAchievement {
    const achievement = Game.Achievements[name];
    if (achievement === undefined) throw new Error(`Failed to find achievement named ${name}`);
    return achievement;
  }
}

//region Reserve
class CookieEffect {
  name: string
  shortName: string

  constructor(name: string, shortName: string = name) {
    this.name = name
    this.shortName = shortName
  }
}

class ReserveLevel {
  effects: Array<CookieEffect>;
  amountF: () => number;

  constructor(effects: Array<CookieEffect>, amountF: () => number) {
    this.effects = effects;
    this.amountF = amountF;
  }

  get title(): string {
    if (this.effects.length === 0) {
      return "Disabled"
    } else if (this.effects.length === 1) {
      return this.effects[0].shortName
    } else {
      return this.effects.map(effect => effect.shortName).join(" with ")
    }
  }

  static goldenCookieMultiplier(): number {
    let mult = 1;
    if (Game.elderWrath > 0) mult *= 1 + Game.auraMult('Unholy Dominion') * 0.1
    else if (Game.elderWrath === 0) mult *= 1 + Game.auraMult('Ancestral Metamorphosis') * 0.1
    if (Game.Has('Green yeast digestives')) mult *= 1.01;
    if (Game.Has('Dragon fang')) mult *= 1.03;
    if (Game.elderWrath === 0) mult *= Game.eff('goldenCookieGain');
    else if (Game.elderWrath === 3) mult *= Game.eff('wrathCookieGain');
    //If elderwrath is between 1 and 3 we don't apply bonus because it depends on the cookie we get.
    return mult;
  }

  static calculateCookieChainReserve(cps: number): number {
    const digit = Game.elderWrath === 3 ? 6 : 7; //With last level of elder wrath cookie the digit will always be 6
    const goldenCookieMultiplier = this.goldenCookieMultiplier();
    const chainDigits = Math.floor(Math.log((cps * 6 * 60 * 60 * goldenCookieMultiplier * 9) / digit) / Math.LN10);

    function repeatingDigitNumber(digit: number, repetitions: number): number {
      return 1 / 9 * Math.pow(10, repetitions) * digit
    }

    function payout(digit: number, digits: number): number {
      let payout = 0
      for (let i = 1; i <= digits; i++) {
        payout += repeatingDigitNumber(digit, i)
      }
      return payout
    }

    let res = 0;
    //If max chain is lesser or equal to 5 we dont need to reserve as chain will not break before the 5 digits and 6 digits is more than 6h production.
    if (chainDigits > 5) {
      res = (Math.floor(repeatingDigitNumber(digit, chainDigits)) * goldenCookieMultiplier * 2  - payout(digit, chainDigits - 1)) * goldenCookieMultiplier;
    }
    return res;
  }

  static lucky(): number {
    //This adds 15% of current cookies in bank or 15 mins of production
    //Reserve for this must make 15% of cookies in bank equal to 15 mins of production
    return (Game.unbuffedCps * 15 * 60) / .15; //Multiplier doesn't matter here as it would cancel out.
  }

  static conjuredBakedGoods(): number {
    return (Game.unbuffedCps * 30 * 60) / .15;
  }

  get amount(): number {
    return this.amountF();
  }

  static get all(): Array<ReserveLevel> {
    return [
      LUCKY_RESERVE,
      BAKED_GOODS_RESERVE,
      CHAIN_RESERVE,
      FRENZY_LUCKY_RESERVE,
      FRENZY_CHAIN_RESERVE,
      FRENZY_BAKED_GOODS_RESERVE,
      DRAGON_HARVEST_LUCKY_RESERVE,
      DRAGON_HARVEST_CHAIN_RESERVE,
      DRAGON_HARVEST_BAKED_GOODS_RESERVE
    ]
  }
}

const LUCKY = new CookieEffect("Lucky")
const CHAIN = new CookieEffect("Chain")
const BAKED_GOODS = new CookieEffect("Baked Goods")
const FRENZY = new CookieEffect("Frenzy")
const DRAGON_HARVEST = new CookieEffect("Dragon Harvest", "DH")

const DISABLED = new ReserveLevel([], () => 0);
const LUCKY_RESERVE = new ReserveLevel([LUCKY], ReserveLevel.lucky);
const BAKED_GOODS_RESERVE = new ReserveLevel([BAKED_GOODS], ReserveLevel.conjuredBakedGoods);
const CHAIN_RESERVE = new ReserveLevel([CHAIN], () => ReserveLevel.calculateCookieChainReserve(Game.unbuffedCps));
const FRENZY_LUCKY_RESERVE = new ReserveLevel([LUCKY, FRENZY], () => ReserveLevel.lucky() * 7);
const FRENZY_CHAIN_RESERVE = new ReserveLevel([CHAIN, FRENZY], () => ReserveLevel.calculateCookieChainReserve(Game.unbuffedCps * 7));
const FRENZY_BAKED_GOODS_RESERVE = new ReserveLevel([BAKED_GOODS, FRENZY], () => ReserveLevel.conjuredBakedGoods() * 7);
const DRAGON_HARVEST_LUCKY_RESERVE = new ReserveLevel([LUCKY, DRAGON_HARVEST], () => ReserveLevel.lucky() * 15);
const DRAGON_HARVEST_CHAIN_RESERVE = new ReserveLevel([CHAIN, DRAGON_HARVEST], () => ReserveLevel.calculateCookieChainReserve(Game.unbuffedCps * 15));
const DRAGON_HARVEST_BAKED_GOODS_RESERVE = new ReserveLevel([BAKED_GOODS, DRAGON_HARVEST], () => ReserveLevel.conjuredBakedGoods() * 15);

class Reserve {
  reserveLevel: ReserveLevel;
  amount: number;

  constructor() {
    this.updateReserveLevel()
  }

  get reserveAmount(): number {
    return this.amount
  }

  static get reservePossibilities(): Array<ReserveLevel> {
    const activeButtonEffects = AUTO_COOKIE.reserveNote ? AUTO_COOKIE.reserveNote.activeButtonEffects : []
    if (activeButtonEffects.length === 0) return []

    return ReserveLevel.all.filter(reserve => reserve.effects.reduce((acc, effect) => acc && activeButtonEffects.includes(effect), true))
  }

  updateReserveLevel(): void {
    //This avoids recalculating reserve amount a bunch of times
    const possibilities = Reserve.reservePossibilities.map(reserveLevel => {return {level: reserveLevel, amount: reserveLevel.amount}})
    const reserveLevel = possibilities.reduce((max, reserveLevel) => {
      if (reserveLevel.amount > max.amount) {
        return reserveLevel
      } else {
        return max
      }
    }, {level: DISABLED, amount: DISABLED.amount})

    if (reserveLevel.level !== this.reserveLevel || reserveLevel.amount !== this.amount) {
      this.reserveLevel = reserveLevel.level
      this.amount = reserveLevel.amount
      AUTO_COOKIE.reserveNote?.update(AUTO_COOKIE)
      AUTO_COOKIE.bestBuyable?.resetOriginalBuyMillis()
      AUTO_COOKIE.loop("Reserve changed");
    }
  }
}
//endregion

class AutoElement {
  html: HTMLElement
  shown = false

  constructor(tag) {
    this.html = document.createElement(tag)
  }

  get style(): CSSStyleDeclaration {
    return this.html.style
  }

  get classList() {
    return this.html.classList
  }

  set onclick(f) {
    this.html.onclick = f
  }

  set onmouseout(f) {
    this.html.onmouseout = f
  }

  set textContent(text: string) {
    this.html.textContent = text
  }

  hide(): AutoElement {
    if (this.shown) {
      this.html.style.display = "none"
      this.shown = false
    }
    return this
  }

  show(): AutoElement {
    if (!this.shown) {
      this.html.style.display = "block"
      this.shown = true
    }
    return this
  }

  appendChild(child: AutoElement | Node): void {
    const html = child instanceof AutoElement ? child.html : child
    this.html.appendChild(html)
  }
}

class NoteArea extends AutoElement {

  constructor() {
    super("div")
    this.style.position = "absolute";
    this.style.bottom = "0px";
    this.style.left = "auto";
    this.style.width = "350px";
    this.style.zIndex = "100000001";
    this.hide()
  }
}

abstract class Note extends AutoElement {
  topRow: AutoElement
  bottomRow: AutoElement

  title: AutoElement
  description: AutoElement

  protected constructor() {
    super("div")
    this.style.width = "350px";
    this.style.display = "none";
    this.classList.add("framed", "text")

    this.topRow = new AutoElement("div")
    this.appendChild(this.topRow)

    this.bottomRow = new AutoElement("div")
    this.appendChild(this.bottomRow)

    this.title = new AutoElement("h3")
    this.title.style.display = "inline-block"
    this.topRow.appendChild(this.title)

    this.description = new AutoElement("h5")
    this.description.classList.add("title")
    this.description.style.display = "inline-block"
    this.description.style.fontSize = "16px"
    this.bottomRow.appendChild(this.description)

  }

  setTitle(title: string): this {
    this.title.textContent = title
    return this
  }

  setDescription(description: string): this {
    this.description.textContent = description
    return this
  }

  abstract update(autoCookie: AutoCookie): void
}

class GoalNote extends Note {
  extraDescription: AutoElement
  extraTitle: AutoElement

  constructor() {
    super()

    this.extraTitle = new AutoElement("span")
    this.topRow.appendChild(this.extraTitle)

    this.onmouseout = () => Game.tooltip.shouldHide = 1;

    this.extraDescription = new AutoElement("span")
    this.extraDescription.style.color = "#6F6"
    this.bottomRow.appendChild(this.extraDescription)

    this.show()
  }

  setExtraDescription(extra: string): this {
    this.extraDescription.textContent = extra
    return this
  }

  setExtraTitle(extra: string, color: string): this {
    this.extraTitle.textContent = extra
    this.extraTitle.style.color = color
    return this
  }

  update(autoCookie: AutoCookie): void {
    const bestBuyable = autoCookie.bestBuyable
    if (typeof bestBuyable === 'undefined') return;

    if (bestBuyable.name === bestBuyable.nextMilestone.name) {
      autoCookie.notesShown = 3
      this.hide()
      return
    } else {
      autoCookie.notesShown = 4
      this.setDescription(bestBuyable.name)
      this.show()
    }

    let title = "Goal: " + (bestBuyable instanceof Achievement ? "Achieve" : "Unlock")
    const secondsToBuy = bestBuyable.millisToBuy / 1000
    if (secondsToBuy > 0) {
      const buyTimeString = timeString(Math.ceil(secondsToBuy))
      title += ` in ${buyTimeString}`
      this.setExtraTitle(bestBuyable.timeSavedText, bestBuyable.timeSavedTextColour)
    }

    if (bestBuyable instanceof Achievement) {
      this.html.onmouseover = () => {
        const achievement = {...Game.Achievements[bestBuyable.name], won: 1} //Clone the achiev and set it as won to avoid it showing as mysterious
        return Game.tooltip.draw(this.html, () => Game.crateTooltip(achievement, "stats"), "this")
      };
    } else if (bestBuyable instanceof Upgrade) {
      this.html.onmouseover = () => Game.tooltip.draw(this.html,() => Game.crateTooltip(Game.Upgrades[bestBuyable.name],'stats'),'this');
    } else {
      this.html.onmouseover = null;
    }
    this.setTitle(title).setExtraDescription(bestBuyable.cpsIncreasePercentText)
  }
}

class NextBuyNote extends GoalNote {
  button

  constructor() {
    super()

    this.button = new AutoElement("a")
    this.button.style.fontSize = "15px"
    this.button.style.float = "right"
    this.button.style.textDecoration = "none"
    //this.updateLockedIcon() //TODO: Check
    this.button.onclick = () => AUTO_COOKIE.toggleBuyLock()
    this.topRow.appendChild(this.button)
  }

  updateLockedIcon(): this {
    if (AUTO_COOKIE.buyLocked) {
      this.button.textContent = "🔒";
    } else {
      this.button.textContent = "🗸";
    }
    return this
  }

  update(autoCookie: AutoCookie): void {
    //Maybe change remain cost
    if (!autoCookie.bestBuyable) return; //Next buy still not set
    const nextMilestone = autoCookie.bestBuyable.nextMilestone;
    const secondsToBuy = nextMilestone.millisToBuy / 1000
    let title
    if (Game.cookiesPs === 0) {
      title = "No production click cookie to buy";
    } else if (secondsToBuy === 0) {
      if (autoCookie.buyLocked) {
        title = "Best buy is"
      } else {
        if (nextMilestone instanceof Building) {
          title = "Buying the " + convertNumeral(nextMilestone.gameObject.amount + 1);
        } else { //Upgrade
          title = "Buying "
        }
      }
    } else {
      const buyTime = timeString(Math.ceil(secondsToBuy));
      if (autoCookie.buyLocked) {
        title = `Can buy in ${buyTime}`;
      } else {
        title = `Next buy in ${buyTime}`;
      }
      this.setExtraTitle(nextMilestone.timeSavedText, nextMilestone.timeSavedTextColour)
    }

    if (nextMilestone instanceof Upgrade) {
      this.html.onmouseover = () => Game.tooltip.draw(this.html,() => Game.crateTooltip(Game.Upgrades[nextMilestone.name],'stats'),'this');
    } else {
      this.html.onmouseover = null;
    }

    this.setTitle(title)
      .setDescription(nextMilestone.name)
      .setExtraDescription(nextMilestone.cpsIncreasePercentText)
      .updateLockedIcon()
  }
}

class ReserveButton extends AutoElement {
  icon: string
  cookieEffect: CookieEffect
  unlockedF: () => boolean
  added: boolean = false
  active: boolean = false

  constructor(icon: string, cookieEffect: CookieEffect, unlockedF: () => boolean) {
    super("a")
    this.icon = icon
    this.cookieEffect = cookieEffect
    this.unlockedF = unlockedF


    this.onclick = () => {
      this.toggle()
      AUTO_COOKIE.reserve.updateReserveLevel()
    }
    this.textContent = this.icon
    this.style.textDecoration = "none"
  }

  get unlocked(): boolean {
    return this.unlockedF()
  }

  toggle(): ReserveButton {
    return this.active ? this.setInactive() : this.setActive()
  }

  setActive(): ReserveButton {
    this.html.style.textShadow = "0px 0px 3px #fff"
    this.active = true
    return this
  }

  setInactive(): ReserveButton {
    this.html.style.textShadow = ""
    this.active = false
    return this
  }

  static get all(): Array<ReserveButton> {
    return [
      new ReserveButton("🍀 ", LUCKY, () => true),
      new ReserveButton("🔗 ", CHAIN, () => Game.cookiesEarned >= 100000),
      new ReserveButton("ɮ ", BAKED_GOODS, () => Game.Objects["Wizard tower"].level > 0),
      new ReserveButton("🍪 ", FRENZY, () => Game.elderWrath < 3 && Game.Has("Get lucky")),
      new ReserveButton("🐉 ", DRAGON_HARVEST, () => Game.hasAura("Reaper of Fields")),
    ]
  }
}

class ReserveNote extends Note {
  buttonDiv: AutoElement
  buttons: Array<ReserveButton>

  constructor() {
    super()

    this.buttonDiv = new AutoElement("div")
    this.buttonDiv.style.float = "right"
    this.buttonDiv.style.fontSize = "15px"
    this.topRow.appendChild(this.buttonDiv)
    this.buttons = ReserveButton.all

    this.addButtons().show()
  }

  get activeButtonEffects(): Array<CookieEffect> {
    return this.buttons.flatMap(button => button.active ? [button.cookieEffect] : [])
  }

  setActiveButtons(buttonNames: Array<string>): void {
    if (buttonNames.length > 0) {
      this.buttons.forEach(button => {
        if (button.unlocked && buttonNames.includes(button.cookieEffect.name)) {
          button.setActive()
        } else {
          button.setInactive()
        }
      })
      debug(`Setting ${buttonNames} to active`)
      AUTO_COOKIE.reserve.updateReserveLevel()
    }
  }

  addButtons(): ReserveNote {
    this.buttons.forEach(button => {
      if (button.unlocked && !button.added) {
        AUTO_COOKIE.buyLocked = true
        this.buttonDiv.appendChild(button.html)
        button.added = true
      }
    })
    return this
  }

  update(autoCookie: AutoCookie): void {
    this.addButtons()

    this.setDescription(Beautify(autoCookie.reserve.reserveAmount))

    const reserveLevel = autoCookie.reserve.reserveLevel;
    this.topRow.style.color = reserveLevel === DISABLED ? "#F66" : "#6F6"
    this.setTitle(`Reserve (${reserveLevel.title})`)
  }
}

class GoldenCookieSpawnNote extends Note {
  constructor() {
    super()
  }

  setGolden(): void {
    this.html.style.filter = "invert(78%) sepia(94%) saturate(1828%) hue-rotate(330deg) brightness(95%) contrast(93%)"
  }

  clearGolden(): void {
    this.html.style.filter = ""
  }

  update(autoCookie: AutoCookie): void {
    if (autoCookie.reserve.reserveLevel === DISABLED) {
      this.hide()
      return
    } else {
      this.show()
    }

    let title
    let description
    if (Game.shimmers.length === 1 && autoCookie.reserve.reserveLevel !== DISABLED) {
      this.setGolden()
      title = "Cookie despawning in";
      description = timeString(Game.shimmers[0].life / Game.fps);
      document.title = description + " until cookie despawn"; //Set page name
    } else {
      this.clearGolden()
      if (Game.shimmerTypes.golden.minTime > Game.shimmerTypes.golden.time) {
        title = "Next cookie spawn window in";
        description = timeString((Game.shimmerTypes.golden.minTime - Game.shimmerTypes.golden.time) / Game.fps);
      } else {
        title = "Cookie spawn window ends in"
        description = timeString((Game.shimmerTypes.golden.maxTime - Game.shimmerTypes.golden.time) / Game.fps)
      }
    }

    this.setTitle(title).setDescription(description)
  }
}

function getBuffs(): Array<Buff> {
  let buffs: Array<Buff> = [];
  for (const buffName in Game.buffs) {
    buffs.push(Game.buffs[buffName]);
  }
  return buffs;
}

/*function getBestWrinkler(): Wrinkler {
  return Game.wrinklers.reduce((max, current) => (current.sucked > max.sucked ) ? current : max);
}

function calculateWrinklerSuckMultiplier(): number {
  let suckMultiplier = 1.1;
  if (Game.Has('Sacrilegious corruption')) suckMultiplier *= 1.05;
  if (Game.Has('Wrinklerspawn')) suckMultiplier *= 1.05;
  return suckMultiplier * (1 + .05 * getGodLevel('scorn'));
}

function calculateCookiesLostDuringWrinklerRespawn(wrinklersPopped = 1) {
  function calculateWrinklerSpawnTime() {
    let chance = 0.00001 * Game.elderWrath;
    chance *= Game.eff('wrinklerSpawn');
    if (Game.Has('Unholy bait')) chance *= 5;
    const godLvl = getGodLevel('scorn');
    if (godLvl === 1) {
      chance *= 2.5;
    } else if (godLvl === 2) {
      chance *= 2;
    } else if (godLvl === 3) {
      chance *= 1.5;
    }
    if (Game.Has('Wrinkler doormat')) chance = 0.1;

    const average = 1 / chance;
    const stdDev = Math.sqrt((1 - chance) / (chance * chance));
    return (average + stdDev) / Game.fps;
  }

  function calculateWrinklerMultiplier(wrinklers) {
    const suckRate = 0.05;
    const suckMultiplier = calculateWrinklerSuckMultiplier();
    return (1 - suckRate * wrinklers) + suckRate * wrinklers * wrinklers * suckMultiplier
  }
  const maxWrinklers = Game.getWrinklersMax();

  return Math.max(Game.unbuffedCps, Game.cookiesPs) * calculateWrinklerSpawnTime() * (calculateWrinklerMultiplier(maxWrinklers) - calculateWrinklerMultiplier(maxWrinklers - wrinklersPopped));
}

function calculateWrinklersWorthPopping() {
  const maxWrinklers = Game.getWrinklersMax();
  const activeWrinklers = Game.wrinklers.filter(wrinkler => wrinkler.sucked > 0);
  const missingWrinklers = maxWrinklers - activeWrinklers.length;
  const wrinklers = activeWrinklers.sort((a, b) => b.sucked - a.sucked);
  const suckMultiplier = calculateWrinklerSuckMultiplier();
  let worthWrinklers = [];
  for (let wrinklerId in wrinklers) {
    const lostDuringRespawn = calculateCookiesLostDuringWrinklerRespawn(missingWrinklers + parseInt(wrinklerId) + 1);
    const wrinkler = wrinklers[wrinklerId];
    if (wrinkler.sucked * suckMultiplier >= lostDuringRespawn) {
      worthWrinklers.push(wrinkler)
    } else {
      break;
    }
  }
  return worthWrinklers;
}*/

function getCps() {
  return Game.cookiesPs * (1 - Game.cpsSucked);
}

/**
 * Log achievements AutoCookie doesn't know about
 */
function logMissingAchievements(): void {
  for (let name in Game.Achievements) {
    const achievement = Achievement.findByName(name);
    if (achievement === undefined) {
      console.log(Game.Achievements[name]);
    }
  }
}

function logMissingUpgrades(): void {
  for (let name in Game.Upgrades) {
    const upgrade = Upgrade.findByName(name);
    if (upgrade === undefined) {
      console.log(Game.Upgrades[name]);
    }
  }
}

abstract class Investment {
  /**
   * Estimates the profit we would make if we ran this investment around the given buyable
   * @param buyable The buyable to invest around
   * @returns {number} The estimated cookies we would gain should we run this investment
   */
  abstract estimateReturns<T extends GameObject>(buyable: Buyable<T>): number
  /**
   * @returns {number} The total cookies invested
   */
  abstract invest(): number
  /**
   * @returns {number} The total cookies gotten from selling this investment
   */
  abstract sellInvestment(): number
}

class EmptyInvestment extends Investment {
  constructor() {
    super()
  }

  /**
   * Estimates the profit we would make if we ran this investment around the given buyable
   * @param buyable The buyable to invest around
   * @returns {number} The estimated cookies we would gain should we run this investment
   */
  estimateReturns<T extends GameObject>(buyable: Buyable<T>): number {
    return 0;
  }

  /**
   * @returns {number} The total cookies invested
   */
  invest(): number {
    return 0;
  }

  /**
   * @returns {number} The total cookies gotten from selling this investment
   */
  sellInvestment(): number {
    return 0;
  }
}

class RealInvestment extends Investment {
  moneyInvestedInStocks: number
  moneyInvestedInBrokers: number
  newBrokers: number
  buyFunctions: Array<() => void>
  sellFunctions: Array<() => number>

  constructor(cookies: number) {
    super()
    const goods = StockMarket.stableActiveGoods.sort((g1, g2) => g2.val - g1.val) //Sorts by price in descending order
    const fullStockPrice = goods.reduce((total, good) => {
      const price = StockMarket.price(good)
      const stockToFillWarehouse = StockMarket.maxStock(good) - good.stock
      return total + price * stockToFillWarehouse
    }, 0)
    const startingMoney = StockMarket.cookiesToMoney(cookies)
    const brokersToBuy = StockMarket.calculateBrokersToBuy(Math.min(startingMoney, fullStockPrice))
    const brokersCost = brokersToBuy * 1200
    const money = startingMoney - brokersCost

    const {remainingMoney, buyFunctions, sellFunctions} = goods.reduce((acc, good) => {
      const {remainingMoney, buyFunctions, sellFunctions} = acc
      const price = StockMarket.price(good)
      const stockToFillWarehouse = StockMarket.maxStock(good) - good.stock
      const stockToBuy = Math.min(stockToFillWarehouse, Math.floor(remainingMoney / price))
      if (stockToBuy > 0) {
        const buyPrice = stockToBuy * price
        const buyFunction = () => StockMarket.buy(good, stockToBuy)
        const sellFunction = () => StockMarket.sell(good, stockToBuy)
        return {
          remainingMoney: remainingMoney - buyPrice,
          buyFunctions: buyFunctions.concat([buyFunction]),
          // @ts-ignore
          sellFunctions: sellFunctions.concat([sellFunction]),
        }
      } else {
        return acc
      }
    }, {remainingMoney: money, buyFunctions: [() => StockMarket.buyBrokers(brokersToBuy)], sellFunctions: []})

    this.moneyInvestedInStocks = money - remainingMoney
    this.moneyInvestedInBrokers = brokersCost
    this.newBrokers = brokersToBuy
    this.buyFunctions = buyFunctions
    this.sellFunctions = sellFunctions
  }

  get totalInvestment(): number {
    return this.moneyInvestedInStocks + this.moneyInvestedInBrokers
  }

  get cookieInvestment(): number {
    return StockMarket.moneyToCookies(this.totalInvestment)
  }

  /**
   * Estimates the profit we would make if we ran this investment around the given buyable
   * @param buyable The buyable to invest around
   * @returns {number} The estimated cookies we would gain should we run this investment
   */
  estimateReturns<T extends GameObject>(buyable: Buyable<T>): number {
    if (buyable.percentCpsIncrease === Infinity) return 0
    const estimatedReturnPercent = buyable.estimatedReturnPercent(this.newBrokers)
    const cpsAfterBuying = Game.unbuffedCps * buyable.percentCpsIncrease
    const returns = StockMarket.moneyToCookies(this.moneyInvestedInStocks, cpsAfterBuying) * estimatedReturnPercent
    return returns - this.cookieInvestment
  }

  /**
   * @returns {number} The total cookies invested
   */
  invest(): number {
    this.buyFunctions.forEach(f => f())
    return this.cookieInvestment
  }

  /**
   * @returns {number} The total cookies gotten from selling this investment
   */
  sellInvestment(): number {
    const moneyFromSales = this.sellFunctions.reduce((total, sellFunction) => total + sellFunction(), 0)
    return StockMarket.moneyToCookies(moneyFromSales)
  }
}

class StockMarket {
  private static get game(): Market {
    return Game.Objects.Bank.minigame
  }

  static get isLoaded(): boolean {
    return Game.Objects.Bank.minigameLoaded
  }

  static get millisToNextTick(): number {
    return (1800 - StockMarket.game.tickT) / Game.fps * 1000
  }

  static get brokers(): number {
    return StockMarket.game.brokers
  }

  static get maxBrokers(): number {
    return StockMarket.game.getMaxBrokers()
  }

  static calculateOverhead(brokers: number): number {
    return 0.2 * Math.pow(0.95, brokers)
  }

  static get overhead(): number {
    return this.calculateOverhead(StockMarket.brokers)
  }

  static maxStock(good: Good): number {
    return StockMarket.game.getGoodMaxStock(good)
  }

  static price(good: Good): number {
    return StockMarket.game.getGoodPrice(good)
  }

  static get activeGoods(): Array<Good> {
    return StockMarket.game.goodsById.flatMap(good => good.active ? [good] : [])
  }

  static get stableActiveGoods(): Array<Good> {
    return StockMarket.activeGoods.filter(good => {
      if (good.vals.length >= STOCK_MARKET_STABILITY_MIN_PRICES) { //Need at least 5 values to check if a good is stable
        const prices = good.vals.slice(0, STOCK_MARKET_STABILITY_MAX_PRICES)
        const min = Math.min(...prices)
        const max = Math.max(...prices)
        return (max - min) / min < STOCK_MARKET_STABILITY_THRESHOLD
      } else {
        return false
      }
    })
  }

  static buyBrokers(amount: number): void {
    for (let i = 0; i < amount; i++) {
      //Apparently there is no accessible function to buy brokers, this is the best I found
      // @ts-ignore
      document.getElementById("bankBrokersBuy").click()
    }
  }

  static buy(good: Good, amount: number): void {
    if (StockMarket.game.buyGood(good.id, amount)) {
      log(`Bought ${amount} ${good.name}`)
    }
  }

  /**
   * Sells the given amount of the given good
   * @param good Good to sell
   * @param amount Amount of good to sell
   * @returns {number} The money we got from the sale
   */
  static sell(good: Good, amount: number): number {
    if (StockMarket.game.sellGood(good.id, amount)) {
      const price = StockMarket.price(good) * amount
      log(`Sold ${amount} ${good.name}`)
      return price;
    } else {
      return 0;
    }
  }

  static calculateBrokersToBuy(stockPrice: number): number {
    const increase = .25 //This simulates an upgrade, this can be any value above the current overhead
    if (StockMarket.brokers === StockMarket.maxBrokers) return 0;
    const maxNewBrokers = StockMarket.maxBrokers - StockMarket.brokers

    function calculateProfit(brokers: number): number {
      return stockPrice * (increase - StockMarket.calculateOverhead(StockMarket.brokers + brokers)) - brokers * 1200
    }

    for (let brokers = 0; brokers < maxNewBrokers - 1; brokers++) {
      const profit = calculateProfit(brokers)
      const nextProfit = calculateProfit(brokers + 1)
      if (profit >= nextProfit) {
        return brokers
      }
    }
    return maxNewBrokers
  }

  static cookiesToMoney(cookies: number): number {
    return cookies / Game.cookiesPsRawHighest
  }

  /**
   * Converts a given amount of money into cookies
   * @param money {number} The moneyh to convert
   * @param highestCps {number} An option value of the highest cookies per second to use which defaults to the current games one
   * @returns {number} The money in cookies
   */
  static moneyToCookies(money: number, highestCps: number = 0): number {
    return money * Math.max(highestCps, Game.cookiesPsRawHighest)
  }

  createInvestment(cookies: number): Investment {
    return StockMarket.isLoaded && cookies > 0 ? new RealInvestment(cookies) : new EmptyInvestment()
  }
}

class AutoCookie {
  stopped = true
  buyLocked = true
  buying = false

  reserve: Reserve
  stockMarket: StockMarket
  bestBuyable: Buyable<GameObject>

  noteArea: NoteArea
  spawnWindowNote: GoldenCookieSpawnNote
  reserveNote: ReserveNote
  goalNote: GoalNote
  nextBuyNote: NextBuyNote
  notes: Array<Note> = []

  buildings: Array<Building> = []
  upgrades: Array<Upgrade> = []
  achievements: Array<Achievement> = []
  buyables: Array<Buyable<GameObject>> = []

  mainInterval: number
  noteUpdateInterval: number

  lastCps: number = 0
  notesShown: number = 3

  addBuildings(): void {
    Game.ObjectsById.forEach(building => this.buildings[building.name] = new Building(building.name));
  }

  addUpgrades(): void {
//Generates a list with all possible upgrades
    //region Legacy
    new LegacyUpgrade("Legacy");

    new LegacyUpgrade("How to bake your dragon", "Legacy");
    new LegacyUpgrade("Classic dairy selection", "Legacy");
    new LegacyUpgrade("Basic wallpaper assortment", "Classic dairy selection");
    new LegacyUpgrade("Fanciful dairy selection", "Classic dairy selection");
    new LegacyUpgrade("Heralds", "Legacy");

    new LegacyUpgrade("Twin Gates of Transcendence", "Legacy");
    new LegacyUpgrade("Angels", "Twin Gates of Transcendence");
    new LegacyUpgrade("Archangels", "Angels");
    new LegacyUpgrade("Virtues", "Archangels");
    new LegacyUpgrade("Dominions", "Virtues");
    new LegacyUpgrade("Cherubim", "Dominions");
    new LegacyUpgrade("Kitten angels", "Dominions");
    new LegacyUpgrade("Seraphim", "Cherubim");
    new LegacyUpgrade("God", "Seraphim");
    new LegacyUpgrade("Belphegor", "Twin Gates of Transcendence");
    new LegacyUpgrade("Mammon", "Belphegor");
    new LegacyUpgrade("Abaddon", "Mammon");
    new LegacyUpgrade("Satan", "Abaddon");
    new LegacyUpgrade("Asmodeus", "Satan");
    new LegacyUpgrade("Beelzebub", "Asmodeus");
    new LegacyUpgrade("Lucifer", "Beelzebub");
    new LegacyUpgrade("Synergies Vol. I", "Dominions", "Satan");
    new LegacyUpgrade("Synergies Vol. II", "Synergies Vol. I", "Seraphim", "Beelzebub");
    new LegacyUpgrade("Chimera", "Synergies Vol. II", "God", "Lucifer");

    new LegacyUpgrade("Persistent memory", "Legacy");
    new LegacyUpgrade("Permanent upgrade slot I", "Legacy");
    new LegacyUpgrade("Permanent upgrade slot II", "Permanent upgrade slot I");
    new LegacyUpgrade("Permanent upgrade slot III", "Permanent upgrade slot II");
    new LegacyUpgrade("Permanent upgrade slot IV", "Permanent upgrade slot III");
    new LegacyUpgrade("Permanent upgrade slot V", "Permanent upgrade slot IV");
    new LegacyUpgrade("Inspired checklist", "Persistent memory" , "Permanent upgrade slot IV");
    new LegacyUpgrade("Genius accounting", "Inspired checklist");

    new LegacyUpgrade("Heavenly luck", "Legacy");
    new LegacyUpgrade("Lasting fortune", "Heavenly luck");
    new LegacyUpgrade("Decisive fate", "Lasting fortune");
    new LegacyUpgrade("Golden switch", "Heavenly luck");
    new LegacyUpgrade("Lucky digit", "Heavenly luck");
    new LegacyUpgrade("Lucky number", "Lucky digit", "Lasting fortune");
    new LegacyUpgrade("Lucky payout", "Lucky number", "Decisive fate");
    new LegacyUpgrade("Residual luck", "Golden switch");
    new LegacyUpgrade("Golden cookie alert sound", "Golden switch", "Decisive fate");
    new LegacyUpgrade("Divine discount", "Decisive fate");
    new LegacyUpgrade("Divine sales", "Decisive fate");
    new LegacyUpgrade("Divine bakeries", "Divine discount", "Divine sales");
    new LegacyUpgrade("Distilled essence of redoubled luck", "Divine bakeries", "Residual luck");
    new LegacyUpgrade("Shimmering veil", "Distilled essence of redoubled luck");
    new LegacyUpgrade("Cosmic beginner's luck", "Shimmering veil");
    new LegacyUpgrade("Reinforced membrane", "Shimmering veil");

    new LegacyUpgrade("Heavenly cookies", "Legacy");
    new LegacyUpgrade("Tin of british tea biscuits", "Heavenly cookies");
    new LegacyUpgrade("Box of macarons", "Heavenly cookies");
    new LegacyUpgrade("Box of brand biscuits", "Heavenly cookies");
    new LegacyUpgrade("Tin of butter cookies", "Heavenly cookies");
    new LegacyUpgrade("Starter kit", "Tin of british tea biscuits", "Box of macarons",  "Box of brand biscuits", "Tin of butter cookies");
    new LegacyUpgrade("Starter kitchen", "Starter kit");
    new LegacyUpgrade("Unholy bait", "Starter kitchen");
    new LegacyUpgrade("Elder spice", "Unholy bait");
    new LegacyUpgrade("Sacrilegious corruption", "Unholy bait");
    new LegacyUpgrade("Wrinkly cookies", "Sacrilegious corruption", "Elder spice");
    new LegacyUpgrade("Stevia Caelestis", "Wrinkly cookies");
    new LegacyUpgrade("Sugar baking", "Stevia Caelestis");
    new LegacyUpgrade("Sugar crystal cookies", "Sugar baking");
    new LegacyUpgrade("Box of maybe cookies", "Sugar crystal cookies");
    new LegacyUpgrade("Box of not cookies", "Sugar crystal cookies");
    new LegacyUpgrade("Box of pastries", "Sugar crystal cookies");
    //endregion
    //region Cursor
    new BuildingUpgradeWithAchievement("Reinforced index finger", new BuildingRequirement(Game.Objects.Cursor, 1), "Click");
    new BuildingUpgradeWithAchievement("Carpal tunnel prevention cream", new BuildingRequirement(Game.Objects.Cursor, 1), "Click");
    new BuildingUpgrade("Ambidextrous", new BuildingRequirement(Game.Objects.Cursor, 10));
    new BuildingUpgrade("Thousand fingers", new BuildingRequirement(Game.Objects.Cursor, 25));
    new BuildingUpgradeWithAchievement("Million fingers", new BuildingRequirement(Game.Objects.Cursor, 50), "Mouse wheel");
    new BuildingUpgradeWithAchievement("Billion fingers", new BuildingRequirement(Game.Objects.Cursor, 100), "Of Mice and Men");
    new BuildingUpgrade("Trillion fingers", new BuildingRequirement(Game.Objects.Cursor, 150));
    new BuildingUpgradeWithAchievement("Quadrillion fingers", new BuildingRequirement(Game.Objects.Cursor, 200), "The Digital");
    new BuildingUpgrade("Quintillion fingers", new BuildingRequirement(Game.Objects.Cursor, 250));
    new BuildingUpgradeWithAchievement("Sextillion fingers", new BuildingRequirement(Game.Objects.Cursor, 300), "Extreme polydactyly");
    new BuildingUpgrade("Septillion fingers", new BuildingRequirement(Game.Objects.Cursor, 350));
    new BuildingUpgradeWithAchievement("Octillion fingers", new BuildingRequirement(Game.Objects.Cursor, 400), "Dr. T");
    new BuildingUpgrade("Nonillion fingers", new BuildingRequirement(Game.Objects.Cursor, 450));
    //endregion
    //region Grandma
    new BuildingUpgradeWithAchievement("Forwards from grandma", new BuildingRequirement(Game.Objects.Grandma, 1), "Grandma\'s cookies");
    new BuildingUpgrade("Steel-plated rolling pins", new BuildingRequirement(Game.Objects.Grandma, 5));
    new BuildingUpgrade("Lubricated dentures", new BuildingRequirement(Game.Objects.Grandma, 25));
    new BuildingUpgradeWithAchievement("Prune juice", new BuildingRequirement(Game.Objects.Grandma, 50), "Sloppy kisses");
    new BuildingUpgradeWithAchievement("Double-thick glasses", new BuildingRequirement(Game.Objects.Grandma, 100), "Retirement home");
    new BuildingUpgradeWithAchievement("Aging agents", new BuildingRequirement(Game.Objects.Grandma, 150), "Friend of the ancients");
    new BuildingUpgradeWithAchievement("Xtreme walkers", new BuildingRequirement(Game.Objects.Grandma, 200), "Ruler of the ancients");
    new BuildingUpgradeWithAchievement("The Unbridling", new BuildingRequirement(Game.Objects.Grandma, 250), "The old never bothered me anyway");
    new BuildingUpgradeWithAchievement("Reverse dementia", new BuildingRequirement(Game.Objects.Grandma, 300), "The agemaster");
    new BuildingUpgradeWithAchievement("Timeproof hair dyes", new BuildingRequirement(Game.Objects.Grandma, 350), "To oldly go");
    new BuildingUpgradeWithAchievement("Good manners", new BuildingRequirement(Game.Objects.Grandma, 400), "Aged well");
    new BuildingUpgradeWithAchievement("Generation degeneration", new BuildingRequirement(Game.Objects.Grandma, 450), "101st birthday");
    new BuildingUpgradeWithAchievement("Visits", new BuildingRequirement(Game.Objects.Grandma, 500), "Defense of the ancients");

    new BuildingUpgrade("Farmer grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects.Farm, 15));
    new BuildingUpgrade("Miner grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects.Mine, 15));
    new BuildingUpgrade("Worker grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects.Factory, 15));
    new BuildingUpgrade("Banker grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects.Bank, 15));
    new BuildingUpgrade("Priestess grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects.Temple, 15));
    new BuildingUpgrade("Witch grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects["Wizard tower"], 15));
    new BuildingUpgrade("Cosmic grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects.Shipment, 15));
    new BuildingUpgrade("Transmuted grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects["Alchemy lab"], 15));
    new BuildingUpgrade("Altered grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects.Portal, 15));
    new BuildingUpgrade("Grandmas\' grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects["Time machine"], 15));
    new BuildingUpgrade("Antigrandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects["Antimatter condenser"], 15));
    new BuildingUpgrade("Rainbow grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects.Prism, 15));
    new BuildingUpgrade("Lucky grandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects.Chancemaker, 15));
    new BuildingUpgrade("Metagrandmas", new BuildingRequirement(Game.Objects.Grandma, 1), new BuildingRequirement(Game.Objects["Fractal engine"], 15));
    //endregion
    //region Farm
    new BuildingUpgradeWithAchievement("Cheap hoes", new BuildingRequirement(Game.Objects.Farm, 1), "Bought the farm");
    new BuildingUpgrade("Fertilizer", new BuildingRequirement(Game.Objects.Farm, 5));
    new BuildingUpgrade("Cookie trees", new BuildingRequirement(Game.Objects.Farm, 25));
    new BuildingUpgradeWithAchievement("Genetically-modified cookies", new BuildingRequirement(Game.Objects.Farm, 50), "Reap what you sow");
    new BuildingUpgradeWithAchievement("Gingerbread scarecrows", new BuildingRequirement(Game.Objects.Farm, 100), "Farm ill");
    new BuildingUpgradeWithAchievement("Pulsar sprinklers", new BuildingRequirement(Game.Objects.Farm, 150), "Perfected agriculture");
    new BuildingUpgradeWithAchievement("Fudge fungus", new BuildingRequirement(Game.Objects.Farm, 200), "Homegrown");
    new BuildingUpgradeWithAchievement("Wheat triffids", new BuildingRequirement(Game.Objects.Farm, 250), "Gardener extraordinaire");
    new BuildingUpgradeWithAchievement("Humane pesticides", new BuildingRequirement(Game.Objects.Farm, 300), "Seedy business");
    new BuildingUpgradeWithAchievement("Barnstars", new BuildingRequirement(Game.Objects.Farm, 350), "You and the beanstalk");
    new BuildingUpgradeWithAchievement("Lindworms", new BuildingRequirement(Game.Objects.Farm, 400), "Harvest moon");
    new BuildingUpgradeWithAchievement("Global seed vault", new BuildingRequirement(Game.Objects.Farm, 450), "Make like a tree");
    new BuildingUpgradeWithAchievement("Reverse-veganism", new BuildingRequirement(Game.Objects.Farm, 500), "Sharpest tool in the shed");
    //endregion
    //region Mine
    new BuildingUpgradeWithAchievement("Sugar gas", new BuildingRequirement(Game.Objects.Mine, 1), "You know the drill");
    new BuildingUpgrade("Megadrill", new BuildingRequirement(Game.Objects.Mine, 5));
    new BuildingUpgrade("Ultradrill", new BuildingRequirement(Game.Objects.Mine, 25));
    new BuildingUpgradeWithAchievement("Ultimadrill", new BuildingRequirement(Game.Objects.Mine, 50), "Excavation site");
    new BuildingUpgradeWithAchievement("H-bomb mining", new BuildingRequirement(Game.Objects.Mine, 100), "Hollow the planet");
    new BuildingUpgradeWithAchievement("Coreforge", new BuildingRequirement(Game.Objects.Mine, 150), "Can you dig it");
    new BuildingUpgradeWithAchievement("Planetsplitters", new BuildingRequirement(Game.Objects.Mine, 200), "The center of the Earth");
    new BuildingUpgradeWithAchievement("Canola oil wells", new BuildingRequirement(Game.Objects.Mine, 250), "Tectonic ambassador");
    new BuildingUpgradeWithAchievement("Mole people", new BuildingRequirement(Game.Objects.Mine, 300), "Freak fracking");
    new BuildingUpgradeWithAchievement("Mine canaries", new BuildingRequirement(Game.Objects.Mine, 350), "Romancing the stone");
    new BuildingUpgradeWithAchievement("Bore again", new BuildingRequirement(Game.Objects.Mine, 400), "Mine?");
    new BuildingUpgradeWithAchievement("Air mining", new BuildingRequirement(Game.Objects.Mine, 450), "Cave story");
    new BuildingUpgradeWithAchievement("Caramel alloys", new BuildingRequirement(Game.Objects.Mine, 500), "Hey now, you're a rock");
    //endregion
    //region Factory
    new BuildingUpgradeWithAchievement("Sturdier conveyor belts", new BuildingRequirement(Game.Objects.Factory, 1), "Production chain");
    new BuildingUpgrade("Child labor", new BuildingRequirement(Game.Objects.Factory, 5));
    new BuildingUpgrade("Sweatshop", new BuildingRequirement(Game.Objects.Factory, 25));
    new BuildingUpgradeWithAchievement("Radium reactors", new BuildingRequirement(Game.Objects.Factory, 50), "Industrial revolution");
    new BuildingUpgradeWithAchievement("Recombobulators", new BuildingRequirement(Game.Objects.Factory, 100), "Global warming");
    new BuildingUpgradeWithAchievement("Deep-bake process", new BuildingRequirement(Game.Objects.Factory, 150), "Ultimate automation");
    new BuildingUpgradeWithAchievement("Cyborg workforce", new BuildingRequirement(Game.Objects.Factory, 200), "Technocracy");
    new BuildingUpgradeWithAchievement("78-hour days", new BuildingRequirement(Game.Objects.Factory, 250), "Rise of the machines");
    new BuildingUpgradeWithAchievement("Machine learning", new BuildingRequirement(Game.Objects.Factory, 300), "Modern times");
    new BuildingUpgradeWithAchievement("Brownie point system", new BuildingRequirement(Game.Objects.Factory, 350), "Ex machina");
    new BuildingUpgradeWithAchievement("\"Volunteer\" interns", new BuildingRequirement(Game.Objects.Factory, 400), "In full gear");
    new BuildingUpgradeWithAchievement("Behavioral reframing", new BuildingRequirement(Game.Objects.Factory, 450), "In-cog-neato");
    new BuildingUpgradeWithAchievement("The infinity engine", new BuildingRequirement(Game.Objects.Factory, 500), "Break the mold");
    //endregion
    //region Bank
    new BuildingUpgradeWithAchievement("Taller tellers", new BuildingRequirement(Game.Objects.Bank, 1), "Pretty penny");
    new BuildingUpgrade("Scissor-resistant credit cards", new BuildingRequirement(Game.Objects.Bank, 5));
    new BuildingUpgrade("Acid-proof vaults", new BuildingRequirement(Game.Objects.Bank, 25));
    new BuildingUpgradeWithAchievement("Chocolate coins", new BuildingRequirement(Game.Objects.Bank, 50), "Fit the bill");
    new BuildingUpgradeWithAchievement("Exponential interest rates", new BuildingRequirement(Game.Objects.Bank, 100), "A loan in the dark");
    new BuildingUpgradeWithAchievement("Financial zen", new BuildingRequirement(Game.Objects.Bank, 150), "Need for greed");
    new BuildingUpgradeWithAchievement("Way of the wallet", new BuildingRequirement(Game.Objects.Bank, 200), "It\'s the economy, stupid");
    new BuildingUpgradeWithAchievement("The stuff rationale", new BuildingRequirement(Game.Objects.Bank, 250), "Acquire currency");
    new BuildingUpgradeWithAchievement("Edible money", new BuildingRequirement(Game.Objects.Bank, 300), "The nerve of war");
    new BuildingUpgradeWithAchievement("Grand supercycles", new BuildingRequirement(Game.Objects.Bank, 350), "And I need it now");
    new BuildingUpgradeWithAchievement("Rules of acquisition", new BuildingRequirement(Game.Objects.Bank, 400), "Treacle tart economics");
    new BuildingUpgradeWithAchievement("Altruistic loop", new BuildingRequirement(Game.Objects.Bank, 450), "Save your breath because that's all you've got left");
    new BuildingUpgradeWithAchievement("Diminishing tax returns", new BuildingRequirement(Game.Objects.Bank, 500), "Get the show on, get paid");
    //endregion
    //region Temple
    new BuildingUpgradeWithAchievement("Golden idols", new BuildingRequirement(Game.Objects.Temple, 1), "Your time to shrine");
    new BuildingUpgrade("Sacrifices", new BuildingRequirement(Game.Objects.Temple, 5));
    new BuildingUpgrade("Delicious blessing", new BuildingRequirement(Game.Objects.Temple, 25));
    new BuildingUpgradeWithAchievement("Sun festival", new BuildingRequirement(Game.Objects.Temple, 50), "New-age cult");
    new BuildingUpgradeWithAchievement("Enlarged pantheon", new BuildingRequirement(Game.Objects.Temple, 100), "New-age cult");
    new BuildingUpgradeWithAchievement("Great Baker in the sky", new BuildingRequirement(Game.Objects.Temple, 150), "Organized religion");
    new BuildingUpgradeWithAchievement("Creation myth", new BuildingRequirement(Game.Objects.Temple, 200), "Fanaticism");
    new BuildingUpgradeWithAchievement("Theocracy", new BuildingRequirement(Game.Objects.Temple, 250), "Zealotry");
    new BuildingUpgradeWithAchievement("Sick rap prayers", new BuildingRequirement(Game.Objects.Temple, 300), "Wololo");
    new BuildingUpgradeWithAchievement("Psalm-reading", new BuildingRequirement(Game.Objects.Temple, 350), "Pray on the weak");
    new BuildingUpgradeWithAchievement("War of the gods", new BuildingRequirement(Game.Objects.Temple, 400), "Holy cookies, grandma!");
    new BuildingUpgradeWithAchievement("A novel idea", new BuildingRequirement(Game.Objects.Temple, 450), "Vengeful and almighty");
    new BuildingUpgradeWithAchievement("Apparitions", new BuildingRequirement(Game.Objects.Temple, 500), "My world's on fire, how about yours");
    //endregion
    //region Wizard tower
    new BuildingUpgradeWithAchievement("Pointier hats", new BuildingRequirement(Game.Objects["Wizard tower"], 1), "Bewitched");
    new BuildingUpgrade("Beardlier beards", new BuildingRequirement(Game.Objects["Wizard tower"], 5));
    new BuildingUpgrade("Ancient grimoires", new BuildingRequirement(Game.Objects["Wizard tower"], 25));
    new BuildingUpgradeWithAchievement("Kitchen curses", new BuildingRequirement(Game.Objects["Wizard tower"], 50), "The sorcerer\'s apprentice");
    new BuildingUpgradeWithAchievement("School of sorcery", new BuildingRequirement(Game.Objects["Wizard tower"], 100), "Charms and enchantments");
    new BuildingUpgradeWithAchievement("Dark formulas", new BuildingRequirement(Game.Objects["Wizard tower"], 150), "Curses and maledictions");
    new BuildingUpgradeWithAchievement("Cookiemancy", new BuildingRequirement(Game.Objects["Wizard tower"], 200), "Magic kingdom");
    new BuildingUpgradeWithAchievement("Rabbit trick", new BuildingRequirement(Game.Objects["Wizard tower"], 250), "The wizarding world");
    new BuildingUpgradeWithAchievement("Deluxe tailored wands", new BuildingRequirement(Game.Objects["Wizard tower"], 300), "And now for my next trick, I'll need a volunteer from the" +
      " audience");
    new BuildingUpgradeWithAchievement("Immobile spellcasting", new BuildingRequirement(Game.Objects["Wizard tower"], 350), "It's a kind of magic");
    new BuildingUpgradeWithAchievement("Electricity", new BuildingRequirement(Game.Objects["Wizard tower"], 400), "The Prestige");
    new BuildingUpgradeWithAchievement("Spelling bees", new BuildingRequirement(Game.Objects["Wizard tower"], 450), "Spell it out for you");
    new BuildingUpgradeWithAchievement("Wizard basements", new BuildingRequirement(Game.Objects["Wizard tower"], 500), "The meteor men beg to differ");
    //endregion
    //region Shipment
    new BuildingUpgradeWithAchievement("Vanilla nebulae", new BuildingRequirement(Game.Objects.Shipment, 1), "Expedition");
    new BuildingUpgrade("Wormholes", new BuildingRequirement(Game.Objects.Shipment, 5));
    new BuildingUpgrade("Frequent flyer", new BuildingRequirement(Game.Objects.Shipment, 25));
    new BuildingUpgradeWithAchievement("Warp drive", new BuildingRequirement(Game.Objects.Shipment, 50), "Galactic highway");
    new BuildingUpgradeWithAchievement("Chocolate monoliths", new BuildingRequirement(Game.Objects.Shipment, 100), "Far far away");
    new BuildingUpgradeWithAchievement("Generation ship", new BuildingRequirement(Game.Objects.Shipment, 150), "Type II civilization");
    new BuildingUpgradeWithAchievement("Dyson sphere", new BuildingRequirement(Game.Objects.Shipment, 200), "We come in peace");
    new BuildingUpgradeWithAchievement("The final frontier", new BuildingRequirement(Game.Objects.Shipment, 250), "Parsec-masher");
    new BuildingUpgradeWithAchievement("Autopilot", new BuildingRequirement(Game.Objects.Shipment, 300), "It's not delivery");
    new BuildingUpgradeWithAchievement("Restaurants at the end of the universe", new BuildingRequirement(Game.Objects.Shipment, 350), "Make it so");
    new BuildingUpgradeWithAchievement("Universal alphabet", new BuildingRequirement(Game.Objects.Shipment, 400), "That's just peanuts to space");
    new BuildingUpgradeWithAchievement("Toroid universe", new BuildingRequirement(Game.Objects.Shipment, 450), "Space space space space space");
    new BuildingUpgradeWithAchievement("Prime directive", new BuildingRequirement(Game.Objects.Shipment, 500), "Only shooting stars");
    //endregion
    //region Alchemy lab
    new BuildingUpgradeWithAchievement("Antimony", new BuildingRequirement(Game.Objects["Alchemy lab"], 1), "Transmutation");
    new BuildingUpgrade("Essence of dough", new BuildingRequirement(Game.Objects["Alchemy lab"], 5));
    new BuildingUpgrade("True chocolate", new BuildingRequirement(Game.Objects["Alchemy lab"], 25));
    new BuildingUpgradeWithAchievement("Ambrosia", new BuildingRequirement(Game.Objects["Alchemy lab"], 50), "Transmogrification");
    new BuildingUpgradeWithAchievement("Aqua crustulae", new BuildingRequirement(Game.Objects["Alchemy lab"], 100), "Gold member");
    new BuildingUpgradeWithAchievement("Origin crucible", new BuildingRequirement(Game.Objects["Alchemy lab"], 150), "Gild wars");
    new BuildingUpgradeWithAchievement("Theory of atomic fluidity", new BuildingRequirement(Game.Objects["Alchemy lab"], 200), "The secrets of the universe");
    new BuildingUpgradeWithAchievement("Beige goo", new BuildingRequirement(Game.Objects["Alchemy lab"], 250), "The work of a lifetime");
    new BuildingUpgradeWithAchievement("The advent of chemistry", new BuildingRequirement(Game.Objects["Alchemy lab"], 300), "Gold, Jerry! Gold!");
    new BuildingUpgradeWithAchievement("On second thought", new BuildingRequirement(Game.Objects["Alchemy lab"], 350), "All that glitters is gold");
    new BuildingUpgradeWithAchievement("Public betterment", new BuildingRequirement(Game.Objects["Alchemy lab"], 400), "Worth its weight in lead");
    new BuildingUpgradeWithAchievement("Hermetic reconciliation", new BuildingRequirement(Game.Objects["Alchemy lab"], 450), "Don't get used to yourself, you're gonna have to change");
    new BuildingUpgradeWithAchievement("Chromatic cycling", new BuildingRequirement(Game.Objects["Alchemy lab"], 500), "We could all use a little change");
    //endregion
    //region Portal
    new BuildingUpgradeWithAchievement("Ancient tablet", new BuildingRequirement(Game.Objects.Portal, 1), "A whole new world");
    new BuildingUpgrade("Insane oatling workers", new BuildingRequirement(Game.Objects.Portal, 5));
    new BuildingUpgrade("Soul bond", new BuildingRequirement(Game.Objects.Portal, 25));
    new BuildingUpgradeWithAchievement("Sanity dance", new BuildingRequirement(Game.Objects.Portal, 50), "Now you\'re thinking");
    new BuildingUpgradeWithAchievement("Brane transplant", new BuildingRequirement(Game.Objects.Portal, 100), "Dimensional shift");
    new BuildingUpgradeWithAchievement("Deity-sized portals", new BuildingRequirement(Game.Objects.Portal, 150), "Brain-split");
    new BuildingUpgradeWithAchievement("End of times back-up plan", new BuildingRequirement(Game.Objects.Portal, 200), "Realm of the Mad God");
    new BuildingUpgradeWithAchievement("Maddening chants", new BuildingRequirement(Game.Objects.Portal, 250), "A place lost in time");
    new BuildingUpgradeWithAchievement("The real world", new BuildingRequirement(Game.Objects.Portal, 300), "Forbidden zone");
    new BuildingUpgradeWithAchievement("Dimensional garbage gulper", new BuildingRequirement(Game.Objects.Portal, 350), "H̸̷͓̳̳̯̟͕̟͍͍̣͡ḛ̢̦̰̺̮̝͖͖̘̪͉͘͡" +
      " ̠̦͕̤̪̝̥̰̠̫̖̣͙̬͘ͅC̨̦̺̩̲̥͉̭͚̜̻̝̣̼͙̮̯̪o̴̡͇̘͎̞̲͇̦̲͞͡m̸̩̺̝̣̹̱͚̬̥̫̳̼̞̘̯͘ͅẹ͇̺̜́̕͢s̶̙̟̱̥̮̯̰̦͓͇͖͖̝͘͘͞");
    new BuildingUpgradeWithAchievement("Embedded microportals", new BuildingRequirement(Game.Objects.Portal, 400), "What happens in the vortex stays in the vortex");
    new BuildingUpgradeWithAchievement("His advent", new BuildingRequirement(Game.Objects.Portal, 450), "Objects in the mirror dimension are closer than they appear");
    new BuildingUpgradeWithAchievement("Domestic rifts", new BuildingRequirement(Game.Objects.Portal, 500), "Your brain gets smart but your head gets dumb");
    //endregion
    //region Time machine
    new BuildingUpgradeWithAchievement("Flux capacitors", new BuildingRequirement(Game.Objects["Time machine"], 1), "Time warp");
    new BuildingUpgrade("Time paradox resolver", new BuildingRequirement(Game.Objects["Time machine"], 5));
    new BuildingUpgrade("Quantum conundrum", new BuildingRequirement(Game.Objects["Time machine"], 25));
    new BuildingUpgradeWithAchievement("Causality enforcer", new BuildingRequirement(Game.Objects["Time machine"], 50), "Alternate timeline");
    new BuildingUpgradeWithAchievement("Yestermorrow comparators", new BuildingRequirement(Game.Objects["Time machine"], 100), "Rewriting history");
    new BuildingUpgradeWithAchievement("Far future enactment", new BuildingRequirement(Game.Objects["Time machine"], 150), "Time duke");
    new BuildingUpgradeWithAchievement("Great loop hypothesis", new BuildingRequirement(Game.Objects["Time machine"], 200), "Forever and ever");
    new BuildingUpgradeWithAchievement("Cookietopian moments of maybe", new BuildingRequirement(Game.Objects["Time machine"], 250), "Heat death");
    new BuildingUpgradeWithAchievement("Second seconds", new BuildingRequirement(Game.Objects["Time machine"], 300), "cookie clicker forever and forever a hundred years cookie clicker," +
      " all day long forever, forever a hundred times, over and over cookie clicker adventures dot com");
    new BuildingUpgradeWithAchievement("Additional clock hands", new BuildingRequirement(Game.Objects["Time machine"], 350), "Way back then");
    new BuildingUpgradeWithAchievement("Nostalgia", new BuildingRequirement(Game.Objects["Time machine"], 400), "Invited to yesterday's party");
    new BuildingUpgradeWithAchievement("Split seconds", new BuildingRequirement(Game.Objects["Time machine"], 450), "Groundhog day");
    new BuildingUpgradeWithAchievement("Patience abolished", new BuildingRequirement(Game.Objects["Time machine"], 500), "The years start coming");
    //endregion
    //region Antimatter condensers
    new BuildingUpgradeWithAchievement("Sugar bosons", new BuildingRequirement(Game.Objects["Antimatter condenser"], 1), "Antibatter");
    new BuildingUpgrade("String theory", new BuildingRequirement(Game.Objects["Antimatter condenser"], 5));
    new BuildingUpgrade("Large macaron collider", new BuildingRequirement(Game.Objects["Antimatter condenser"], 25));
    new BuildingUpgradeWithAchievement("Big bang bake", new BuildingRequirement(Game.Objects["Antimatter condenser"], 50), "Quirky quarks");
    new BuildingUpgradeWithAchievement("Reverse cyclotrons", new BuildingRequirement(Game.Objects["Antimatter condenser"], 100), "It does matter!");
    new BuildingUpgradeWithAchievement("Nanocosmics", new BuildingRequirement(Game.Objects["Antimatter condenser"], 150), "Molecular maestro");
    new BuildingUpgradeWithAchievement("The Pulse", new BuildingRequirement(Game.Objects["Antimatter condenser"], 200), "Walk the planck");
    new BuildingUpgradeWithAchievement("Some other super-tiny fundamental particle? Probably?", new BuildingRequirement(Game.Objects["Antimatter condenser"], 250), "Microcosm");
    new BuildingUpgradeWithAchievement("Quantum comb", new BuildingRequirement(Game.Objects["Antimatter condenser"], 300), "Scientists baffled everywhere");
    new BuildingUpgradeWithAchievement("Baking Nobel prize", new BuildingRequirement(Game.Objects["Antimatter condenser"], 350), "Exotic matter");
    new BuildingUpgradeWithAchievement("The definite molecule", new BuildingRequirement(Game.Objects["Antimatter condenser"], 400), "Downsizing");
    new BuildingUpgradeWithAchievement("Flavor itself", new BuildingRequirement(Game.Objects["Antimatter condenser"], 450), "A matter of perspective");
    new BuildingUpgradeWithAchievement("Delicious pull", new BuildingRequirement(Game.Objects["Antimatter condenser"], 500), "What a concept");
    //endregion
    //region Prisms
    new BuildingUpgradeWithAchievement("Gem polish", new BuildingRequirement(Game.Objects.Prism, 1), "Lone photon");
    new BuildingUpgrade("9th color", new BuildingRequirement(Game.Objects.Prism, 5));
    new BuildingUpgrade("Chocolate light", new BuildingRequirement(Game.Objects.Prism, 25));
    new BuildingUpgradeWithAchievement("Grainbow", new BuildingRequirement(Game.Objects.Prism, 50), "Dazzling glimmer");
    new BuildingUpgradeWithAchievement("Pure cosmic light", new BuildingRequirement(Game.Objects.Prism, 100), "Blinding flash");
    new BuildingUpgradeWithAchievement("Glow-in-the-dark", new BuildingRequirement(Game.Objects.Prism, 150), "Unending glow");
    new BuildingUpgradeWithAchievement("Lux sanctorum", new BuildingRequirement(Game.Objects.Prism, 200), "Rise and shine");
    new BuildingUpgradeWithAchievement("Reverse shadows", new BuildingRequirement(Game.Objects.Prism, 250), "Bright future");
    new BuildingUpgradeWithAchievement("Crystal mirrors", new BuildingRequirement(Game.Objects.Prism, 300), "Harmony of the spheres");
    new BuildingUpgradeWithAchievement("Reverse theory of light", new BuildingRequirement(Game.Objects.Prism, 350), "At the end of the tunnel");
    new BuildingUpgradeWithAchievement("Light capture measures", new BuildingRequirement(Game.Objects.Prism, 400), "My eyes");
    new BuildingUpgradeWithAchievement("Light speed limit", new BuildingRequirement(Game.Objects.Prism, 450), "Optical illusion");
    new BuildingUpgradeWithAchievement("Occam's laser", new BuildingRequirement(Game.Objects.Prism, 500), "You'll never shine if you don't glow");
    //endregion
    //region Chancemaker
    new BuildingUpgradeWithAchievement("Your lucky cookie", new BuildingRequirement(Game.Objects.Chancemaker, 1), "Lucked out");
    new BuildingUpgrade("\"All Bets Are Off\" magic coin", new BuildingRequirement(Game.Objects.Chancemaker, 5));
    new BuildingUpgrade("Winning lottery ticket", new BuildingRequirement(Game.Objects.Chancemaker, 25));
    new BuildingUpgradeWithAchievement("Four-leaf clover field", new BuildingRequirement(Game.Objects.Chancemaker, 50), "What are the odds");
    new BuildingUpgradeWithAchievement("A recipe book about books", new BuildingRequirement(Game.Objects.Chancemaker, 100), "Grandma needs a new pair of shoes");
    new BuildingUpgradeWithAchievement("Leprechaun village", new BuildingRequirement(Game.Objects.Chancemaker, 150), "Million to one shot, doc");
    new BuildingUpgradeWithAchievement("Improbability drive", new BuildingRequirement(Game.Objects.Chancemaker, 200), "As luck would have it");
    new BuildingUpgradeWithAchievement("Antisuperstistronics", new BuildingRequirement(Game.Objects.Chancemaker, 250), "Ever in your favor");
    new BuildingUpgradeWithAchievement("Bunnypedes", new BuildingRequirement(Game.Objects.Chancemaker, 300), "Be a lady");
    new BuildingUpgradeWithAchievement("Revised probabilistics", new BuildingRequirement(Game.Objects.Chancemaker, 350), "Dicey business");
    new BuildingUpgradeWithAchievement("0-sided dice", new BuildingRequirement(Game.Objects.Chancemaker, 400), "Maybe a chance in hell, actually");
    new BuildingUpgradeWithAchievement("A touch of determinism", new BuildingRequirement(Game.Objects.Chancemaker, 450), "Jackpot");
    new BuildingUpgradeWithAchievement("On a streak", new BuildingRequirement(Game.Objects.Chancemaker, 500), "You'll never know if you don't go");
    //endregion
    //region Fractal Engine
    new BuildingUpgradeWithAchievement("Metabakeries", new BuildingRequirement(Game.Objects["Fractal engine"], 1), "Self-contained");
    new BuildingUpgrade("Mandelbrown sugar", new BuildingRequirement(Game.Objects["Fractal engine"], 5));
    new BuildingUpgrade("Fractoids", new BuildingRequirement(Game.Objects["Fractal engine"], 25));
    new BuildingUpgradeWithAchievement("Nested universe theory", new BuildingRequirement(Game.Objects["Fractal engine"], 50), "Threw you for a loop");
    new BuildingUpgradeWithAchievement("Menger sponge cake", new BuildingRequirement(Game.Objects["Fractal engine"], 100), "The sum of its parts");
    new BuildingUpgradeWithAchievement("One particularly good-humored cow", new BuildingRequirement(Game.Objects["Fractal engine"], 150), "Bears repeating");
    new BuildingUpgradeWithAchievement("Chocolate ouroboros", new BuildingRequirement(Game.Objects["Fractal engine"], 200), "More of the same");
    new BuildingUpgradeWithAchievement("Nested", new BuildingRequirement(Game.Objects["Fractal engine"], 250), "Last recurse");
    new BuildingUpgradeWithAchievement("Space-filling fibers", new BuildingRequirement(Game.Objects["Fractal engine"], 300), "Out of one, many");
    new BuildingUpgradeWithAchievement("Endless book of prose", new BuildingRequirement(Game.Objects["Fractal engine"], 350), "An example of recursion");
    new BuildingUpgradeWithAchievement("The set of all sets", new BuildingRequirement(Game.Objects["Fractal engine"], 400), "For more information on this achievement, please refer to its title");
    new BuildingUpgradeWithAchievement("This upgrade", new BuildingRequirement(Game.Objects["Fractal engine"], 450), "I'm so meta, even this achievement");
    new BuildingUpgradeWithAchievement("A box", new BuildingRequirement(Game.Objects["Fractal engine"], 500), "Never get bored");
    //endregion
    //TODO: Heralds Upgrade
    //region Heavenly Chips Power
    new HeavenlyChipUpgrade("Heavenly chip secret", 5);
    new HeavenlyChipUpgradeWithUpgradeRequirement("Heavenly cookie stand", 20, "Heavenly chip secret");
    new HeavenlyChipUpgradeWithUpgradeRequirement("Heavenly bakery", 25, "Heavenly cookie stand");
    new HeavenlyChipUpgradeWithUpgradeRequirement("Heavenly confectionery", 25, "Heavenly bakery");
    new HeavenlyChipUpgradeWithUpgradeRequirement("Heavenly key", 25, "Heavenly confectionery");
    //endregion
    //region Bingo Center
    new ResearchUpgrade("Bingo center/Research facility", [new BuildingRequirement(Game.Objects.Grandma, 7)]);
    new ResearchCookieUpgrade("Specialized chocolate chips", "Bingo center/Research facility", 1);
    new ResearchCookieUpgrade("Designer cocoa beans","Specialized chocolate chips", 2);
    new ResearchUpgrade("Ritual rolling pins", [], "Designer cocoa beans");
    new ResearchCookieUpgrade("Underworld ovens", "Ritual rolling pins", 3);
    new ResearchUpgrade("One mind", [],"Underworld ovens");
    new ResearchCookieUpgrade("Exotic nuts", "One mind", 4);
    new ResearchUpgrade("Communal brainsweep", [], "Exotic nuts");
    new ResearchCookieUpgrade("Arcane sugar", "Communal brainsweep", 5);
    new ResearchUpgrade("Elder Pact", [], "Arcane sugar");
    //endregion
    //region Kitten
    new KittenUpgrade("Kitten helpers", 0.52);
    new KittenUpgrade("Kitten workers", 1);
    new KittenUpgrade("Kitten engineers", 2);
    new KittenUpgrade("Kitten overseers", 3);
    new KittenUpgrade("Kitten managers", 4);
    new KittenUpgrade("Kitten accountants", 5);
    new KittenUpgrade("Kitten specialists", 6);
    new KittenUpgrade("Kitten experts", 7);
    new KittenUpgrade("Kitten consultants", 8);
    new KittenUpgrade("Kitten assistants to the regional manager", 9);
    new KittenUpgrade("Kitten marketeers", 10);
    new KittenUpgrade("Kitten analysts", 11);
    //endregion
    //region Mouse
    new MouseUpgrade("Plastic mouse");
    new MouseUpgrade("Iron mouse");
    new MouseUpgrade("Titanium mouse");
    new MouseUpgrade("Adamantium mouse");
    new MouseUpgrade("Unobtainium mouse");
    new MouseUpgrade("Eludium mouse");
    new MouseUpgrade("Wishalloy mouse");
    new MouseUpgrade("Fantasteel mouse");
    new MouseUpgrade("Nevercrack mouse");
    new MouseUpgrade("Armythril mouse");
    new MouseUpgrade("Technobsidian mouse");
    new MouseUpgrade("Plasmarble mouse");
    //endregion
    //region Golden Cookie
    new GoldenCookieUpgrade("Lucky day");
    new GoldenCookieUpgrade("Serendipity");
    new GoldenCookieUpgrade("Get lucky");
    new GoldenCookieUpgrade("Golden goose egg");
    new GoldenCookieUpgrade("Green yeast digestives");
    //endregion
    //region Cookies
    new CookieUpgrade("Plain cookies");
    new CookieUpgrade("Sugar cookies");
    new CookieUpgrade("Oatmeal raisin cookies");
    new CookieUpgrade("Peanut butter cookies");
    new CookieUpgrade("Coconut cookies");
    new CookieUpgrade("Almond cookies");
    new CookieUpgrade("Hazelnut cookies");
    new CookieUpgrade("Walnut cookies");
    new CookieUpgrade("Cashew cookies");
    new CookieUpgrade("White chocolate cookies");
    new CookieUpgrade("Milk chocolate cookies");
    new CookieUpgrade("Macadamia nut cookies");
    new CookieUpgrade("Double-chip cookies");
    new CookieUpgrade("White chocolate macadamia nut cookies");
    new CookieUpgrade("All-chocolate cookies");
    new CookieUpgrade("Dark chocolate-coated cookies");
    new CookieUpgrade("White chocolate-coated cookies");
    new CookieUpgrade("Eclipse cookies");
    new CookieUpgrade("Zebra cookies");
    new CookieUpgrade("Snickerdoodles");
    new CookieUpgrade("Stroopwafels");
    new CookieUpgrade("Macaroons");
    new CookieUpgrade("Madeleines");
    new CookieUpgrade("Palmiers");
    new CookieUpgrade("Palets");
    new CookieUpgrade("Sabl&eacute;s");

    new CookieUpgradeWithUpgradeRequirement("Caramoas", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Sagalongs", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Shortfoils", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Win mints", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Fig gluttons", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Loreols", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Jaffa cakes", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Grease's cups", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Digits", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Lombardia cookies", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Bastenaken cookies", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Festivity loops", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Havabreaks", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Zilla wafers", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Dim Dams", "Box of brand biscuits");
    new CookieUpgradeWithUpgradeRequirement("Pokey", "Box of brand biscuits");

    new CookieUpgrade("Gingerbread men");
    new CookieUpgrade("Gingerbread trees");
    new CookieUpgrade("Pure black chocolate cookies");
    new CookieUpgrade("Pure white chocolate cookies");
    new CookieUpgrade("Ladyfingers");
    new CookieUpgrade("Tuiles");
    new CookieUpgrade("Chocolate-stuffed biscuits");
    new CookieUpgrade("Checker cookies");
    new CookieUpgrade("Butter cookies");
    new CookieUpgrade("Cream cookies");
    new CookieUpgrade("Gingersnaps");
    new CookieUpgrade("Cinnamon cookies");
    new CookieUpgrade("Vanity cookies");
    new CookieUpgrade("Cigars");
    new CookieUpgrade("Pinwheel cookies");
    new CookieUpgrade("Fudge squares");
    new CookieUpgrade("Shortbread biscuits");
    new CookieUpgrade("Millionaires' shortbreads");
    new CookieUpgrade("Caramel cookies");
    new CookieUpgrade("Pecan sandies");
    new CookieUpgrade("Moravian spice cookies");
    new CookieUpgrade("Anzac biscuits");
    new CookieUpgrade("Buttercakes");
    new CookieUpgrade("Ice cream sandwiches");
    new CookieUpgrade("Birthday cookie");
    new CookieUpgrade("Pink biscuits");
    new CookieUpgrade("Whole-grain cookies");
    new CookieUpgrade("Candy cookies");
    new CookieUpgrade("Big chip cookies");
    new CookieUpgrade("One chip cookies");
    new CookieUpgrade("Sprinkles cookies");
    new CookieUpgrade("Peanut butter blossoms");
    new CookieUpgrade("No-bake cookies");
    new CookieUpgrade("Florentines");
    new CookieUpgrade("Chocolate crinkles");
    new CookieUpgrade("Maple cookies");
    new CookieUpgrade("Persian rice cookies");
    new CookieUpgrade("Norwegian cookies");
    new CookieUpgrade("Crispy rice cookies");
    new CookieUpgrade("Ube cookies");
    new CookieUpgrade("Butterscotch cookies");
    new CookieUpgrade("Speculaas");
    new CookieUpgrade("Chocolate oatmeal cookies");
    new CookieUpgrade("Molasses cookies");
    new CookieUpgrade("Biscotti");
    new CookieUpgrade("Waffle cookies");
    new CookieUpgrade("Custard creams");
    new CookieUpgrade("Bourbon biscuits");
    new CookieUpgrade("Mini-cookies");
    new CookieUpgrade("Whoopie pies");
    new CookieUpgrade("Caramel wafer biscuits");
    new CookieUpgrade("Chocolate chip mocha cookies");
    new CookieUpgrade("Earl Grey cookies");
    new CookieUpgrade("Chai tea cookies");
    new CookieUpgrade("Corn syrup cookies");
    new CookieUpgrade("Icebox cookies");
    new CookieUpgrade("Graham crackers");
    new CookieUpgrade("Hardtack");
    new CookieUpgrade("Cornflake cookies");
    new CookieUpgrade("Tofu cookies");
    new CookieUpgrade("Gluten-free cookies");
    new CookieUpgrade("Russian bread cookies");
    new CookieUpgrade("Lebkuchen");
    new CookieUpgrade("Aachener Printen");
    new CookieUpgrade("Canistrelli");
    new CookieUpgrade("Nice biscuits");
    new CookieUpgrade("French pure butter cookies");
    new CookieUpgrade("Petit beurre");
    new CookieUpgrade("Nanaimo bars");
    new CookieUpgrade("Berger cookies");
    new CookieUpgrade("Chinsuko");
    new CookieUpgrade("Panda koala biscuits");
    new CookieUpgrade("Putri salju");
    new CookieUpgrade("Milk cookies");
    new CookieUpgrade("Kruidnoten");
    new CookieUpgrade("Marie biscuits");
    new CookieUpgrade("Meringue cookies");
    new CookieUpgrade("Yogurt cookies");
    new CookieUpgrade("Thumbprint cookies");
    new CookieUpgrade("Pizzelle");
    new CookieUpgrade("Granola cookies");
    new CookieUpgrade("Ricotta cookies");
    new CookieUpgrade("Roze koeken");
    new CookieUpgrade("Peanut butter cup cookies");
    new CookieUpgrade("Sesame cookies");
    new CookieUpgrade("Taiyaki");
    new CookieUpgrade("Vanillekipferl");
    new CookieUpgrade("Battenberg biscuits");
    new CookieUpgrade("Rosette cookies");
    new CookieUpgrade("Gangmakers");
    new CookieUpgrade("Welsh cookies");
    new CookieUpgrade("Raspberry cheesecake cookies");
    new CookieUpgrade("Bokkenpootjes");
    new CookieUpgrade("Fat rascals");
    new CookieUpgrade("Ischler cookies");
    new CookieUpgrade("Matcha cookies");

    new CookieUpgrade("Empire biscuits");
    new CookieUpgradeWithUpgradeRequirement("British tea biscuits", "Tin of british tea biscuits");
    new CookieUpgradeWithUpgradeRequirement("Chocolate british tea biscuits", "British tea biscuits");
    new CookieUpgradeWithUpgradeRequirement("Round british tea biscuits", "Chocolate british tea biscuits");
    new CookieUpgradeWithUpgradeRequirement("Round chocolate british tea biscuits", "Round british tea biscuits");
    new CookieUpgradeWithUpgradeRequirement("Round british tea biscuits with heart motif", "Round chocolate british tea biscuits");
    new CookieUpgradeWithUpgradeRequirement("Round chocolate british tea biscuits with heart motif", "Round british tea biscuits with heart motif");

    new CookieUpgradeWithUpgradeRequirement("Butter horseshoes", "Tin of butter cookies");
    new CookieUpgradeWithUpgradeRequirement("Butter pucks", "Tin of butter cookies");
    new CookieUpgradeWithUpgradeRequirement("Butter knots", "Tin of butter cookies");
    new CookieUpgradeWithUpgradeRequirement("Butter slabs", "Tin of butter cookies");
    new CookieUpgradeWithUpgradeRequirement("Butter swirls", "Tin of butter cookies");

    new CookieUpgradeWithUpgradeRequirement("Rose macarons", "Box of macarons");
    new CookieUpgradeWithUpgradeRequirement("Lemon macarons", "Box of macarons");
    new CookieUpgradeWithUpgradeRequirement("Chocolate macarons", "Box of macarons");
    new CookieUpgradeWithUpgradeRequirement("Pistachio macarons", "Box of macarons");
    new CookieUpgradeWithUpgradeRequirement("Hazelnut macarons", "Box of macarons");
    new CookieUpgradeWithUpgradeRequirement("Violet macarons", "Box of macarons");
    new CookieUpgradeWithUpgradeRequirement("Caramel macarons", "Box of macarons");
    new CookieUpgradeWithUpgradeRequirement("Licorice macarons", "Box of macarons");
    new CookieUpgradeWithUpgradeRequirement("Earl Grey macarons", "Box of macarons");

    new CookieUpgradeWithUpgradeRequirement("Cookie dough", "Box of maybe cookies");
    new CookieUpgradeWithUpgradeRequirement("Burnt cookie", "Box of maybe cookies");
    new CookieUpgradeWithUpgradeRequirement("A chocolate chip cookie but with the chips picked off for some reason", "Box of maybe cookies");
    new CookieUpgradeWithUpgradeRequirement("Flavor text cookie", "Box of maybe cookies");
    new CookieUpgradeWithUpgradeRequirement("High-definition cookie", "Box of maybe cookies");
    new CookieUpgradeWithUpgradeRequirement("Crackers", "Box of maybe cookies");

    new CookieUpgradeWithUpgradeRequirement("Toast", "Box of not cookies");
    new CookieUpgradeWithUpgradeRequirement("Peanut butter & jelly", "Box of not cookies");
    new CookieUpgradeWithUpgradeRequirement("Wookies", "Box of not cookies");
    new CookieUpgradeWithUpgradeRequirement("Cheeseburger", "Box of not cookies");
    new CookieUpgradeWithUpgradeRequirement("One lone chocolate chip", "Box of not cookies");
    new CookieUpgradeWithUpgradeRequirement("Pizza", "Box of not cookies");
    new CookieUpgradeWithUpgradeRequirement("Candy", "Box of not cookies");

    new CookieUpgradeWithUpgradeRequirement("Profiteroles", "Box of pastries");
    new CookieUpgradeWithUpgradeRequirement("Jelly donut", "Box of pastries");
    new CookieUpgradeWithUpgradeRequirement("Glazed donut", "Box of pastries");
    new CookieUpgradeWithUpgradeRequirement("Chocolate cake", "Box of pastries");
    new CookieUpgradeWithUpgradeRequirement("Strawberry cake", "Box of pastries");
    new CookieUpgradeWithUpgradeRequirement("Apple pie", "Box of pastries");
    new CookieUpgradeWithUpgradeRequirement("Lemon meringue pie", "Box of pastries");
    new CookieUpgradeWithUpgradeRequirement("Butter croissant", "Box of pastries");

    new CookieUpgradeWithUpgradeRequirement("Cookie crumbs", "Legacy");
    new CookieUpgradeWithUpgradeRequirement("Chocolate chip cookie", "Legacy");

    new CookieUpgrade("Milk chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(100));
    new CookieUpgrade("Dark chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(150));
    new CookieUpgrade("White chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(200));
    new CookieUpgrade("Ruby chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(250));
    new CookieUpgrade("Lavender chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(300));
    new CookieUpgrade("Synthetic chocolate green honey butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(350));
    new CookieUpgrade("Royal raspberry chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(400));
    new CookieUpgrade("Ultra-concentrated high-energy chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(450));
    new CookieUpgrade("Pure pitch-black chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(500));
    new CookieUpgrade("Cosmic chocolate butter biscuit", BuildingRequirement.generateRequirementsForAllBuildings(550));
    new CookieUpgrade("Butter biscuit (with butter)", BuildingRequirement.generateRequirementsForAllBuildings(600));
    //endregion
    //region Synergies
    new BuildingUpgradeWithUpgradeRequirement("Extra physics funding", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Bank, 15), new BuildingRequirement(Game.Objects["Antimatter condenser"], 15));
    new BuildingUpgradeWithUpgradeRequirement("Contracts from beyond", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Bank, 15), new BuildingRequirement(Game.Objects.Prism, 15));
    new BuildingUpgradeWithUpgradeRequirement("Quantum electronics", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Factory, 15), new BuildingRequirement(Game.Objects["Antimatter condenser"], 15));
    new BuildingUpgradeWithUpgradeRequirement("Infernal crops", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Farm, 15), new BuildingRequirement(Game.Objects.Portal, 15));
    new BuildingUpgradeWithUpgradeRequirement("Future almanacs", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Farm, 15), new BuildingRequirement(Game.Objects["Time machine"], 15));
    new BuildingUpgradeWithUpgradeRequirement("Primordial ores", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Mine, 15), new BuildingRequirement(Game.Objects["Alchemy lab"], 15));
    new BuildingUpgradeWithUpgradeRequirement("Gemmed talismans", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Mine, 15), new BuildingRequirement(Game.Objects.Chancemaker, 15));
    new BuildingUpgradeWithUpgradeRequirement("Fossil fuels", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Mine, 15), new BuildingRequirement(Game.Objects.Shipment, 15));
    new BuildingUpgradeWithUpgradeRequirement("Seismic magic", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Mine, 15), new BuildingRequirement(Game.Objects["Wizard tower"], 15));
    new BuildingUpgradeWithUpgradeRequirement("Recursive mirrors", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Prism, 15), new BuildingRequirement(Game.Objects["Fractal engine"], 15));
    new BuildingUpgradeWithUpgradeRequirement("Relativistic parsec-skipping", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Shipment, 15), new BuildingRequirement(Game.Objects["Time machine"], 15));
    new BuildingUpgradeWithUpgradeRequirement("Paganism", "Synergies Vol. I", new BuildingRequirement(Game.Objects.Temple, 15), new BuildingRequirement(Game.Objects.Portal, 15));
    new BuildingUpgradeWithUpgradeRequirement("Arcane knowledge", "Synergies Vol. I", new BuildingRequirement(Game.Objects["Wizard tower"], 15), new BuildingRequirement(Game.Objects["Alchemy lab"], 15));
    new BuildingUpgradeWithUpgradeRequirement("Light magic", "Synergies Vol. I", new BuildingRequirement(Game.Objects["Wizard tower"], 15), new BuildingRequirement(Game.Objects.Prism, 15));


    new BuildingUpgradeWithUpgradeRequirement("Chemical proficiency", "Synergies Vol. II", new BuildingRequirement(Game.Objects["Alchemy lab"], 75), new BuildingRequirement(Game.Objects["Antimatter condenser"], 75));
    new BuildingUpgradeWithUpgradeRequirement("Charm quarks", "Synergies Vol. II", new BuildingRequirement(Game.Objects["Antimatter condenser"], 75), new BuildingRequirement(Game.Objects.Chancemaker, 75));
    new BuildingUpgradeWithUpgradeRequirement("Gold fund", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Bank, 75), new BuildingRequirement(Game.Objects["Alchemy lab"], 75));
    new BuildingUpgradeWithUpgradeRequirement("Printing presses", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Bank, 75), new BuildingRequirement(Game.Objects.Factory, 75));
    new BuildingUpgradeWithUpgradeRequirement("Mice clicking mice", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Cursor, 75), new BuildingRequirement(Game.Objects["Fractal engine"], 75));
    new BuildingUpgradeWithUpgradeRequirement("Shipyards", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Factory, 75), new BuildingRequirement(Game.Objects.Shipment, 75));
    new BuildingUpgradeWithUpgradeRequirement("Temporal overclocking", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Factory, 75), new BuildingRequirement(Game.Objects["Time machine"], 75));
    new BuildingUpgradeWithUpgradeRequirement("Rain prayer", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Farm, 75), new BuildingRequirement(Game.Objects.Temple, 75));
    new BuildingUpgradeWithUpgradeRequirement("Asteroid mining", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Mine, 75), new BuildingRequirement(Game.Objects.Shipment, 75));
    new BuildingUpgradeWithUpgradeRequirement("Abysmal glimmer", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Prism, 75), new BuildingRequirement(Game.Objects.Portal, 75));
    new BuildingUpgradeWithUpgradeRequirement("Primeval glow", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Prism, 75), new BuildingRequirement(Game.Objects["Time machine"], 75));
    new BuildingUpgradeWithUpgradeRequirement("God particle", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Temple, 75), new BuildingRequirement(Game.Objects["Antimatter condenser"], 75));
    new BuildingUpgradeWithUpgradeRequirement("Mystical energies", "Synergies Vol. II", new BuildingRequirement(Game.Objects.Temple, 75), new BuildingRequirement(Game.Objects.Prism, 75));
    new BuildingUpgradeWithUpgradeRequirement("Magical botany", "Synergies Vol. II", new BuildingRequirement(Game.Objects["Wizard tower"], 75), new BuildingRequirement(Game.Objects.Farm, 75));
    //endregion
  }

  addAchievements(): void {
    //region Cursor
    new Achievement("Click", new BuildingRequirement(Game.Objects.Cursor, 1));
    new Achievement("Double-click", new BuildingRequirement(Game.Objects.Cursor, 2));
    new Achievement("Mouse wheel", new BuildingRequirement(Game.Objects.Cursor, 50));
    new Achievement("Of Mice and Men", new BuildingRequirement(Game.Objects.Cursor, 100));
    new Achievement("The Digital", new BuildingRequirement(Game.Objects.Cursor, 200));
    new Achievement("Extreme polydactyly", new BuildingRequirement(Game.Objects.Cursor, 300));
    new Achievement("Dr. T", new BuildingRequirement(Game.Objects.Cursor, 400));
    new Achievement("Thumbs, phalanges, metacarpals", new BuildingRequirement(Game.Objects.Cursor, 500));
    new Achievement("With her finger and her thumb", new BuildingRequirement(Game.Objects.Cursor, 600));
    new Achievement("Gotta hand it to you", new BuildingRequirement(Game.Objects.Cursor, 700));
    new Achievement("The devil's workshop", new BuildingRequirement(Game.Objects.Cursor, 800));
    //endregion
    //region Grandma
    new Achievement("Grandma\'s cookies", new BuildingRequirement(Game.Objects.Grandma, 1));
    new Achievement("Sloppy kisses", new BuildingRequirement(Game.Objects.Grandma, 50));
    new Achievement("Retirement home", new BuildingRequirement(Game.Objects.Grandma, 100));
    new Achievement("Friend of the ancients", new BuildingRequirement(Game.Objects.Grandma, 150));
    new Achievement("Ruler of the ancients", new BuildingRequirement(Game.Objects.Grandma, 200));
    new Achievement("The old never bothered me anyway", new BuildingRequirement(Game.Objects.Grandma, 250));
    new Achievement("The agemaster", new BuildingRequirement(Game.Objects.Grandma, 300));
    new Achievement("To oldly go", new BuildingRequirement(Game.Objects.Grandma, 350));
    new Achievement("Aged well", new BuildingRequirement(Game.Objects.Grandma, 400));
    new Achievement("101st birthday", new BuildingRequirement(Game.Objects.Grandma, 450));
    new Achievement("Defense of the ancients", new BuildingRequirement(Game.Objects.Grandma, 500));
    new Achievement("But wait 'til you get older", new BuildingRequirement(Game.Objects.Grandma, 550));
    new Achievement("Okay boomer", new BuildingRequirement(Game.Objects.Grandma, 600));
    new Achievement("Elder");
    //endregion
    //region Farm
    new Achievement("Bought the farm", new BuildingRequirement(Game.Objects.Farm, 1));
    new Achievement("Reap what you sow", new BuildingRequirement(Game.Objects.Farm, 50));
    new Achievement("Farm ill", new BuildingRequirement(Game.Objects.Farm, 100));
    new Achievement("Perfected agriculture", new BuildingRequirement(Game.Objects.Farm, 150));
    new Achievement("Homegrown", new BuildingRequirement(Game.Objects.Farm, 200));
    new Achievement("Gardener extraordinaire", new BuildingRequirement(Game.Objects.Farm, 250));
    new Achievement("Seedy business", new BuildingRequirement(Game.Objects.Farm, 300));
    new Achievement("You and the beanstalk", new BuildingRequirement(Game.Objects.Farm, 350));
    new Achievement("Harvest moon", new BuildingRequirement(Game.Objects.Farm, 400));
    new Achievement("Make like a tree", new BuildingRequirement(Game.Objects.Farm, 450));
    new Achievement("Sharpest tool in the shed", new BuildingRequirement(Game.Objects.Farm, 500));
    new Achievement("Overripe", new BuildingRequirement(Game.Objects.Farm, 550));
    new Achievement("In the green", new BuildingRequirement(Game.Objects.Farm, 600));
    //endregion
    //region Mine
    new Achievement("You know the drill", new BuildingRequirement(Game.Objects.Mine, 1));
    new Achievement("Excavation site", new BuildingRequirement(Game.Objects.Mine, 50));
    new Achievement("Hollow the planet", new BuildingRequirement(Game.Objects.Mine, 100));
    new Achievement("Can you dig it", new BuildingRequirement(Game.Objects.Mine, 150));
    new Achievement("The center of the Earth", new BuildingRequirement(Game.Objects.Mine, 200));
    new Achievement("Tectonic ambassador", new BuildingRequirement(Game.Objects.Mine, 250));
    new Achievement("Freak fracking", new BuildingRequirement(Game.Objects.Mine, 300));
    new Achievement("Romancing the stone", new BuildingRequirement(Game.Objects.Mine, 350));
    new Achievement("Mine?", new BuildingRequirement(Game.Objects.Mine, 400));
    new Achievement("Cave story", new BuildingRequirement(Game.Objects.Mine, 450));
    new Achievement("Hey now, you're a rock", new BuildingRequirement(Game.Objects.Mine, 500));
    new Achievement("Rock on", new BuildingRequirement(Game.Objects.Mine, 550));
    new Achievement("Mountain out of a molehill, but like in a good way", new BuildingRequirement(Game.Objects.Mine, 600));
    //endregion
    //region Factory
    new Achievement("Production chain", new BuildingRequirement(Game.Objects.Factory, 1));
    new Achievement("Industrial revolution", new BuildingRequirement(Game.Objects.Factory, 50));
    new Achievement("Global warming", new BuildingRequirement(Game.Objects.Factory, 100));
    new Achievement("Ultimate automation", new BuildingRequirement(Game.Objects.Factory, 150));
    new Achievement("Technocracy", new BuildingRequirement(Game.Objects.Factory, 200));
    new Achievement("Rise of the machines", new BuildingRequirement(Game.Objects.Factory, 250));
    new Achievement("Modern times", new BuildingRequirement(Game.Objects.Factory, 300));
    new Achievement("Ex machina", new BuildingRequirement(Game.Objects.Factory, 350));
    new Achievement("In full gear", new BuildingRequirement(Game.Objects.Factory, 400));
    new Achievement("In-cog-neato", new BuildingRequirement(Game.Objects.Factory, 450));
    new Achievement("Break the mold", new BuildingRequirement(Game.Objects.Factory, 500));
    new Achievement("Self-manmade man", new BuildingRequirement(Game.Objects.Factory, 550));
    new Achievement("The wheels of progress", new BuildingRequirement(Game.Objects.Factory, 600));
    //endregion
    //region Bank
    new Achievement("Pretty penny", new BuildingRequirement(Game.Objects.Bank, 1));
    new Achievement("Fit the bill", new BuildingRequirement(Game.Objects.Bank, 50));
    new Achievement("A loan in the dark", new BuildingRequirement(Game.Objects.Bank, 100));
    new Achievement("Need for greed", new BuildingRequirement(Game.Objects.Bank, 150));
    new Achievement("It's the economy, stupid", new BuildingRequirement(Game.Objects.Bank, 200));
    new Achievement("Acquire currency", new BuildingRequirement(Game.Objects.Bank, 250));
    new Achievement("The nerve of war", new BuildingRequirement(Game.Objects.Bank, 300));
    new Achievement("And I need it now", new BuildingRequirement(Game.Objects.Bank, 350));
    new Achievement("Treacle tart economics", new BuildingRequirement(Game.Objects.Bank, 400));
    new Achievement("Save your breath because that's all you've got left", new BuildingRequirement(Game.Objects.Bank, 450));
    new Achievement("Get the show on, get paid", new BuildingRequirement(Game.Objects.Bank, 500));
    new Achievement("Checks out", new BuildingRequirement(Game.Objects.Bank, 550));
    new Achievement("That's rich", new BuildingRequirement(Game.Objects.Bank, 600));
    //endregion
    //region Temple
    new Achievement("Your time to shrine", new BuildingRequirement(Game.Objects.Temple, 1));
    new Achievement("Shady sect", new BuildingRequirement(Game.Objects.Temple, 50));
    new Achievement("New-age cult", new BuildingRequirement(Game.Objects.Temple, 100));
    new Achievement("Organized religion", new BuildingRequirement(Game.Objects.Temple, 150));
    new Achievement("Fanaticism", new BuildingRequirement(Game.Objects.Temple, 200));
    new Achievement("Zealotry", new BuildingRequirement(Game.Objects.Temple, 250));
    new Achievement("Wololo", new BuildingRequirement(Game.Objects.Temple, 300));
    new Achievement("Pray on the weak", new BuildingRequirement(Game.Objects.Temple, 350));
    new Achievement("Holy cookies, grandma!", new BuildingRequirement(Game.Objects.Temple, 400));
    new Achievement("Vengeful and almighty", new BuildingRequirement(Game.Objects.Temple, 450));
    new Achievement("My world's on fire, how about yours", new BuildingRequirement(Game.Objects.Temple, 500));
    new Achievement("Living on a prayer", new BuildingRequirement(Game.Objects.Temple, 550));
    new Achievement("Preaches and cream", new BuildingRequirement(Game.Objects.Temple, 600));
    //endregion
    //region Wizard Tower
    new Achievement("Bewitched", new BuildingRequirement(Game.Objects["Wizard tower"], 1));
    new Achievement("The sorcerer's apprentice", new BuildingRequirement(Game.Objects["Wizard tower"], 50));
    new Achievement("Charms and enchantments", new BuildingRequirement(Game.Objects["Wizard tower"], 100));
    new Achievement("Curses and maledictions", new BuildingRequirement(Game.Objects["Wizard tower"], 150));
    new Achievement("Magic kingdom", new BuildingRequirement(Game.Objects["Wizard tower"], 200));
    new Achievement("The wizarding world", new BuildingRequirement(Game.Objects["Wizard tower"], 250));
    new Achievement("And now for my next trick, I'll need a volunteer from the audience", new BuildingRequirement(Game.Objects["Wizard tower"], 300));
    new Achievement("It's a kind of magic", new BuildingRequirement(Game.Objects["Wizard tower"], 350));
    new Achievement("The Prestige", new BuildingRequirement(Game.Objects["Wizard tower"], 400));
    new Achievement("Spell it out for you", new BuildingRequirement(Game.Objects["Wizard tower"], 450));
    new Achievement("The meteor men beg to differ", new BuildingRequirement(Game.Objects["Wizard tower"], 500));
    new Achievement("Higitus figitus migitus mum", new BuildingRequirement(Game.Objects["Wizard tower"], 550));
    new Achievement("Magic thinking", new BuildingRequirement(Game.Objects["Wizard tower"], 600));
    //endregion
    //region Shipment
    new Achievement("Expedition", new BuildingRequirement(Game.Objects.Shipment, 1));
    new Achievement("Galactic highway", new BuildingRequirement(Game.Objects.Shipment, 50));
    new Achievement("Far far away", new BuildingRequirement(Game.Objects.Shipment, 100));
    new Achievement("Type II civilization", new BuildingRequirement(Game.Objects.Shipment, 150));
    new Achievement("We come in peace", new BuildingRequirement(Game.Objects.Shipment, 200));
    new Achievement("Parsec-masher", new BuildingRequirement(Game.Objects.Shipment, 250));
    new Achievement("It's not delivery", new BuildingRequirement(Game.Objects.Shipment, 300));
    new Achievement("Make it so", new BuildingRequirement(Game.Objects.Shipment, 350));
    new Achievement("That's just peanuts to space", new BuildingRequirement(Game.Objects.Shipment, 400));
    new Achievement("Space space space space space", new BuildingRequirement(Game.Objects.Shipment, 450));
    new Achievement("Only shooting stars", new BuildingRequirement(Game.Objects.Shipment, 500));
    new Achievement("The incredible journey", new BuildingRequirement(Game.Objects.Shipment, 550));
    new Achievement("Is there life on Mars?", new BuildingRequirement(Game.Objects.Shipment, 600));
    //endregion
    //region Alchemy
    new Achievement("Transmutation", new BuildingRequirement(Game.Objects["Alchemy lab"], 1));
    new Achievement("Transmogrification", new BuildingRequirement(Game.Objects["Alchemy lab"], 50));
    new Achievement("Gold member", new BuildingRequirement(Game.Objects["Alchemy lab"], 100));
    new Achievement("Gild wars", new BuildingRequirement(Game.Objects["Alchemy lab"], 150));
    new Achievement("The secrets of the universe", new BuildingRequirement(Game.Objects["Alchemy lab"], 200));
    new Achievement("The work of a lifetime", new BuildingRequirement(Game.Objects["Alchemy lab"], 250));
    new Achievement("Gold, Jerry! Gold!", new BuildingRequirement(Game.Objects["Alchemy lab"], 300));
    new Achievement("All that glitters is gold", new BuildingRequirement(Game.Objects["Alchemy lab"], 350));
    new Achievement("Worth its weight in lead", new BuildingRequirement(Game.Objects["Alchemy lab"], 400));
    new Achievement("Don't get used to yourself, you're gonna have to change", new BuildingRequirement(Game.Objects["Alchemy lab"], 450));
    new Achievement("We could all use a little change", new BuildingRequirement(Game.Objects["Alchemy lab"], 500));
    new Achievement("Just a phase", new BuildingRequirement(Game.Objects["Alchemy lab"], 550));
    new Achievement("Bad chemistry", new BuildingRequirement(Game.Objects["Alchemy lab"], 600));
    //endregion
    //region Portals
    new Achievement("A whole new world", new BuildingRequirement(Game.Objects.Portal, 1));
    new Achievement("Now you\'re thinking", new BuildingRequirement(Game.Objects.Portal, 50));
    new Achievement("Dimensional shift", new BuildingRequirement(Game.Objects.Portal, 100));
    new Achievement("Brain-split", new BuildingRequirement(Game.Objects.Portal, 150));
    new Achievement("Realm of the Mad God", new BuildingRequirement(Game.Objects.Portal, 200));
    new Achievement("A place lost in time", new BuildingRequirement(Game.Objects.Portal, 250));
    new Achievement("Forbidden zone", new BuildingRequirement(Game.Objects.Portal, 300));
    new Achievement("H̸̷͓̳̳̯̟͕̟͍͍̣͡ḛ̢̦̰̺̮̝͖͖̘̪͉͘͡ ̠̦͕̤̪̝̥̰̠̫̖̣͙̬͘ͅC̨̦̺̩̲̥͉̭͚̜̻̝̣̼͙̮̯̪o̴̡͇̘͎̞̲͇̦̲͞͡m̸̩̺̝̣̹̱͚̬̥̫̳̼̞̘̯͘ͅẹ͇̺̜́̕͢s̶̙̟̱̥̮̯̰̦͓͇͖͖̝͘͘͞", new BuildingRequirement(Game.Objects.Portal, 350));
    new Achievement("What happens in the vortex stays in the vortex", new BuildingRequirement(Game.Objects.Portal, 400));
    new Achievement("Objects in the mirror dimension are closer than they appear", new BuildingRequirement(Game.Objects.Portal, 450));
    new Achievement("Your brain gets smart but your head gets dumb", new BuildingRequirement(Game.Objects.Portal, 500));
    new Achievement("Don't let me leave, Murph", new BuildingRequirement(Game.Objects.Portal, 550));
    new Achievement("Reduced to gibbering heaps", new BuildingRequirement(Game.Objects.Portal, 600));
    //endregion
    //region Time Machines
    new Achievement("Time warp", new BuildingRequirement(Game.Objects["Time machine"], 1));
    new Achievement("Alternate timeline", new BuildingRequirement(Game.Objects["Time machine"], 50));
    new Achievement("Rewriting history", new BuildingRequirement(Game.Objects["Time machine"], 100));
    new Achievement("Time duke", new BuildingRequirement(Game.Objects["Time machine"], 150));
    new Achievement("Forever and ever", new BuildingRequirement(Game.Objects["Time machine"], 200));
    new Achievement("Heat death", new BuildingRequirement(Game.Objects["Time machine"], 250));
    new Achievement("cookie clicker forever and forever a hundred years cookie clicker, all day long forever, forever a hundred times, over and over cookie clicker adventures dot com", new BuildingRequirement(Game.Objects["Time machine"], 300));//Y u do dis? No capital letter at start and huge ass achiev name!
    new Achievement("Way back then", new BuildingRequirement(Game.Objects["Time machine"], 350));
    new Achievement("Invited to yesterday's party", new BuildingRequirement(Game.Objects["Time machine"], 400));
    new Achievement("Groundhog day", new BuildingRequirement(Game.Objects["Time machine"], 450));
    new Achievement("The years start coming", new BuildingRequirement(Game.Objects["Time machine"], 500));
    new Achievement("Caveman to cosmos", new BuildingRequirement(Game.Objects["Time machine"], 550));
    new Achievement("Back already?", new BuildingRequirement(Game.Objects["Time machine"], 600));
    //endregion
    //region Antimatter Condensers
    new Achievement("Antibatter", new BuildingRequirement(Game.Objects["Antimatter condenser"], 1));
    new Achievement("Quirky quarks", new BuildingRequirement(Game.Objects["Antimatter condenser"], 50));
    new Achievement("It does matter!", new BuildingRequirement(Game.Objects["Antimatter condenser"], 100));
    new Achievement("Molecular maestro", new BuildingRequirement(Game.Objects["Antimatter condenser"], 150));
    new Achievement("Walk the planck", new BuildingRequirement(Game.Objects["Antimatter condenser"], 200));
    new Achievement("Microcosm", new BuildingRequirement(Game.Objects["Antimatter condenser"], 250));
    new Achievement("Scientists baffled everywhere", new BuildingRequirement(Game.Objects["Antimatter condenser"], 300));
    new Achievement("Exotic matter", new BuildingRequirement(Game.Objects["Antimatter condenser"], 350));
    new Achievement("Downsizing", new BuildingRequirement(Game.Objects["Antimatter condenser"], 400));
    new Achievement("A matter of perspective", new BuildingRequirement(Game.Objects["Antimatter condenser"], 450));
    new Achievement("What a concept", new BuildingRequirement(Game.Objects["Antimatter condenser"], 500));
    new Achievement("Particular tastes", new BuildingRequirement(Game.Objects["Antimatter condenser"], 550));
    new Achievement("Nuclear throne", new BuildingRequirement(Game.Objects["Antimatter condenser"], 600));
    //endregion
    //region Prims
    new Achievement("Lone photon", new BuildingRequirement(Game.Objects.Prism, 1));
    new Achievement("Dazzling glimmer", new BuildingRequirement(Game.Objects.Prism, 50));
    new Achievement("Blinding flash", new BuildingRequirement(Game.Objects.Prism, 100));
    new Achievement("Unending glow", new BuildingRequirement(Game.Objects.Prism, 150));
    new Achievement("Rise and shine", new BuildingRequirement(Game.Objects.Prism, 200));
    new Achievement("Bright future", new BuildingRequirement(Game.Objects.Prism, 250));
    new Achievement("Harmony of the spheres", new BuildingRequirement(Game.Objects.Prism, 300));
    new Achievement("At the end of the tunnel", new BuildingRequirement(Game.Objects.Prism, 350));
    new Achievement("My eyes", new BuildingRequirement(Game.Objects.Prism, 400));
    new Achievement("Optical illusion", new BuildingRequirement(Game.Objects.Prism, 450));
    new Achievement("You'll never shine if you don't glow", new BuildingRequirement(Game.Objects.Prism, 500));
    new Achievement("A light snack", new BuildingRequirement(Game.Objects.Prism, 550));
    new Achievement("Making light of the situation", new BuildingRequirement(Game.Objects.Prism, 600));
    //endregion
    //region Chancemakers
    new Achievement("Lucked out", new BuildingRequirement(Game.Objects.Chancemaker, 1));
    new Achievement("What are the odds", new BuildingRequirement(Game.Objects.Chancemaker, 50));
    new Achievement("Grandma needs a new pair of shoes", new BuildingRequirement(Game.Objects.Chancemaker, 100));
    new Achievement("Million to one shot, doc", new BuildingRequirement(Game.Objects.Chancemaker, 150));
    new Achievement("As luck would have it", new BuildingRequirement(Game.Objects.Chancemaker, 200));
    new Achievement("Ever in your favor", new BuildingRequirement(Game.Objects.Chancemaker, 250));
    new Achievement("Be a lady", new BuildingRequirement(Game.Objects.Chancemaker, 300));
    new Achievement("Dicey business", new BuildingRequirement(Game.Objects.Chancemaker, 350));
    new Achievement("Maybe a chance in hell, actually", new BuildingRequirement(Game.Objects.Chancemaker, 400));
    new Achievement("Jackpot", new BuildingRequirement(Game.Objects.Chancemaker, 450));
    new Achievement("You'll never know if you don't go", new BuildingRequirement(Game.Objects.Chancemaker, 500));
    new Achievement("Tempting fate", new BuildingRequirement(Game.Objects.Chancemaker, 550));
    new Achievement("Flip a cookie. Chips, I win. Crust, you lose.", new BuildingRequirement(Game.Objects.Chancemaker, 600));
    //endregion
    //region Fractal Engines
    new Achievement("Self-contained", new BuildingRequirement(Game.Objects["Fractal engine"], 1));
    new Achievement("Threw you for a loop", new BuildingRequirement(Game.Objects["Fractal engine"], 50));
    new Achievement("The sum of its parts", new BuildingRequirement(Game.Objects["Fractal engine"], 100));
    new Achievement("Bears repeating", new BuildingRequirement(Game.Objects["Fractal engine"], 150));
    new Achievement("More of the same", new BuildingRequirement(Game.Objects["Fractal engine"], 200));
    new Achievement("Last recurse", new BuildingRequirement(Game.Objects["Fractal engine"], 250));
    new Achievement("Out of one, many", new BuildingRequirement(Game.Objects["Fractal engine"], 300));
    new Achievement("An example of recursion", new BuildingRequirement(Game.Objects["Fractal engine"], 350));
    new Achievement("For more information on this achievement, please refer to its title", new BuildingRequirement(Game.Objects["Fractal engine"], 400));
    new Achievement("I'm so meta, even this achievement", new BuildingRequirement(Game.Objects["Fractal engine"], 450));
    new Achievement("Never get bored", new BuildingRequirement(Game.Objects["Fractal engine"], 500));
    new Achievement("Tautological", new BuildingRequirement(Game.Objects["Fractal engine"], 550));
    new Achievement("In and of itself", new BuildingRequirement(Game.Objects["Fractal engine"], 600));
    //endregion
    //region All buildings
    new Achievement("One with everything", ...BuildingRequirement.generateRequirementsForAllBuildings(1));
    new Achievement("Centennial", ...BuildingRequirement.generateRequirementsForAllBuildings(100));
    new Achievement("Centennial and a half", ...BuildingRequirement.generateRequirementsForAllBuildings(150));
    new Achievement("Bicentennial", ...BuildingRequirement.generateRequirementsForAllBuildings(200));
    new Achievement("Bicentennial and a half", ...BuildingRequirement.generateRequirementsForAllBuildings(250));
    new Achievement("Tricentennial", ...BuildingRequirement.generateRequirementsForAllBuildings(300));
    new Achievement("Tricentennial and a half", ...BuildingRequirement.generateRequirementsForAllBuildings(350));
    new Achievement("Quadricentennial", ...BuildingRequirement.generateRequirementsForAllBuildings(400));
    new Achievement("Quadricentennial and a half", ...BuildingRequirement.generateRequirementsForAllBuildings(450));
    new Achievement("Quincentennial", ...BuildingRequirement.generateRequirementsForAllBuildings(500));
    new Achievement("Base 10", ...Game.ObjectsById.map(object => new BuildingRequirement(object, (Game.ObjectsById.length - object.id) * 10)));
    new Achievement("Mathematician", ...Game.ObjectsById.map(object => new BuildingRequirement(object, Math.min(128, Math.pow(2, Game.ObjectsById.length - 1 - object.id)))));
    //endregion
  }

  updatedBuyables(): Array<Buyable<GameObject>> {
    this.buyables.forEach(b => b.needsUpdate = true);
    for (let buildingName in this.buildings) {
      this.buildings[buildingName].update();
    }
    this.buyables.forEach(b => b.update());
    return this.buyables;
  }

  updateNotes(): void {
    const cps = getCps();
    if (this.lastCps !== cps) {
      this.lastCps = cps;
      setTimeout(() => this.loop(`Cookies per second changed`))
    } else {
      this.lastCps = cps;
    }
    this.notes.forEach(note => note.update(this))
    // @ts-ignore
    document.getElementById("specialPopup").style.bottom = `${25 + 37 * this.notesShown}px`
  }

  testEverything(): void {
    const buyables = this.updatedBuyables();

    buyables
      .filter(b => b.payback === Infinity)
      .forEach(b => {
        if (b instanceof Building) {
          log(`${b.name} has Infinite payback`)
        }
      });

    buyables
      .filter(b => isNaN(b.payback))
      .forEach(b => log(`${b.name} has NaN payback`));

    buyables
      .filter(b => b.payback < 0)
      .forEach(b => log(`${b.name} has negative payback`));
  }

  /**
   * Creates the notification area and notes
   */
  createNotes(): void {
    this.noteArea = new NoteArea();
    this.spawnWindowNote = new GoldenCookieSpawnNote()
    this.reserveNote = new ReserveNote()
    this.goalNote = new GoalNote()
    this.nextBuyNote = new NextBuyNote()

    this.notes = [this.spawnWindowNote, this.reserveNote, this.goalNote, this.nextBuyNote]

    this.noteArea.html.appendChild(this.spawnWindowNote.html);
    this.noteArea.html.appendChild(this.reserveNote.html);
    this.noteArea.html.appendChild(this.goalNote.html);
    this.noteArea.html.appendChild(this.nextBuyNote.html);

    const notes = document.getElementById("notes");
    // @ts-ignore
    notes.parentElement.insertBefore(this.noteArea.html, notes);
    log("All notes created successfully.");
  }

  toggleBuyLock(): void {
    this.buyLocked = !this.buyLocked;
    this.loop(`Set buy lock to ${this.buyLocked}`);
  }

  loop(message): void {
    debug("Loop: " + message)
    if (this.stopped) return
    if (this.mainInterval) clearTimeout(this.mainInterval) //Prevent two or more timers from running this.
    const bestBuyableTarget = minByPayback(this.updatedBuyables())
    if (this.bestBuyable !== bestBuyableTarget) {
      this.bestBuyable = bestBuyableTarget
      this.bestBuyable.resetOriginalBuyMillis()
    }
    this.bestBuyable.updateInvestmentAndBuyMillis()

    if (!this.buyLocked) {
      const nextMilestone = this.bestBuyable.nextMilestone
      if (nextMilestone.millisToBuy <= 0) {
        let invested = nextMilestone.investment.invest()
        nextMilestone.buy()
        if (invested > 0) {
          //Sell investment on the next stock market price update since we can't sell in the same update we buy
          setTimeout(() => {
            const profit = nextMilestone.investment.sellInvestment() - invested
            log(`Investments earned ${Beautify(profit)} or ${timeString(profit / Game.unbuffedCps)} of production`)
            this.loop("Investment Sold")
          }, 50 + StockMarket.millisToNextTick)
        }
      } else {
        if (nextMilestone.millisToBuy < Infinity) {
          // Set timeout to the expected time we can purchase the target buyable
          let message = "";
          if (nextMilestone instanceof Building) {
            message = `Buying the ${convertNumeral(nextMilestone.gameObject.amount + 1)} ${nextMilestone.name}`;
          } else {
            message = `Buying ${nextMilestone.name}`;
          }

          this.mainInterval = setTimeout(() => this.loop(message), nextMilestone.millisToBuy);
        }
      }
    }
    this.updateNotes();
  }

  engageHooks(): void {
    Game.registerHook("click", () => this.loop("Big Cookie clicked"))
    // @ts-ignore
    document.getElementById("store").addEventListener("click", () => this.loop("Store clicked")); //Hook store items and buildings
    // @ts-ignore
    document.getElementById("shimmers").addEventListener("click", () => setTimeout(() => this.loop("Golden Cookie clicked"), 50)); //Hook golden cookie clicks
  }

  start(): void {
    if (this.stopped) {
      this.stopped = false;
      for (let buildingName in this.buildings) {
        this.buyables.push(this.buildings[buildingName]);
      }
      for (let upgradeName in this.upgrades) {
        const upgrade = this.upgrades[upgradeName];
        if (upgrade.canEventuallyGet) {
          this.buyables.push(upgrade);
        }
      }

      let i = 0
      for (let achievementName in AUTO_COOKIE.achievements) {
        i += 1
      }

      for (let achievementName in this.achievements) {
        const achievement = this.achievements[achievementName];
        if (achievement.canEventuallyGet) {
          this.buyables.push(achievement);
        }
      }
      this.reserveNote.update(this)
      this.loop("Start"); //Run the first calculation.
      this.noteUpdateInterval = setInterval(() => this.updateNotes(), NOTE_UPDATE_FREQUENCY);
      this.engageHooks();
      this.noteArea.show()
    }
  }

  stop(): void {
    clearInterval(this.noteUpdateInterval)
    clearTimeout(this.mainInterval)
    this.stopped = true
    //remove hooks
    //clear buyables
  }

  init(): void {
    this.reserve = new Reserve();
    this.stockMarket = new StockMarket();
    this.createNotes();
    // @ts-ignore
    document.getElementById("versionNumber").hidden = true;
    this.addBuildings();
    this.addAchievements();
    this.addUpgrades();
    this.testEverything();
    Game.Notify(`Auto Cookie started!`,'',[16,5]);
    this.start()
  }

  save(): string {
    //This can never return empty string or load will never be called
    const saveString = [
      AUTO_COOKIE_VERSION,
      this.reserveNote.activeButtonEffects.map(effect => effect.name).join(", "),
      this.buyLocked,
      "AutoEnd"
    ].join("|")
    debug(`Save string: ${saveString}`)
    return saveString
  }

  load(string): void {
    const load = () => { //This is an arrow function to avoid dereferencing this
      const split = string.split("|")
      const version = parseFloat(split[0]) || 0
      if (version > AUTO_COOKIE_VERSION) {
        error("Trying to load code from future version")
      } else if (version > 0) {
        debug(`Load string ${string}`)
        this.reserveNote.setActiveButtons(split[1].split(", ").filter(s => s !== ""))
        this.buyLocked = split[2] === "true"
      }
      this.buyables = this.buyables.filter(buyable => buyable.canEventuallyGet)
      this.reserveNote.update(this)
      log("Load Finished")
    }

    //At this points buildings have already loaded but cookies per second has not
    if (Game.BuildingsOwned === 0) { //We have no buildings, we are ready to go
      debug("Loading with no buildings")
      load()
    } else {
      const cpsHook = (cps) => {
        if (Game.unbuffedCps > 0) { //Wait for Game.unbuffedCps to be updated
          Game.removeHook('cps', cpsHook)
          load()
        }
        return cps
      }
      Game.registerHook('cps', cpsHook)
    }
  }
}

const AUTO_COOKIE = new AutoCookie()

Game.registerMod(MOD_ID, AUTO_COOKIE);