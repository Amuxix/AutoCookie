package autocookie.notes

import cookieclicker.Game

import scala.concurrent.duration.*
import autocookie.Helpers.*
import autocookie.reserve.ReserveLevel.Disabled
import autocookie.reserve.{Reserve, ReserveLevel}
import org.scalajs.dom.document

object GoldenCookieSpawnNote extends Note:
  def setGolden(): Unit =
    html.style.colorInterpolationFilters = "invert(78%) sepia(94%) saturate(1828%) hue-rotate(330deg) brightness(95%) contrast(93%)"

  def clearGolden(): Unit =
    html.style.colorInterpolationFilters = ""

  override def update(): Unit =
    if Reserve.level == Disabled then
      hide()
      return
    else
      show()

    if Game.shimmers.nonEmpty then
      setGolden()
      val description = (Game.shimmers.head.life / Game.fps).seconds.prettyPrint
      setTitle("Cookie despawning in")
      setDescription(description)
      document.title = description + " until cookie despawn" //Set page name
    else
      clearGolden()
      if Game.shimmerTypes.golden.minTime > Game.shimmerTypes.golden.time then
        setTitle("Next cookie spawn window in")
        setDescription(((Game.shimmerTypes.golden.minTime - Game.shimmerTypes.golden.time) / Game.fps).seconds.prettyPrint)
      else
        setTitle("Cookie spawn window ends in")
        setDescription(((Game.shimmerTypes.golden.maxTime - Game.shimmerTypes.golden.time) / Game.fps).seconds.prettyPrint)