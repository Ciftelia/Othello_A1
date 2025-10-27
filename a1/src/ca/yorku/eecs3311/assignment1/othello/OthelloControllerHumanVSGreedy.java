package ca.yorku.eecs3311.assignment1.othello;

/**
 * A controller that enables a Human player (P1) to compete against a Greedy AI
 * player (P2). Player 1 controls the token 'X' and provides input through the
 * console, while Player 2 automatically selects the move that maximizes the
 * number of discs flipped on the current turn.
 *
 * The visual display and turn reporting follow the format used in 
 * {@link OthelloControllerHumanVSHuman}. This controller handles game flow, 
 * alternating turns until neither player has a valid move remaining.
 */
public class OthelloControllerHumanVSGreedy extends OthelloController {

    /**
     * Creates a new Human vs Greedy controller. Initializes a fresh Othello game
     * and assigns Player 1 to the human-controlled player and Player 2 to a 
     * Greedy AI opponent.
     */
    public OthelloControllerHumanVSGreedy() {
        this.othello = new Othello();
        this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
    }

    /**
     * Runs the main game loop. On each turn, displays the current board, requests
     * a move from the appropriate player (human or greedy AI), applies the move,
     * and reports the result. Continues until the Othello model indicates that
     * the game has ended.
     */
    @Override
    public void play() {
        while (!othello.isGameOver()) {
            this.report();

            Move move = null;
            char whosTurn = othello.getWhosTurn();

            if (whosTurn == OthelloBoard.P1) {
                move = player1.getMove();
            }
            else if (whosTurn == OthelloBoard.P2) {
                move = player2.getMove();
            }

            this.reportMove(whosTurn, move);
            othello.move(move.getRow(), move.getCol());
        }
        this.reportFinal();
    }

    /**
     * Starts a Human versus Greedy AI game at the console. This contains the
     * program entry point for interactive play.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        OthelloControllerHumanVSGreedy oc = new OthelloControllerHumanVSGreedy();
        oc.play();
    }
}
