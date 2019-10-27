package graphic

import org.scalajs.dom

trait Drawable {

  def draw(ctx: dom.CanvasRenderingContext2D, bounds: Point) : Unit

}
