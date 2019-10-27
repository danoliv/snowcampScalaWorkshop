package graphic

import org.scalajs.dom.CanvasRenderingContext2D

object Draw {

  def stroke(block: Any => Unit)(implicit ctx: CanvasRenderingContext2D) : Unit = {
    ctx.beginPath()
    block()
    ctx.stroke()
  }

  def line(from: Point, to: Point)(implicit ctx: CanvasRenderingContext2D): Unit = stroke { _ =>
    ctx.moveTo(from.x, from.y)
    ctx.lineTo(to.x, to.y)
  }

  def circle(center: Point, r: Double)(implicit ctx: CanvasRenderingContext2D) : Unit = stroke { _ =>
    ctx.arc(center.x, center.y, r, 0, 2 * Math.PI)
  }

  def rectangle(center: Point, w:Double, h:Double)(implicit ctx: CanvasRenderingContext2D) : Unit = stroke { _ =>
    ctx.rect(center.x - w/2, center.y - h/2, w, h)
  }

  def rotate(angle: Double, center: Point)(implicit ctx: CanvasRenderingContext2D): Unit = {
    ctx.translate(center.x, center.y)
    ctx.rotate(angle / 360.0 * math.Pi)
    ctx.translate(-center.x, -center.y)
  }

}
