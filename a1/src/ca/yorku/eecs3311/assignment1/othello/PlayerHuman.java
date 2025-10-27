package ca.yorku.eecs3311.assignment1.othello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A human-controlled Othello player that gathers user input from the console.
 * This class prompts the user to enter the row and column of their desired move,
 * validates that the input is formatted correctly and within the board bounds,
 * and returns the move to the controller.
 * 
 * This player does not enforce full legality of a move—that is handled by
 * {@link Othello#move(int, int)} and validated by the model. It only ensures
 * numeric input in the valid index range [0‥7].
 */
public class PlayerHuman extends Player {

    /** Error message displayed when the user enters a row/column outside 0–7. */
    private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";

    /** Error message displayed for unexpected input/output failure. */
    private static final String IO_ERROR_MESSAGE = "I/O Error";

    /** Shared input reader for console interaction. */
    private static BufferedReader stdin =
            new BufferedReader(new InputStreamReader(System.in));

    /**
     * Constructs a human-controlled player for the given Othello game.
     *
     * @param othello the associated game model used for move validation
     * @param player the token used by this player ('X' for P1 or 'O' for P2)
     */
    public PlayerHuman(Othello othello, char player) {
        super(othello, player);
    }

    /**
     * Prompts the human player for input specifying the row and column of a move.
     * The input is validated to ensure it falls within the board limits. A
     * {@link Move} object is returned using the confirmed coordinates.
     *
     * @return a move selected by the user
     */
    @Override
    public Move getMove() {
        int row = getMove("row: ");
        int col = getMove("col: ");
        return new Move(row, col);
    }

    /**
     * Repeatedly prompts the player to enter a single coordinate until a valid
     * number in the range [0‥7] is provided. If invalid input or a formatting
     * error occurs, a message is printed and the user is re-prompted.
     *
     * @param message prompt text ("row: " or "col: ")
     * @return a validated board index in {0,…,7}, or −1 if an I/O failure occurs
     */
    private int getMove(String message) {

        int move, lower = 0, upper = 7;

        while (true) {
            try {
                System.out.print(message);
                String line = PlayerHuman.stdin.readLine();
                move = Integer.parseInt(line);

                if (lower <= move && move <= upper) {
                    return move;
                } else {
                    System.out.println(INVALID_INPUT_MESSAGE);
                }
            }
            catch (IOException e) {
                System.out.println(IO_ERROR_MESSAGE);
                break;
            }
            catch (NumberFormatException e) {
                System.out.println(INVALID_INPUT_MESSAGE);
            }
        }
        return -1; // unreachable unless there is an I/O failure
    }
}
