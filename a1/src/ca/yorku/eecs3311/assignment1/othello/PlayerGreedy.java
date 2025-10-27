package ca.yorku.eecs3311.assignment1.othello;

/**
 * Greedy player: choose the legal move that maximizes the immediate number of
 * flipped opponent discs (no lookahead). Ties are broken by smaller row, then
 * smaller column. Evaluation is non-mutating; the real board is not changed here.
 */
public class PlayerGreedy extends Player{

    public PlayerGreedy(Othello othello, char player) {
        super(othello, player);
    }

    /**
     * Returns the greedy move (row, col) or null if no legal move exists for this player.
     * Legal = flips >= 1 in at least one direction.
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
                if (flips <= 0) continue; // not a legal move

                // argmax with tie-break: smaller row, then smaller col
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
