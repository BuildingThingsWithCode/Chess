package test;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Game;
import chess.Move;
import chess.Player;
import chess.pieces.King;
import chess.pieces.Queen;

class KingTest {
	private Board  board 	  = new Board();
	private Player piet 	  = new Player("Piet", BLACK);
	private Player jan  	  = new Player("Jan", WHITE); 
	private Game   game   	  = new Game(board, jan, piet);
	private Move   whiteRight = new Move(game.getField(0, 3), game.getField(0, 1));
	private Move   whiteLeft  = new Move(game.getField(0, 3), game.getField(0, 5));
	private Move   blackLeft  = new Move(game.getField(7, 3), game.getField(7, 5));
	private Move   blackRight = new Move(game.getField(7, 3), game.getField(7, 1));
	
	@Test
	void testRookEligibleToCastle() {
		//long castling needs no piece next to rook or rook is not eligible to castle.
		King whiteKing = (King) board.getPiece(0, 3).get();
		King blackKing = (King) board.getPiece(7, 3).get();
		assertEquals(true, whiteKing instanceof King);
		assertEquals(true, blackKing instanceof King);
		assertEquals(true, whiteKing.rookEligibleToCastle.test(game, whiteRight));
		assertEquals(true, blackKing.rookEligibleToCastle.test(game, blackRight));
		//long castling.
		assertNotEquals(true, whiteKing.rookEligibleToCastle.test(game, whiteLeft));
		assertNotEquals(true, blackKing.rookEligibleToCastle.test(game, blackLeft));
		board.setPiece(0, 6, null);
		board.setPiece(7, 6, null);
		assertEquals(true, whiteKing.rookEligibleToCastle.test(game, whiteLeft));
		assertEquals(true, blackKing.rookEligibleToCastle.test(game, blackLeft));
		board.getPiece(0, 0).get().setHasMoved(true);
		assertNotEquals(true, whiteKing.rookEligibleToCastle.test(game, whiteRight));
	}
	
	@Test
	void testKingEligibleToCastle() {
		board.setPiece(0, 1, null);
		board.setPiece(0, 2, null);
		King whiteKing = (King) board.getPiece(0, 3).get();
		assertEquals(true, whiteKing.kingEligibleToCastle.test(game, whiteRight));
		board.setPiece(1, 2, null);
		Queen queenB = (Queen) board.getPiece(7, 4).get();
		board.setPiece(4, 2, queenB);
		assertEquals(false, whiteKing.kingEligibleToCastle.test(game, whiteRight));
		board.setPiece(4, 2, null);
		assertEquals(true, whiteKing.kingEligibleToCastle.test(game, whiteRight));
		whiteKing.setHasMoved(true);
		assertEquals(false, whiteKing.kingEligibleToCastle.test(game, whiteRight));
		whiteKing.setHasMoved(false);
		board.setPiece(1, 3, null);
		board.setPiece(4, 3, queenB);
		assertEquals(false, whiteKing.kingEligibleToCastle.test(game, whiteRight));
	}

}
