package ca.yorku.eecs3311.assignment1.othello;

/**
 * A controller used to investigate whether Player 1 or Player 2 has an advantage
 * when both players select moves uniformly at random among their legal options.
 * 
 * This simulation repeatedly plays Othello with both players controlled by
 * {@link PlayerRandom} using no strategic reasoning. After running a large
 * number of trials (10,000 by default), the controller estimates and reports the
 * empirical win probability for each player.
 * 
 * The resulting probabilities can be used to test the null hypothesis that P1
 * and P2 have equal chances of winning under random play versus the alternative
 * hypothesis that one player has an advantage.
 */
public class OthelloControllerRandomVSRandom extends OthelloController {

    /** Othello game instance used for a single simulation run. */
    protected Othello othello;

    /** Player 1, using random strategy. */
    PlayerRandom player1;

    /** Player 2, also using random strategy. */
    PlayerRandom player2;

    /**
     * Constructs a new Random vs Random controller. Initializes a fresh Othello
     * game and assigns both players to use random-move generators.
     */
    public OthelloControllerRandomVSRandom() {
        this.othello = new Othello();
        this.player1 = new PlayerRandom(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerRandom(this.othello, OthelloBoard.P2);
    }

    /**
     * Executes a single random-play Othello game. Continues making moves until the
     * game model declares no further legal moves exist for either player.
     * 
     * This version does not print game progression to standard output, as repeated
     * thousands of times for simulation purposes.
     */
    @Override
    public void play() {
        while (!othello.isGameOver()) {

            Move move = null;
            char whosTurn = othello.getWhosTurn();

            if (whosTurn == OthelloBoard.P1) {
                move = player1.getMove();
            }
            else if (whosTurn == OthelloBoard.P2) {
                move = player2.getMove();
            }

            if (move != null) {
                othello.move(move.getRow(), move.getCol());
            } else {
                break; // both players have no moves remaining
            }
        }
    }

    /**
     * Runs 10,000 independent Random vs Random simulations and prints the
     * estimated win probabilities for each player.
     * 
     * Draws (equal token counts) are tallied implicitly; therefore probabilities
     * may not sum to one exactly.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        int p1wins = 0, p2wins = 0, numGames = 10000;

        OthelloControllerRandomVSRandom oc = new OthelloControllerRandomVSRandom();

        for (int i = 0; i < numGames; i++) {
            oc.play();

            char winner = oc.othello.getWinner();
            if (winner == OthelloBoard.P1) {
                p1wins++;
            } else if (winner == OthelloBoard.P2) {
                p2wins++;
            }

            // Reset board for next trial
            oc = new OthelloControllerRandomVSRandom();
        }

        System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
        System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
    }
}
