package adventofcode.day20

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PlutoMazeTest {

    @Test
    fun `surrounding visitable cells`() {
        assertEquals(
            listOf(Coordinate(9, 3)),
            part1sample1.surroundingVisitableCells(Coordinate(9, 2)).map { it.coordinate }
        )
    }
    @Test
    fun `walk through portal`() { // A = portal in this case :(
        assertEquals(
            listOf(Coordinate(17, 7), Coordinate(32, 17)),
            part1sample2.surroundingVisitableCells(Coordinate(17, 8)).map { it.coordinate }
        )
    }

    @Test
    fun `part 1 sample 1`() {
//[Coordinate(x=9, y=1), Coordinate(x=9, y=3), Coordinate(x=10, y=3)]
//
//        val paths = """
//         A
//         A
//  #######0#########
//  #######123456789#
//  #######2#######0#
//  #######3#######1#
//  #######4#######2#
//  #####  B    ###3#
//BC567##  C    ###4#
//  ##8##       ###5#
//  ##901DE  F  ###6#
//  #####    G  ###7#
//  #########7#####8#
//DE23#######890###9#
//  #4#########1###0#
//FG65#########24321#
//  ###########3#####
//             Z
//             Z       """.trim().toMaze()

//        part1sample1.printPaths()

        assertEquals(23, part1sample1.shortestPathFromAAtoZZ())
    }

    @Test
    fun `part 1 sample 2`() {
        part1sample2.printPaths()
        assertEquals(58, part1sample2.shortestPathFromAAtoZZ())
    }

    @Test
    fun `part 1`() {
        assertEquals(552, part1.shortestPathFromAAtoZZ())
    }
}

private val part1sample1 = """
         A           
         A           
  #######.#########  
  #######.........#  
  #######.#######.#  
  #######.#######.#  
  #######.#######.#  
  #####  B    ###.#  
BC...##  C    ###.#  
  ##.##       ###.#  
  ##...DE  F  ###.#  
  #####    G  ###.#  
  #########.#####.#  
DE..#######...###.#  
  #.#########.###.#  
FG..#########.....#  
  ###########.#####  
             Z       
             Z       """.substring(1).toMaze()

private val part1sample2 = """
                   A               
                   A               
  #################.#############  
  #.#...#...................#.#.#  
  #.#.#.###.###.###.#########.#.#  
  #.#.#.......#...#.....#.#.#...#  
  #.#########.###.#####.#.#.###.#  
  #.............#.#.....#.......#  
  ###.###########.###.#####.#.#.#  
  #.....#        A   C    #.#.#.#  
  #######        S   P    #####.#  
  #.#...#                 #......VT
  #.#.#.#                 #.#####  
  #...#.#               YN....#.#  
  #.###.#                 #####.#  
DI....#.#                 #.....#  
  #####.#                 #.###.#  
ZZ......#               QG....#..AS
  ###.###                 #######  
JO..#.#.#                 #.....#  
  #.#.#.#                 ###.#.#  
  #...#..DI             BU....#..LF
  #####.#                 #.#####  
YN......#               VT..#....QG
  #.###.#                 #.###.#  
  #.#...#                 #.....#  
  ###.###    J L     J    #.#.###  
  #.....#    O F     P    #.#...#  
  #.###.#####.#.#####.#####.###.#  
  #...#.#.#...#.....#.....#.#...#  
  #.#####.###.###.#.#.#########.#  
  #...#.#.....#...#.#.#.#.....#.#  
  #.###.#####.###.###.#.#.#######  
  #.#.........#...#.............#  
  #########.###.###.#############  
           B   J   C               
           U   P   P               """.substring(1).toMaze()

