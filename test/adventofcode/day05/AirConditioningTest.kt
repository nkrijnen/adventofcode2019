package adventofcode.day05

import adventofcode.day05.ParamMode.IMMEDIATE
import adventofcode.day05.ParamMode.POSITION
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class AirConditioningTest {
    @Test
    fun `should extract opcode`() {
        assertEquals(Opcode.ADD, 1.toOpcode())
        assertEquals(Opcode.END, 99.toOpcode())
        assertThrows<IllegalArgumentException> { 98.toOpcode() }
    }

    @Test
    fun `should extract parameter modes`() {
        assertEquals(listOf(POSITION, POSITION, POSITION), 1.toParamModes())
        assertEquals(listOf(POSITION, IMMEDIATE, POSITION), 1002.toParamModes())
        assertEquals(listOf(IMMEDIATE, POSITION, IMMEDIATE), 10104.toParamModes())
        assertEquals(listOf(POSITION, POSITION, IMMEDIATE), 10004.toParamModes())
    }

    @Test
    fun `sample program`() {
        assertEquals(listOf(324), IntcodeProcessor("3,0,4,0,99".toProgram()).run(324))
        assertEquals(listOf(), IntcodeProcessor("1002,4,3,4,33".toProgram()).run(1))
    }

    @Test
    fun `sample direct program`() {
        assertEquals(listOf(), IntcodeProcessor("1101,100,-1,4,0".toProgram()).run(-1))
    }

    /**
     * Thermal Environment Supervision Terminal Manual:
     *
     * Opcodes:
     * 01 - [Opcode, a, b, register] Stores a + b into register. Moves registry pointer 4 places.
     * 02 - [Opcode, a, b, register] Stores a * b into register. Moves registry pointer 4 places.
     * 03 - [Opcode, register] Reads from input to register. Moves registry pointer 2 places.
     * 04 - [Opcode, register] Writes from register to output. Moves registry pointer 2 places.
     * 99 - [Opcode] Terminates program
     *
     * Full opcode:
     *  A  B  C  DE
     * [ ][1][0][02]
     *
     *   DE - two-digit opcode,      02 == opcode 2
     *   C - mode of 1st parameter,  0 == position mode
     *   B - mode of 2nd parameter,  1 == immediate mode
     *   A - mode of 3rd parameter,  0 == position mode,
     *   omitted due to being a leading zero
     *
     * Parameter modes:
     * 0 - Position mode - Parameter at this position is a reference to a register
     * 1 - Immediate mode - Parameter at this position is a value
     *
     * Input:
     * ID - Any integer (Our Air conditioning Unit is 1)
     *
     * Output:
     * Diagnostic code
     *
     * Part 1:
     * It will then perform a series of diagnostic tests confirming that various parts of the Intcode computer, like parameter modes, function correctly. For each test, it will run an output instruction indicating how far the result of the test was from the expected value, where 0 means the test was successful. Non-zero outputs mean that a function is not working correctly; check the instructions that were run before the output instruction to see which one failed.
     *
     * What diagnostic code does the program produce for input 1?
     */
    @Test
    fun `part 1`() {
        val result = IntcodeProcessor(program1).run(1)

        assertEquals(listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 14155342), result)
    }
}

private val program1 =
    "3,225,1,225,6,6,1100,1,238,225,104,0,1102,57,23,224,101,-1311,224,224,4,224,1002,223,8,223,101,6,224,224,1,223,224,223,1102,57,67,225,102,67,150,224,1001,224,-2613,224,4,224,1002,223,8,223,101,5,224,224,1,224,223,223,2,179,213,224,1001,224,-469,224,4,224,102,8,223,223,101,7,224,224,1,223,224,223,1001,188,27,224,101,-119,224,224,4,224,1002,223,8,223,1001,224,7,224,1,223,224,223,1,184,218,224,1001,224,-155,224,4,224,1002,223,8,223,1001,224,7,224,1,224,223,223,1101,21,80,224,1001,224,-101,224,4,224,102,8,223,223,1001,224,1,224,1,224,223,223,1101,67,39,225,1101,89,68,225,101,69,35,224,1001,224,-126,224,4,224,1002,223,8,223,1001,224,1,224,1,224,223,223,1102,7,52,225,1102,18,90,225,1101,65,92,225,1002,153,78,224,101,-6942,224,224,4,224,102,8,223,223,101,6,224,224,1,223,224,223,1101,67,83,225,1102,31,65,225,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1007,226,226,224,102,2,223,223,1005,224,329,1001,223,1,223,108,677,226,224,1002,223,2,223,1005,224,344,1001,223,1,223,1007,677,677,224,1002,223,2,223,1005,224,359,1001,223,1,223,1107,677,226,224,102,2,223,223,1006,224,374,1001,223,1,223,8,226,677,224,1002,223,2,223,1006,224,389,101,1,223,223,8,677,677,224,102,2,223,223,1006,224,404,1001,223,1,223,1008,226,226,224,102,2,223,223,1006,224,419,1001,223,1,223,107,677,226,224,102,2,223,223,1006,224,434,101,1,223,223,7,226,226,224,1002,223,2,223,1005,224,449,1001,223,1,223,1107,226,226,224,1002,223,2,223,1006,224,464,1001,223,1,223,1107,226,677,224,1002,223,2,223,1005,224,479,1001,223,1,223,8,677,226,224,1002,223,2,223,1006,224,494,1001,223,1,223,1108,226,677,224,1002,223,2,223,1006,224,509,101,1,223,223,1008,677,677,224,1002,223,2,223,1006,224,524,1001,223,1,223,1008,677,226,224,102,2,223,223,1006,224,539,1001,223,1,223,1108,677,677,224,102,2,223,223,1005,224,554,101,1,223,223,108,677,677,224,102,2,223,223,1006,224,569,101,1,223,223,1108,677,226,224,102,2,223,223,1005,224,584,1001,223,1,223,108,226,226,224,1002,223,2,223,1005,224,599,1001,223,1,223,1007,226,677,224,102,2,223,223,1005,224,614,1001,223,1,223,7,226,677,224,102,2,223,223,1006,224,629,1001,223,1,223,107,226,226,224,102,2,223,223,1005,224,644,101,1,223,223,7,677,226,224,102,2,223,223,1005,224,659,101,1,223,223,107,677,677,224,1002,223,2,223,1005,224,674,1001,223,1,223,4,223,99,226".toProgram()
