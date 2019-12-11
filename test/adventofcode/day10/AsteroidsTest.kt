package adventofcode.day10

import org.junit.jupiter.api.Test
import kotlin.math.PI
import kotlin.math.atan
import kotlin.test.assertEquals

internal class AsteroidsTest {
    @Test
    fun `sample 1`() {
        val scan = """
            .#..#
            .....
            #####
            ....#
            ...##
        """.toScan()

        assertEquals(8, scan.nrOfDetectableAsteroidsFromBestLocation())
    }

    @Test
    fun calc() {
        val l = Line(start = Coordinate(x = 1, y = 0), end = Coordinate(x = 4, y = 3))
        val p = Coordinate(x = 3, y = 2)
        val lp = l.start.lineTo(p)
        assertEquals(0.7853981633974483, atan(l.height / l.width))
        assertEquals(0.7853981633974483, atan(lp.height / lp.width))
    }

    @Test
    fun `part 1`() {
        val bestLocation = scan.nrOfDetectableAsteroidsFromBestLocation()
        assertEquals(309, bestLocation)
    }

    @Test
    fun deg() {
        assertEquals(0.0, 0.0.degrees)
        assertEquals(90.0, (0.5 * PI).degrees)
        assertEquals(180.0, (PI).degrees)
        assertEquals(270.0, (1.5 * PI).degrees)
        assertEquals(360.0, (2 * PI).degrees)
        assertEquals(720.0, (4 * PI).degrees)
    }

    @Test
    fun coordinateMinus() {
        val station = Coordinate(2, 2)
        assertEquals(Coordinate(0, 0), Coordinate(2, 2) - station)
        assertEquals(Coordinate(0, 2), Coordinate(2, 4) - station)
        assertEquals(Coordinate(0, -1), Coordinate(2, 1) - station)
        assertEquals(Coordinate(-2, -1), Coordinate(0, 1) - station)
    }

    @Test
    fun laserRotation() {
        val station = Coordinate(2, 2)
        assertEquals(0.0, station.laserRotationTo(Coordinate(2, 0)))
        assertEquals(90.0, station.laserRotationTo(Coordinate(4, 2)))
        assertEquals(180.0, station.laserRotationTo(Coordinate(2, 4)))
        assertEquals(270.0, station.laserRotationTo(Coordinate(0, 2)))
    }

    @Test
    fun laserRotation2() {
        val station = Coordinate(37, 25)
        assertEquals(304.04593735660166, station.laserRotationTo(Coordinate(0, 0)))
        assertEquals(305.5376777919744, station.laserRotationTo(Coordinate(2, 0)))
        assertEquals(308.6598082540901, station.laserRotationTo(Coordinate(7, 1)))
    }

    @Test
    fun `part 2`() {
        val vaporizedAsteroid200 = scan.findVaporizedAsteroid200()
        assertEquals("416", vaporizedAsteroid200.toString())
    }
}

val scan = """
            #.#................#..............#......#......
            .......##..#..#....#.#.....##...#.........#.#...
            .#...............#....#.##......................
            ......#..####.........#....#.......#..#.....#...
            .....#............#......#................#.#...
            ....##...#.#.#.#.............#..#.#.......#.....
            ..#.#.........#....#..#.#.........####..........
            ....#...#.#...####..#..#..#.....#...............
            .............#......#..........#...........#....
            ......#.#.........#...............#.............
            ..#......#..#.....##...##.....#....#.#......#...
            ...#.......##.........#.#..#......#........#.#..
            #.............#..........#....#.#.....#.........
            #......#.#................#.......#..#.#........
            #..#.#.....#.....###..#.................#..#....
            ...............................#..........#.....
            ###.#.....#.....#.............#.......#....#....
            .#.....#.........#.....#....#...................
            ........#....................#..#...............
            .....#...#.##......#............#......#.....#..
            ..#..#..............#..#..#.##........#.........
            ..#.#...#.......#....##...#........#...#.#....#.
            .....#.#..####...........#.##....#....#......#..
            .....#..#..##...............................#...
            .#....#..#......#.#............#........##...#..
            .......#.....................#..#....#.....#....
            #......#..###...........#.#....#......#.........
            ..............#..#.#...#.......#..#.#...#......#
            .......#...........#.....#...#.............#.#..
            ..##..##.............#........#........#........
            ......#.............##..#.........#...#.#.#.....
            #........#.........#...#.....#................#.
            ...#.#...........#.....#.........#......##......
            ..#..#...........#..........#...................
            .........#..#.......................#.#.........
            ......#.#.#.....#...........#...............#...
            ......#.##...........#....#............#........
            #...........##.#.#........##...........##.......
            ......#....#..#.......#.....#.#.......#.##......
            .#....#......#..............#.......#...........
            ......##.#..........#..................#........
            ......##.##...#..#........#............#........
            ..#.....#.................###...#.....###.#..#..
            ....##...............#....#..................#..
            .....#................#.#.#.......#..........#..
            #........................#.##..........#....##..
            .#.........#.#.#...#...#....#........#..#.......
            ...#..#.#......................#...............#
        """.toScan()
