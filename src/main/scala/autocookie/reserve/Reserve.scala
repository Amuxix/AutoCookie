package autocookie.reserve

import autocookie.AutoCookie
import autocookie.reserve.ReserveLevel.Disabled

object Reserve {
  private var possibleLevels = Set.empty[ReserveLevel]

  var level: ReserveLevel = Disabled
  var amount: Long = 0

  def update(): Unit =
    val (activeLevel, amount) = possibleLevels.foldLeft((Disabled, 0L)) {
      case (old @ (maxlevel, max), level) =>
        val amount = level.calculateAmount()
        if amount > max then (level, amount) else old
    }
    this.level = activeLevel
    this.amount = amount


  def changeReserveLevels(remove: ReserveLevel, add: ReserveLevel): Unit =
    possibleLevels = possibleLevels - remove + add
    update()
    AutoCookie.loop("Reserve levels changed")
}
