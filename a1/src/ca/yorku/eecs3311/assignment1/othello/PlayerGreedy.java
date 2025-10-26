package ca.yorku.eecs3311.assignment1.othello;

/**
 * Greedy player: choose the legal move that maximizes the immediate number of
 * flipped opponent discs (no lookahead). Ties are broken by smaller row, then
 * smaller column. Evaluation is non-mutating; the real board is not changed here.
 */
public class PlayerGreedy {

    private final Othello othello;
    private final char me;

    // 8 directions (row, col) to scan from a candidate square
    private static final int[][] DIRS = {
            {-1,-1}, {-1,0}, {-1,1},
            { 0,-1},         { 0,1},
            { 1,-1}, { 1,0}, { 1,1}
    };

    public PlayerGreedy(Othello othello, char player) {
        this.othello = othello;
        this.me = player;
    }

    /**
     * Returns the greedy move (row, col) or null if no legal move exists for this player.
     * Legal = flips >= 1 in at least one direction.
     */
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

                int flips = potentialFlips(board, r, c, me);
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

    //Total discs that would flip if 'me' plays (row,col); 0 means illegal.
    private int potentialFlips(OthelloBoard board, int row, int col, char me) {
        if (board.get(row, col) != OthelloBoard.EMPTY) return 0;

        final int dim = board.getDimension();
        final char opp = OthelloBoard.otherPlayer(me);

        int total = 0;
        for (int[] d : DIRS) {
            total += flipsInDirection(board, row, col, d[0], d[1], me, opp, dim);
        }
        return total;
    }

    //Count flips along one direction (dr,dc); returns 0 if not bracketed.
    private int flipsInDirection(OthelloBoard board, int row, int col,
                                 int dr, int dc, char me, char opp, int dim) {
        int r = row + dr, c = col + dc, seen = 0;

        // must encounter ≥1 opponent first
        while (inBounds(r, c, dim) && board.get(r, c) == opp) {
            seen++;
            r += dr; c += dc;
        }

        // legal if the run of opponent discs is followed by our own piece
        return (seen > 0 && inBounds(r, c, dim) && board.get(r, c) == me) ? seen : 0;
    }

    private boolean inBounds(int r, int c, int dim) {
        return r >= 0 && r < dim && c >= 0 && c < dim;
    }
}
