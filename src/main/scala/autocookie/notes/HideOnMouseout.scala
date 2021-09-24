package autocookie.notes

import cookieclicker.Game

trait HideOnMouseout { this: Note =>
  html.onmouseout = (mouseEvent) => Game.tooltip.shouldHide = true
}
