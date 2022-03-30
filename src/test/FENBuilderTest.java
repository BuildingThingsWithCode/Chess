package test;

import static chess.util.FENBuilder.gameToFEN;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Game;
import chess.Move;
import chess.Player;


class FENBuilderTest {
	
	Board board = new Board();
	Game game = new Game(board, new Player("James", WHITE), new Player("Johan", BLACK));
	/*
	 * from wikipedia page about FEN notation:
	 * The following example is from the FEN specification:[8]
	 * Here's the FEN for the starting position:
	 * rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
	 * And after the move 1.e4:
	 * rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1
	 * And then after 1...c5:
	 * rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2
	 * And then after 2.Nf3:
	 * rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2
	 */

	@Test
	// chess columns are counted from left to right. A gridPane counts from right to left.
	// c5 is 3th column (a,b,c) but in model is 2 column (0,1,2) and because we have to count from right to left it's 5 (7-2).
	void test() {
		assertEquals(gameToFEN.apply(game), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		Move e4 = new Move(board.getField(1, 3), board.getField(3, 3));
		game.play(e4);
		assertEquals(gameToFEN.apply(game), "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1");
		Move c5 = new Move(board.getField(6, 5), board.getField(4, 5));
		game.play(c5);
		assertEquals(gameToFEN.apply(game),"rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2");
		Move Nf3 = new Move(board.getField(0, 1), board.getField(2, 2));
		game.play(Nf3);
		assertEquals(gameToFEN.apply(game), "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2");
	}

}
