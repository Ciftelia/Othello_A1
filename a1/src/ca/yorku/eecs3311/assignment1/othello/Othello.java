package ca.yorku.eecs3311.assignment1.othello;

import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 *
 */
public class Othello {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
	private final OthelloBoard board;
	
	
	
	/*
	 * Constructor
	 */
	
	public Othello() {
		this.board = new OthelloBoard(DIMENSION);
	}
	
	
	

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		if (isGameOver()) {return OthelloBoard.EMPTY;}
		else {return whosTurn;}
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		if (isGameOver()) {return false;}
		
		boolean move = board.move(row, col, whosTurn);
		
		if (move) {return false;}
		else {
			char whosMove = board.hasMove();
			if (whosMove == OthelloBoard.BOTH) {
				whosTurn = OthelloBoard.otherPlayer(whosTurn);
			}
			else {
				whosTurn = whosMove;
			}
		}
		
		return true;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return board.getCount(player);
	}

	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		
		if (!isGameOver()) {
			return OthelloBoard.EMPTY;
		}
		
		else {
			int a = board.getCount(OthelloBoard.P1);
			int b = board.getCount(OthelloBoard.P2);
			
			if (a > b) {return OthelloBoard.P1;}
			else if (b > a) {return OthelloBoard.P2;}
			else {return OthelloBoard.EMPTY;}
		}
	}

	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		return board.hasMove() == OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString();
	}
	
	
	public OthelloBoard getboard() {
		return board;
	}

	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random rand = new Random();

		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}

	}
}