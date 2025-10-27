package ca.yorku.eecs3311.assignment1.othello;

public class ANS7 {
	private static final int NUM_GAMES = 10000;
	
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
	
	private static char playOneGame() {
        Othello game = new Othello();
        PlayerRandom p1 = new PlayerRandom(game, OthelloBoard.P1);
        PlayerRandom p2 = new PlayerRandom(game, OthelloBoard.P2);

        while (!game.isGameOver()) {
            char turn = game.getWhosTurn();
            if (turn != OthelloBoard.P1 && turn != OthelloBoard.P2) {
                break; // safety: unexpected state means stop this game loop
            }

            Move m = (turn == OthelloBoard.P1) ? p1.getMove() : p2.getMove();

            // Apply move only if one exists (null means no legal move this turn)
            if (m != null) {
                game.move(m.getRow(), m.getCol());
            }
        }

        return game.getWinner();
    }

}
