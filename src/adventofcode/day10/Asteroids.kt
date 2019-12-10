package adventofcode.day10

import java.awt.geom.Line2D
import java.awt.geom.Point2D

//.A..#
//.....
//###Zz
//....#
//...##
//z d = 0.0 for Line(start=Coordinate(x=1, y=0), end=Coordinate(x=4, y=3)), blocking: Coordinate(x=3, y=2)


//s d = 0.0 for Line(start=Coordinate(x=1, y=0), end=Coordinate(x=3, y=4)), blocking: Coordinate(x=2, y=2)


data class Coordinate(val x: Int, val y: Int) {
    fun lineTo(other: Coordinate) = Line(this, other)
    internal fun toPoint(): Point2D = Point2D.Double(x.toDouble(), y.toDouble())
    infix fun isNotBlocking(line: Line) = !line.isOnSegment(this)
}

data class Line(val start: Coordinate, val end: Coordinate) {
    //    fun isOnSegment(point: Coordinate): Boolean = toLine().ptSegDist(point.toPoint()) == 0.0
    fun isOnSegment(point: Coordinate): Boolean {
        val ptSegDist = toLine().ptSegDist(point.toPoint())
//        println("  d = $ptSegDist for $this, blocking: ${point}")
        return ptSegDist == 0.0
    }

    private fun toLine() = Line2D.Double(start.toPoint(), end.toPoint())

    val height get() = end.y.toDouble() - start.y
    val width get() = end.x.toDouble() - start.x
}

class Scan(private val asteroids: Set<Coordinate>) {
    fun nrOfDetectableAsteroidsFromBestLocation(): Int = asteroids.map { visibleAsteroids(it) }.max()!!
    // for possibleStation in asteroids
    //   count possiblyVisible in asteroids without possibleStation
    //     exclude any if block view: check possiblyBlocking in asteroids without possibleStation and possiblyVisible
    //         if in direct path between possibleStation and possiblyVisible
    private fun visibleAsteroids(station: Coordinate): Int {
        val possiblyVisible = asteroids.filterNot { it == station }
        val count = possiblyVisible.count { target ->
            val lineOfSight = station.lineTo(target)
            val possiblyBlocking = possiblyVisible.filterNot { it == target }
            val blocking = possiblyBlocking.firstOrNull { lineOfSight.isOnSegment(it) }
            if (blocking != null)
                println("line(${station.x},${station.y}, ${target.x},${target.y}); line(${station.x},${station.y}, ${blocking.x},${blocking.y}, True)")
//            println("  for $lineOfSight, blocking: $blocking")
            possiblyBlocking.all { it isNotBlocking lineOfSight }
        }
        println("#for $station, $count")
        return count
    }
}


fun String.toScan(): Scan = Scan(this.trimIndent()
    .lines().mapIndexed { y, line ->
        line.toCharArray().mapIndexed { x, char ->
            if (char == '#') Coordinate(x, y) else null
        }
    }
    .flatten().filterNotNull().toSet()
)
