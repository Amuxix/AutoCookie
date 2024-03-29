package autocookie.reserve

import autocookie.LoopReason.ReserveLevelChanged
import autocookie.{AutoCookie, LoopReason}
import autocookie.reserve.ReserveLevel.Disabled

object Reserve:
  private var possibleLevels = Set.empty[ReserveLevel]

  var level: ReserveLevel = Disabled
  var amount: Double = 0

  def update(): Unit =
    val (activeLevel, amount) = possibleLevels.foldLeft((Disabled, 0D)) {
      case (old @ (maxlevel, max), level) =>
        val amount = level.calculateAmount()
        if amount > max then (level, amount) else old
    }
    this.level = activeLevel
    this.amount = amount


  def changeReserveLevels(remove: ReserveLevel, add: ReserveLevel): Unit =
    possibleLevels = possibleLevels - remove + add
    update()
    AutoCookie.loop(ReserveLevelChanged)
