package adventofcode.day10

import java.awt.geom.Line2D
import java.awt.geom.Point2D

data class Coordinate(val x: Int, val y: Int) {
    fun lineTo(other: Coordinate) = Line(this, other)
    internal fun toPoint(): Point2D = Point2D.Double(x.toDouble(), y.toDouble())
    infix fun isNotBlocking(line: Line) = !line.isOnSegment(this)
}

data class Line(val start: Coordinate, val end: Coordinate) {
    fun isOnSegment(point: Coordinate) = toLine().ptSegDist(point.toPoint()) == 0.0

    private fun toLine() = Line2D.Double(start.toPoint(), end.toPoint())

    val height get() = end.y.toDouble() - start.y
    val width get() = end.x.toDouble() - start.x
}

class Scan(private val asteroids: Set<Coordinate>) {
    fun nrOfDetectableAsteroidsFromBestLocation(): Int = asteroids.map { visibleAsteroids(it) }.max()!!

    private fun visibleAsteroids(station: Coordinate): Int {
        val possiblyVisible = asteroids.filterNot { it == station }
        return possiblyVisible.count { target ->
            val lineOfSight = station.lineTo(target)
            val possiblyBlocking = possiblyVisible.filterNot { it == target }
            possiblyBlocking.all { it isNotBlocking lineOfSight }
        }
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
