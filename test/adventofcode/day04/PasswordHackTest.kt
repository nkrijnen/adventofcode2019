package adventofcode.day04

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

// https://adventofcode.com/2019/day/4
internal class PasswordHackTest {
    @Test
    fun `sample cases`() {
        assertTrue(111111.isValidPassword())

        assertFalse(223450.isValidPassword())
        assertFalse(223450.digitsNeverDecrease())
        assertTrue(223450.hasTwoAdjacentSameDigits())

        assertFalse(123789.isValidPassword())
        assertFalse(123789.hasTwoAdjacentSameDigits())
        assertTrue(123789.digitsNeverDecrease())
    }

    @Test
    fun `how many different passwords`() {
        val range = 109165..576723
        assertEquals(2814, range.possiblePasswordCount())
    }

    @Test
    fun `sample cases part 2`() {
        assertFalse(111112.isStrictlyValidPassword())
        assertFalse(123444.isStrictlyValidPassword())
        assertTrue(112233.isStrictlyValidPassword())
        assertTrue(111122.isStrictlyValidPassword())
    }

    @Test
    fun `how many different 'strict' passwords`() {
        val range = 109165..576723
        // range.filter { it.isStrictlyValidPassword() }.forEach(::println)
        assertNotEquals(2581, range.possibleStrictPasswordCount())
        assertEquals(1991, range.possibleStrictPasswordCount())
    }
}
