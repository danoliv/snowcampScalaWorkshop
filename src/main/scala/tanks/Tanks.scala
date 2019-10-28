package tanks

import game.{Game, Key}
import graphic.{Box, Color, Draw, Drawable, GraphicContext, Point}
import graphic.Draw._

case class Tanks(bounds: Point, resetGame: () => Unit) extends Game {
  val speed = 2
  val rotation = math.Pi / 64
  var myTank = Tank(
    Point(0, 0), math.Pi / 2,
    Point(bounds.x / 16, bounds.y / 32),
    bounds
  )

  override def update(keys: Set[Int]): Unit = {
    import Key._
    if (keys.nonEmpty) println(keys)

    myTank = myTank.update()

    myTank = keys.foldLeft(myTank) { case (t, key) =>
      key match {
        case `up` => t.move(speed)
        case `down` => t.move(-speed)
        case `left` => t.rotation(-rotation)
        case `right` => t.rotation(rotation)
        case `space` => t.shoot(speed)
        case _ => t
      }
    }

  }

  override def draw(graphicContext: GraphicContext): Unit = {
    graphicContext.op { (ctx, bounds) =>
      ctx.fillStyle = Color.Black
      ctx.fillRect(0, 0, bounds.x, bounds.y)

      ctx.fillStyle = Color.White
      ctx.strokeStyle = Color.White
    }
    myTank.draw(graphicContext)
  }
}
