package ca.yorku.eecs3311.assignment1.othello;

/**
 * A computer-controlled Othello player using a greedy strategy. This player
 * evaluates all legal moves available on its turn and selects the one that
 * results in the maximum number of opponent discs flipped immediately, without
 * considering future consequences.
 * 
 * Ties in flip count are resolved deterministically: the move with the smallest
 * row index is preferred; if rows tie, the move with the smallest column index
 * is chosen. The evaluation does not modify the underlying game state; it only
 * inspects the board.
 */
public class PlayerGreedy extends Player {

    /**
     * Constructs a Greedy strategy player associated with the given Othello game
     * and token.
     *
     * @param othello the game state interface from which to query legal moves
     * @param player  the token this player controls ('X' for P1 or 'O' for P2)
     */
    public PlayerGreedy(Othello othello, char player) {
        super(othello, player);
    }

    /**
     * Computes and returns the move that yields the greatest number of discs
     * flipped immediately for this player. If no legal move exists, returns null.
     *
     * A move is considered legal only if it would flip at least one adjacent
     * opponent disc when placed, based on Othello rules. Evaluation is performed
     * non-mutatively using {@link Player#potentialFlips(OthelloBoard, int, int, char)}.
     *
     * @return the greedy-selected {@link Move}, or null if this player must pass
     */
    @Override
    public Move getMove() {
        if (othello == null) return null;
        OthelloBoard board = othello.getboard();
        if (board == null) return null;

        final int dim = board.getDimension();

        int bestFlips = -1;
        int bestRow = 0, bestCol = 0;

        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (board.get(r, c) != OthelloBoard.EMPTY) continue;

                int flips = potentialFlips(board, r, c, player);
                if (flips <= 0) continue;

                // Maximize flips; break ties using row, then column
                if (flips > bestFlips ||
                    (flips == bestFlips && (r < bestRow || (r == bestRow && c < bestCol)))) {
                    bestFlips = flips;
                    bestRow = r;
                    bestCol = c;
                }
            }
        }

        return (bestFlips > 0) ? new Move(bestRow, bestCol) : null;
    }

}
