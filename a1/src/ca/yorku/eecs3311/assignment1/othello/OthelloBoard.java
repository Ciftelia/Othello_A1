package ca.yorku.eecs3311.assignment1.othello;

/**
 * Represents the Othello game board and manages all board-level rules.
 * This includes tracking the placement of all discs, validating move legality,
 * flipping opponent discs after a valid move, checking whether either player
 * has available moves, and maintaining the current board state.
 *
 * The board is indexed using zero-based coordinates, with rows and columns
 * ranging from 0 to dim-1. Tokens are represented as characters:
 * P1 ('X'), P2 ('O'), and EMPTY (' ').
 *
 * This class is used internally by the Othello controller to manage game flow.
 */
public class OthelloBoard {
    
    /** Token constants used for the game board. */
    public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';

    /** The dimension of the board, typically 8 for an 8×8 grid. */
    private int dim = 8;

    /** Two-dimensional array storing all tokens on the board. */
    private char[][] board;

    /**
     * Constructs a new Othello board of the specified size. Initially places
     * the standard four discs in the center according to Othello rules.
     *
     * @param dim board dimension, typically 8
     */
    public OthelloBoard(int dim) {
        this.dim = dim;
        board = new char[this.dim][this.dim];
        for (int row = 0; row < this.dim; row++) {
            for (int col = 0; col < this.dim; col++) {
                this.board[row][col] = EMPTY;
            }
        }
        int mid = this.dim / 2;
        this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
        this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
    }

    /**
     * Returns the dimension of the board.
     * @return size of the board along one edge
     */
    public int getDimension() {
        return this.dim;
    }

    /**
     * Returns the opposite player token.
     *
     * @param player P1 or P2
     * @return the other player token, or EMPTY if input does not match a player
     */
    public static char otherPlayer(char player) {
        
        if (player == ' ') {return ' ';}
        else if (player == 'O') {return 'X';}
        else if (player == 'X') {return 'O';}
        else {return EMPTY;}
    }

    /**
     * Returns the token located at the given board position.
     *
     * @param row row index, 0–dim-1
     * @param col column index, 0–dim-1
     * @return P1, P2, or EMPTY if the cell is outside the board or empty
     */
    public char get(int row, int col) {
        
        if (!validCoordinate(row,col)) {return EMPTY;}
        else if (board[row][col]=='X') {return P1;}
        else if (board[row][col] == 'O') {return P2;}
        else {return EMPTY;}    
    }

    /**
     * Determines whether a coordinate lies on the board.
     *
     * @param row row index
     * @param col column index
     * @return true if within valid board bounds, false otherwise
     */
    private boolean validCoordinate(int row, int col) {
        if (row < dim && col < dim && row > -1 && col > -1) {return true;}
        else {return false;}
    }

    /**
     * Determines alternation in direction (drow, dcol), meaning whether a sequence
     * of consecutive discs of one player is followed by a disc of the opponent.
     * This method does not modify the board.
     *
     * @return P1 if sequence ends with P1, P2 if ends with P2, EMPTY if none found
     */
    private char alternation(int row, int col, int drow, int dcol) {
        
        if(!validCoordinate(row, col) || board[row][col] == EMPTY || (dcol == 0 && drow == 0)) return EMPTY;
        char select = board[row][col];
        
        while(validCoordinate(row,col) && board[row][col]==select) {
            row+= drow;
            col+= dcol; }
        
        if (!validCoordinate(row,col)) {return EMPTY;}
        else {return board[row][col];}
        
    }
    
    /**
     * Public access wrapper for alternation check.
     *
     * @return same result as private alternation method
     */
    public char Access_Alternation(int row, int col, int drow, int dcol) {
        return alternation(row,col,drow,dcol);
    }
    
