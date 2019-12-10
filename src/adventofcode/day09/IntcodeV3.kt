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
            val ctx =
                OpContext(opcodeIdx, relativeBase, memory, nextInput, { relativeBase = it; println(relativeBase) })
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

    fun resolveParam(param: Int): Long {
        return memory[resolvePosition(param)] ?: 0
    }

    fun write(param: Int, block: () -> Long) {
        memory[resolvePosition(param)] = block()
    }

    private fun resolvePosition(param: Int) = paramModes[param - 1].resolveMemoryPosition(this, param)

    private fun immediatePosition(param: Int) = opcodeIdx + param
    private fun absolutePosition(param: Int) = memory[immediatePosition(param)]!!.toInt()
    private fun relativePosition(param: Int) = relativeBase + memory[immediatePosition(param)]!!.toInt()

    internal enum class ParamMode(val resolveMemoryPosition: (OpContext, Int) -> Int) {
        POSITION(OpContext::absolutePosition),
        RELATIVE(OpContext::relativePosition),
        IMMEDIATE(OpContext::immediatePosition)
    }
}

internal class OpcodeResult(val nextOpcodeIdx: Int, val output: Long? = null)

internal enum class Opcode(val code: Int, val execute: (OpContext) -> OpcodeResult) {
    ADD(1, {
        it.write(3) { it.resolveParam(1) + it.resolveParam(2) }
        OpcodeResult(it.opcodeIdx + 4)
    }),
    MUL(2, {
        it.write(3) { it.resolveParam(1) * it.resolveParam(2) }
        OpcodeResult(it.opcodeIdx + 4)
    }),
    INP(3, {
        it.write(1) { it.nextInput() }
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
        it.write(3) { if (it.resolveParam(1) < it.resolveParam(2)) 1 else 0 }
        OpcodeResult(it.opcodeIdx + 4)
    }),
    EQ(8, {
        it.write(3) { if (it.resolveParam(1) == it.resolveParam(2)) 1 else 0 }
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