private val part1 = """
                                     X       H           D   Z O       K   W                                         
                                     N       O           W   Z S       L   F                                         
  ###################################.#######.###########.###.#.#######.###.#######################################  
  #.....#.........................#.....#.#...#...........#.#.....#.......#...............................#.....#.#  
  ###.#####.#.#####.#######.###.#####.###.#.#######.#####.#.#####.#######.###.###.###.###.#.#.###.#######.###.###.#  
  #.#...#...#.#.#.....#...#.#.#.#.......#...#.#.#.....#.#.#.....#...#.....#.#...#...#...#.#.#.#.......#...#.....#.#  
  #.#.#########.#########.###.#.###.#####.#.#.#.#.###.#.#.###.#.#.#.###.###.#####.###.#####.#####.###########.###.#  
  #.....#.#.....#.........#...........#.#.#.....#.#.#.#...#...#...#.#.......#.#.....#.#.#.#...#...#.#.#...#...#.#.#  
  #.#####.#####.###.#####.###.###.#.###.#####.#####.#.###.###.#########.#.###.###.#####.#.#########.#.#.###.###.#.#  
  #.....#...#.#...#...#.......#.#.#.......#.#.#.#.....#.#.#.........#...#.#.#...#.....#.#...#.#...#.#.#.....#.#...#  
  ###.#####.#.###.#######.###.#.#######.###.#.#.###.###.###.#####.#####.#.#.#.#.#.###.#.#.###.###.#.#.###.###.#.###  
  #.#.....#.#...#...#.#.#.#.#.#.#.......#.......#.........#.#...#.#.....#.#...#.#.#.........#.#.#...........#...#.#  
  #.#.#####.###.#.###.#.###.###.###.#.#.#.###.#####.###########.#.###.###.#.###.#.###.#.#.###.#.#.###.###.#####.#.#  
  #.......#.#.#.#...#.#...#.........#.#.#.#.....#.......#.#.#...#...#.#...#.#.....#.#.#.#...........#...#...#...#.#  
  ###.#.###.#.#.#.#.#.###.#########.###.#####.#####.#####.#.###.#.#######.#.#.#.###.#####.###.#.###############.#.#  
  #.#.#.#.......#.#.#...#.....#...#...#.#...#...#.....#.#...........#.....#.#.#.....#.#...#.#.#.#.#.....#.#...#...#  
  #.#.#########.###.#.#####.#####.#.#######.#.#.###.###.#######.#.#######.#.###.#####.###.#.#####.#.#.#.#.###.#.###  
  #...#...#.....................#.#...#.#.#...#.#...#...#.......#.#...#...#...#.....#...............#.#...#.....#.#  
  #.#.#.###.#.###.###.#####.#.#.#.#.###.#.#.#######.###.#####.###.###.###.#.###.###########.#.###.#########.###.#.#  
  #.#.#...#.#.#.#.#.....#...#.#.........#.#.#.#.#...#.....#.....#...#...#.#...#.#.....#...#.#.#.#.....#.#...#...#.#  
  #.###.#######.#####.#.#.#.###.#######.#.#.#.#.###.###.#.#.#####.###.#.#.###.###.#.#####.#####.#######.#######.#.#  
  #.....#...#.#...#.#.#.#.#.#...#.#.....#.#.#.#...#.....#.#.#.#.....#.#...#...#.#.#.#.....#...#.........#.......#.#  
  #.#######.#.###.#.###.#######.#.#.#####.#.#.#.#####.#######.#.#.###.#.#.#.###.#.#####.#.#.#########.#####.###.#.#  
  #.......#.....#...#...#.......#.........#...#...#.#.....#.....#.#...#.#.#.....#...#...#.#.........#...#.#.#...#.#  
  #.#########.###.#.#######.#########.#.#####.#.#.#.#####.#.#####.#.###########.#.###.#####.#.#######.#.#.#####.#.#  
  #.#...#.#.#...#.#.......#...#.#.#...#...#.#...#...#.....#...#.#.#...#.....#.#...#.........#...#.#...#...#.#.#...#  
  #.#.###.#.#.#######.###.#####.#.#######.#.#####.#####.#.###.#.###.#####.###.#.#.#.#.###.#.#####.###.#####.#.###.#  
  #...#.#.#...#.#...#.#.#.#.#...#.#.#.#.......#...#.#.#.#.#...#...#.......#.....#...#...#.#.#.....#.....#.#...#...#  
  #.###.#.#.#.#.###.###.#.#.###.#.#.#.###.###.#.#.#.#.###.#.###.###.#####.#######.###########.#.#####.###.###.###.#  
  #.#.#...#.#.....#...#.#.#.......#.......#.#.#.#.#.......#...#.#.....#.....#...#.........#.#.#.#.#.#.#.#.#.......#  
  #.#.###.###.#######.#.#.#####.###.#####.#.###.###.#.#.#####.#.###.#.#.#.#####.#.#########.###.#.#.#.#.#.###.#####  
  #.#.#.#.........#.........#.#.........#...#.....#.#.#.#.........#.#.#.#.#.......#...#...#.#.....#.#.#...#.......#  
  #.#.#.#####.###########.#.#.#.#######.#####.#####.###########.#######.###.#########.###.#.###.###.#.#.#.###.#####  
  #.....#...#.#.....#.#.#.#.#.#.#      S     K     H           R       I   H        #.#.......#.#.....#.#.#.....#.#  
  #####.###.#.#####.#.#.#.###.###      U     A     W           P       O   C        #.###.#####.###.#.###.###.###.#  
  #.#.#...#.....#.#...#...#.#...#                                                   #.....#...#...#.#.#.#...#...#.#  
  #.#.#.#####.###.###.###.#.#.###                                                   ###.#####.#.###.###.###.#.###.#  
  #.#...#...#.#...............#.#                                                 NZ..............#.............#.#  
  #.#.#####.#.###.#######.###.#.#                                                   #.###.#####.###.###.#.###.###.#  
KA....#.#.......#.#.......#.....#                                                   #.#.#.#.........#.#.#...#.....#  
  #.###.#####.#########.#######.#                                                   ###.#####.#.###.#.#.#########.#  
  #.......#...#.#.#.#.#.....#....DA                                                 #...#.#.#.#.#.....#.#.#...#.#..HC
  ###.###.#.#.#.#.#.#.#####.###.#                                                   #.#.#.#.#########.#.#.#.###.###  
  #...#.....#...............#...#                                                   #.#.......#.#.#.#.#.#..........PZ
  #.#####################.#.#.#.#                                                   #####.#.###.#.#.#####.#.###.#.#  
  #.#.......#.........#.#.#.#.#.#                                                   #.#...#...............#...#.#.#  
  ###.###.#.#.#######.#.#########                                                   #.###.#####.###.#.#########.###  
RO....#.#.#...#.#.#.....#.#.....#                                                   #...#...#.....#.#.#...#.#.#...#  
  ###.#.###.###.#.###.#.#.###.#.#                                                   ###.#.###########.#.###.#.#####  
  #.....#.#.#.#.#.#.#.#.....#.#.#                                                 XN......#.#.#...#.......#...#...#  
  #######.###.#.#.#.###.###.#.#.#                                                   #####.#.#.###.#.###.###.#.#.###  
  #...#.......#...#...#.#.....#..WF                                               TZ....#.#.......#.#...#...#.#.#.#  
  ###.###.#####.#####.###########                                                   ###.###.#.#.#####.#######.#.#.#  
TZ....#...#.....................#                                                   #.......#.#.#.#.....#...#.#.#..YK
  ###.#.#.#.#######.###.#####.#.#                                                   ###.#.###.###.#######.###.#.#.#  
  #...#.#.#.....#...#.#.#...#.#.#                                                   #...#.#...#.......#.#...#.....#  
  ###.#.#.###.#######.###.#####.#                                                   #########.#.#.#####.#.###.###.#  
  #.#...#.......#.....#.#...#....QN                                                 #...#.#.....#.............#...#  
  #.###############.###.#.#####.#                                                   ###.#.###.#.###.#.#.#.#.#######  
  #...#.....#.#.......#...#.....#                                                   #.#.....#.#.#.#.#.#.#.#.#...#.#  
  #.###.#.#.#.#.#####.#.#.#####.#                                                   #.###.#######.###########.#.#.#  
  #.#.#.#.#...#...#...#.#...#.#.#                                                   #.#.......#.#.....#.....#.#....IO
  #.#.#.#####.#.###.###.###.#.###                                                   #.###.###.#.#.#########.#.###.#  
  #.......#.......#.#.....#.....#                                                 JZ......#.........#.#.#.....#...#  
  #.###.#####.#####.#####.#.#####                                                   #########.###.###.#.###.#####.#  
DQ....#...#.....#.........#......JO                                                 #...#.#.#.#.................#.#  
  #.###.#####.###################                                                   ###.#.#.#######.#######.#######  
  #...#.#...#.#.....#.#...#.....#                                                   #.#...........#.#.....#...#...#  
  #####.###.#####.###.#.#.#.###.#                                                   #.#.#####.###.#####.#########.#  
RU....#.#.....#.#...#...#.#...#.#                                                 OS....#.#...#...#.....#.#...#.#.#  
  #.#######.###.#.#.#.###.###.#.#                                                   #.#.#.###.#.#.###.#.#.###.#.#.#  
  #...............#.....#.....#..QT                                                 #.#...#...#.#...#.#...#.#.....#  
  #.#######.#.###.###.###########                                                   ###.#.#####.#####.###.#.#.###.#  
  #.#.#.#...#.#...#.#...#.#.#...#                                                   #.#.#.#...#.........#.......#..QN
  ###.#.###.#.###.#.#####.#.#.#.#                                                   #.#.#.###.#####################  
  #.#...#...#.#...#.#.#.#.....#.#                                                   #.#.#.#.............#.#...#.#..HW
  #.#.#############.#.#.#####.#.#                                                   #.#####.#.#########.#.#.#.#.#.#  
  #.#.#...#.#...#.#.......#.#.#..DQ                                                 #.#.#...#.#...#.........#.....#  
  #.#.#.###.#.###.###.#.###.#.###                                                   #.#.#####.#.###.#####.#.###.###  
JZ....................#.........#                                                 QO....#.#.......#.#.#...#...#...#  
  ###.#########################.#                                                   ###.#.#.#.#######.###.###.#.#.#  
PF..#.#.......#.....#.........#.#                                                   #.......#...#...#.....#.#.#.#.#  
  #.###.###.#.#.###.#.#.###.#####                                                   #############.#####.#.#.#######  
  #.......#.#.....#...#.#.......#                                                   #...#...#...#...#...#.#.#...#.#  
  #.#.#.###.#.#####.#.###.#.#####                                                   #.#.#.#.#.###.#####.###.###.#.#  
  #.#.#...#.#.....#.#.#.#.#.#.#.#                                                 KL..#.#.#.#.....#...#.#.#.......#  
  #.#.###.#####.###.###.###.#.#.#                                                   ###.#.#.#.#.#####.###.#######.#  
  #.#...#...#...#...#............RU                                                 #...#.#...#...#.#.#.#...#.#.#..RP
  #.#.###.###.###.#.#.#.#.#.###.#                                                   #.###.#.#.###.#.#.#.#.###.#.#.#  
  #.#...#.#.....#.#.#.#.#.#...#.#                                                   #.....#.#.#...................#  
  ###.###.#.###.###.###.###.###.#      D     P         Y         O     H   R   P    #.###.#######.#.#########.#.###  
  #...#...#.#...#.....#.#...#.#.#      W     Z         K         A     O   O   F    #.#.......#...#.....#.....#...#  
  #.#.###.###.###.#.#.#####.#.#########.#####.#########.#########.#####.###.###.#####.###.###.#.#.#.#######.#.#.###  
  #.#.#.....#...#.#.#.#.#.........#.........#.#...#.......#.......#.#...#.....#.....#.#.....#.#.#.#...#.#...#.#...#  
  ###.###.###.#.#.#.###.#.###.#.###.###.###.#.#.#.###.#.#.#####.###.#.#####.#.#.#.#.#########.#####.#.#.###.###.###  
  #.....#...#.#.#.#...#.....#.#.#.#.#...#.#.#...#...#.#.#.....#.#.........#.#.#.#.#.....#.#...#.#...#...#...#.....#  
  ###.#.#.#########.#######.#####.#######.#.#####.###.#.#######.###.#######.###.#.#######.#####.#####.#####.###.#.#  
  #...#.#.#.#.#.#.....#.#...#...............#...#...#.#.#...#.....#.....#...#...#...#.#.........#.........#...#.#.#  
  #.#.#.###.#.#.#.#####.#######.#.#.#.###.###.#.#.###.#####.#####.#.###.###.#.###.###.#.###########.###.#.###.#.#.#  
  #.#.#...#.......#.#...#...#...#.#.#.#.#...#.#...#.......#.......#.#.#.#.......#.......#.......#...#...#...#.#.#.#  
  #####.#########.#.#.###.#############.###.#.#######.###.#.#######.#.#####.###.#.#####.#####.#####.#.#.#.###.###.#  
  #.#.......#.......#.#...#.................#.#.....#...#.#.#.#.#.....#.......#.#.....#...........#.#.#.#...#...#.#  
  #.#.###.#.#########.#.#############.#######.#.###.###.###.#.#.###.#.###.#######.#####.#.#.#.#.#######.###.#.###.#  
  #.....#.#.#.....#...#...#.#...............#.#...#...#.#...#.....#.#...#.#.#.........#.#.#.#.#...#.......#.#...#.#  
  #.#.#####.#####.###.#.###.###############.#.###.#.###.###.#.###.#.#####.#.###.#####.###.###.#.#.###.#.#.#######.#  
  #.#...#.....#.#.....#.....#.....#.#.......#.....#.#.....#.....#.#...#...#...#...#.#...#...#.#.#.#...#.#.#.......#  
  #.#.#########.#####.#.#########.#.#.#######.#####.#.#.#.#.#.###.#.#########.#.###.#.###.#####.###.#.#.#########.#  
  #.#.#...#.....#...#.....#.#.#.........#.......#...#.#.#.#.#.#.#.#...#.....#.......#.#.....#.....#.#.#...#.......#  
  #.#####.#####.#.###.#.###.#.###.#.#.###.#.#.###.#.#.#####.###.#.#.#####.#######.#.###########.#####.#######.#.###  
  #.#.......#.........#.#.#.......#.#.#.#.#.#.#...#.#.#.#.....#...#...#.....#...#.#.#...#...#...#.#.#.....#...#...#  
  ###.###.#.#.#.#.#.#.#.#.#.###.#.###.#.###.#####.###.#.#########.#.###.#######.#.#####.###.###.#.#.#########.###.#  
  #.#...#.#...#.#.#.#.#...#.#.#.#...#.....#.#.......#...#.....#...#.......#.#.#...#.#...#.....#.#...#.....#.#.#...#  
  #.#.#.#.#.#.#.#######.#####.###.###.#########.###.#.#####.#####.#######.#.#.#.###.#.###.#######.#.#.#.#.#.#####.#  
  #.#.#.#.#.#.#.#.........#.........#.......#...#...#.....#...#...#.........#...........#.....#...#.#.#.#.#...#.#.#  
  #.#####.###.#########.#########.###.#######.#####.#####.###.###.###.#.#.#######.#.#####.#.#####.###.#####.###.#.#  
  #.......#.#.#.............#.....#...#.#.#.....#.#.#.....#...#.....#.#.#.#.....#.#...#.#.#...#...#...#.#.....#...#  
  #.#.#####.###.###.#.#####.#########.#.#.#.#####.#.#.#.#.###.###.###.#######.#.#.#####.#.#####.#####.#.###.#######  
  #.#...#.....#.#.#.#.#.#.........#.......#.#.#.#...#.#.#.#.......#...#...#.#.#.....................#...#.........#  
  #########.#.###.#####.###.#####.###.#######.#.###.#.#######.###.#.#####.#.#.###############.#.#####.###.#########  
  #.........#.........#.....#.........#...#.#...#...#...#...#.#...#.#.#.....................#.#...................#  
  ###.#.#####.#####.###.#.#####.#####.###.#.#.###.#.#.#.###.#.#####.#.###########.#######.#####.###.###########.###  
  #...#.#.......#.......#.#.......#.....#.........#.#.#.#.......#.......#...............#.....#.#...........#.....#  
  ###################################.#######.#.#####.#####.###########.###.###.###################################  
                                     J       A O     Q     N           Q   S   D                                     
                                     O       A A     T     Z           O   U   A                                     """.substring(1).toMaze()