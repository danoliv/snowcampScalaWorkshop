package game

import graphic.{Color, Point}
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.collection.mutable

class GameHolder(canvas: HTMLCanvasElement, gameMaker: (Point, () => Unit) => Game) {
  private[this] val bounds = Point(canvas.width, canvas.height)
  private[this] val keys = mutable.Set.empty[Int]
  var game: Game = gameMaker(bounds, () => resetGame())

  canvas.onkeydown = { (e: dom.KeyboardEvent) =>
    keys.add(e.keyCode.toInt)
    if (Seq(32, 37, 38, 39, 40).contains(e.keyCode.toInt)) e.preventDefault()
    message = None
  }
  canvas.onkeyup = { (e: dom.KeyboardEvent) =>
    keys.remove(e.keyCode.toInt)
    if (Seq(32, 37, 38, 39, 40).contains(e.keyCode.toInt)) e.preventDefault()
  }

  canvas.onfocus = { (e: dom.FocusEvent) =>
    active = true
  }
  canvas.onblur = { (e: dom.FocusEvent) =>
    active = false
  }

  private[this] val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
  var active = false
  var firstFrame = false

  def update() = {
    if (!firstFrame) {
      game.draw(ctx)
      firstFrame = true
    }
    if (active && message.isEmpty) {
      game.draw(ctx)
      game.update(keys.toSet)
      keys.clear()
    } else if (message.isDefined) {
      ctx.fillStyle = Color.Black
      ctx.fillRect(0, 0, bounds.x, bounds.y)
      ctx.fillStyle = Color.White
      ctx.font = "20pt Arial"
      ctx.textAlign = "center"
      ctx.fillText(message.get, bounds.x / 2, bounds.y / 2)
      ctx.font = "14pt Arial"
      ctx.fillText("Press any key to continue", bounds.x / 2, bounds.y / 2 + 30)
    }
  }

  var message: Option[String] = None

  def resetGame(): Unit = {
    message = game.result
    game = gameMaker(bounds, () => resetGame())
  }

  ctx.font = "12pt Arial"
  ctx.textAlign = "center"
}
