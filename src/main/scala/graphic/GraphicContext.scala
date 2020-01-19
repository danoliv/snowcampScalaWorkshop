package graphic

import org.scalajs.dom

case class GraphicContext(ctx: dom.CanvasRenderingContext2D, bounds: Point, debug: Boolean = false) {

  def op( func : (dom.CanvasRenderingContext2D, Point) => Unit) : Unit = func(ctx, bounds)

}
