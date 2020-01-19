package graphic

import graphic.Draw.{autoRestore, line, rectangle, stroke}

trait Drawable {

  def boundingBox(): Box

  def collide(that: Drawable): Boolean = this.boundingBox() intersect that.boundingBox()

  def draw(graphicContext: GraphicContext): Unit = {
    if (graphicContext.debug) {
      val box = boundingBox()

      graphicContext.op { (ctx, bounds) =>
        implicit val implicitCanvas = ctx
        autoRestore {
          ctx.globalAlpha = 0.5
          stroke {
            rectangle(box.topLeft, box.bottomRight)
          }
          line(box.topLeft, box.bottomRight)
        }
      }

    }
  }

}
