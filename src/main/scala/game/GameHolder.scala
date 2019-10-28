package game

import graphic.{Color, GraphicContext, Point}
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.collection.mutable

class GameHolder(canvas: HTMLCanvasElement, gameMaker: (Point, () => Unit) => Game) {
  private[this] val keys = mutable.Set.empty[Int]

  val graphicContext = GraphicContext(
    canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D],
    Point(canvas.width, canvas.height)
  )

  var game: Game = gameMaker(graphicContext.bounds, () => resetGame())

  canvas.addEventListener("keydown", keyPressed _, useCapture = false)
  canvas.addEventListener("keyup", keyReleased _, useCapture = false)

  def keyPressed(e: dom.KeyboardEvent) = {
    keys.add(e.keyCode.toInt)
    e.preventDefault()
    message = None
  }

  def keyReleased(e: dom.KeyboardEvent) = {
    keys.remove(e.keyCode.toInt)
    e.preventDefault()
  }

  canvas.onfocus = { (e: dom.FocusEvent) =>
    active = true
  }
  canvas.onblur = { (e: dom.FocusEvent) =>
    active = false
  }

  var active = false
  var firstFrame = false

  def update() = {
    if (!firstFrame) {
      game.draw(graphicContext)
      firstFrame = true
    }
    if (active && message.isEmpty) {
      game.draw(graphicContext)
      game.update(keys.toSet)
    } else if (message.isDefined) {
      import graphicContext._
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
    game = gameMaker(graphicContext.bounds, () => resetGame())
  }

  graphicContext.ctx.font = "12pt Arial"
  graphicContext.ctx.textAlign = "center"
}
