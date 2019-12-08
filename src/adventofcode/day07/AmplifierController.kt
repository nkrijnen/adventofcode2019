package adventofcode.day07

import adventofcode.day05.IntcodeProcessor
import adventofcode.day05.Program
import adventofcode.day05.ProgramEndedException
import kotlin.math.min

internal fun generateCombinations(phaseSettings: IntRange): List<IntArray> {
    fun generateRemaining(arr: IntArray, remaining: Int, collect: (IntArray) -> Unit) {
        for (x in phaseSettings) {
            if (x in arr) continue
            val combo = intArrayOf(*arr, x)
            if (remaining == 1) collect(combo)
            else generateRemaining(combo, remaining - 1, collect)
        }
    }

    val combinations = mutableListOf<IntArray>()
    generateRemaining(IntArray(0), phaseSettings.count()) { combinations += it }
    return combinations
}

private class Inputs(private vararg val inputProviderSequence: () -> Int) {
    private var idx = 0
    fun nextInput(): Int = inputProviderSequence[min(idx++, inputProviderSequence.lastIndex)]()
}

open class AmplifierController(protected val program: Program, private val phaseSettings: IntRange = 0 until 5) {
    fun findMaxThrusterSignal(): Int {
        val combinations = generateCombinations(phaseSettings)
        val comboWithAmpsOutput = combinations.map { Pair(it, tryCombination(it)) }
        return comboWithAmpsOutput.maxBy { it.second }!!.second
    }

    protected open fun tryCombination(phaseCombination: IntArray): Int {
        val amps = phaseSettings.map { IntcodeProcessor(program) }
        return phaseCombination.zip(amps).fold(0) { input, (phaseSetting, processor) ->
            val inputs = Inputs({ phaseSetting }, { input })
            processor.run { inputs.nextInput() }[0]
        }
    }
}

class FeedbackAmplifierController(program: Program, phaseSettings: IntRange = 5..9) :
    AmplifierController(program, phaseSettings) {

    override fun tryCombination(phaseCombination: IntArray): Int {
        var output: Int? = null

        val i1 = Inputs({ phaseCombination[0] }, { 0 }, { output!! })
        val p1 = IntcodeProcessor(program) { i1.nextInput() }

        val i2 = Inputs({ phaseCombination[1] }, { p1.runUntilOutput() })
        val p2 = IntcodeProcessor(program) { i2.nextInput() }

        val i3 = Inputs({ phaseCombination[2] }, { p2.runUntilOutput() })
        val p3 = IntcodeProcessor(program) { i3.nextInput() }

        val i4 = Inputs({ phaseCombination[3] }, { p3.runUntilOutput() })
        val p4 = IntcodeProcessor(program) { i4.nextInput() }

        val i5 = Inputs({ phaseCombination[4] }, { p4.runUntilOutput() })
        val p5 = IntcodeProcessor(program) { i5.nextInput() }

        // run, until input is required.. then p4.resume until p4 produces output, then use that as input, etc
        try {
            while (true)
                output = p5.runUntilOutput()
        } catch (e: ProgramEndedException) {
        }
        return output!!
    }
}
