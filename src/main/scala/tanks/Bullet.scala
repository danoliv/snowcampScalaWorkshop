package tanks

import graphic._
import graphic.Draw._

case class Bullet(position: Point, momentum: Point, radius : Double = 5, acceleration: Double = 0.02)
  extends Drawable {

  val size = Point(radius, radius)

  def update: Bullet = Bullet(position + momentum, momentum * (1 + acceleration))

  override def boundingBox(): Box = {
    Box(position - size, position + size)
  }

  override def draw(gc: GraphicContext): Unit = {
    super.draw(gc)
    circle(position, radius)(gc.ctx)
  }
}