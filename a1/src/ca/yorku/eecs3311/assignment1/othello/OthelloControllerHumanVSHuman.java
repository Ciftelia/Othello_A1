package ca.yorku.eecs3311.assignment1.othello;

/**
 * A controller that allows two human players to play Othello against each other 
 * at the console. Player 1 controls token 'X' and Player 2 controls token 'O'.
 * 
 * This class manages the user interaction and turn progression while using the
 * model classes to update and validate moves. Output formatting follows the
 * standard board display defined in the Othello model.
 *
 * Only minimal modifications were permitted per the assignment instructions.
 */
public class OthelloControllerHumanVSHuman extends OthelloController {

    /**
     * Constructs a new Human vs Human controller. Initializes a fresh Othello game
     * and assigns both Player 1 and Player 2 as human-controlled players who
     * provide their moves via console input.
     */
    public OthelloControllerHumanVSHuman() {
        this.othello = new Othello();
        this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerHuman(this.othello, OthelloBoard.P2);
    }

    /**
     * Runs the main game loop. Displays the board state, prompts the player whose
     * turn it is to input a move, validates the move, and then applies it if
     * legal. Invalid moves are rejected with a message, and the same player is
     * asked to try again.
     * 
     * The loop ends when the Othello model reports that no legal moves remain for
     * either player.
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

            boolean moveAccepted = othello.move(move.getRow(), move.getCol());

            if (!moveAccepted) {
                System.out.println("Invalid Move: Please Try Again");
            } else {
                this.reportMove(whosTurn, move);
            }
        }

        this.reportFinal();
    }

    /**
     * Main entry point to begin a Human vs Human Othello match at the console.
     * Creates the controller and starts interactive play.
     *
     * @param args unused command-line input
     */
    public static void main(String[] args) {
        OthelloControllerHumanVSHuman oc = new OthelloControllerHumanVSHuman();
        oc.play();
    }
}
