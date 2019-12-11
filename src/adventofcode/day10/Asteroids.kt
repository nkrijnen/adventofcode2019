package adventofcode.day10

import java.awt.geom.Line2D
import java.awt.geom.Point2D
import kotlin.math.PI
import kotlin.math.atan2

data class Coordinate(val x: Int, val y: Int) {
    fun lineTo(other: Coordinate) = Line(this, other)
    internal fun toPoint(): Point2D = Point2D.Double(x.toDouble(), y.toDouble())
    infix fun isNotBlocking(line: Line) = !line.isOnSegment(this)
    operator fun minus(other: Coordinate): Coordinate = Coordinate(this.x - other.x, this.y - other.y)
    fun laserRotationTo(asteroid: Coordinate): Double = (asteroid - this)
        .let { atan2(it.y.toDouble(), it.x.toDouble()) + 0.5 * PI }
        .let { if (it < 0) it + 2 * PI else it }.degrees

    override fun toString() = "${x * 100 + y}"
}

data class Line(val start: Coordinate, val end: Coordinate) {
    fun isOnSegment(point: Coordinate) = toLine().ptSegDist(point.toPoint()) == 0.0

    private fun toLine() = Line2D.Double(start.toPoint(), end.toPoint())

    val height get() = end.y.toDouble() - start.y
    val width get() = end.x.toDouble() - start.x
}

data class Target(val asteroid: Coordinate, var rotationDelta: Double)

class Scan(private val asteroids: Set<Coordinate>) {
    fun nrOfDetectableAsteroidsFromBestLocation(): Int = bestStationLocation().second
    private fun bestLocation(): Coordinate = bestStationLocation().first
    private fun bestStationLocation(): Pair<Coordinate, Int> =
        asteroids.map { Pair(it, visibleAsteroids(it)) }.maxBy { it.second }!!

    private fun visibleAsteroids(station: Coordinate): Int {
        val possiblyVisible = asteroids.filterNot { it == station }
        return possiblyVisible.count { target ->
            val lineOfSight = station.lineTo(target)
            val possiblyBlocking = possiblyVisible.filterNot { it == target }
            possiblyBlocking.all { it isNotBlocking lineOfSight }
        }
    }

    // 1. find station
    // 2. enrich each target with: rotation-delta = angle between line going from station UP and asteroid and distance from station
    //      bring asteroid coordinate into station-coordinates (station = 0,0), line up = 0,0->0,-10, line to target = 0,0->(asteroid - station)
    //      angle = atan2( -dy / dx ) angle can be in RAD
    //      probably write some unit tests to test angle for some obvious coordinates (check if the are ok)
    // 3. group by angle, for each group, first gets 0, second +360 (2*PI), third +720, etc added to their rotation-delta
    // 4. sort by rotation-delta
    // 5. grab [199]
    fun findVaporizedAsteroid200(): Coordinate {
        val station = bestLocation()
        val targets = asteroids.filterNot { it == station }.map { Target(it, station.laserRotationTo(it)) }
        targets.groupBy { it.rotationDelta }.filter { it.value.size > 1 }.forEach { (_, targets) ->
            targets.withIndex().forEach { it.value.rotationDelta += it.index * 360 }
        }
        val sortedTargets = targets.sortedBy { it.rotationDelta }
        return sortedTargets[199].asteroid
    }
}

val Double.degrees: Double get() = this * 180 / PI

fun String.toScan(): Scan = Scan(this.trimIndent()
    .lines().mapIndexed { y, line ->
        line.toCharArray().mapIndexed { x, char ->
            if (char == '#') Coordinate(x, y) else null
        }
    }
    .flatten().filterNotNull().toSet()
)
