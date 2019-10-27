package tanks

import game.{Game, Key}
import graphic.{Color, Drawable, Point}
import org.scalajs.dom.CanvasRenderingContext2D
import graphic.Draw._

case class Tank(x: Int, y: Int, angle: Double, grid: Grid) extends Drawable {

  def position(bounds: Point) = {
    val (cornerFrom, cornerTo) = grid.cell(x, y, bounds)
    val size = grid.cellSize(bounds)

    cornerFrom + size / 2
  }

  override def draw(ctx: CanvasRenderingContext2D, bounds: Point): Unit = {
    val size = grid.cellSize(bounds)
    val center = position(bounds)
    implicit val implicitCtx: CanvasRenderingContext2D = ctx

    rotate(angle, center)
    rectangle(center, size.x * 0.8, size.x * 0.5)
    circle(center, size.x * 0.2)
    line(center, center.copy(x = center.x + size.x * 0.35))

    rotate(-angle, center)
  }
}

case class Bullet(position: Point, momentum: Point) extends Drawable {

  def update : Bullet = Bullet(position + momentum, momentum)

  override def draw(ctx: CanvasRenderingContext2D, bounds: Point): Unit = {
    circle(position, bounds.x / 100)(ctx)
  }
}

case class Tanks(bounds: Point, resetGame: () => Unit) extends Game {
  val grid = Grid(16, 16)
  var bullets = Seq.empty[Bullet]
  var myTank = Tank(0, 0, 180, grid)

  println(s"bounds: $bounds")

  override def update(keys: Set[Int]): Unit = {
    import Key._
    myTank = keys.foldLeft(myTank) { case (t, key) =>
      key match {
        case `up` => t.copy(y = (t.y - 1).max(0))
        case `down` => t.copy(y = (t.y + 1).min(grid.lines - 1))
        case `left` => t.copy(x = (t.x - 1).max(0))
        case `right` => t.copy(x = (t.x + 1).min(grid.columns - 1))
        case `space` => t
      }
    }

    bullets = bullets.map(_.update)
  }

  override def draw(ctx: CanvasRenderingContext2D): Unit = {
    ctx.fillStyle = Color.Black
    ctx.fillRect(0, 0, bounds.x, bounds.y)

    ctx.fillStyle = Color.White
    ctx.strokeStyle = Color.White

    grid.draw(ctx, bounds)
    myTank.draw(ctx, bounds)
    //    (0 until grid.columns).foreach(x =>
    //      (0 until grid.lines).foreach(y =>
    //        Tank(x, y, grid).draw(ctx, bounds)
    //      )
    //    )
  }
}
