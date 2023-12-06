class Day01 : Day {
    override fun part1(input: String): Int {
        return input
            .split("\n")
            .map { it.filter { that -> isNumeric(that) } }
            .sumOf { (it.first() + "" + it.last()).toInt() }
    }

    override fun part2(input: String): Int {
        return input
            .split("\n")
            .map { replaceNumbersAsWordsWithNumbersAsDigits(it) }
            .map { it.filter { that -> isNumeric(that) } }
            .sumOf { (it.first() + "" + it.last()).toInt() }
    }

    fun findDoubleDigitOutOfFirstAndLast(s: String): Int {
        val digits = replaceNumbersAsWordsWithNumbersAsDigits(s).filter { c -> isNumeric(c) }
        return (digits.first() + "" + digits.last()).toInt()
    }

    private fun replaceNumbersAsWordsWithNumbersAsDigits(input: String): String {
        val regex = Regex("([1-9]|one|two|three|four|five|six|seven|eight|nine)")
        val replacement = { match: MatchResult ->
            when (match.value) {
                "one", "1" -> "1"
                "two", "2" -> "2"
                "three", "3" -> "3"
                "four", "4" -> "4"
                "five", "5" -> "5"
                "six", "6" -> "6"
                "seven", "7" -> "7"
                "eight", "8" -> "8"
                "nine", "9" -> "9"
                else -> throw RuntimeException()
            }
        }

        val inputWithFirstOccurenceReplaced = replace(input, regex, replacement)


        val reversedRegex = Regex("([1-9]|eno|owt|eerht|ruof|evif|xis|neves|thgie|enin)")
        val reversedReplacement = { match: MatchResult ->
            when (match.value) {
                "eno", "1" -> "1"
                "owt", "2" -> "2"
                "eerht", "3" -> "3"
                "ruof", "4" -> "4"
                "evif", "5" -> "5"
                "xis", "6" -> "6"
                "neves", "7" -> "7"
                "thgie", "8" -> "8"
                "enin", "9" -> "9"
                else -> throw RuntimeException()
            }
        }

        val modifiedAgain = replace(inputWithFirstOccurenceReplaced.reversed(), reversedRegex, reversedReplacement)

        return modifiedAgain.reversed()
    }

    private fun replace(input: String, re: Regex, replacement: (MatchResult) -> CharSequence): String {
        val matches = re.findAll(input)
        val matchedRange = matches.first().range

        val modified = input.replace(re, fun(match: MatchResult): CharSequence {
            if (match.range.first == matchedRange.first && match.range.last == matchedRange.last) {
                return replacement(match)
            }

            return match.value
        })
        return modified
    }

    private fun isNumeric(char: Char): Boolean {
        return char.toString().toIntOrNull()?.let { true } ?: false
    }
}