package ca.yorku.eecs3311.assignment1.othello;

public abstract class OthelloController {
	protected Othello othello;
    protected Player player1;
    protected Player player2;

    protected OthelloController() {}
    
    protected abstract void play();
	
    protected void reportMove(char whosTurn, Move move) {
        System.out.println(whosTurn + " makes move " + move + "\n");
    }

    protected void report() {
        String s = othello.getBoardString()
                + OthelloBoard.P1 + ":" + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
                + othello.getWhosTurn() + " moves next";
        System.out.println(s);
    }

    protected void reportFinal() {
        String s = othello.getBoardString()
                + OthelloBoard.P1 + ":" + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
                + othello.getWinner() + " won\n";
        System.out.println(s);
    }

}
