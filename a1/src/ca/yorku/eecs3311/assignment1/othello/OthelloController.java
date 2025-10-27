package ca.yorku.eecs3311.assignment1.othello;

/**
 * An abstract base controller that manages interaction between players and the
 * Othello game model. Concrete subclasses of this controller define how the
 * game should run, such as Human vs Human or Human vs Computer play.

 * The controller does not enforce move logic directly; instead, it delegates
 * rule enforcement and state updates to the {@link Othello} and 
 * {@link OthelloBoard} classes.
 */
public abstract class OthelloController {

    /** The active Othello game instance controlled by this controller. */
    protected Othello othello;

    /** Player 1, typically controlling token P1 ('X'). */
    protected Player player1;

    /** Player 2, typically controlling token P2 ('O'). */
    protected Player player2;

    /**
     * Protected constructor to be used by subclasses. Ensures only specific
     * controller types can be instantiated.
     */
    protected OthelloController() {}

    /**
     * Runs the gameplay loop, alternating moves between players until the
     * game ends. Specific behavior (e.g., human input, AI strategy, automated
     * simulation) must be implemented by subclasses.
     */
    protected abstract void play();

    /**
     * Prints a formatted report of the most recent move played.
     *
     * @param whosTurn the player who executed the move
     * @param move the move location applied to the board
     */
    protected void reportMove(char whosTurn, Move move) {
        System.out.println(whosTurn + " makes move " + move + "\n");
    }

    /**
     * Displays the current board state along with the count of discs belonging
     * to each player and an indication of whose turn is next.
     */
    protected void report() {
        String s = othello.getBoardString()
                + OthelloBoard.P1 + ":" + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
                + othello.getWhosTurn() + " moves next";
        System.out.println(s);
    }

    /**
     * Displays the final board state and announces the game winner.
     * Call this method after the game is complete.
     */
    protected void reportFinal() {
        String s = othello.getBoardString()
                + OthelloBoard.P1 + ":" + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
                + othello.getWinner() + " won\n";
        System.out.println(s);
    }
}
