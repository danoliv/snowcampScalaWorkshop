package tanks

import game.{Game, Key}
import graphic.{Color, GraphicContext, Point}

case class Tanks(bounds: Point, resetGame: () => Unit) extends Game {

  val name = "TANKS"
  var result: Option[String] = None
  val tankSize = Point(bounds.y / 12, bounds.y / 24)

  var level = 1

  var myTank = MyTank(
    position = Point(tankSize.x, bounds.y / 2),
    angle = 0,
    size = tankSize
  )

  var enemies = initEnemies(level)

  def initEnemies(num: Int) = {
    println(s"init $num enemies")
    val space = bounds.y / (num + 1)

    (1 to num).map { i =>
      EnemyVehicle(
        position = Point(bounds.x - tankSize.x, space * i),
        angle = math.Pi,
        size = tankSize
      )
    }
  }

  var bullets: Set[Bullet] = Set()

  override def update(pressedKeys: Set[Int], releasedKeys: Set[Int]): Unit = {
    val (updatedTank, newBullets) = myTank.update(pressedKeys, releasedKeys, bounds)

    val oldBullets = bullets
      .map(_.update)
      .filter(_.position.within(bounds))

    val movedEnemies = enemies.map(_.update(myTank.position, bounds))

    val updatedBullets = oldBullets ++ newBullets

    val destroyed = movedEnemies.filter(enemy =>
      updatedBullets.exists(_.collide(enemy))
    )

    val remainingEnemies = (movedEnemies diff destroyed)

    //check collisions
    if (movedEnemies.exists(_.collide(myTank))) {
      result = Some("GAME OVER")
      resetGame()
    } else {
      bullets = updatedBullets
      myTank = updatedTank
      enemies = if (remainingEnemies.isEmpty) {
        level += 1
        bullets = Set()
        initEnemies(level)
      } else remainingEnemies
    }
  }

  override def draw(graphicContext: GraphicContext): Unit = {
    graphicContext.op { (ctx, bounds) =>
      ctx.fillStyle = Color.Black
      ctx.fillRect(0, 0, bounds.x, bounds.y)

      ctx.fillStyle = Color.White
      ctx.strokeStyle = Color.White
    }
    myTank.draw(graphicContext)

    enemies.foreach(_.draw(graphicContext))
    bullets.foreach(_.draw(graphicContext))
  }
}
