package ca.yorku.eecs3311.assignment1.othello;

import java.util.ArrayList;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 *
 */
public class PlayerRandom {
	private Random rand = new Random();
	private Othello othello;
	private char player;
	private OthelloBoard othelloBoard;
	private char other;
	private char empty;

	public PlayerRandom(Othello othello, char player) {
		
		this.othello = othello;
		this.othelloBoard = othello.getboard();
		this.player = player;
		this.other = (player == 'X') ? 'O' : 'X';
	}

	public Move getMove() {
		// Create list to store all possible moves
		ArrayList<int[]> list = new ArrayList<>();
		// Create a 2D list to store all directions
		int[][] directions = {
			    {-1, -1}, {-1, 0}, {-1, 1},
			    {0, -1},           {0, 1},
			    {1, -1},  {1, 0},  {1, 1}
			};
		for (int row = 0; row < othelloBoard.getDimension(); row++) {
			for (int col = 0; col < othelloBoard.getDimension(); col++) {
				if (othelloBoard.get(row, col) != OthelloBoard.EMPTY) continue;
				boolean flag = false;
				for (int[] direction: directions) {
					int newRow = row + direction[0]; int newCol = col + direction[1];
					if (newRow < 0 || newRow >= othelloBoard.getDimension() || newCol < 0 || newCol >= othelloBoard.getDimension()) continue;
					if (othelloBoard.get(newRow, newCol) != this.other) continue;
					
					newCol+=direction[1]; newRow+=direction[0];
					while (newRow >= 0 && newRow < othelloBoard.getDimension()  && newCol >= 0 && newCol < othelloBoard.getDimension()) {
						char c = othelloBoard.get(newRow, newCol);
						if (c == OthelloBoard.EMPTY) break;
	                    if (c == this.player) {
	                    	flag = true;
	                        break;
	                    }
						newRow+=direction[0]; newCol+=direction[1];
					}
					if (flag) break;
				}
				if (flag) list.add(new int[] {row,col});
			}
		}
		if (list.isEmpty()) return null;
	    int[] move = list.get(rand.nextInt(list.size()));
	    return new Move(move[0], move[1]);
	}
}