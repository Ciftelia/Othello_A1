package ca.yorku.eecs3311.assignment1.othello;

/**
 * A controller that enables a Human player (P1) to compete against a computer
 * player (P2) that selects moves uniformly at random among its legal options.
 * 
 * This controller manages the turn-taking process, displays the board and token
 * counts, and interacts with the model classes to apply moves. Its output format
 * matches the structure used in the Human vs Human controller.
 */
public class OthelloControllerHumanVSRandom extends OthelloController {

    /**
     * Constructs a new controller for Human vs Random gameplay. Initializes a fresh
     * Othello game and associates P1 with console input (human), while P2 is played
     * by a computer selecting random legal moves.
     */
    public OthelloControllerHumanVSRandom() {
        this.othello = new Othello();
        this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerRandom(this.othello, OthelloBoard.P2);
    }

    /**
     * Executes the gameplay loop. Continues until the model indicates the game is
     * over (no legal moves remaining for either player). Each turn, the board is
     * printed and the appropriate player provides a move. If a move is invalid, the
     * player is prompted again without switching turns.
     */
    @Override
    protected void play() {

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
     * Runs an interactive Human vs Random Othello match at the console.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {
        OthelloControllerHumanVSRandom oc = new OthelloControllerHumanVSRandom();
        oc.play();
    }
}
