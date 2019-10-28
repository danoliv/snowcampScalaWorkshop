package game

import graphic.GraphicContext

abstract class Game {
  var result: Option[String] = None

  def update(keys: Set[Int]): Unit

  def draw(ctx: GraphicContext): Unit

}
