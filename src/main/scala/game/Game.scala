package game

import graphic.GraphicContext

abstract class Game {

  def name: String

  def result: Option[String]

  def update(pressedKeys: Set[Int], releasedKeys: Set[Int]): Unit

  def draw(ctx: GraphicContext): Unit

}
