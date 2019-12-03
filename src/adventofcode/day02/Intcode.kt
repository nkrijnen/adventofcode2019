package adventofcode.day02

fun runIntcode(initialMemory: List<Int>): List<Int>? {
    val memory = initialMemory.toMutableList()
    var opcodeIdx = 0
    while (memory[opcodeIdx] != 99) {
        val opcode = memory[opcodeIdx]
        if (opcode == 1)
            memory.set(opcodeIdx) { memory.arg1(opcodeIdx) + memory.arg2(opcodeIdx) }
        else if (opcode == 2)
            memory.set(opcodeIdx) { memory.arg1(opcodeIdx) * memory.arg2(opcodeIdx) }
        else
            return null
        opcodeIdx += 4
    }
    return memory
}

fun findIntcodeInput(program: List<Int>, desiredOutput: Int): IntcodeInput? {
    for (noun in 0..99) {
        for (verb in 0..99) {
            val output = runIntcode(program.setup(noun, verb))?.let { it[0] }
            if (output == desiredOutput)
                return IntcodeInput(noun, verb)
        }
    }
    return null
}

data class IntcodeInput(val noun: Int, val verb: Int)

fun List<Int>.arg1(opcodeIdx: Int) = this[this[opcodeIdx + 1]]
fun List<Int>.arg2(opcodeIdx: Int) = this[this[opcodeIdx + 2]]
fun MutableList<Int>.set(opcodeIdx: Int, value: () -> Int) {
    this[this[opcodeIdx + 3]] = value.invoke()
}

fun List<Int>.setup(address1Value: Int, address2Value: Int): List<Int> {
    val program = this.toMutableList()
    program[1] = address1Value
    program[2] = address2Value
    return program
}
