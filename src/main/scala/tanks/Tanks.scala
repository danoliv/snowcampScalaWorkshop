package tanks

import game.{Game, Key}
import graphic.{Box, Color, Draw, Drawable, GraphicContext, Point}
import graphic.Draw._

case class Tanks(bounds: Point, resetGame: () => Unit) extends Game {
  val speed = 2
  val rotation = math.Pi / 64
  var myTank = Tank(
    Point(bounds.x / 2.0, bounds.y / 2.0), math.Pi / 2,
    Point(bounds.y / 12, bounds.y / 24),
    bounds
  )

  var bullets: Set[Bullet] = Set()

  override def update(pressedKeys: Set[Int], releasedKeys: Set[Int]): Unit = {
    import Key._
    if (pressedKeys.nonEmpty) println(pressedKeys)

    myTank = pressedKeys.foldLeft(myTank) { case (t, key) =>
      key match {
        case `up` => t.move(speed)
        case `down` => t.move(-speed)
        case `left` => t.rotation(-rotation)
        case `right` => t.rotation(rotation)
        case _ => t
      }
    }

    val oldBullets = bullets
      .map(_.update)
      .filter(_.position.within(bounds))

    val newBullets = releasedKeys.flatMap {
      case `space`  => Some(myTank.shoot(2 * speed))
      case _ => None
    }

    bullets = oldBullets ++ newBullets
  }

  override def draw(graphicContext: GraphicContext): Unit = {
    graphicContext.op { (ctx, bounds) =>
      ctx.fillStyle = Color.Black
      ctx.fillRect(0, 0, bounds.x, bounds.y)

      ctx.fillStyle = Color.White
      ctx.strokeStyle = Color.White
    }
    myTank.draw(graphicContext)

    bullets.foreach(_.draw(graphicContext))
  }
}
