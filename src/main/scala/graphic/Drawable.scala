package graphic

import org.scalajs.dom

trait Drawable {

  def draw(ctx: GraphicContext) : Unit

}
