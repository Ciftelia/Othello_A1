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
		this.other = player == 'X' ? 'O' : 'X';
		this.empty = ' ';
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
		// loop over every row
		for (int row = 0; row < 8; row++) {
			// loop over every column
			for (int col = 0; col < 8; col++) {
				// get a position on the board
				// if the current position is empty, it might be valid
				if (othelloBoard.get(row, col) == this.empty) {
					// try every direction
					for (int[] direction: directions) {
						// move in direction
						boolean flag = false;
						int newRow = row + direction[0];
						int newCol = col + direction[1];
						// while we can still go in this direction
						while (newRow > 0 && newRow < 8 && newCol > 0 && newCol < 8) {
							// if there's a gap, this direction is not valid
							if (othelloBoard.get(newRow, newCol) == ' ') {
								break;
							}
							// if i see another of my pieces, it's valid
							else if (othelloBoard.get(newRow, newCol) == this.player) {
								// so add it to the list
								if (flag) {
									list.add(new int[] {row, col});
								}
								break;
							}
							// we see the other player's tiles, skip
							else {
								// move in direction
								flag = true;
								newRow+=direction[0];
								newCol+=direction[1];
								continue;
							}
							
						}
					}
		        }
			}
		}
		// randomly choose one
		int max = list.size();
		// get number from 0 to max-1
	    int index = (int)(Math.random() * max);
	    
	    if (index >= max) {
	    	return null;
	    }
	    
	    // get from list
	    int[] move = list.get(index);
	    // return new move
	    return new Move(move[0], move[1]);
	}
}