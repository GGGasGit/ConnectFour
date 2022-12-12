package connectfour

class Board(val rows: Int, val columns: Int) {
    val table = MutableList(this.rows) { MutableList(this.columns) { " " } }

    fun isFull(): Boolean {
        for (i in 0 until this.rows) {
            for (j in 0 until this.columns) {
                if (this.table[i][j] == " ") return false
            }
        }
        return true
    }

}

class Player(val name: String) {
    var isNext = false
    var sign = ""
    var points = 0
}

class Game(private val board: Board, private val player1: Player, private val player2: Player, private val numberOfGames: Int) {
    private val multiGame = numberOfGames > 1
    private var round = 1
    init {
        println("${this.player1.name} VS ${this.player2.name}")
        println("${this.board.rows} X ${this.board.columns} board")
        println(if (this.numberOfGames == 1) "Single game" else "Total ${this.numberOfGames} games")
        this.player1.isNext = true
        this.player1.sign = "o"
        this.player2.sign = "*"
    }

    private fun printBoard() {
        for (i in 1..this.board.columns) {
            print(" $i")
        }
        println(" ")
        for (i in 0 until this.board.rows) {
            for (j in 0 until this.board.columns) {
                print("║${this.board.table[i][j]}")
            }
            println("║")
        }
        print("╚")
        repeat(this.board.columns - 1) {
            print("═╩")
        }
        println("═╝")
    }

    private fun getColumnInput(input: String): Int {
        val acceptableInput = Regex("[0-9]+")
        return if (input == "end") {
            -1
        } else if (!acceptableInput.matches(input.replace(Regex("\\s"), ""))) {
            println("Incorrect column number")
            0
        } else if (input.toInt() < 1 || input.toInt() > this.board.columns) {
            println("The column number is out of range (1 - ${this.board.columns})")
            0
        } else if (this.board.table[0][input.toInt() - 1] != " ") {
            println("Column ${input.toInt()} is full")
            0
        } else {
            input.toInt()
        }
    }

    private fun putSignInColumn(colNumber: Int) {
        for (i in this.board.rows - 1 downTo 0) {
            if (this.board.table[i][colNumber - 1] == " ") {
                this.board.table[i][colNumber - 1] = if (this.player1.isNext) this.player1.sign else this.player2.sign
                break
            }
        }
    }

    private fun roundWon(): Boolean {
        for (i in 0 until this.board.rows) {
            for (j in 0..this.board.columns - 4) {
                if (this.board.table[i][j] == this.board.table[i][j+1] &&
                    this.board.table[i][j] == this.board.table[i][j+2] &&
                    this.board.table[i][j] == this.board.table[i][j+3] &&
                    (this.board.table[i][j] == player1.sign || this.board.table[i][j] == player2.sign)) return true
            }
        }
        for (j in 0 until this.board.columns) {
            for (i in 0..this.board.rows - 4) {
                if (this.board.table[i][j] == this.board.table[i+1][j] &&
                    this.board.table[i][j] == this.board.table[i+2][j] &&
                    this.board.table[i][j] == this.board.table[i+3][j] &&
                    (this.board.table[i][j] == player1.sign || this.board.table[i][j] == player2.sign)) return true
            }
        }
        for (i in 3 until this.board.rows) {
            for (j in 0..this.board.columns - 4) {
                if (this.board.table[i][j] == this.board.table[i-1][j+1] &&
                    this.board.table[i][j] == this.board.table[i-2][j+2] &&
                    this.board.table[i][j] == this.board.table[i-3][j+3] &&
                    (this.board.table[i][j] == player1.sign || this.board.table[i][j] == player2.sign)) return true
            }
        }
        for (i in 3 until this.board.rows) {
            for (j in 3 until this.board.columns) {
                if (this.board.table[i][j] == this.board.table[i-1][j-1] &&
                    this.board.table[i][j] == this.board.table[i-2][j-2] &&
                    this.board.table[i][j] == this.board.table[i-3][j-3] &&
                    (this.board.table[i][j] == player1.sign || this.board.table[i][j] == player2.sign)) return true
            }
        }
        return false
    }

    private fun runRound(): Int {
        while(true) {
            var colNumber: Int
            do {
                println("${if (this.player1.isNext) this.player1.name else this.player2.name}'s turn:")
                colNumber = getColumnInput(readln())
            } while (colNumber == 0)
            if (colNumber == -1) {
                return -1
            } else {
                this.putSignInColumn(colNumber)
                this.printBoard()
                if (this.roundWon()) {
                    println("Player ${if (this.player1.isNext) this.player1.name else this.player2.name} won")
                    return if (this.player1.isNext) 1 else 2
                }
                if (this.board.isFull()) {
                    println("It is a draw")
                    return 0
                }
                player1.isNext = !player1.isNext
                player2.isNext = !player2.isNext
            }
        }
    }

    fun runGame() {
        while (true) {
            if (this.multiGame && this.round <= this.numberOfGames) println("Game #$round")
            this.printBoard()
            when (this.runRound()) {
                -1 -> {
                    println("Game over!")
                    return
                }
                0 -> {
                    this.player1.points++
                    this.player2.points++
                }
                1 -> this.player1.points += 2
                2 -> this.player2.points += 2
            }
            if (this.multiGame) {
                println("Score")
                println("${this.player1.name}: ${this.player1.points} ${this.player2.name}: ${this.player2.points}")
            }
            this.round++
            if (this.round > this.numberOfGames) {
                println("Game over!")
                return
            } else {
                for (i in 0 until this.board.rows) {
                    for (j in 0 until this.board.columns) {
                        this.board.table[i][j] = " "
                    }
                }
                if (this.round % 2 == 0) {
                    this.player1.isNext = false
                    this.player2.isNext = true
                } else {
                    this.player1.isNext = true
                    this.player2.isNext = false
                }
            }
        }
    }

}

fun main() {
    println("Connect Four\nFirst player's name:")
    val player1 = Player(readln())
    println("Second player's name:")
    val player2 = Player(readln())
    var rows = 6
    var columns = 7
    val whitespaces = Regex("\\s")
    val possibleDimensions = Regex("[0-9]+[xX][0-9]+")
    while (true) {
        println("Set the board dimensions (Rows x Columns)\nPress Enter for default (6 x 7)")
        val dimensions = readln()
        if (dimensions == "") {
            break
        } else if (!possibleDimensions.matches(dimensions.replace(whitespaces, ""))) {
            println("Invalid input")
        } else {
            rows = dimensions.replace(whitespaces, "").lowercase().split("x")[0].toInt()
            columns = dimensions.replace(whitespaces, "").lowercase().split("x")[1].toInt()
            if (rows !in (5..9)) {
                println("Board rows should be from 5 to 9")
            } else if (columns !in (5..9)) {
                println("Board columns should be from 5 to 9")
            } else {
                break
            }
        }
    }
    val board = Board(rows, columns)
    val numberOfGames: Int
    val acceptableInput = Regex("[0-9]+")
    while (true) {
        println("Do you want to play single or multiple games?\n" +
                "For a single game, input 1 or press Enter\n" +
                "Input a number of games:")
        val input = readln()
        if (input == "") {
            numberOfGames = 1
            break
        } else if (input == "0" || !acceptableInput.matches(input)) {
            println("Invalid input")
        } else {
            numberOfGames = input.toInt()
            break
        }
    }
    val game = Game(board, player1, player2, numberOfGames)
    game.runGame()
}