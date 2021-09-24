package autocookie.notes

import autocookie.*
import cookieclicker.global.Beautify
import autocookie.Helpers.round
import autocookie.notes.NextBuyNote.drawTooltip
import autocookie.notes.Tooltip.*
import com.raquo.laminar.api.L.*
import cookieclicker.stockmarket.Good
import cookieclicker.global.Beautify
import cookieclicker.Game

object InvestmentNote extends Note with HideOnMouseout {
  def convertAndBeautify(money: Double) =
    Beautify(StockMarket.moneyToCookies(money))

  def tooltip(investment: RealInvestment) =
    val percent = investment.estimatedReturnPercent
    val brokersLine =
      if investment.newBrokers > 0 then
        s"• ${investment.newBrokers}x Brokers costing ${convertAndBeautify(investment.moneyInvestedInBrokers)}"
      else
        ""
    val body = div(
      //"Estimated return percent is ",
      //b(s"${(percent * 100).round(2)}%"),
      line,
      brokersLine,
      if investment.newBrokers > 0 then line else emptyNode,
      investment.goodsInvested.map {
        case goodInvestment @ GoodInvestment(good, amount, cost) =>
          div(
            fontSize := "11px",
            s"• ",
            smallIcon(goodInvestment.icon),
            nbsp,
            b(amount),
            s"x ${good.name} yielding ",
            b(s"${convertAndBeautify(cost * (percent - 1))}"),
          )
      },
      //s"• Total cost $$${investment.totalMoneyInvestment.round(2)}",
    )
    Tooltip(s"Investing ${Beautify(investment.totalCookieInvestment)}", body).ref.outerHTML

  override def update(): Unit =
    AutoCookie.bestBuyable.investment match {
      case EmptyInvestment => hide()
      case investment: RealInvestment       =>
        setTitle("Expected investment returns")
        //setDescription(s"$$${investment.totalMoneyInvestment.round(2)}")
        setDescription(s"${Beautify(investment.estimatedReturns)}")
        html.onmouseover = (m) => Game.tooltip.draw(html, tooltip(investment), "this")
        show()
    }
}
