package autocookie

import cookieclicker.Named

sealed trait ShouldUpdate

sealed trait LoopReason extends Named:
  val message: String = className
  lazy val shouldUpdate = this.isInstanceOf[ShouldUpdate]

object LoopReason:
  object InvestmentSold extends LoopReason
  case class BuyLockToggle(newState: Boolean) extends LoopReason {
    override val message: String = s"Set buy lock to $newState"
  }
  object BigCookieClicked extends LoopReason
  object GoldenCookieClicked extends LoopReason with ShouldUpdate
  object Start extends LoopReason
  object ReserveLevelChanged extends LoopReason
  object CPSChanged extends LoopReason with ShouldUpdate
  object BuffEnded extends LoopReason with ShouldUpdate
  object AfterBuy extends LoopReason with ShouldUpdate
  case class Buying(override val message: String) extends LoopReason with ShouldUpdate

