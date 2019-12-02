package adventofcode.day01

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class FuelTest {

    // https://adventofcode.com/2019/day/1

    @Test
    fun `fuel required for mass`() {
        // For a mass of 12, divide by 3 and round down to get 4, then subtract 2 to get 2.
        assertEquals(2, fuelRequiredForMass(12))

        // For a mass of 14, dividing by 3 and rounding down still yields 4, so the fuel required is also 2.
        assertEquals(2, fuelRequiredForMass(14))

        // For a mass of 1969, the fuel required is 654.
        assertEquals(654, fuelRequiredForMass(1969))

        // For a mass of 100756, the fuel required is 33583.
        assertEquals(33583, fuelRequiredForMass(100756))
    }

    @Test
    fun `should parse input to list`() {
        val input = """
            51590
            53619
            101381
            """.trimIndent()

        val result = parseInput(input)

        assertEquals(listOf(51590, 53619, 101381), result)
    }

    // https://adventofcode.com/2019/day/1#part2

    @Test
    fun `fuel required by mass and fuel`() {
        // A module of mass 14 requires 2 fuel. This fuel requires no further fuel (2 divided by 3 and rounded down is 0, which would call for a negative fuel), so the total fuel required is still just 2.
        assertEquals(2, fuelRequiredForMassIncludingExtraFuel(14))

        // At first, a module of mass 1969 requires 654 fuel. Then, this fuel requires 216 more fuel (654 / 3 - 2). 216 then requires 70 more fuel, which requires 21 fuel, which requires 5 fuel, which requires no further fuel. So, the total fuel required for a module of mass 1969 is 654 + 216 + 70 + 21 + 5 = 966.
        assertEquals(966, fuelRequiredForMassIncludingExtraFuel(1969))

        // The fuel required by a module of mass 100756 and its fuel is: 33583 + 11192 + 3728 + 1240 + 411 + 135 + 43 + 12 + 2 = 50346.
        assertEquals(50346, fuelRequiredForMassIncludingExtraFuel(100756))
    }
}
