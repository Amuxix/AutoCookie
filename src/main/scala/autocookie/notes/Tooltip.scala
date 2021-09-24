package autocookie.notes

import autocookie.{GoodInvestment, RealInvestment, StockMarket}
import autocookie.Helpers.round
import org.scalajs.dom.{console, document}
import org.scalajs.dom.document.createElement
import com.raquo.laminar.api.L.*

object Tooltip {

  def apply(title: String, body: Element, icon: Option[(Int, Int)] = None) =
    div(
      minWidth := "350px",
      padding := "8px",
      icon.fold(emptyNode)(bigIcon(_)),
      div(
        className := "name",
        title
      ),
      line,
      body
    )

  def bigIcon(icon: (Int, Int), scale: Double = 1): HtmlElement  =
    div(
      className := "icon",
      float := "left",
      marginLeft := "-8px",
      marginTop := "-8px",
      backgroundImage := "url(img/icons.png?v=2.031)",
      backgroundPosition := s"${-icon._1 * 48}px ${-icon._2 * 48}px",
      if scale != 1 then transform := s"scale($scale)" else emptyMod,
    )

  def smallIcon(icon: (Int, Int), scale: Double = 1) =
    div(
      className := "icon",
      pointerEvents := "none",
      display := "inline-block",
      margin := "-16px -18px -16px -14px",
      verticalAlign := "middle",
      backgroundImage := "url(img/icons.png?v=2.031)",
      backgroundPosition := s"${-icon._1 * 48}px ${-icon._2 * 48}px",
      transform := s"scale(0.5)",
    )

  lazy val line: HtmlElement = div(
    className := "line"
  )
}