    /**
     * Flips discs in a given direction after a valid move. Returns number flipped,
     * or −1 if this direction does not result in a valid flip pattern.
     *
     * @return number of discs flipped, or −1 if invalid
     */
    private int flip(int row, int col, int drow, int dcol, char player) {
        if (board[row][col] == player) {return 0;}
        
        else if ( board[row][col] == EMPTY || !validCoordinate(row,col) || (drow==0 && dcol==0)) {return -1;}
        
        else {
        int NoFlips = 0;
        char [][] NewBoard = board.clone();
        
        while(validCoordinate(row,col) && NewBoard[row][col]== otherPlayer(player)) {
            NoFlips++;
            NewBoard[row][col] = player;
            row+= drow;
            col+= dcol;
        }
        
        if (!validCoordinate(row,col)) {return -1;}
        board = NewBoard.clone();
        return NoFlips;
        }
    }

    /**
     * Determines whether a player can legally move in one specific direction.
     *
     * @return player token if move is possible, otherwise EMPTY
     */
    private char hasMove(int row, int col, int drow, int dcol) {
        return alternation(row+drow,col+dcol,drow,dcol);
    }

    /**
     * Indicates which player has at least one legal move.
     * Returns BOTH if both players can move, EMPTY if none.
     *
     * @return P1, P2, BOTH, or EMPTY
     */
    public char hasMove() {
        boolean x = hasAnyMove(P1);
        if (!x) {
            return hasAnyMove(P2) ? P2 : EMPTY;
        }
        return hasAnyMove(P2) ? BOTH : P1;
        
    }

    /**
     * Helper for checking whether a player has any legal moves on the board.
     *
     * @param player P1 or P2
     * @return true if at least one move exists for the player
     */
    private boolean hasAnyMove(char player) {
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                if (board[row][col] != EMPTY) continue;
                for (int drow = -1; drow <= 1; drow++) {
                    for (int dcol = -1; dcol <= 1; dcol++) {
                        if (drow == 0 && dcol == 0) continue;
                        char m = hasMove(row, col, drow, dcol);
                        if (m == player) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Attempts to place a disc at the given location. If the move is legal in at
     * least one direction, flips all appropriate discs and returns true.
     *
     * @return true if board changes, false if move invalid
     */
    public boolean move(int row, int col, char player) {
        if (!validCoordinate(row,col) || board[row][col]!=EMPTY) {return false;}
        
        int score = 0;
        
        for (int drow = -1; drow < 2; drow++) {
            for (int dcol = -1; dcol < 2; dcol++) {
                if (drow==0 && dcol==0) {continue;}
                else if (hasMove(row,col,drow,dcol) == player) {
                    board[row][col] = player;
                    score+= flip(row+drow, col+dcol, drow, dcol,player);
                }
            }
        }
        return score > 0;
    }

    /**
     * Counts number of discs belonging to specified player.
     *
     * @param player P1 or P2
     * @return count of tokens for that player
     */
    public int getCount(char player) {
        int total = 0;
        
        for (int i= 0; i < dim; i++) {
            for (int j=0; j < dim; j++) {
                if (board[i][j]==player) {total++;}
            }
        }
        return total;
    }

    /**
     * Returns a text-formatted representation of the board.
     * This includes row and column labels.
     *
     * @return string representation of board state
     */
    public String toString() {
        String s = "";
        s += "  ";
        for (int col = 0; col < this.dim; col++) {
            s += col + " ";
        }
        s += '\n';

        s += " +";
        for (int col = 0; col < this.dim; col++) {
            s += "-+";
        }
        s += '\n';

        for (int row = 0; row < this.dim; row++) {
            s += row + "|";
            for (int col = 0; col < this.dim; col++) {
                s += this.board[row][col] + "|";
            }
            s += row + "\n";

            s += " +";
            for (int col = 0; col < this.dim; col++) {
                s += "-+";
            }
            s += '\n';
        }
        s += "  ";
        for (int col = 0; col < this.dim; col++) {
            s += col + " ";
        }
        s += '\n';
        return s;
    }

    /**
     * Test method that demonstrates board functionality. Output shown in assignment.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        
        OthelloBoard ob = new OthelloBoard(8);
        System.out.println(ob.toString());
        System.out.println("getCount(P1)=" + ob.getCount(P1));
        System.out.println("getCount(P2)=" + ob.getCount(P2));

        // ... (remaining content unchanged)
    }
}
