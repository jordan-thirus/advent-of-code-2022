fun main() {
    fun part1(input: List<String>): Int {

        val scores = buildMap {
            put(OpponentPlay.ROCK,
                mapOf(
                    Play.ROCK to Play.ROCK.score + Outcome.DRAW.score,
                    Play.PAPER to Play.PAPER.score + Outcome.WIN.score,
                    Play.SCISSORS to Play.SCISSORS.score + Outcome.LOSE.score))
            put(OpponentPlay.PAPER,
                mapOf(
                    Play.ROCK to Play.ROCK.score + Outcome.LOSE.score,
                    Play.PAPER to Play.PAPER.score + Outcome.DRAW.score,
                    Play.SCISSORS to Play.SCISSORS.score + Outcome.WIN.score))
            put(OpponentPlay.SCISSORS,
                mapOf(
                    Play.ROCK to Play.ROCK.score + Outcome.WIN.score,
                    Play.PAPER to Play.PAPER.score + Outcome.LOSE.score,
                    Play.SCISSORS to Play.SCISSORS.score + Outcome.DRAW.score))
        }
        fun scoreGame2(game: String): Int{
            val opponentPlay = OpponentPlay.get(game[0])
            val selfPlay = Play.get(game[2])

            return scores.getOrDefault(opponentPlay, emptyMap()).getOrDefault(selfPlay, 0)
        }
        return input.sumOf { game -> scoreGame2(game) }
    }

    fun part2(input: List<String>): Int {
        fun scoreGame(game: String): Int {
            val opponentPlay = OpponentPlay.get(game[0])
            val outcome = Outcome.get(game[2])
            val score = when(opponentPlay) {
                OpponentPlay.ROCK -> {
                    when(outcome) {
                        Outcome.WIN -> Play.PAPER.score + Outcome.WIN.score
                        Outcome.DRAW -> Play.ROCK.score + Outcome.DRAW.score
                        Outcome.LOSE -> Play.SCISSORS.score + Outcome.LOSE.score
                    }
                }
                OpponentPlay.PAPER -> {
                    when(outcome) {
                        Outcome.WIN -> Play.SCISSORS.score + Outcome.WIN.score
                        Outcome.DRAW -> Play.PAPER.score + Outcome.DRAW.score
                        Outcome.LOSE -> Play.ROCK.score + Outcome.LOSE.score
                    }
                }
                OpponentPlay.SCISSORS -> {
                    when(outcome) {
                        Outcome.WIN -> Play.ROCK.score + Outcome.WIN.score
                        Outcome.DRAW -> Play.SCISSORS.score + Outcome.DRAW.score
                        Outcome.LOSE -> Play.PAPER.score + Outcome.LOSE.score
                    }
                }
            }
            //println("Game '$game' had score $score")
            return score
        }
        return input.sumOf { game -> scoreGame(game) }
    }


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

enum class Outcome(val score:Int) {
    LOSE(0),
    DRAW(3),
    WIN(6);

    companion object {
        fun get(outcome: Char) = when(outcome){
            'X' -> LOSE
            'Y' -> DRAW
            'Z' -> WIN
            else -> throw UnsupportedOperationException("Invalid outcome")
        }
    }
}

enum class OpponentPlay{
    ROCK,
    PAPER,
    SCISSORS;

    companion object {
        fun get(play: Char) = when(play){
            'A' -> ROCK
            'B' -> PAPER
            'C' -> SCISSORS
            else -> throw UnsupportedOperationException("Invalid play")
        }
    }
}

enum class Play(val score: Int){
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    companion object {
        fun get(play: Char) = when(play){
            'X' -> ROCK
            'Y' -> PAPER
            'Z' -> SCISSORS
            else -> throw UnsupportedOperationException("Invalid play")
        }
    }
}