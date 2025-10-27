package ca.yorku.eecs3311.assignment1.othello;

/**
 * This controller lets a Human (P1) play against a Greedy computer (P2).
 * The output format mirrors OthelloControllerHumanVSHuman.
 */
public class OthelloControllerHumanVSGreedy extends OthelloController {
	 public OthelloControllerHumanVSGreedy() {
	        this.othello = new Othello();
	        this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
	        this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
	    }

	    @Override
	    public void play() {
	        while (!othello.isGameOver()) {
	            this.report();

	            Move move = null;
	            char whosTurn = othello.getWhosTurn();

	            if (whosTurn == OthelloBoard.P1) {
	                move = player1.getMove();
	            }
	            if (whosTurn == OthelloBoard.P2) {
	                move = player2.getMove();
	            }

	            this.reportMove(whosTurn, move);
	            othello.move(move.getRow(), move.getCol());
	        }
	        this.reportFinal();
	    }


	    /** Run main to play Human (P1) vs Greedy (P2). */
	    public static void main(String[] args) {
	        OthelloControllerHumanVSGreedy oc = new OthelloControllerHumanVSGreedy();
	        oc.play();
	    }
}
