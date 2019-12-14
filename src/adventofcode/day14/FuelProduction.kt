package adventofcode.day14

import kotlin.math.ceil
import kotlin.math.min
import kotlin.math.round

data class Element(val name: String)

data class Amount(val element: Element, val amount: Int)

data class Reaction(
    private val produces: Element,
    private val batchSize: Int,
    private val ingredients: Set<Amount>
) {
    internal var totalProduced: Int = 0
    private var totalUsed: Int = 0

    fun consume(amount: Int, consumeRequired: (Element, Int) -> Unit) {
        val amountInStore = totalProduced - totalUsed
        val takenFromStore = min(amount, amountInStore)
        val toProduce = amount - takenFromStore
        val produced = (ceil(toProduce.toDouble() / batchSize) * batchSize).toInt()

        if (toProduce > 0) ingredients.forEach { (element, required) ->
            consumeRequired(element, round(required.toDouble() / batchSize * produced).toInt())
        }

        totalProduced += produced
        totalUsed += takenFromStore + toProduce

        println("+ $produces: produced $totalProduced, used $totalUsed")
    }
}

class Reactor(reactions: Map<Element, Reaction>) {
    private val oreReaction = Element("ORE") to Reaction(Element("ORE"), 1, emptySet())
    private val reactions = reactions + oreReaction

    fun howMuchOreForOneFuel(): Int {
        consume(Element("FUEL"), 1)
        return consumedOre
    }

    private fun consume(element: Element, amount: Int) {
        println("$element: require $amount")
        reactions.getValue(element)
            .consume(amount, ::consume)
    }

    private val consumedOre: Int get() = oreReaction.second.totalProduced
}

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
    .let { (nr, name) -> Amount(Element(name), nr.toInt()) }
