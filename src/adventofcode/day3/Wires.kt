package adventofcode.day3

import adventofcode.day3.Direction.*
import kotlin.math.absoluteValue

fun Iterable<Point>.closestToCentralPort() = this.minBy { it.distanceFromCentralPort() }

class Wire(path: String) {
    private val lines = path.toLines()

    private fun allPoints(): Set<Point> = lines.flatMap { it.allPointsExcludingStart() }
        .filter { it.distanceFromCentralPort() != 0 }.toSet()

    infix fun intersections(other: Wire) = this.allPoints() intersect other.allPoints()
}

class Line internal constructor(private val start: Point, private val instruction: Instruction) {
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

data class Point(private val x: Int, private val y: Int) {
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
private fun String.toLines(): List<Line> {
    var currentPoint = Point(0, 0)
    return split(",").map(::Instruction).map { instruction ->
        val prevPoint = currentPoint
        val nextPoint = prevPoint + instruction
        currentPoint = nextPoint
        Line(prevPoint, instruction)
    }
}

internal data class Instruction internal constructor(internal val direction: Direction, internal val distance: Int) {
    internal constructor(instruction: String) : this(
        direction = valueOf(instruction[0].toString()),
        distance = instruction.substring(1).toInt()
    )
}

internal enum class Direction { R, L, U, D }
