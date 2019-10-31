package tanks

import graphic.Draw._
import graphic.{Box, Drawable, GraphicContext, Point}

case class Tank(position: Point, angle: Double, size: Point, bounds: Point)
  extends Drawable {

  def shoot(speed: Double): Bullet = {
    val momentum = Point(speed, 0).rotate(angle)
    Bullet(position, momentum)
  }

  def boundingBox() : Box = Tank.boundingBox(position, angle, size)

  def move(speed: Double): Tank = {
    val movement = Point(speed, 0).rotate(angle)
    val newPos = (position + movement)
    val newBox = Tank.boundingBox(newPos, angle, size).limit(Point(0, 0), bounds)

    this.copy(position = newBox.center)
  }

  def rotation(rotation: Double): Tank = {
    val newAngle = angle + rotation
    this.copy(angle = newAngle)
  }

  override def draw(ctx: GraphicContext): Unit = {
    val center = position
    implicit val implicitCanvas: Canvas = ctx.ctx

    autoRestore {
      rotate(angle, center)
      rectangle(center, size.x, size.y)
      circle(center, size.x * 0.2)
      line(center, center.copy(x = center.x + size.x * 0.35))
    }

    boundingBox().draw(ctx)
  }
}

object Tank {

  def apply(position: Point, angle: Double, size: Point, bounds: Point) : Tank = {
    val box = boundingBox(position, angle, size).limit(Point(0, 0), bounds)

    new Tank(box.center, angle, size, bounds)
  }

  def boundingBox(position: Point, angle: Double, size: Point): Box = {
    val (seqX, seqY) = Seq(
      Point(size.x / 2.0, -size.y / 2.0),
      Point(size.x / 2.0, size.y / 2.0),
      Point(-size.x / 2.0, size.y / 2.0),
      Point(-size.x / 2.0, -size.y / 2.0)
    ).map(_.rotate(angle) + position)
      .map(p => (p.x, p.y))
      .unzip

    Box(
      Point(seqX.min, seqY.min),
      Point(seqX.max, seqY.max)
    )
  }
}