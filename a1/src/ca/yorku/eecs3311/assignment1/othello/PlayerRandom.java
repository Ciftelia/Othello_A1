package ca.yorku.eecs3311.assignment1.othello;

import java.util.ArrayList;
import java.util.Random;

/**
 * A computer-controlled Othello player using a purely random strategy.
 * 
 * This player evaluates all legal moves available on its turn — that is, board
 * positions where placing its token would flip at least one opponent disc — and
 * selects one uniformly at random. No positional or long-term strategic factors
 * are considered.
 * 
 * If no legal moves exist, {@code getMove()} returns {@code null}, which signals
 * to the controller that the player must pass.
 */
public class PlayerRandom extends Player {

    /** Random number generator used to choose uniformly among legal moves. */
    private Random rand = new Random();

    /**
     * Constructs a random strategy player for the specified game and token.
     *
     * @param othello the Othello game model providing board state access
     * @param player the token this player uses ('X' or 'O')
     */
    public PlayerRandom(Othello othello, char player) {
        super(othello, player);
    }

    /**
     * Generates all legal moves available to this player and selects one at random.
     * A legal move is defined as any placement that would flip at least one
     * opponent disc, based on Othello rules.
     * 
     * The board is not modified as part of this evaluation; legality is determined
     * using {@link Player#potentialFlips(OthelloBoard, int, int, char)}.
     *
     * @return a randomly selected {@link Move}, or {@code null} if the player has
     *         no legal moves this turn
     */
    @Override
    public Move getMove() {

        ArrayList<int[]> legalMoves = new ArrayList<>();

        if (othello == null) return null;
        OthelloBoard board = othello.getboard();
        if (board == null) return null;

        final int dim = board.getDimension();

        // Collect coordinates of all legal moves
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (board.get(r, c) != OthelloBoard.EMPTY) continue;

                int flips = potentialFlips(board, r, c, player);
                if (flips > 0) {
                    legalMoves.add(new int[] { r, c });
                }
            }
        }

        if (legalMoves.isEmpty()) return null;

        int[] move = legalMoves.get(rand.nextInt(legalMoves.size()));
        return new Move(move[0], move[1]);
    }
}
