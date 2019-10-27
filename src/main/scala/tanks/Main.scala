package tanks

import game.GameHolder
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js.timers

object Main {

  def main(args: Array[String]): Unit = {
    val d = dom.document
    val canvas = d.getElementById("canvas").asInstanceOf[html.Canvas]

    val tanks = new GameHolder(canvas, Tanks)
    timers.setInterval(15)({
      tanks.update()
    })
  }
}
