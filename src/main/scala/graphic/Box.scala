package graphic
import Draw._

case class Box(topLeft: Point, bottomRight: Point) extends Drawable {

  def center: Point = Point( (topLeft.x + bottomRight.x) / 2.0, (topLeft.y + bottomRight.y) / 2.0 )

  def limit(min: Point, max: Point) : Box = {
    val diffTopLeft = topLeft.limit(min,max) - topLeft
    val diffBottomRight = bottomRight.limit(min,max) - bottomRight

    Box(topLeft + diffTopLeft + diffBottomRight, bottomRight + diffTopLeft + diffBottomRight)
  }

  override def draw(graphicContext: GraphicContext): Unit = {
    graphicContext.op { (ctx, bounds) =>
      implicit val implicitCanvas = ctx
      autoRestore {
        ctx.globalAlpha = 0.5
        stroke {
          ctx.rect(topLeft.x, topLeft.y, bottomRight.x - topLeft.x, bottomRight.y - topLeft.y)
        }
        line(topLeft, bottomRight)
      }
    }
  }
}
