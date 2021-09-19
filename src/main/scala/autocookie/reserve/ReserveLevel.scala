package autocookie.reserve

import autocookie.Helpers
import autocookie.Helpers.given
import autocookie.reserve.CookieEffect
import typings.cookieclicker.global.Game

object ReserveLevel {
  def goldenCookieMultiplier: Double = {
    import Helpers.toBoolean
    var mult = 1D
    if (Game.elderWrath > 0) {
      mult *= 1 + Game.auraMult("Unholy Dominion") * 0.1
    } else if (Game.elderWrath == 0) {
      mult *= 1 + Game.auraMult("Ancestral Metamorphosis") * 0.1
    }
    if (Game.Has("Green yeast digestives")) mult *= 1.01
    if (Game.Has("Dragon fang")) mult *= 1.03
    if (Game.elderWrath == 0) mult *= Game.eff("goldenCookieGain", ???)
    else if (Game.elderWrath == 3) mult *= Game.eff("wrathCookieGain", ???)
    //If elderwrath is between 1 and 3 we don't apply bonus because it depends on the cookie we get.
    mult
  }

  def calculateCookieChainReserve(cps: Double): Int = {
    val digit = if (Game.elderWrath == 3) 6 else 7 //With last level of elder wrath cookie the digit will always be 6
    val goldenCookieMultiplier = this.goldenCookieMultiplier
    val chainDigits = Math.log10((cps * 6 * 60 * 60 * goldenCookieMultiplier * 9) / digit).toInt

    def repeatingDigitNumber(digit: Int, repetitions: Int): Int =
      (1 / 9.0 * Math.pow(10, repetitions) * digit).toInt

    def payout(digit: Int, digits: Int): Int =
      (1 to digits).map(repeatingDigitNumber(digit, _)).sum

    //If max chain is lesser or equal to 5 we dont need to reserve as chain will not break before the 5 digits and 6 
    // digits is more than 6h production.
    if (chainDigits > 5) {
      ((repeatingDigitNumber(digit, chainDigits) * 2 - payout(digit, chainDigits - 1)) * goldenCookieMultiplier).toInt
    } else {
      0
    }
  }

  def lucky: Int = ((Game.unbuffedCps * 15 * 60) / .15).toInt

  def conjuredBakedGoods: Int = ((Game.unbuffedCps * 30 * 60) / .15).toInt
}

enum ReserveLevel(val effects: Seq[CookieEffect], val amount: () => Int):
  case Disabled extends ReserveLevel(Seq.empty, () => 0)
  case LuckyReserve extends ReserveLevel(Seq(CookieEffect.Lucky), () => ReserveLevel.lucky)
  case BakedGoodsReserve extends ReserveLevel(Seq(CookieEffect.BakedGoods), () => ReserveLevel.conjuredBakedGoods)
  case ChainReserve extends ReserveLevel(
    Seq(CookieEffect.Chain), () => ReserveLevel.calculateCookieChainReserve(Game
      .unbuffedCps
    )
  )
  case FrenzyLuckyReserve extends ReserveLevel(
    Seq(CookieEffect.Lucky, CookieEffect.Frenzy), () => ReserveLevel
      .lucky * 7
  )
  case FrenzyChainReserve extends ReserveLevel(
    Seq(
      CookieEffect.Chain, CookieEffect
        .Frenzy
    ), () => ReserveLevel.calculateCookieChainReserve(Game.unbuffedCps * 7)
  )
  case FrenzyBakedGoodsReserve extends ReserveLevel(
    Seq(
      CookieEffect.BakedGoods, CookieEffect
        .Frenzy
    ), () => ReserveLevel.conjuredBakedGoods * 7
  )
  case DragonHarvestLuckyReserve extends ReserveLevel(
    Seq(
      CookieEffect.Lucky, CookieEffect
        .DragonHarvest
    ), () => ReserveLevel.lucky * 15
  )
  case DragonHarvestChainReserve extends ReserveLevel(
    Seq(
      CookieEffect.Chain, CookieEffect
        .DragonHarvest
    ), () => ReserveLevel.calculateCookieChainReserve(Game.unbuffedCps * 15)
  )
  case DragonHarvestBakedGoodsReserve extends ReserveLevel(
    Seq(
      CookieEffect.BakedGoods, CookieEffect
        .DragonHarvest
    ), () => ReserveLevel.conjuredBakedGoods * 15
  )

  val title: String =
    if (effects.isEmpty) {
      "Disabled"
    } else if (effects.lengthIs == 1) {
      effects.head.shortName
    } else {
      effects.map(effect => effect.shortName).mkString(" with ")
    }


