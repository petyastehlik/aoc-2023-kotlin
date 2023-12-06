class Day03 : Day {
    override fun part1(input: String): Int {
        val grid = Grid(
            input.split("\n").first().length,
            input.split("\n").count()
        )

        val flattenedInputString = input.replace("\n", "")

        val re = Regex("[0-9]+")
        val matches = re.findAll(flattenedInputString)
        var sum = 0

        for (match in matches) {
            val adjacents = getAdjacentPositionIndexes(grid, match.range.first, match.value.length)
            val symbolsCount =
                adjacents.map { flattenedInputString[it] }.count { !it.isDigit() && it.toString() != "." }

            if (symbolsCount > 0) {
                sum += match.value.toInt()
            }
        }

        return sum
    }

    override fun part2(input: String): Int {
        val grid = Grid(
            input.split("\n").first().length,
            input.split("\n").count()
        )

        val flattenedInputString = input.replace("\n", "")

        val re = Regex("[0-9]+")
        val matches = re.findAll(flattenedInputString)

        val stars = mutableMapOf<Int, MutableList<Int>>()

        for (match in matches) {
            val adjacentsOfNumbers = getAdjacentPositionIndexes(grid, match.range.first, match.value.length)
            val adjacentStarPositions = adjacentsOfNumbers.filter { flattenedInputString[it].toString() == "*" }

            for (starPosition in adjacentStarPositions) {
                if (stars.contains(starPosition)) {
                    stars[starPosition]?.add(match.value.toInt())
                } else {
                    stars[starPosition] = mutableListOf(match.value.toInt())
                }
            }
        }

        return stars
            .filter { it.toPair().second.count() == 2 }
            .map { it.toPair().second }
            .sumOf { it.first() * it.last() }
    }

    fun getAdjacentPositionIndexes(grid: Grid, startsAt: Int, length: Int): Set<Int> {
        val adjacents: MutableList<Int> = mutableListOf()
        val columnIndex = startsAt % grid.cols
        val rowIndex = ((startsAt / grid.cols) % grid.rows)


        val roomToLeft = columnIndex > 0
        val roomToRight = (columnIndex + length) < grid.cols
        val roomToTop = rowIndex > 0
        val roomToBottom = rowIndex < grid.rows - 1

        if (roomToLeft) {
            adjacents.add(startsAt - 1)
        }

        if (roomToRight) {
            adjacents.add(startsAt + length)
        }

        if (roomToTop) {
            for (i in 0..<length) {
                adjacents.add(startsAt - grid.cols + i)
            }
        }

        if (roomToBottom) {
            for (i in 0..<length) {
                adjacents.add(startsAt + grid.cols + i)
            }
        }

        if (roomToTop && roomToLeft) {
            adjacents.add(startsAt - grid.cols - 1)
        }

        if (roomToTop && roomToRight) {
            adjacents.add(startsAt - grid.cols + length)
        }

        if (roomToBottom && roomToLeft) {
            adjacents.add(startsAt + grid.cols - 1)
        }

        if (roomToBottom && roomToRight) {
            adjacents.add(startsAt + grid.cols + length)
        }

        return adjacents.toSet()
    }

    fun consecutives(set: Set<Int>): List<List<Int>> {
        var tmp: MutableList<Int> = mutableListOf()
        val lists: MutableList<List<Int>> = mutableListOf()

        for (n in set.sorted()) {
            tmp.add(n)

            // not consecutive, add list
            if (!set.contains(n + 1)) {
                lists.add(tmp)
                // and create new empty
                tmp = mutableListOf()
            }
        }

        // add final list
        if (tmp.isNotEmpty()) {
            lists.add(tmp)
        }

        return lists.toMutableList()
    }
}

data class Grid(val cols: Int, val rows: Int) {

}