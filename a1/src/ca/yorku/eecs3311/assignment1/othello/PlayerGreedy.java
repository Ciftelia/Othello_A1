package ca.yorku.eecs3311.assignment1.othello;

/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 * 
 *
 */

public class PlayerGreedy {

    private final Othello othello;

    public PlayerGreedy(Othello othello) {
        this.othello = othello;
    }

    /**
     * Choose the legal move that maximizes the immediate number of tokens owned
     * by the current player (equivalently: maximizes flipped discs).
     * Tie-breaker: smaller row first; if rows tie, smaller column.
     */
    public Move getMove() {
        if (othello == null) return null;

        OthelloBoard board = othello.getboard(); // accessor present in Othello.java
        if (board == null) return null;

        char me = othello.getWhosTurn();
        if (me == OthelloBoard.EMPTY) return null; // game over / nobody to move

        int dim = board.getDimension();

        int bestFlips = -1;
        int bestRow = -1, bestCol = -1;

        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                // skip if not empty
                if (board.get(r, c) != OthelloBoard.EMPTY) continue;

                int flips = countFlipsIfPlayed(board, r, c, me);
                if (flips <= 0) continue; // illegal move (must flip at least one)

                if (flips > bestFlips || (flips == bestFlips && (r < bestRow || (r == bestRow && c < bestCol)))) {
                    bestFlips = flips;
                    bestRow = r;
                    bestCol = c;
                }
            }
        }

        if (bestFlips < 0) {
            // no legal moves
            return null;
        }
        return new Move(bestRow, bestCol);
    }


    private int countFlipsIfPlayed(OthelloBoard board, int row, int col, char me) {
        if (board.get(row, col) != OthelloBoard.EMPTY) return 0;

        int total = 0;
        int dim = board.getDimension();
        char opp = OthelloBoard.otherPlayer(me);

        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if (dRow == 0 && dCol == 0) continue;

                int r = row + dRow, c = col + dCol;
                int inBetween = 0;

                // First, we need at least one opponent disc
                while (inBounds(r, c, dim) && board.get(r, c) == opp) {
                    inBetween++;
                    r += dRow;
                    c += dCol;
                }

                // Valid line if we saw >=1 opp disc and ended on our own disc
                if (inBetween > 0 && inBounds(r, c, dim) && board.get(r, c) == me) {
                    total += inBetween;
                }
            }
        }
        return total;
    }

    private boolean inBounds(int r, int c, int dim) {
        return r >= 0 && r < dim && c >= 0 && c < dim;
    }
}

