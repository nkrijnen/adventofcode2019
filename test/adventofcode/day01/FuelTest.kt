package adventofcode.day01

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class FuelTest {
    @Test
    fun fuelRequiredForMass() {
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
    fun `should parse input`() {
        val input = """
            51590
            53619
            101381
            """.trimIndent()

        val result = parseInput(input)

        assertEquals(listOf(51590, 53619, 101381), result)
    }
}
