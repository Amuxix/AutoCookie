package autocookie.reserve

import autocookie.Helpers
import autocookie.buyable.Building
import autocookie.buyable.upgrade.Upgrade
import autocookie.reserve.CookieEffect
import autocookie.reserve.ReserveLevel.*
import cookieclicker.Game

object ReserveLevel {
  def goldenCookieMultiplier: Double = {
    import Helpers.toBoolean
    var mult = 1D
    if (Game.elderWrath > 0) {
      mult *= 1 + Game.auraMult("Unholy Dominion") * 0.1
    } else if (Game.elderWrath == 0) {
      mult *= 1 + Game.auraMult("Ancestral Metamorphosis") * 0.1
    }
    if (Game.upgradeBought("Green yeast digestives")) mult *= 1.01
    if (Game.upgradeBought("Dragon fang")) mult *= 1.03
    if (Game.elderWrath == 0) mult *= Game.eff("goldenCookieGain")
    else if (Game.elderWrath == 3) mult *= Game.eff("wrathCookieGain")
    //If elderwrath is between 1 and 3 we don't apply bonus because it depends on the cookie we get.
    mult
  }

  def calculateCookieChainReserve(cps: Double): Long = {
    val digit = if (Game.elderWrath == 3) 6 else 7 //With last level of elder wrath cookie the digit will always be 6
    val goldenCookieMultiplier = this.goldenCookieMultiplier
    val chainDigits = Math.log10((cps * 6 * 60 * 60 * goldenCookieMultiplier * 9) / digit).toInt

    def repeatingDigitNumber(digit: Long, repetitions: Long): Long =
      (1 / 9.0 * Math.pow(10, repetitions.toDouble) * digit).toLong

    def payout(digit: Int, digits: Int): Long =
      (1 to digits).map(repeatingDigitNumber(digit, _)).sum

    //If max chain is lesser or equal to 5 we dont need to reserve as chain will not break before the 5 digits and 6 
    // digits is more than 6h production.
    if (chainDigits > 5) {
      ((repeatingDigitNumber(digit, chainDigits) * 2 - payout(digit, chainDigits - 1)) * goldenCookieMultiplier).toLong
    } else {
      0
    }
  }

  def lucky(): Long = ((Game.unbuffedCps * 15 * 60) / .15).toLong

  def conjuredBakedGoods(): Long = ((Game.unbuffedCps * 30 * 60) / .15).toLong

  lazy val goldenCookieUpgrades = Seq("Lucky day", "Serendipity", "Get lucky").map(Upgrade.getByName)

  def chainIsUnlocked() = Game.cookiesEarned >= 100000
  def bakedGoodsIsUnlocked() = Building.wizardTower.level > 0 && Building.wizardTower.amount > 0
  def frenzyIsUnlocked() = Game.elderWrath < 3
  def dragonHarvestIsUnlocked() = Game.hasAura("Reaper of Fields")
  def allGoldenCookieUpgradesOwned() = goldenCookieUpgrades.forall(_.owned)
}

enum ReserveLevel(val effects: Seq[CookieEffect], val calculateAmount: () => Long, val isUnlocked: () => Boolean):
  case Disabled extends ReserveLevel(Seq.empty, () => 0, () => true)
  case Lucky extends ReserveLevel(Seq(CookieEffect.Lucky), lucky, () => true)
  case FrenzyLucky extends ReserveLevel(Seq(CookieEffect.Lucky, CookieEffect.Frenzy), () => lucky() * 7, allGoldenCookieUpgradesOwned)
  case DragonHarvestLucky extends ReserveLevel(Seq(CookieEffect.Lucky, CookieEffect.DragonHarvest), () => lucky() * 15, dragonHarvestIsUnlocked)
  case Chain extends ReserveLevel(Seq(CookieEffect.Chain), () => calculateCookieChainReserve(Game.unbuffedCps), chainIsUnlocked)
  case FrenzyChain extends ReserveLevel(Seq(CookieEffect.Chain, CookieEffect.Frenzy), () => calculateCookieChainReserve(Game.unbuffedCps * 7), () => allGoldenCookieUpgradesOwned() && chainIsUnlocked())
  case DragonHarvestChain extends ReserveLevel(Seq(CookieEffect.Chain, CookieEffect.DragonHarvest), () => calculateCookieChainReserve(Game.unbuffedCps * 15), () => dragonHarvestIsUnlocked() && chainIsUnlocked())
  case BakedGoods extends ReserveLevel(Seq(CookieEffect.BakedGoods), conjuredBakedGoods, bakedGoodsIsUnlocked)
  case FrenzyBakedGoods extends ReserveLevel(Seq(CookieEffect.BakedGoods, CookieEffect.Frenzy), () => conjuredBakedGoods() * 7, bakedGoodsIsUnlocked)
  case DragonHarvestBakedGoods extends ReserveLevel(Seq(CookieEffect.BakedGoods, CookieEffect.DragonHarvest), () => conjuredBakedGoods() * 15, () => dragonHarvestIsUnlocked() && bakedGoodsIsUnlocked())

  val title: String =
    if (effects.isEmpty) {
      "Disabled"
    } else if (effects.lengthIs == 1) {
      effects.head.shortName
    } else {
      effects.map(effect => effect.shortName).mkString(" with ")
    }


