package graphic

import org.scalajs.dom.CanvasRenderingContext2D

object Draw {

  type Canvas = CanvasRenderingContext2D

  def autoRestore(block: => Unit)(implicit canvas: Canvas) : Unit = {
    canvas.save()
    block
    canvas.restore()
  }

  def stroke(block: => Unit)(implicit canvas: Canvas) : Unit = {
    canvas.beginPath()
    block
    canvas.stroke()
  }

  def line(from: Point, to: Point)(implicit canvas: Canvas): Unit = stroke {
    canvas.moveTo(from.x, from.y)
    canvas.lineTo(to.x, to.y)
  }

  def circle(center: Point, r: Double)(implicit canvas: Canvas) : Unit = stroke {
    canvas.arc(center.x, center.y, r, 0, 2 * Math.PI)
  }

  def rectangle(center: Point, w:Double, h:Double)(implicit canvas: Canvas) : Unit = stroke {
    canvas.rect(center.x - w/2, center.y - h/2, w, h)
  }

  def rotate(angle: Double, center: Point)(implicit canvas: Canvas): Unit = {
    canvas.translate(center.x, center.y)
    canvas.rotate(angle)
    canvas.translate(-center.x, -center.y)
  }

  def path(points: Point*)(implicit canvas: Canvas) = stroke {
    canvas.moveTo(points.last.x, points.last.y)
    for (p <- points) {
      canvas.lineTo(p.x, p.y)
    }
  }

}
