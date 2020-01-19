package graphic

case class Box(topLeft: Point, bottomRight: Point) {

  def center: Point = Point( (topLeft.x + bottomRight.x) / 2.0, (topLeft.y + bottomRight.y) / 2.0 )

  def limit(min: Point, max: Point) : Box = {
    val diffTopLeft = topLeft.limit(min,max) - topLeft
    val diffBottomRight = bottomRight.limit(min,max) - bottomRight

    Box(topLeft + diffTopLeft + diffBottomRight, bottomRight + diffTopLeft + diffBottomRight)
  }

  def corners = Seq(topLeft, topLeft.copy(y = bottomRight.y), bottomRight, bottomRight.copy(x = topLeft.x))

  def intersect(that: Box): Boolean = {
    corners.exists(_.within(that.topLeft, that.bottomRight))
  }
}
