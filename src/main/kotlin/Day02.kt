class Day02 : Day {
    override fun part1(input: String): Int {
        val games = parse(input)
        var sumOfValidGames = 0

        game@ for (game in games) {
            var bagHasBeenInvalidWithinARound = false

            round@ for (round in game.rounds) {
                var bag = mutableMapOf(Color.RED to 12, Color.GREEN to 13, Color.BLUE to 14)

                turn@ for (turn in round.turns) {
                    bag = pick(bag, turn.color, turn.count)
                }

                if (!isBagValid(bag)) {
                    bagHasBeenInvalidWithinARound = true
                }
            }

            if (!bagHasBeenInvalidWithinARound) {
                sumOfValidGames += game.id
            }
        }

        return sumOfValidGames
    }

    private fun parse(input: String): List<Game> {
        val games = input.split("\n").map {
            val x = it.split(":")
            val gameId = x.first().replace("Game", "").trim().toInt()
            val rounds = x.last().split(";").map {
                val turns = it.split(",").map {
                    val y = it.trim().split(" ")
                    val count = y.first().toInt()
                    val color = y.last()

                    Turn(Color.valueOf(color.uppercase()), count)
                }

                Round(turns)
            }

            Game(gameId, rounds)
        }

        return games
    }

    private fun isBagValid(bag: MutableMap<Color, Int>) =
        bag[Color.RED]!! >= 0 && bag[Color.GREEN]!! >= 0 && bag[Color.BLUE]!! >= 0

    private fun pick(bag: MutableMap<Color, Int>, color: Color, count: Int): MutableMap<Color, Int> {
        val current = bag[color] ?: 0
        val changed = current - count
        bag[color] = changed
        return bag
    }

    override fun part2(input: String): Int {
        val games = parse(input)
        var sum = 0

        game@ for (game in games) {
            var red = 0
            var green = 0
            var blue = 0

            round@ for (round in game.rounds) {
                turn@ for (turn in round.turns) {
                    if (turn.color == Color.RED && turn.count > red) {
                        red = turn.count
                    }

                    if (turn.color == Color.BLUE && turn.count > blue) {
                        blue = turn.count
                    }

                    if (turn.color == Color.GREEN && turn.count > green) {
                        green = turn.count
                    }
                }
            }

            sum += red * blue * green
        }

        return sum
    }
}

data class Game(val id: Int, val rounds: List<Round>) {

}

data class Round(val turns: List<Turn>) {

}

data class Turn(val color: Color, val count: Int) {

}

enum class Color {
    RED, GREEN, BLUE
}