package adventofcode.day04

fun IntRange.possiblePasswordCount(): Int = this.count(Int::isValidPassword)

internal fun Int.isValidPassword() =
    this.hasTwoAdjacentSameDigits() && this.digitsNeverDecrease()

internal fun Int.digitsNeverDecrease(): Boolean {
    val digits = this.toString().toCharArray().map { it.toInt() }
    for (idx in 1 until digits.size) {
        if (digits[idx] < digits[idx - 1]) return false
    }
    return true
}

internal fun Int.hasTwoAdjacentSameDigits(): Boolean {
    val chars = this.toString().toCharArray()
    for (idx in 1 until chars.size) {
        if (chars[idx - 1] == chars[idx]) return true
    }
    return false
}

// part 2

fun IntRange.possibleStrictPasswordCount(): Int = this.count(Int::isStrictlyValidPassword)

internal fun Int.isStrictlyValidPassword() =
    this.hasAtLeastOneSetOfTwoAdjacentSameDigits() && this.digitsNeverDecrease()

internal fun Int.hasAtLeastOneSetOfTwoAdjacentSameDigits(): Boolean {
    val chars = this.toString()
    var streakStart = 0
    while (streakStart < chars.length - 1) {
        var streakLength = 1
        for (streakEnd in streakStart + 1 until chars.length) {
            if (chars[streakStart] == chars[streakEnd]) streakLength++
            else break
        }
        if (streakLength == 2) return true
        streakStart += streakLength
    }
    return false
}
