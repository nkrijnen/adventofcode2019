package adventofcode.day05

import adventofcode.day05.ParamMode.IMMEDIATE
import adventofcode.day05.ParamMode.POSITION

typealias Program = List<Int>
typealias Memory = MutableList<Int>

fun String.toProgram(): Program = this.split(",").map(String::toInt)

class IntcodeProcessor(private val program: Program) {
    fun run(input: Int): List<Int> {
        val output = mutableListOf<Int>()
        val memory: Memory = program.toMutableList()
        var opcodeIdx = 0
        loop@ while (true) {
            val op = FullOpcode(memory[opcodeIdx])
            when (op.opcode) {
                Opcode.ADD -> memory[memory[opcodeIdx + 3]] =
                    memory.read(opcodeIdx, 1, op) + memory.read(opcodeIdx, 2, op)
                Opcode.MUL -> memory[memory[opcodeIdx + 3]] =
                    memory.read(opcodeIdx, 1, op) * memory.read(opcodeIdx, 2, op)
                Opcode.INP -> memory[memory[opcodeIdx + 1]] = input
                Opcode.OUT -> output += memory.read(opcodeIdx, 1, op)
                Opcode.END -> break@loop
            }
            opcodeIdx += op.opcode.length
        }
        return output
    }
}

private fun Memory.read(opcodeIdx: Int, param: Int, op: FullOpcode): Int =
    if (op.modeFor(param - 1) == POSITION)
        this[this[opcodeIdx + param]]
    else
        this[opcodeIdx + param]

data class FullOpcode(val opcode: Opcode, val paramModes: List<ParamMode>) {
    fun modeFor(param: Int): ParamMode {
        return paramModes[param]
    }

    internal constructor (
        value: Int
    ) : this(
        value.toString().takeLast(2).toInt().toOpcode(),
        listOf(
            if (value / 100 % 10 == 1) IMMEDIATE else POSITION,
            if (value / 1000 % 10 == 1) IMMEDIATE else POSITION,
            if (value / 10000 % 10 == 1) IMMEDIATE else POSITION
        )
    )
}

enum class Opcode(val code: Int, val length: Int) {
    ADD(1, 4),
    MUL(2, 4),
    INP(3, 2),
    OUT(4, 2),
    END(99, 1)
}

fun Int.toOpcode(): Opcode = Opcode.values().find { this == it.code }
    ?: throw java.lang.IllegalArgumentException("Illegal opcode: $this")

enum class ParamMode {
    POSITION,
    IMMEDIATE
}
