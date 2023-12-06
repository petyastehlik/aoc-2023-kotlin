import kotlin.math.pow

class Day04 : Day {
    override fun part1(input: String): Int {
        val parsed = parse(input)
        val base = 2.0
        var sum = 0.0

        for (pair in parsed) {
            val matching = pair.toSet().first().intersect(pair.toSet().last()).count()

            if (matching > 0) {
                sum += base.pow(matching - 1)
            }
        }

        return sum.toInt()
    }

    override fun part2(input: String): Int {
        val parsed = parse(input)

        val bonuses = (1..parsed.count()).associateWith { 1 }.toMutableMap()

        var i = 1
        for (pair in parsed) {
            val matching = pair.toSet().first().intersect(pair.toSet().last()).count()

            if (matching > 0) {
                val copies = bonuses[i] ?: throw RuntimeException()

                for (x in 1..matching) {
                    val current = bonuses[i + x] ?: throw RuntimeException()
                    bonuses[i + x] = current + copies
                }
            }

            i++
        }

        return bonuses.map { it.value }.sum()

    }

    private fun parse(input: String) = input
        .split("\n")
        .map { it.split(":").last() }
        .map { it.split("|").map { it.split(" ").filter { it != "" }.map { it.toInt() } } }
}