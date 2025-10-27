package ca.yorku.eecs3311.assignment1.othello;

/**
 * Runs a Random-vs-Random experimental study to compare the performance of
 * Player 1 and Player 2 when both players rely exclusively on random move
 * selection. The program executes a large number of independent games and
 * reports empirical win probabilities.
 *
 * This simulation supports hypothesis testing regarding whether the first move
 * advantage produces a measurable increase in P1's win probability when no
 * deterministic strategy is involved.
 */
public class ANS7 {

    /** Number of independent simulation trials executed. */
    private static final int NUM_GAMES = 10000;

    /**
     * Main entry point for the experiment. Runs {@value #NUM_GAMES} automated games
     * of Random vs Random, tallies win frequencies for each player, and prints the
     * corresponding estimated probabilities.
     *
     * @param args unused command-line arguments
     */
    public static void main(String[] args) {

        int p1wins = 0;
        int p2wins = 0;

        for (int i = 0; i < NUM_GAMES; i++) {
            char winner = playOneGame();
            if (winner == OthelloBoard.P1) {
                p1wins++;
            } else if (winner == OthelloBoard.P2) {
                p2wins++;
            }
        }

        double p1 = (double) p1wins / NUM_GAMES;
        double p2 = (double) p2wins / NUM_GAMES;

        System.out.println("P1 wins=" + p1);
        System.out.println("P2 wins=" + p2);
    }

    /**
     * Executes one fully automated Othello game where both players choose random
     * legal moves. Play continues until the {@link Othello} model declares that no
     * player has an available move.
     *
     * @return the winner of the game ({@link OthelloBoard#P1}, 
     *         {@link OthelloBoard#P2}, or {@link OthelloBoard#EMPTY} in case of a tie)
     */
    private static char playOneGame() {
        Othello game = new Othello();
        PlayerRandom p1 = new PlayerRandom(game, OthelloBoard.P1);
        PlayerRandom p2 = new PlayerRandom(game, OthelloBoard.P2);

        while (!game.isGameOver()) {
            char turn = game.getWhosTurn();
            if (turn != OthelloBoard.P1 && turn != OthelloBoard.P2) {
                break; // unexpected state — end simulation early
            }

            Move m = (turn == OthelloBoard.P1) ? p1.getMove() : p2.getMove();

            if (m != null) { // apply move only if legal
                game.move(m.getRow(), m.getCol());
            }
        }

        return game.getWinner();
    }
}
