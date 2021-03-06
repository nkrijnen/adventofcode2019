package adventofcode.day01

import kotlin.math.floor

fun main() {
    val massForModules = parseInput(input)
    val sumOfFuelForMass = massForModules
        .map(::fuelRequiredForMass)
        .sum()
    println("Fuel for mass: $sumOfFuelForMass")

    val sumOfFuelForMassAndFuel = massForModules
        .map(::fuelRequiredForMassIncludingExtraFuel)
        .sum()
    println("Fuel for mass and fuel: $sumOfFuelForMassAndFuel")
}

fun parseInput(input: String): List<Int> = input.lines().map { it.toInt() }

// Fuel required for a module, take its mass, divide by three, round down, and subtract 2
fun fuelRequiredForMass(mass: Number): Int = floor(mass.toDouble() / 3).toInt() - 2

// Fuel itself requires fuel just like a module, mass that would require negative fuel should instead be treated as if it requires zero fuel
fun fuelRequiredForMassIncludingExtraFuel(mass: Number): Int {
    val fuel = fuelRequiredForMass(mass)
    if (fuel <= 0) return 0
    return fuel + fuelRequiredForMassIncludingExtraFuel(fuel)
}

private const val input = """51590
53619
101381
81994
139683
53417
124196
127640
99688
116170
127812
95979
73734
105347
130495
89331
116486
65177
143689
130487
57206
74950
141398
100921
114019
137106
137690
70779
61421
121827
122432
108229
65362
70884
56127
83611
126776
91422
51444
52424
69535
110270
115006
97214
85306
77028
102078
82928
101635
91889
58082
72996
74276
135691
113622
118522
56796
115576
138861
63418
64090
131682
93394
61302
98591
67253
69822
121652
133636
106283
83460
53394
65208
66158
113100
52984
126741
75880
124770
54681
69994
138088
83435
75332
114436
141680
68659
111337
56920
74203
96424
86848
69561
53861
118216
79570
136039
120959
122917
122226"""
