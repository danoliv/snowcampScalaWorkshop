package game

import graphic.{Color, GraphicContext, Point}
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLCanvasElement

import scala.collection.mutable

class GameHolder(canvas: HTMLCanvasElement, gameMaker: (Point, () => Unit) => Game) {
  private[this] val pressedKeys = mutable.Set.empty[Int]
  private[this] val releasedKeys = mutable.Set.empty[Int]

  val graphicContext = GraphicContext(
    ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D],
    bounds = Point(canvas.width, canvas.height),
    debug = false
  )

  var game: Game = gameMaker(graphicContext.bounds, () => resetGame())

  canvas.addEventListener("keydown", keyPressed _, useCapture = false)
  canvas.addEventListener("keyup", keyReleased _, useCapture = false)

  def keyPressed(e: dom.KeyboardEvent) = {
    pressedKeys.add(e.keyCode)
    e.preventDefault()
    if (e.keyCode == Key.enter) {
      message = None
    }
  }

  def keyReleased(e: dom.KeyboardEvent) = {
    pressedKeys.remove(e.keyCode)
    releasedKeys.add(e.keyCode)
    e.preventDefault()
  }

  canvas.onfocus = { (e: dom.FocusEvent) =>
    active = true
  }
  canvas.onblur = { (e: dom.FocusEvent) =>
    active = false
  }

  var active = false
  var firstFrame = true

  def update() = {
    if (firstFrame) {
      message = Some(game.name)
      game.draw(graphicContext)
      firstFrame = false
    }

    if (active && message.isEmpty) {
      game.draw(graphicContext)
      game.update(pressedKeys.toSet, releasedKeys.toSet)
      releasedKeys.clear()
    } else if (message.isDefined) {
      import graphicContext._
      ctx.fillStyle = Color.Black
      ctx.fillRect(0, 0, bounds.x, bounds.y)
      ctx.fillStyle = Color.White
      ctx.font = "20pt Arial"
      ctx.textAlign = "center"
      ctx.fillText(message.get, bounds.x / 2, bounds.y / 2)
      ctx.font = "14pt Arial"
      ctx.fillText("Press return to continue", bounds.x / 2, bounds.y / 2 + 30)
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
