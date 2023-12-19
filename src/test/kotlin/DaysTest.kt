import day05Utils.Mapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.File
import java.util.stream.Stream
import kotlin.test.assertEquals

class DaysTest {

    private val map: Map<(String) -> Number, Map<String, Number>> = mapOf(
        { input: String -> Day01().part1(input) } to mapOf(
            "src/test/resources/day01_test_input.txt" to 142,
            "src/test/resources/day01_full_input.txt" to 53194,
        ),
        { input: String -> Day01().part2(input) } to mapOf(
            "src/test/resources/day01_part2_test_input.txt" to 281,
            "src/test/resources/day01_full_input.txt" to 54249,
        ),
        { input: String -> Day02().part1(input) } to mapOf(
            "src/test/resources/day02_test_input.txt" to 8,
            "src/test/resources/day02_full_input.txt" to 2617,
        ),
        { input: String -> Day02().part2(input) } to mapOf(
            "src/test/resources/day02_test_input.txt" to 2286,
            "src/test/resources/day02_full_input.txt" to 59795,
        ),
        { input: String -> Day03().part1(input) } to mapOf(
            "src/test/resources/day03_test_input.txt" to 4361,
            "src/test/resources/day03_full_input.txt" to 512794,
        ),
        { input: String -> Day03().part2(input) } to mapOf(
            "src/test/resources/day03_test_input.txt" to 467835,
            "src/test/resources/day03_full_input.txt" to 67779080,
        ),
        { input: String -> Day04().part1(input) } to mapOf(
            "src/test/resources/day04_test_input.txt" to 13,
            "src/test/resources/day04_full_input.txt" to 18653,
        ),
        { input: String -> Day04().part2(input) } to mapOf(
            "src/test/resources/day04_part2_test_input.txt" to 30,
            "src/test/resources/day04_full_input.txt" to 5921508,
        ),
        { input: String -> Day05().part1(input) } to mapOf(
            "src/test/resources/day05_test_input.txt" to (35).toLong(),
            "src/test/resources/day05_full_input.txt" to (51580674).toLong(),
        ),
        { input: String -> Day05().part2(input) } to mapOf(
            "src/test/resources/day05_test_input.txt" to (46).toLong(),
            "src/test/resources/day05_full_input.txt" to (-1).toLong(),
        ),
    )

    @Test
    fun test() {
        map.forEach { (fn, data) -> data.forEach { (filePath, expectedResult) -> test(filePath, expectedResult, fn) } }
    }

    @ParameterizedTest
    @MethodSource("provideGrids")
    fun findAdjacentPositions(grid: Grid, startsAt: Int, length: Int, expectedResult: Set<Int>, message: String) {
        assertEquals(
            expectedResult,
            Day03().getAdjacentPositionIndexes(grid, startsAt, length),
            message
        )
    }

    @ParameterizedTest
    @MethodSource("provideStrings")
    fun findDoubleDigitOutOfFirstAndLast(expected: Int, input: String) {
        assertEquals(
            expected,
            Day01().findDoubleDigitOutOfFirstAndLast(input)
        )

    }

    private fun test(inputFilePath: String, expectedResult: Number, fn: (String) -> Number) {
        val input = File(inputFilePath).readText()
        assertEquals(
            expectedResult,
            fn(input)
        )
    }

    companion object {
        @JvmStatic
        fun provideStrings(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(11, "1a"),
                Arguments.of(13, "onetwothree"),
                Arguments.of(13, "abcone2threexyz"),
                Arguments.of(83, "eightwothree"),
                Arguments.of(81, "eightwo1"),
                Arguments.of(81, "eightwoone"),
                Arguments.of(12, "1eightwo"),
                Arguments.of(18, "1oneight"),
                Arguments.of(18, "1oneightX"),
                Arguments.of(11, "Xoneight"),
                Arguments.of(11, "oneight"),
                Arguments.of(12, "oneight2"),
                Arguments.of(48, "4oneightqd"),
                Arguments.of(62, "ztgszqjjsrtmgqx6572"),
            )
        }

        @JvmStatic
        fun provideGrids(): Stream<Arguments> {
            return Stream.of(
                // 0 1 2
                // 3 4 5
                // 6 7 8
                Arguments.of(Grid(3, 3), 0, 1, setOf(1, 3, 4), "top left corner"),
                Arguments.of(Grid(3, 3), 1, 1, setOf(0, 2, 3, 4, 5), ""),
                Arguments.of(Grid(3, 3), 2, 1, setOf(1, 4, 5), ""),
                Arguments.of(Grid(3, 3), 3, 1, setOf(0, 1, 4, 6, 7), ""),
                Arguments.of(Grid(3, 3), 4, 1, setOf(0, 1, 2, 3, 5, 6, 7, 8), "middle"),
                Arguments.of(Grid(3, 3), 5, 1, setOf(1, 2, 4, 7, 8), ""),
                Arguments.of(Grid(3, 3), 6, 1, setOf(3, 4, 7), ""),
                Arguments.of(Grid(3, 3), 7, 1, setOf(3, 4, 5, 6, 8), ""),
                Arguments.of(Grid(3, 3), 8, 1, setOf(4, 5, 7), "bottom right corner"),

                Arguments.of(Grid(3, 3), 0, 3, setOf(3, 4, 5), "top row"),
                Arguments.of(Grid(3, 3), 3, 3, setOf(0, 1, 2, 6, 7, 8), "middle row"),
                Arguments.of(Grid(3, 3), 6, 3, setOf(3, 4, 5), "bottom row"),

                // 0   1   2   3   4
                // 5   6   7   8   9
                // 10  11  12  13  14
                Arguments.of(Grid(5, 3), 6, 3, setOf(0, 1, 2, 3, 4, 5, 9, 10, 11, 12, 13, 14), "larger grid"),
            );
        }

        @JvmStatic
        fun provideRanges(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(lr(1..1), lr(2..2), listOf(lr(1..1))),
                Arguments.of(lr(3..3), lr(2..2), listOf(lr(3..3))),
                Arguments.of(lr(1..3), lr(2..2), listOf(lr(1..1), lr(2..2), lr(3..3))),
                Arguments.of(lr(2..2), lr(2..2), listOf(lr(2..2))),
                Arguments.of(lr(2..2), lr(1..3), listOf(lr(2..2))),
                Arguments.of(lr(1..3), lr(2..4), listOf(lr(1..1), lr(2..3))),
                Arguments.of(lr(3..5), lr(2..4), listOf(lr(3..4), lr(5..5))),
                Arguments.of(lr(2..3), lr(2..4), listOf(lr(2..3))),
                Arguments.of(lr(3..4), lr(2..4), listOf(lr(3..4))),
                Arguments.of(lr(1..2), lr(2..2), listOf(lr(1..1), lr(2..2))),
                Arguments.of(lr(1..3), lr(1..2), listOf(lr(1..2), lr(3..3))),
                Arguments.of(lr(1..3), lr(2..3), listOf(lr(1..1), lr(2..3))),
            )
        }

    }

    @ParameterizedTest
    @MethodSource("provideRanges")
    fun splitInputToMatchRangesInFull(input: LongRange, range: LongRange, expected: List<LongRange>) {
        assertEquals(
            expected, Mapper.splitRange(input, range)
        )
    }

    @Test
    fun consecutives() {
        assertEquals(
            listOf(listOf(1), listOf(3, 4), listOf(6)),
            Day03().consecutives(setOf(1, 3, 4, 6))
        )

        assertEquals(
            listOf(listOf(1), listOf(3, 4), listOf(6)),
            Day03().consecutives(setOf(6, 4, 1, 3))
        )
    }
}