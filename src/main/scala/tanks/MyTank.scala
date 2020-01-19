package tanks

import game.Key
import graphic.Draw._
import graphic.{GraphicContext, Point}

case class MyTank(
                   position: Point,
                   angle: Double,
                   size: Point
                 ) extends Vehicle {

  val maxSpeed = 2.5

  def shoot(speed: Double): Bullet = {
    val momentum = Point(speed, 0).rotate(angle)
    Bullet(position, momentum)
  }

  def update(pressedKeys: Set[Int], releasedKeys: Set[Int], bounds: Point): (MyTank, Set[Bullet]) = {
    import Key._
    val newPosition = pressedKeys.foldLeft(position) { case (currPosition, key) =>
      key match {
        case `up` => move(currPosition, maxSpeed, bounds)
        case `down` => move(currPosition, -maxSpeed, bounds)
        case _ => currPosition
      }
    }

    val newAngle = pressedKeys.foldLeft(angle) { case (currAngle, key) =>
      key match {
        case `left` => rotation(currAngle, -stepRotation)
        case `right` => rotation(currAngle, stepRotation)
        case _ => currAngle
      }
    }

    val newTank = copy(position = newPosition, angle = newAngle)

    val newBullets = releasedKeys.flatMap {
      case `space`  => Some(shoot(2 * maxSpeed))
      case _ => None
    }

    (newTank, newBullets)
  }

  override def draw(graphicContext: GraphicContext): Unit = {
    super.draw(graphicContext)

    val center = position
    implicit val implicitCanvas: Canvas = graphicContext.ctx

    autoRestore {
      rotate(angle, center)
      circle(center, size.x * 0.2)
      line(center, center.copy(x = center.x + size.x * 0.35))
    }
  }
}
