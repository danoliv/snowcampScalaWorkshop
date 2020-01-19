package graphics

import graphic.Point
import utest._
import math.Pi

object PointTest extends TestSuite {

  def tests = Tests {

    'AngleRight - {

      val reference = Point(0, 50)

      val others = Seq(Point(50, 50), Point(50,0), Point(50,100))
      //val expected = Seq(0.0, -Pi/4.0, Pi/4.0)
      val expected = Seq(0.0, 7.0*Pi/4.0, Pi/4.0)

      val angles = others.map(other => reference.angle(other))
      assert(angles == expected)
    }

    'AngleLeft - {

      val reference = Point(100, 50)

      val others = Seq(Point(50, 50), Point(50,0), Point(50,100))
      //val expected = Seq(Pi, -3.0*Pi/4.0, 3.0*Pi/4.0)
      val expected = Seq(Pi, 5.0*Pi/4.0, 3.0*Pi/4.0)

      val angles = others.map(other => reference.angle(other))
      assert(angles == expected)
    }

  }

}
