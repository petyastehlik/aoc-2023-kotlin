package day05Utils

class Mapper(data: String) {
    private val name: String = data.split("\n", limit = 2).first()
    private val ranges: List<Map<LongRange, Long>> = data
        .split("\n", limit = 2)
        .last()
        .split("\n")
        .sortedBy { it.split(" ")[1] }
        .map { it.split(" ").map { it.toLong() } }
        .map { mapOf(it[1]..<it[1] + it[2] to it[0] - it[1]) } + listOf(mapOf(1..9223372036854775807 to 0)) // catch-all range

    fun map(input: List<LongRange>): List<LongRange> {
        val output: MutableList<LongRange> = mutableListOf()
        val completeInputs: MutableList<LongRange> = mutableListOf()
        val added: MutableSet<String> = mutableSetOf()

        for (i in input) {
            for (r in 0..<ranges.size - 1) {

                val splitted = splitSingleInputIntoMultipleToMatchSingleRange(i, ranges[r].keys.first())
                for (split in splitted) {
                    if (split.toString() !in added) {
                        added.add(split.toString())
                        completeInputs.add(split)
                    }
                }
            }
        }

        input@ for (i in completeInputs) {
            range@ for (r in ranges) {
                val range = r.keys.first()
                val diff = r.values.first()
                val match = !(i.last < range.first || i.first > range.last)
                if (match) {
                    output.add((i.first + diff)..(i.last + diff))
                    continue@input
                }
            }
        }

        return output.toList()
    }


    private fun splitSingleInputIntoMultipleToMatchSingleRange(input: LongRange, range: LongRange): List<LongRange> {
        val output = mutableListOf<LongRange>()
        val added = mutableSetOf<String>()


        // ensure we're not returning the same range multiple times
        for (split in splitRange(input, range)) {
            if (split.toString() !in added) {
                added.add(split.toString())
                output.add(split)
            }
        }

        return output.toList()
    }

    companion object {
        fun splitRange(input: LongRange, range: LongRange): List<LongRange> {
            if (input.last < range.first) {
                return listOf(input)
            }

            if (input.first > range.last) {
                return listOf(input)
            }

            if (input.first < range.first && input.last > range.last) {
                return listOf(input.first..<range.first, range.first..range.last, (range.last + 1)..input.last)
            }

            if (input.first >= range.first && input.last <= range.last) {
                return listOf(input)
            }

            if (input.first < range.first && input.last < range.last) {
                return listOf(input.first..<range.first, range.first..input.last)
            }

            if (input.first > range.first) {
                return listOf(input.first..range.last, (range.last + 1)..input.last)
            }

            if (input.first < range.first && input.last == range.first && range.first == range.last) {
                return listOf(input.first..<input.last, input.last..range.last)
            }

            if (input.first == range.first && input.last > range.last) {
                return listOf(input.first..range.last, range.last + 1..input.last)
            }

            if (input.first < range.first && input.last == range.last) {
                return listOf(input.first..<range.first, range.first..input.last)
            }

            throw RuntimeException()
        }
    }

}