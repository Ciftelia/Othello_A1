package ca.yorku.eecs3311.assignment1.othello;

// TODO: Javadoc this class


/*
 * Move class represents a single move made by a player in Othello board.
 * -- It stores only the "rows and columns" where a disc is place
 * -- The info about <which player> is making move is managed externally by the class and it's subclass
 * 
 * @author Akash Deep, Klodiana Kamberi
 */

 

public class Move {
	private int row, col; // row and column index
	
	
	/*
	 * Constructs the {move} with specified position
	 * @row - row index
	 * @col - col index
	 */

	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/* -- Returns the row of this move
	 * @return row index
	 * 
	 * 
	 */
	public int getRow() {
		return row;
	}
	
	/*-- Returns the column of this move
	 * @return col index
	 * 
	 */

	public int getCol() {
		return col;
	}
	
	/*
	 * Returns the string representation of this move
	 * @return - a string such as ( "Move{row=2,col=3"
	 */

	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
