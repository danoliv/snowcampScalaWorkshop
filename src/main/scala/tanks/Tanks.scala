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

  def initEnemies(num: Int): Seq[EnemyVehicle] = {
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

  //TODO 1) update bullets and return only bullets within bounds
  def updateBullets: Set[Bullet] = bullets

  //TODO 2) update enemies positions
  def updateEnemies: Seq[EnemyVehicle] = enemies

  // TODO 3) return only destroyed enemies, use collide method
  def destroyEnemies: Seq[EnemyVehicle] = Seq()

  override def update(pressedKeys: Set[Int], releasedKeys: Set[Int]): Unit = {
    val (updatedTank, shootedBullets) = myTank.update(pressedKeys, releasedKeys, bounds)

    val movedEnemies = updateEnemies

    val newBullets = updateBullets ++ shootedBullets

    val destroyed = destroyEnemies

    val remainingEnemies = (movedEnemies diff destroyed)

    //check collisions
    if (movedEnemies.exists(_.collide(myTank))) {
      result = Some("GAME OVER")
      resetGame()
    } else {
      bullets = newBullets
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
