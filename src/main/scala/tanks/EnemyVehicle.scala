package tanks

import graphic.Draw._
import graphic.{GraphicContext, Point}

case class EnemyVehicle(position: Point, angle: Double, size: Point) extends Vehicle {

  val maxSpeed = 1

  def degrees(radiants: Double) = radiants * 180 / math.Pi

  def update(targetPosition: Point, bounds: Point): EnemyVehicle = {

    val limitedAngle = {
      val diffAngle = position.angle(targetPosition) - angle

      val absAngle = math.abs(diffAngle)
      val signum = math.signum(diffAngle)
      absAngle.min(stepRotation) * (if (absAngle < math.Pi) signum else -signum)
    }

    copy(
      angle = rotation(angle, limitedAngle),
      position = move(position, maxSpeed, bounds)
    )
  }

  override def draw(graphicContext: GraphicContext): Unit = {
    super.draw(graphicContext)
    val center = position
    implicit val implicitCanvas: Canvas = graphicContext.ctx

    autoRestore {
      rotate(angle, center)
      rectangle(center, size.y, size.y)
    }
  }
}
