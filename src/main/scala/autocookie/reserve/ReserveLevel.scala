package autocookie.reserve

import autocookie.{AutoCookie, Helpers}
import autocookie.buyable.{Building, GameBuildings}
import autocookie.buyable.upgrade.Upgrade
import autocookie.reserve.CookieEffect
import autocookie.reserve.ReserveLevel.*
import autocookie.Helpers.sumBy
import cookieclicker.Game

object ReserveLevel:
  def goldenCookieMultiplier: Double =
    import Helpers.toBoolean
    var mult = 1D
    if Game.elderWrath > 0 then
      mult *= 1 + Game.auraMult("Unholy Dominion") * 0.1
    else if Game.elderWrath == 0 then
      mult *= 1 + Game.auraMult("Ancestral Metamorphosis") * 0.1
    if Game.upgradeBought("Green yeast digestives") then mult *= 1.01
    if Game.upgradeBought("Dragon fang") then mult *= 1.03
    if Game.elderWrath == 0 then mult *= Game.eff("goldenCookieGain")
    else if Game.elderWrath == 3 then mult *= Game.eff("wrathCookieGain")
    //If elderwrath is between 1 and 3 we don't apply bonus because it depends on the cookie we get.
    mult

  def calculateCookieChainReserve(cps: Double): Double =
    val digit = if (Game.elderWrath == 3) 6 else 7 //With last level of elder wrath cookie the digit will always be 6
    val goldenCookieMultiplier = this.goldenCookieMultiplier
    val chainDigits = Math.log10((cps * 6 * 60 * 60 * goldenCookieMultiplier * 9) / digit).toInt

    def repeatingDigitNumber(digit: Double, repetitions: Double): Double = 1 / 9.0 * Math.pow(10, repetitions) * digit

    def payout(digit: Int, digits: Int): Double = (1 to digits).map(repeatingDigitNumber(digit, _)).sum

    //If max chain is lesser or equal to 5 we dont need to reserve as chain will not break before the 5 digits and 6 
    // digits is more than 6h production.
    if chainDigits > 5 then
      (repeatingDigitNumber(digit, chainDigits) * 2 - payout(digit, chainDigits - 1)) * goldenCookieMultiplier
    else
      0

  def lucky(): Double = (Game.unbuffedCps * 15 * 60) / .15

  def conjuredBakedGoods(): Double = (Game.unbuffedCps * 30 * 60) / .15

  def averageBuildingSpecial(): Double =
    val buildings = AutoCookie.buildings.values.filter(_.amount > 10)
    1 + buildings.sumBy(_.amount) / buildings.size.toDouble / 10

  lazy val goldenCookieUpgrades = Seq("Lucky day", "Serendipity", "Get lucky").map(Upgrade.getByName)

  def chainIsUnlocked(): Boolean = Game.cookiesEarned >= 100000
  def bakedGoodsIsUnlocked(): Boolean = GameBuildings.wizardTower.level > 0 && GameBuildings.wizardTower.amount > 0
  def frenzyIsUnlocked(): Boolean = Game.elderWrath < 3
  def dragonHarvestIsUnlocked(): Boolean = Game.hasAura("Reaper of Fields")
  def allGoldenCookieUpgradesOwned(): Boolean = goldenCookieUpgrades.forall(_.owned)

enum ReserveLevel(val effects: Set[CookieEffect], val calculateAmount: () => Double, val isUnlocked: () => Boolean):
  case Disabled extends ReserveLevel(Set.empty, () => 0, () => true)
  case Lucky extends ReserveLevel(Set(CookieEffect.Lucky), lucky, () => true)
  case FrenzyLucky extends ReserveLevel(Set(CookieEffect.Lucky, CookieEffect.Frenzy), () => lucky() * 7, allGoldenCookieUpgradesOwned)
  case DragonHarvestLucky extends ReserveLevel(Set(CookieEffect.Lucky, CookieEffect.DragonHarvest), () => lucky() * 15, dragonHarvestIsUnlocked)
  //case FrenzyDragonHarvestLucky extends ReserveLevel(Set(CookieEffect.Lucky, CookieEffect.Frenzy, CookieEffect.DragonHarvest), () => lucky() * 15 * 7, () => dragonHarvestIsUnlocked() && allGoldenCookieUpgradesOwned())

  case Chain extends ReserveLevel(Set(CookieEffect.Chain), () => calculateCookieChainReserve(Game.unbuffedCps), chainIsUnlocked)
  case FrenzyChain extends ReserveLevel(Set(CookieEffect.Chain, CookieEffect.Frenzy), () => calculateCookieChainReserve(Game.unbuffedCps * 7), () => allGoldenCookieUpgradesOwned() && chainIsUnlocked())
  case DragonHarvestChain extends ReserveLevel(Set(CookieEffect.Chain, CookieEffect.DragonHarvest), () => calculateCookieChainReserve(Game.unbuffedCps * 15), () => dragonHarvestIsUnlocked() && chainIsUnlocked())

  case BakedGoods extends ReserveLevel(Set(CookieEffect.BakedGoods), conjuredBakedGoods, bakedGoodsIsUnlocked)
  case FrenzyBakedGoods extends ReserveLevel(Set(CookieEffect.BakedGoods, CookieEffect.Frenzy), () => conjuredBakedGoods() * 7, bakedGoodsIsUnlocked)
  case BuildingSpecialChain extends ReserveLevel(Set(CookieEffect.BakedGoods, CookieEffect.BuildingSpecial), () => conjuredBakedGoods() * averageBuildingSpecial(), bakedGoodsIsUnlocked)
  case DragonHarvestBakedGoods extends ReserveLevel(Set(CookieEffect.BakedGoods, CookieEffect.DragonHarvest), () => conjuredBakedGoods() * 15, () => dragonHarvestIsUnlocked() && bakedGoodsIsUnlocked())
  case FrenzyBuildingSpecialBakedGoods extends ReserveLevel(Set(CookieEffect.BakedGoods, CookieEffect.Frenzy, CookieEffect.BuildingSpecial), () => conjuredBakedGoods() * averageBuildingSpecial() * 7, () => bakedGoodsIsUnlocked() && allGoldenCookieUpgradesOwned())
  case FrenzyDragonHarvestBakedGoods extends ReserveLevel(Set(CookieEffect.BakedGoods, CookieEffect.Frenzy, CookieEffect.DragonHarvest), () => conjuredBakedGoods() * 15 * 7, () => bakedGoodsIsUnlocked() && dragonHarvestIsUnlocked())

  val title: String =
    if effects.isEmpty then
      "Disabled"
    else if effects.size == 1 then
      effects.head.shortName
    else
      effects.map(effect => effect.shortName).mkString(" with ")
