package autocookie.reserve

import autocookie.Helpers
import cookieclicker.Game
import ReserveLevel.*

object ReserveGroup:
  def nextLevelInGroup(group: ReserveGroup, current: ReserveLevel): ReserveLevel =
    val index = group.reserveLevels.indexOf(current)
    group.reserveLevels((index + 1) % group.reserveLevels.length)


enum ReserveGroup(val icon: String, val reserveLevels: Array[ReserveLevel]):
  case LuckyGroup extends ReserveGroup("🍀", Array(Disabled, Lucky, FrenzyLucky, DragonHarvestLucky))
  case ChainGroup extends ReserveGroup("🔗", Array(Disabled, Chain, FrenzyChain, DragonHarvestChain))
  case BakedGoodsGroup extends ReserveGroup("🍪", Array(Disabled, BakedGoods, FrenzyBakedGoods, DragonHarvestBakedGoods))
