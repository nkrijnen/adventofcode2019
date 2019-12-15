package adventofcode.day14

import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.round

data class Element(val name: String)

data class Amount(val element: Element, val amount: Long)

data class Reaction(
    private val produces: Element,
    private val batchSize: Long,
    private val ingredients: Set<Amount>
) {
    internal var totalProduced: Long = 0
    private var totalUsed: Long = 0

    fun consume(amount: Long, consumeRequired: (Element, Long) -> Unit) {
        val amountInStore = totalProduced - totalUsed
        val takenFromStore = min(amount, amountInStore)
        val toProduce = amount - takenFromStore
        val produced = (ceil(toProduce.toDouble() / batchSize) * batchSize).toLong()

        if (toProduce > 0) ingredients.forEach { (element, required) ->
            consumeRequired(element, round(required.toDouble() / batchSize * produced).toLong())
        }

        totalProduced += produced
        totalUsed += takenFromStore + toProduce
    }
}

class Reactor(reactions: Map<Element, Reaction>, private val oreInHold: Long = 1000000000000) {
    private val ore = Element("ORE")
    private val fuel = Element("FUEL")
    private val oreReaction = ore to Reaction(ore, 1, emptySet())
    private val reactions = reactions + oreReaction

    fun howMuchOreForOneFuel(): Long {
        consume(fuel, 1)
        return consumedOre
    }

    fun howMuchFuelForOreInHold(): Long {
        val oreForOneFuel = howMuchOreForOneFuel()
        var fuelFromOre = oreInHold / oreForOneFuel
        // consume base amount (-1 since we already consumed 1 fuel above)
        consume(fuel, fuelFromOre - 1)
        try {
            while (true) {
                consume(fuel, 1)
                fuelFromOre++
            }
        } catch (e: NotEnoughOreException) {
        }
        return fuelFromOre
    }

    private fun consume(element: Element, amount: Long) {
        if (element == ore && consumedOre + amount > oreInHold) throw NotEnoughOreException()
        reactions.getValue(element)
            .consume(amount, ::consume)
    }

    private val consumedOre: Long get() = oreReaction.second.totalProduced
}

private class NotEnoughOreException : Exception()

// 15 NRLM, 5 KDXDC, 1 DQRQW => 5 QTSK
fun String.parseReactions(): Map<Element, Reaction> = this.lines().map {
    val (ingredients, result) = it.split(" => ")
    val resultAmount = result.parseAmount()
    Pair(
        resultAmount.element,
        Reaction(
            produces = resultAmount.element,
            batchSize = resultAmount.amount,
            ingredients = ingredients.split(", ")
                .map { it.parseAmount() }.toSet()
        )
    )
}.toMap()

// 5 QTSK
private fun String.parseAmount() = this.split(" ")
    .let { (nr, name) -> Amount(Element(name), nr.toLong()) }
