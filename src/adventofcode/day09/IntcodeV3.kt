package adventofcode.day09

import adventofcode.day09.OpContext.ParamMode.*

typealias Program = List<Long>
private typealias Memory = MutableMap<Int, Long>

fun String.toProgram(): Program = this.split(",").map(String::toLong)

class IntcodeProcessor(
    program: Program,
    private var nextInput: () -> Long = { throw IllegalStateException("No input provided") }
) {
    private val memory: Memory = program.withIndex().associate { Pair(it.index, it.value) }.toMutableMap()
    private var opcodeIdx = 0
    private var relativeBase = 0

    fun run(inputProvider: () -> Long = nextInput): List<Long> {
        nextInput = inputProvider
        val output = mutableListOf<Long>()
        runUntilEnd { output += it }
        return output
    }

    fun runUntilEnd(consumeOutput: (Long) -> Unit) {
        try {
            while (true) consumeOutput(runUntilOutput())
        } catch (e: ProgramEndedException) {
        }
    }

    fun runUntilOutput(): Long {
        while (opcodeIdx >= 0) {
            val ctx = OpContext(opcodeIdx, relativeBase, memory, nextInput, { relativeBase = it })
            val result = ctx.opcode.execute(ctx)
            opcodeIdx = result.nextOpcodeIdx
            if (result.output != null) return result.output
        }
        throw ProgramEndedException()
    }
}

private class ProgramEndedException : RuntimeException()

internal data class OpContext(
    val opcodeIdx: Int,
    val relativeBase: Int,
    private val memory: Memory,
    val nextInput: () -> Long,
    val updateRelativeBase: (Int) -> Unit
) {
    val opcode: Opcode = memory[opcodeIdx]!!.toOpcode()
    private val paramModes: List<ParamMode> = memory[opcodeIdx]!!.toParamModes()

    fun resolveParam(param: Int): Long = paramModes[param - 1].resolve(this, param)

    private fun memoryLookup(param: Int): Long = memory[paramValue(param).toInt()] ?: 0

    private fun relativeLookup(param: Int): Long = memory[relativeBase + paramValue(param).toInt()] ?: 0

    private fun paramValue(param: Int): Long = memory[opcodeIdx + param]!!

    fun writeAtParam(param: Long, block: () -> Long) {
        memory[memory[opcodeIdx + param.toInt()]!!.toInt()] = block()
    }

    internal enum class ParamMode(val resolve: (OpContext, Int) -> Long) {
        POSITION(OpContext::memoryLookup),
        RELATIVE(OpContext::relativeLookup),
        IMMEDIATE(OpContext::paramValue)
    }
}

internal class OpcodeResult(val nextOpcodeIdx: Int, val output: Long? = null)

internal enum class Opcode(val code: Int, val execute: (OpContext) -> OpcodeResult) {
    ADD(1, {
        it.writeAtParam(3) { it.resolveParam(1) + it.resolveParam(2) }
        OpcodeResult(it.opcodeIdx + 4)
    }),
    MUL(2, {
        it.writeAtParam(3) { it.resolveParam(1) * it.resolveParam(2) }
        OpcodeResult(it.opcodeIdx + 4)
    }),
    INP(3, {
        it.writeAtParam(1) { it.nextInput() }
        OpcodeResult(it.opcodeIdx + 2)
    }),
    OUT(4, {
        //        it.consumeOutput(it.resolveParam(1))
        OpcodeResult(it.opcodeIdx + 2, it.resolveParam(1))
    }),
    JMP_IF_TRUE(5, {
        OpcodeResult(
            if (it.resolveParam(1) != 0L) it.resolveParam(2).toInt()
            else it.opcodeIdx + 3
        )
    }),
    JMP_IF_FALSE(6, {
        OpcodeResult(
            if (it.resolveParam(1) == 0L) it.resolveParam(2).toInt()
            else it.opcodeIdx + 3
        )
    }),
    LT(7, {
        it.writeAtParam(3) { if (it.resolveParam(1) < it.resolveParam(2)) 1 else 0 }
        OpcodeResult(it.opcodeIdx + 4)
    }),
    EQ(8, {
        it.writeAtParam(3) { if (it.resolveParam(1) == it.resolveParam(2)) 1 else 0 }
        OpcodeResult(it.opcodeIdx + 4)
    }),
    REL_BASE(9, {
        it.updateRelativeBase(it.relativeBase + it.resolveParam(1).toInt())
        OpcodeResult(it.opcodeIdx + 2)
    }),
    END(99, { OpcodeResult(-1) })
}

internal fun Long.toOpcode(): Opcode {
    val code = toString().takeLast(2).toInt()
    val opcode = Opcode.values().find { code == it.code }
    return opcode ?: throw java.lang.IllegalArgumentException("Illegal opcode: $code, in $this")
}

internal fun Long.toParamModes() = listOf(
    (this / 100 % 10).toParamMode(),
    (this / 1000 % 10).toParamMode(),
    (this / 1000 % 10).toParamMode()
)

internal fun Long.toParamMode() = when (this) {
    1L -> IMMEDIATE
    2L -> RELATIVE
    else -> POSITION
}
