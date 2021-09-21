package autocookie

import cookieclicker.Named

sealed trait CookiesChanged
sealed trait CPSChanged

sealed trait LoopReason extends Named:
  val message: String = className
  lazy val cookiesChanged = this.isInstanceOf[CookiesChanged]
  lazy val CPSChanged = this.isInstanceOf[CPSChanged]

object LoopReason:
  object InvestmentSold extends LoopReason with CookiesChanged
  case class BuyLockToggle(newState: Boolean) extends LoopReason {
    override val message: String = s"Set buy lock to $newState"
  }
  object BigCookieClicked extends LoopReason with CookiesChanged
  object GoldenCookieClicked extends LoopReason with CookiesChanged
  object Start extends LoopReason
  object ReserveLevelChanged extends LoopReason
  object CPSChanged extends LoopReason with CPSChanged
  case class Buying(override val message: String) extends LoopReason with CPSChanged with CookiesChanged

