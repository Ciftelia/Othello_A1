package ca.yorku.eecs3311.assignment1.othello;

/**
 * An abstract superclass for Othello players. Concrete subclasses such as
 * {@link PlayerHuman}, {@link PlayerRandom}, and {@link PlayerGreedy} provide
 * specific strategies for selecting moves.
 * 
 * A Player stores a reference to the {@link Othello} game model it plays within,
 * its assigned token ('X' for P1 or 'O' for P2), and provides helper logic
 * useful for determining legal move behavior and potential disc flips.
 */
public abstract class Player {

    /** Reference to the game model used for querying the board state. */
    protected final Othello othello;

    /** The token used by this player: {@link OthelloBoard#P1} or {@link OthelloBoard#P2}. */
    protected final char player;

    /** Constant list of the eight directional increments checked for flips. */
    protected static final int[][] DIRS = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };

    /**
     * Creates a Player attached to a specific Othello game instance and token.
     *
     * @param othello the Othello game environment this player interacts with
     * @param player the token assigned to this player ('X' or 'O')
     */
    public Player(Othello othello, char player) {
        this.othello = othello;
        this.player = player;
    }

    /**
     * Returns the move chosen by this player's strategy.
     * Called during the game loop when it is this player's turn.
     *
     * @return a legal {@link Move}, or null if no valid move exists
     */
    public abstract Move getMove();

    /**
     * Checks whether given board coordinates lie within bounds.
     *
     * @param r row index to check
     * @param c column index to check
     * @param dim dimension of the board
     * @return true if 0 ≤ r,c &lt; dim, false otherwise
     */
    protected static boolean inBounds(int r, int c, int dim) {
        return r >= 0 && r < dim && c >= 0 && c < dim;
    }

    /**
     * Computes how many discs would be flipped if this player's token were placed
     * at (row, col). A return value of 0 indicates the move is illegal.
     *
     * @param board the board state to evaluate
     * @param row the candidate move row
     * @param col the candidate move column
     * @param player the token making the move
     * @return number of discs flipped in total; zero means not a legal move
     */
    protected int potentialFlips(OthelloBoard board, int row, int col, char player) {
        if (board.get(row, col) != OthelloBoard.EMPTY) return 0;

        final int dim = board.getDimension();
        final char opp = OthelloBoard.otherPlayer(player);

        int total = 0;
        for (int[] d : DIRS) {
            total += flipsInDirection(board, row, col, d[0], d[1], player, opp, dim);
        }
        return total;
    }

    /**
     * Counts opponent discs that would flip in a given direction if the player
     * placed their token at (row, col). Legal flips require encountering a run of
     * opponent discs followed by one of the player's own discs before leaving the
     * board.
     *
     * @param board the board to examine
     * @param row candidate move row
     * @param col candidate move column
     * @param dr row step direction (-1,0,1)
     * @param dc column step direction (-1,0,1)
     * @param player the token placing the move
     * @param opp the opponent's token
     * @param dim board dimension
     * @return number of flips in this direction, or 0 if not bracketed legally
     */
    protected int flipsInDirection(OthelloBoard board, int row, int col,
                                   int dr, int dc, char player, char opp, int dim) {
        int r = row + dr, c = col + dc, seen = 0;

        // must encounter ≥1 opponent disc immediately adjacent
        while (inBounds(r, c, dim) && board.get(r, c) == opp) {
            seen++;
            r += dr;
            c += dc;
        }

        // legal only if run ends on player's own disc within bounds
        return (seen > 0 && inBounds(r, c, dim) && board.get(r, c) == player) ? seen : 0;
    }
}
