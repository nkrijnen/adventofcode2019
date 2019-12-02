package adventofcode.day02

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class IntcodeTest {
    @Test
    fun `sample cases`() {
        assertEquals(
            listOf(
                3500, 9, 10, 70,
                2, 3, 11, 0,
                99,
                30, 40, 50
            ), runIntcode(
                listOf(
                    1, 9, 10, 3,
                    2, 3, 11, 0,
                    99,
                    30, 40, 50
                )
            )
        )
        assertEquals(listOf(2, 0, 0, 0, 99), runIntcode(listOf(1, 0, 0, 0, 99)))
        assertEquals(listOf(2, 3, 0, 6, 99), runIntcode(listOf(2, 3, 0, 3, 99)))
        assertEquals(listOf(2, 4, 4, 5, 99, 9801), runIntcode(listOf(2, 4, 4, 5, 99, 0)))
        assertEquals(listOf(30, 1, 1, 4, 2, 5, 6, 0, 99), runIntcode(listOf(1, 1, 1, 4, 99, 5, 6, 0, 99)))
    }

    @Test
    fun `part 1`() {
        val input =
            "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,13,1,19,1,6,19,23,2,23,6,27,1,5,27,31,1,10,31,35,2,6,35,39,1,39,13,43,1,43,9,47,2,47,10,51,1,5,51,55,1,55,10,59,2,59,6,63,2,6,63,67,1,5,67,71,2,9,71,75,1,75,6,79,1,6,79,83,2,83,9,87,2,87,13,91,1,10,91,95,1,95,13,99,2,13,99,103,1,103,10,107,2,107,10,111,1,111,9,115,1,115,2,119,1,9,119,0,99,2,0,14,0"
                .split(",").map(String::toInt).toMutableList()

        // reset to 1202 program alarm state
        input[1] = 12
        input[2] = 2

        val result = runIntcode(input)

        assertEquals(3850704, result[0])
    }
}
