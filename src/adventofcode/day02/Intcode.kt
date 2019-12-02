package adventofcode.day02

fun runIntcode(initialMemory: List<Int>): List<Int> {
    val memory = initialMemory.toMutableList()
    var opcodeIdx = 0
    while (memory[opcodeIdx] != 99) {
        val opcode = memory[opcodeIdx]
        if (opcode == 1)
            memory.set(opcodeIdx) { memory.arg1(opcodeIdx) + memory.arg2(opcodeIdx) }
        else if (opcode == 2)
            memory.set(opcodeIdx) { memory.arg1(opcodeIdx) * memory.arg2(opcodeIdx) }
        else
            throw IllegalStateException()
        opcodeIdx += 4
    }
    return memory
}

fun List<Int>.arg1(opcodeIdx: Int) = this[this[opcodeIdx + 1]]
fun List<Int>.arg2(opcodeIdx: Int) = this[this[opcodeIdx + 2]]
fun MutableList<Int>.set(opcodeIdx: Int, value: () -> Int) {
    this[this[opcodeIdx + 3]] = value.invoke()
}
