package autocookie.reserve

enum CookieEffect(val name: String, val shortName: String):
  case Lucky extends CookieEffect("Lucky", "Lucky")
  case Chain extends CookieEffect("Chain", "Chain")
  case BakedGoods extends CookieEffect("Baked Goods", "Baked Goods")
  case Frenzy extends CookieEffect("Frenzy", "Frenzy")
  case DragonHarvest extends CookieEffect("Dragon Harvest", "DH")
