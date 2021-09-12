declare class GameObject {
  name: string
  unlocked: number

  buy(amount: number): void
}

declare class GameBuilding extends GameObject {
  id: number
  amount: number
  level: number
  baseCps: number
  storedTotalCps: number

  getPrice(): number
  getSumPrice(missingAmount: number): number
}

declare class Good {
  name: string
  active: boolean
  vals: Array<number>
  id: number
  val: number
  stock: number
}

declare class Market {
  tickT: number
  brokers: number
  goodsById: Array<Good>

  getMaxBrokers(): number
  getGoodMaxStock(good: Good): number
  getGoodPrice(good: Good): number
  buyGood(id: number, amount: number): boolean
  sellGood(id: number, amount: number): boolean
}

declare class Bank extends GameBuilding {
  minigame: Market
  minigameLoaded: boolean
}

declare class GameAchievement extends GameObject {
  won: number
}

declare class GameUpgrade extends GameObject {
  cookies: number
  require: string
  season: string
  bought: number
  power: number

  getPrice(): number
}

declare class GameObjects {
  Cursor: GameBuilding
  Grandma: GameBuilding
  Farm: GameBuilding
  Mine: GameBuilding
  Factory: GameBuilding
  Bank: Bank
  Temple: GameBuilding
  "Wizard tower": GameBuilding
  Shipment: GameBuilding
  "Alchemy lab": GameBuilding
  Portal: GameBuilding
  "Time machine": GameBuilding
  "Antimatter condenser": GameBuilding
  Prism: GameBuilding
  Chancemaker: GameBuilding
  "Fractal engine": GameBuilding
}

declare class GameTooltip {
  shouldHide: number

  draw(from, text: (() => string) | string, origin: string): void
}

declare class Shimmer {
  life: number
}

declare class GoldenShimmer extends Shimmer {
  minTime: number
  maxTime: number
  time: number
}

declare function Beautify(number: number): string

declare class ShimmerTypes {
  golden: GoldenShimmer
}

declare class Buff {
  multClick: number
  multCpS: number
  time: number

}

declare class Wrinkler {
  id: number
  sucked: number
}

declare class Game {
  static UnlockAt: Array<GameUpgrade>
  static cookiesEarned: number
  static season: string
  static ObjectsById: Array<GameBuilding>
  static UpgradesById: Array<GameUpgrade>
  static Objects: GameObjects
  static globalCpsMult: number
  static fps: number
  static cookies: number
  static cookiesPs: number
  static unbuffedCps: number
  static cookiesPsRawHighest: number
  static cpsSucked: number
  static buyMode: number
  static milkProgress: number
  static prestige: number
  static baseResearchTime: number
  static researchT: number
  static nextResearch: number
  static Upgrades: {}
  static Achievements: {}
  static elderWrath: number
  static tooltip: GameTooltip
  static shimmers: Array<Shimmer>
  static shimmerTypes: ShimmerTypes
  static buffs: {}
  static wrinklers: Array<Wrinkler>
  static BuildingsOwned: number

  static Object(): GameBuilding

  static Has(what: string): boolean
  static HasAchiev(what: string): boolean
  static Win(what: string | Array<string>): boolean
  static Unlock(what: string | Array<string>): boolean
  static ComputeCps(base: number, mult: number, bonus: number): number
  static ComputeCps(base: number, mult: number): number
  static hasGod(name: string): number
  static getWrinklersMax(): number
  static Notify(title: string, desc: string, pic: Array<number>): number
  static Notify(title: string, desc: string): number
  static CalculateGains(): void
  static mouseCps(): number
  static auraMult(name: string): number
  static eff(name: string): number
  static crateTooltip(object: GameObject, context: string): string
  static hasAura(name: string): boolean

  static registerHook(hookId: string, f: Function): void
  static registerMod(id: string, mod: {}): void

  static removeHook(hookId: string, f: Function): void
}