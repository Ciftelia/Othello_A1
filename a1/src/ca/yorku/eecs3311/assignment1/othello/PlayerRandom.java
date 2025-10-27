package ca.yorku.eecs3311.assignment1.othello;

import java.util.ArrayList;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 *
 */
public class PlayerRandom extends Player {
	private Random rand = new Random();
	private char other;

	public PlayerRandom(Othello othello, char player) {
		super(othello, player);
	}

	public Move getMove() {
		ArrayList<int[]> list = new ArrayList<>();
		if (othello == null) return null;
        OthelloBoard board = othello.getboard();
        if (board == null) return null;

        final int dim = board.getDimension();

        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (board.get(r, c) != OthelloBoard.EMPTY) continue;

                int flips = potentialFlips(board, r, c, player);
                if (flips <= 0) continue; // not a legal move

                list.add(new int[] {r, c});
            }
        }
        if (list.isEmpty()) return null;
	    int[] move = list.get(rand.nextInt(list.size()));
	    return new Move(move[0], move[1]);
	}
}