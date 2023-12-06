import java.util.LinkedList

class Day05 {
    fun part1(input: String): Number {
        val seeds = input.split("\n").first().split(" ").mapNotNull { it.toLongOrNull() }
        val maps = input.split("\n", limit = 2).last().split("\n\n").map{it.trim()}
        val mappers = LinkedList(maps.map{Mapper(it)})
        val pipeline = Pipeline(mappers)

        return seeds.minOf { pipeline.process(it) }
    }

    fun part2(input: String): Number {
        return 0
    }
}

class Mapper(data: String)
{
    private val name: String = data.split("\n", limit = 2).first()
    private val ranges: List<String> = data.split("\n", limit = 2).last().split("\n")

    fun map(input: Long): Long {
        for (range in ranges) {
            val parts = range.split(" ").map { it.toLong() }
            val min = parts[1]
            val max = parts[1] + parts[2]

            // not mapped, thus it remains the same
            if (input < min || input > max) {
                continue
            }

            return input + (parts[0] - parts[1])
        }

        return input
    }
}

class Pipeline(private val mappers: LinkedList<Mapper>) {
    fun process(seed: Long): Long {
        var interseed = seed
        for (mapper in mappers) {
            interseed = mapper.map(interseed)
        }

        return interseed
    }
}