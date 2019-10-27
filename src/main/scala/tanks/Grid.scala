package tanks

import game.Game
import graphic.{Color, Drawable, Point}
import org.scalajs.dom.CanvasRenderingContext2D
import graphic.Draw._

case class Grid(columns: Int, lines: Int) extends Drawable {

  def cellSize(bounds: Point) : Point = Point(
    (bounds.x / columns).toInt,
    (bounds.y / lines).toInt
  )

  def cell(x: Int, y: Int, bounds: Point) : (Point, Point) = {
    val cellBounds = cellSize(bounds)

    val cornerFrom = Point(x, y) * cellBounds
    val cornerTo = cornerFrom + cellBounds

    (cornerFrom, cornerTo)
  }

  override def draw(ctx: CanvasRenderingContext2D, bounds: Point): Unit = {
    val Point(cellSizeX, cellSizeY) = cellSize(bounds)
    implicit val implicitContext: CanvasRenderingContext2D = ctx
    (1 until columns).foreach { step =>
      val currentX = step * cellSizeX
      line(Point(currentX, 0), Point(currentX, bounds.y))
    }
    (1 until lines).foreach { step =>
      val currentY = step * cellSizeY
      line(Point(0, currentY), Point(bounds.x, currentY))
    }
  }
}