package adventofcode.day3

import adventofcode.day3.Direction.*
import kotlin.math.absoluteValue

fun distanceToIntersectionClosestToCentralPort(wire1: Wire, wire2: Wire) =
    (wire1 intersections wire2).closestToCentralPort()?.distanceFromCentralPort()

fun shortestRouteToCentralPortVia(wire1: Wire, wire2: Wire): Int {
    val intersections = wire1 intersections wire2

    val costsViaWire1 = wire1.intersectionCosts(intersections)
    val costsViaWire2 = wire2.intersectionCosts(intersections)

    fun combinedCost(intersection: Point): Int? {
        val a = costsViaWire1[intersection] ?: return null
        val b = costsViaWire2[intersection] ?: return null
        return a + b
    }

    return intersections.mapNotNull(::combinedCost).min()!!
}

private fun Iterable<Point>.closestToCentralPort() = this.minBy { it.distanceFromCentralPort() }

class Wire(path: String) {
    private val path = path.toLines()

    private fun allPoints(): Set<Point> = path.flatMap { it.allPointsExcludingStart() }.toSet()

    internal infix fun intersections(other: Wire) = this.allPoints() intersect other.allPoints()

    internal fun intersectionCosts(intersections: Set<Point>): Map<Point, SignalLoss> {
        val intersectionsWithCost = mutableMapOf<Point, SignalLoss>()
        var signalLoss = 0
        path.forEach { segment ->
            segment.allPointsExcludingStart().forEach { point ->
                signalLoss++
                if (point in intersections)
                    intersectionsWithCost.putIfAbsent(point, signalLoss)
            }
        }
        return intersectionsWithCost
    }
}

internal typealias SignalLoss = Int

internal class Segment internal constructor(private val start: Point, private val instruction: Instruction) {
    private val end: Point = start + instruction

    fun allPointsExcludingStart(): Set<Point> {
        val points = mutableSetOf<Point>()
        val step = instruction.copy(distance = 1)
        var next = start
        do {
            next += step
            points += next
        } while (next != end)
        return points.toSet()
    }
}

internal data class Point(private val x: Int, private val y: Int) {
    fun distanceFromCentralPort() = x.absoluteValue + y.absoluteValue

    internal operator fun plus(instruction: Instruction): Point {
        return when (instruction.direction) {
            R -> Point(x + instruction.distance, y)
            L -> Point(x - instruction.distance, y)
            U -> Point(x, y + instruction.distance)
            D -> Point(x, y - instruction.distance)
        }
    }
}

// R75,D30,R83,U83,L12
private fun String.toLines(): List<Segment> {
    var currentPoint = Point(0, 0)
    return split(",").map(::Instruction).map { instruction ->
        val prevPoint = currentPoint
        val nextPoint = prevPoint + instruction
        currentPoint = nextPoint
        Segment(prevPoint, instruction)
    }
}

internal data class Instruction internal constructor(internal val direction: Direction, internal val distance: Int) {
    internal constructor(instruction: String) : this(
        direction = valueOf(instruction[0].toString()),
        distance = instruction.substring(1).toInt()
    )
}

internal enum class Direction { R, L, U, D }
