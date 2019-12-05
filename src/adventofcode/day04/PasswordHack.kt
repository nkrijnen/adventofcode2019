package adventofcode.day04

fun IntRange.possiblePasswordCount(): Int = this.count(Int::isValidPassword)

internal fun Int.isValidPassword() = this.hasTwoAdjacentSameDigits() && this.digitsNeverDecrease()

internal fun Int.hasTwoAdjacentSameDigits(): Boolean {
    val chars = this.toString().toCharArray()
    for (idx in 1 until chars.size) {
        if (chars[idx - 1] == chars[idx]) return true
    }
    return false
}

internal fun Int.digitsNeverDecrease(): Boolean {
    val digits = this.toString().toCharArray().map { it.toInt() }
    for (idx in 1 until digits.size) {
        if (digits[idx] < digits[idx - 1]) return false
    }
    return true
}

