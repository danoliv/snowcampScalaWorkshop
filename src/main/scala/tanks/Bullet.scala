package tanks

import graphic._
import graphic.Draw._

case class Bullet(position: Point, momentum: Point, acceleration: Double = 0.02) extends Drawable {

  def update: Bullet = Bullet(position + momentum, momentum * (1 + acceleration))

  override def draw(ctx: GraphicContext): Unit = {
    circle(position, ctx.bounds.y / 100)(ctx.ctx)
  }
}