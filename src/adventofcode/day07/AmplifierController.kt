package adventofcode.day07

import adventofcode.day05.IntcodeProcessor
import adventofcode.day05.Program

internal fun generateCombinations(length: Int): List<IntArray> {
    fun generateRemaining(arr: IntArray, remaining: Int, collect: (IntArray) -> Unit) {
        for (x in 0 until length) {
            if (x in arr) continue
            val combo = intArrayOf(*arr, x)
            if (remaining == 1) collect(combo)
            else generateRemaining(combo, remaining - 1, collect)
        }
    }

    val combinations = mutableListOf<IntArray>()
    generateRemaining(IntArray(0), length) { combinations += it }
    return combinations
}

private class InputQueue(private vararg val inputProviderSequence: () -> Int) {
    private var idx = 0
    fun nextInput(): Int = inputProviderSequence[idx++]()
}

class AmplifierController(program: Program) {
    private val nrOfAmps = 5
    private val amps = (0 until 5).map { IntcodeProcessor(program) }

    fun findMaxThursterSignal(): Int {
        val combinations = generateCombinations(nrOfAmps)
        val comboWithAmpsOutput = combinations.map { Pair(it, tryCombination(it)) }
        return comboWithAmpsOutput.maxBy { it.second }!!.second
    }

    private fun tryCombination(phaseCombination: IntArray): Int =
        phaseCombination.zip(amps).fold(0) { input, (phaseSetting, processor) ->
            val inputs = InputQueue({ phaseSetting }, { input })
            processor.run { inputs.nextInput() }[0]
        }
}




