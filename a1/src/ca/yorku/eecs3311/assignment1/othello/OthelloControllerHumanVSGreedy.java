package ca.yorku.eecs3311.assignment1.othello;

/**
 * This controller lets a Human (P1) play against a Greedy computer (P2).
 * The output format mirrors OthelloControllerHumanVSHuman.
 */
public class OthelloControllerHumanVSGreedy {

    protected Othello othello;
    protected PlayerHuman player1;
    protected PlayerGreedy player2;

    public OthelloControllerHumanVSGreedy() {
        this.othello = new Othello();
        this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
        this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
    }

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

    private void reportMove(char whosTurn, Move move) {
        System.out.println(whosTurn + " makes move " + move + "\n");
    }

    private void report() {
        String s = othello.getBoardString()
                + OthelloBoard.P1 + ":" + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
                + othello.getWhosTurn() + " moves next";
        System.out.println(s);
    }

    private void reportFinal() {
        String s = othello.getBoardString()
                + OthelloBoard.P1 + ":" + othello.getCount(OthelloBoard.P1) + " "
                + OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  "
                + othello.getWinner() + " won\n";
        System.out.println(s);
    }

    /** Run main to play Human (P1) vs Greedy (P2). */
    public static void main(String[] args) {
        OthelloControllerHumanVSGreedy oc = new OthelloControllerHumanVSGreedy();
        oc.play();
    }
}
