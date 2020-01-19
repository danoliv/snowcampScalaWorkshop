package tanks

import graphic.Draw._
import graphic.{Box, Drawable, GraphicContext, Point}

trait Vehicle extends Drawable {

  def position: Point
  def angle: Double
  def size: Point
  def maxSpeed: Double

  val stepRotation = math.Pi / 64

  def boundingBox() : Box = boundingBox(position, angle, size)

  private def boundingBox(position: Point, angle: Double, size: Point): Box = {
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

  def move(position: Point, speed: Double, bounds: Point): Point = {
    val movement = Point(speed, 0).rotate(angle)
    val newPos = (position + movement)
    boundingBox(newPos, angle, size)
      .limit(Point(0, 0), bounds)
      .center
  }

  def rotation(angle:Double, rotation: Double): Double = {
    (angle + rotation + 2 * math.Pi) % (2 * math.Pi)
  }

  override def draw(graphicContext: GraphicContext): Unit = {
    super.draw(graphicContext)
    val center = position
    implicit val implicitCanvas: Canvas = graphicContext.ctx

    autoRestore {
      rotate(angle, center)
      rectangle(center, size.x, size.y)
    }
  }
}