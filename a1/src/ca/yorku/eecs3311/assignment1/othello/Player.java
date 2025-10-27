package ca.yorku.eecs3311.assignment1.othello;

public abstract class Player {
	protected final Othello othello;
	protected final char player;
	protected static final int[][] DIRS = {
			{-1, -1}, {-1, 0}, {-1, 1},
			{0, -1},           {0, 1},
			{1, -1},  {1, 0},  {1, 1}
	};

	public Player(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
	}

	public abstract Move getMove();

	protected static boolean inBounds(int r, int c, int dim) {
		return r >= 0 && r < dim && c >= 0 && c < dim;
	}
	
    //Total discs that would flip if 'me' plays (row,col); 0 means illegal.
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

    //Count flips along one direction (dr,dc); returns 0 if not bracketed.
	protected int flipsInDirection(OthelloBoard board, int row, int col,
                                 int dr, int dc, char player, char opp, int dim) {
        int r = row + dr, c = col + dc, seen = 0;

        // must encounter ≥1 opponent first
        while (inBounds(r, c, dim) && board.get(r, c) == opp) {
            seen++;
            r += dr; c += dc;
        }

        // legal if the run of opponent discs is followed by our own piece
        return (seen > 0 && inBounds(r, c, dim) && board.get(r, c) == player) ? seen : 0;
    }
}
