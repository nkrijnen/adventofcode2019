package adventofcode.day20

import adventofcode.day20.CellType.*

fun String.toMaze(): PlutoMaze = PlutoMaze(this.lines().toTypedArray())

class PlutoMaze(private val maze: Array<String>) {

    private val mazeXRange = maze[0].indices
    private val mazeYRange = maze.indices
    private val cells = findCells()
    private var mazeStart: Cell
    private var mazeEnd: Cell

    init {
        findPortalsAndReturnStartAndEnd().apply {
            mazeStart = this.first
            mazeEnd = this.second
        }
        mazeStart.distanceFromStart = 0
        visit(mazeStart.surroundingVisitableCells(), 1)
    }

    fun shortestPathFromAAtoZZ(): Int {
        return mazeEnd.distanceFromStart!!
    }

    fun printPaths() {
        maze.mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                cellAt(Coordinate(x, y))?.distanceFromStart ?: c
            }.joinToString("\t")
        }.forEach(::println)
    }

    internal fun cellAt(coordinate: Coordinate): Cell? =
        if (coordinate.x in mazeXRange && coordinate.y in mazeYRange)
            cells[coordinate.x][coordinate.y]
        else
            null

    internal inner class Cell(internal val coordinate: Coordinate) {

        internal var distanceFromStart: Int? = null
        private var type: CellType = char().toCellType()
        internal var portalExit: Cell? = null

        internal fun char() = maze[coordinate.y][coordinate.x]

        internal fun surroundingCells(): List<Cell> = listOfNotNull(
            cellAt(coordinate.copy(y = coordinate.y - 1)),
            cellAt(coordinate.copy(y = coordinate.y + 1)),
            cellAt(coordinate.copy(x = coordinate.x - 1)),
            cellAt(coordinate.copy(x = coordinate.x + 1))
        )

        internal fun surroundingVisitableCells(): List<Cell> {
            val filter = surroundingCells()
                .filter { it.type == TRAVERSABLE || it.portalExit != null }
            return filter.map { it.portalExit ?: it }
        }

        internal fun surroundingTraversableCells(): List<Cell> =
            this.surroundingCells().filter { it.type == TRAVERSABLE }

        internal fun isLabel() = type == LABEL
    }

    private fun findCells(): List<List<Cell>> =
        mazeXRange.map { x ->
            mazeYRange.map { y ->
                Cell(Coordinate(x, y))
            }
        }

    internal fun surroundingVisitableCells(at: Coordinate) = cellAt(at)!!.surroundingVisitableCells()

    private tailrec fun visit(coordinates: Iterable<Cell>, pathLength: Int) {
        val toPursue = coordinates.filter {
            val existingPathLength = it.distanceFromStart
            if (existingPathLength == null || pathLength < existingPathLength) {
                it.distanceFromStart = pathLength
                true
            } else {
                // this path is same length or longer than previous route, so stop pursuing this route
                // the previous route already covered rest
                false
            }
        }
        val next = toPursue.flatMap { it.surroundingVisitableCells() }.toSet()
        if (next.isNotEmpty())
            visit(next, pathLength + 1)
    }

    private fun allCells(): List<Cell> =
        mazeXRange.map { x -> mazeYRange.map { y -> cellAt(Coordinate(x, y))!! } }.flatten()

    private fun findPortalsAndReturnStartAndEnd(): Pair<Cell, Cell> {
        val foundLabels = mutableMapOf<String, List<Cell>>()
        val found = mutableSetOf<Coordinate>()

        allCells().forEach { cell ->
            if (!found.contains(cell.coordinate) && cell.isLabel()) {
                val otherLabelPart = cell.surroundingCells().first { it.isLabel() }
                val labelCells = listOf(cell, otherLabelPart)
                found += labelCells.map { it.coordinate }
                val label = labelCells.map { it.char() }.sorted().joinToString("")
                val entrance = labelCells.flatMap { it.surroundingTraversableCells() }.first()
//                println("Found $entrance for $label at $labelCells")
                val foundOtherSide = foundLabels[label]
                if (foundOtherSide == null) foundLabels[label] = labelCells + entrance
                else {
                    foundOtherSide.subList(0, 2).forEach { it.portalExit = entrance }
                    labelCells.forEach { it.portalExit = foundOtherSide[2] }
                }
            }
        }

        return foundLabels["AA"]!![2] to foundLabels["ZZ"]!![2]
    }
}

data class Coordinate(val x: Int, val y: Int)

internal enum class CellType {
    TRAVERSABLE, NON_TRAVERSABLE, LABEL
}

private fun Char.toCellType(): CellType = when (this) {
    '.' -> TRAVERSABLE
    '#' -> NON_TRAVERSABLE
    ' ' -> NON_TRAVERSABLE
    else -> LABEL
}
