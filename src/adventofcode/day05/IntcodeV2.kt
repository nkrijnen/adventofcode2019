package adventofcode.day05

import adventofcode.day05.ParamMode.IMMEDIATE
import adventofcode.day05.ParamMode.POSITION

typealias Program = List<Int>
typealias Memory = MutableList<Int>
typealias Output = MutableList<Int>

fun String.toProgram(): Program = this.split(",").map(String::toInt)

class IntcodeProcessor(private val program: Program) {
    fun run(input: Int): List<Int> {
        val output: Output = mutableListOf()
        val memory: Memory = program.toMutableList()
        var opcodeIdx = 0
        while (opcodeIdx >= 0) {
            val ctx = OpContext(opcodeIdx, memory, input, output)
            opcodeIdx = ctx.opcode.execute(ctx)
        }
        return output
    }
}

internal enum class Opcode(val code: Int, val execute: (OpContext) -> Int) {
    ADD(1, {
        it.writeAtParam(3) { it.resolveParam(1) + it.resolveParam(2) }
        it.opcodeIdx + 4
    }),
    MUL(2, {
        it.writeAtParam(3) { it.resolveParam(1) * it.resolveParam(2) }
        it.opcodeIdx + 4
    }),
    INP(3, {
        it.writeAtParam(1) { it.input }
        it.opcodeIdx + 2
    }),
    OUT(4, {
        it.output += it.resolveParam(1)
        it.opcodeIdx + 2
    }),
    JMP_IF_TRUE(5, {
        if (it.resolveParam(1) != 0) it.resolveParam(2)
        else it.opcodeIdx + 3
    }),
    JMP_IF_FALSE(6, {
        if (it.resolveParam(1) == 0) it.resolveParam(2)
        else it.opcodeIdx + 3
    }),
    LT(7, {
        it.writeAtParam(3) { if (it.resolveParam(1) < it.resolveParam(2)) 1 else 0 }
        it.opcodeIdx + 4
    }),
    EQ(8, {
        it.writeAtParam(3) { if (it.resolveParam(1) == it.resolveParam(2)) 1 else 0 }
        it.opcodeIdx + 4
    }),
    END(99, { -1 })
}

internal fun Int.toOpcode(): Opcode {
    val code = this.let { it.toString().takeLast(2).toInt() }
    val opcode = Opcode.values().find { code == it.code }
    return opcode ?: throw java.lang.IllegalArgumentException("Illegal opcode: $code, in $this")
}

internal enum class ParamMode {
    POSITION,
    IMMEDIATE
}

internal fun Int.toParamModes() = listOf(
    if (this / 100 % 10 == 1) IMMEDIATE else POSITION,
    if (this / 1000 % 10 == 1) IMMEDIATE else POSITION,
    if (this / 10000 % 10 == 1) IMMEDIATE else POSITION
)


internal data class OpContext(
    val opcodeIdx: Int,
    private val memory: Memory,
    val input: Int,
    val output: Output
) {
    val opcode: Opcode = memory[opcodeIdx].toOpcode()
    private val paramModes: List<ParamMode> = memory[opcodeIdx].toParamModes()

    fun resolveParam(param: Int): Int {
        return if (paramModes[param - 1] == POSITION)
            memory[memory[opcodeIdx + param]]
        else
            memory[opcodeIdx + param]
    }

    fun writeAtParam(param: Int, block: () -> Int) {
        memory[memory[opcodeIdx + param]] = block()
    }
}

