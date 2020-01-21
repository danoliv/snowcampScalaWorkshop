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

  def shoot(bulletSpeed: Double): Bullet = {
    val startPosition = position + Point(size.x / 2.0, 0).rotate(angle)
    val momentum = Point(bulletSpeed, 0).rotate(angle)
    Bullet(startPosition, momentum)
  }

  // TODO 4) compute the new position by combining up and down keys
  def computePosition(pressedKeys: Set[Int]): Point = position

  // TODO 5) compute the new angle by combining left and right keys
  def computeAngle(pressedKeys: Set[Int]): Double = angle

  def update(pressedKeys: Set[Int], releasedKeys: Set[Int], bounds: Point): (MyTank, Set[Bullet]) = {
    import Key._
    val newPosition = computePosition(pressedKeys)

    val newAngle = computeAngle(pressedKeys)

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
