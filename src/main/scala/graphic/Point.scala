package graphic

import scala.scalajs.js.Math

case class Point(x: Double, y: Double) {
  def +(other: Point) = Point(x + other.x, y + other.y)

  def -(other: Point) = Point(x - other.x, y - other.y)

  def %(other: Point) = Point(x % other.x, y % other.y)

  def <(other: Point) = x < other.x && y < other.y

  def >(other: Point) = x > other.x && y > other.y

  def /(value: Double) = Point(x / value, y / value)

  def *(value: Double) = Point(x * value, y * value)

  def *(other: Point) = Point(x * other.x, y * other.y)

  def dot(other: Point) = x * other.x + y * other.y

  def limit(min: Point, max: Point) = Point(
    x.max(min.x).min(max.x),
    y.max(min.y).min(max.y)
  )

  def angle(that: Point) : Double = {
    val posDiff = that - this
    val angle = math.atan2(posDiff.y, posDiff.x)
    if (angle < 0) 2.0 * math.Pi + angle // force angle to be positive
    else angle
  }

  def length = Math.sqrt(lengthSquared)

  def lengthSquared = x * x + y * y

  def within(a: Point, b: Point = Point(0, 0), extra: Point = Point(0, 0)) = {
    import math.{max, min}
    x >= min(a.x, b.x) - extra.x &&
      x <= max(a.x, b.x) + extra.y &&
      y >= min(a.y, b.y) - extra.x &&
      y <= max(a.y, b.y) + extra.y
  }

  def rotate(theta: Double) = {
    val (cos, sin) = (Math.cos(theta), math.sin(theta))
    Point(cos * x - sin * y, sin * x + cos * y)
  }
}
