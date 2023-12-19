import day05Utils.Mapper
import day05Utils.Pipeline
import java.util.LinkedList

class Day05 {
    fun part1(input: String): Number {
        val seeds = input.split("\n").first().split(" ").mapNotNull { it.toLongOrNull() }.map { listOf(it..it) }
        val maps = input.split("\n", limit = 2).last().split("\n\n").map { it.trim() }
        val mappers = LinkedList(maps.map { Mapper(it) })
        val pipeline = Pipeline(mappers)

        return seeds.map{pipeline.process(it)}.map{it.first().first}.min()
    }

    fun part2(input: String): Number {
        val seeds = mutableListOf<List<LongRange>>()
        val m = input.split("\n").first().split(" ").mapNotNull { it.toLongOrNull() }
        for (i in m.indices step 2) {
            seeds.add(listOf( m[i]..<m[i] + m[i + 1]))
        }

        val maps = input.split("\n", limit = 2).last().split("\n\n").map { it.trim() }
        val mappers = LinkedList(maps.map { Mapper(it) })
        val pipeline = Pipeline(mappers)

        return seeds.map{pipeline.process(it)}.flatten().map{it.first}.min()
    }
}

fun lr(range: IntRange): LongRange {
    return range.first.toLong()..range.last.toLong()
}