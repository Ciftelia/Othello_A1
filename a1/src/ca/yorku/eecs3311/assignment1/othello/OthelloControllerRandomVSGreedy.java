package ca.yorku.eecs3311.assignment1.othello;

/**
 * A controller class dedicated to conducting automated simulations between a
 * Random strategy player (P1) and a Greedy strategy player (P2) in Othello.
 * 
 * This class can also run a single automated game through the {@code play()}
 * method, while the {@code main()} method performs repeated trials to estimate
 * win probabilities.
 */
public class OthelloControllerRandomVSGreedy extends OthelloController {

    /**
     * Constructs a Random vs Greedy controller for automated self-play.
     * Uses P1 as a random strategy and P2 as a greedy strategy.
     */
    public OthelloControllerRandomVSGreedy() {
        this.othello = new Othello();
        this.player1 = new PlayerRandom(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
    }

    /**
     * Runs a single automated game of Random (P1) vs Greedy (P2).
     * Continues until neither player has a legal move remaining.
     */
    @Override
    protected void play() {
        int passes = 0;

        while (!othello.isGameOver()) {
            char turn = othello.getWhosTurn();
            Move move = (turn == OthelloBoard.P1) ? player1.getMove() : player2.getMove();

            if (move == null) {    // pass
                passes++;
                if (passes == 2) break; // both passed consecutively
                continue;
            }

            boolean moved = othello.move(move.getRow(), move.getCol());
            if (moved) {
                passes = 0; // reset on successful move
            } else {
                passes++;
                if (passes == 2) break;
            }
        }
    }

    /**
     * Executes 10,000 simulations and prints the resulting win probabilities.
     */
    public static void main(String[] args) {

        int p1wins = 0, p2wins = 0;
        int numGames = 10000;

        for (int i = 0; i < numGames; i++) {

            OthelloControllerRandomVSGreedy oc = new OthelloControllerRandomVSGreedy();
            oc.play();

            char winner = oc.othello.getWinner();
            if (winner == OthelloBoard.P1) p1wins++;
            else if (winner == OthelloBoard.P2) p2wins++;
        }

        System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
        System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
    }
}
