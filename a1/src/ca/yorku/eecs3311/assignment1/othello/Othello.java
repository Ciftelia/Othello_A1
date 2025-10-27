package ca.yorku.eecs3311.assignment1.othello;

import java.util.Random;

/**
 * Represents a complete Othellogame, including the board state, 
 * the active player, and the number of moves made so far. 
 *
 * This class enforces the rules of Othello. Player 1 ('X') always moves first.
 * A move is valid only if it flips at least one opponent disc. After a 
 * successful move, the game checks whether the opponent has any available moves.
 * If both players are unable to make a move, the game ends.
 *
 * This class manages game flow, while the OthelloBoard class performs 
 * legality checks, flipping operations, and score calculations.
 */
public class Othello {

    /** The standard board size for Othello: 8 rows and 8 columns. */
    public static final int DIMENSION = 8;

    /** Tracks who has the next turn. P1 begins the game. */
    private char whosTurn = OthelloBoard.P1;

    /** Total number of valid moves successfully executed. */
    private int numMoves = 0;

    /** The board containing current token placement and move logic. */
    private final OthelloBoard board;
    
    /**
     * Constructs a new Othello game with a fresh board initialized 
     * to the standard starting configuration.
     */
    public Othello() {
        this.board = new OthelloBoard(DIMENSION);
    }
    
    /**
     * Returns which player is next to move.
     * Returns EMPTY if the game has already finished.
     *
     * @return P1, P2, or EMPTY if the game is over
     */
    public char getWhosTurn() {
        if (isGameOver()) {return OthelloBoard.EMPTY;}
        else {return whosTurn;}
    }

    /**
     * Attempts to make a move at the specified board position on behalf of 
     * the current player. A move is only valid if it flips at least one 
     * opponent disc. If valid, the turn may switch to the opponent depending 
     * on move availability.
     *
     * @param row row index for the move (0-based)
     * @param col column index for the move (0-based)
     * @return true if the move was legal and completed; false otherwise
     */
    public boolean move(int row, int col) {
        if (isGameOver()) {return false;}

        boolean move = board.move(row, col, whosTurn);

        if (!move) {return false;}
        else {
            numMoves++;
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
     * Returns the number of discs currently on the board that belong 
     * to the specified player.
     *
     * @param player P1 or P2
     * @return count of discs belonging to that player
     */
    public int getCount(char player) {
        return board.getCount(player);
    }

    /**
     * Identifies the winner when the game has ended.
     * If the number of discs is equal or the game is not finished,
     * EMPTY is returned.
     *
     * @return P1, P2, or EMPTY if tied or game not complete
     */
    public char getWinner() {
        
        if (!isGameOver()) {
            return OthelloBoard.EMPTY;
        }
        
        int a = board.getCount(OthelloBoard.P1);
        int b = board.getCount(OthelloBoard.P2);
        
        if (a > b) {return OthelloBoard.P1;}
        else if (b > a) {return OthelloBoard.P2;}
        else {return OthelloBoard.EMPTY;}
    }

    /**
     * Indicates whether the game has ended. The game is over only when 
     * neither player has any valid moves remaining.
     *
     * @return true if no player can move; false otherwise
     */
    public boolean isGameOver() {
        return board.hasMove() == OthelloBoard.EMPTY;
    }

    /**
     * Provides a visual string representation of the current game board.
     *
     * @return formatted board layout
     */
    public String getBoardString() {
        return board.toString();
    }
    
    /**
     * Provides access to the underlying OthelloBoard object.
     *
     * @return the active game board
     */
    public OthelloBoard getboard() {
        return board;
    }

    /**
     * A simple demonstration that runs a completely random game,
     * printing moves and board updates after each valid move.
     * 
     * Do not modify this method. It exists solely for testing purposes.
     *
     * @param args unused command-line arguments
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
