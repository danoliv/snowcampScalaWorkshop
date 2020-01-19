package graphics

import graphic.{Box, Point}
import utest._

object BoxTest extends TestSuite {

  def tests = Tests {

    'WillNotIntersect - {
      assert(
        ! ( Box(Point(0, 0), Point(10, 10)) intersect Box(Point(5, 15), Point(15, 25)) )
      )

      assert(
        ! ( Box(Point(0, 0), Point(10, 10)) intersect Box(Point(15, 0), Point(25, 10)) )
      )
    }

    'WillIntersect - {
      assert(
        Box(Point(0, 0), Point(10, 10)) intersect Box(Point(5, 5), Point(15, 15))
      )

      assert(
        Box(Point(0, 0), Point(10, 10)) intersect Box(Point(5, 0), Point(15, 10))
      )
    }

  }

}
