package ca.yorku.eecs3311.assignment1.othello;

/**
 * The goal here is to print out the probability that Random wins and Greedy
 * wins as a result of playing 10000 games against each other with P1=Random and
 * P2=Greedy. What is your conclusion, which is the better strategy?
 *
 */
public class OthelloControllerRandomVSGreedy extends OthelloController {
	
	@Override
	protected void play() {
		// TODO Auto-generated method stub
		
	}

    /**
     * Run main to execute the simulation and print out the two line results.
     * Output looks like:
     * Probability P1 wins=.75
     * Probability P2 wins=.20
     * @param args
     */
    public static void main(String[] args) {

        int p1wins = 0, p2wins = 0, numGames = 10000;

        for (int i = 0; i < numGames; i++) {

            Othello game = new Othello();
            PlayerRandom p1 = new PlayerRandom(game, OthelloBoard.P1);
            PlayerGreedy p2 = new PlayerGreedy(game, OthelloBoard.P2);

            int passesInRow = 0;

            while (!game.isGameOver()) {

                char turn = game.getWhosTurn();
                Move move = (turn == OthelloBoard.P1) ? p1.getMove() : p2.getMove();

                if (move == null) {
                    // PASS TURN
                    passesInRow++;
                    if (passesInRow == 2) break;  // both players passed -> game over

                    // manually flip turn without altering board
                    turn = OthelloBoard.otherPlayer(turn);
                    // assign new turn back to Othello using move trick:
                    game.move(-1, -1); // call invalid move to toggle? (NO!)

                    continue;
                }

                boolean applied = game.move(move.getRow(), move.getCol());

                if (applied) {
                    passesInRow = 0; // reset pass count on valid move
                } else {
                    // move invalid -> treat as pass
                    passesInRow++;
                    if (passesInRow == 2) break;
                }
            }

            char winner = game.getWinner();
            if (winner == OthelloBoard.P1) p1wins++;
            if (winner == OthelloBoard.P2) p2wins++;
        }

        System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
        System.out.println("Probability P2 wins=" + (float) p2wins / numGames);


    }
}