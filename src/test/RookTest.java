package test;

import static javafx.scene.paint.Color.BLACK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Field;
import chess.Game;
import chess.Move;
import chess.pieces.Rook;

class RookTest {
	
	Game  game;
	Rook  rook       = new Rook(BLACK);
	Field startfield = new Field(1, 3);
	Field endfield   = new Field(5, 4);
	Field endfield2  = new Field(1, 0);
	Field endfield3  = new Field(7, 3);
	Move  move       = new Move (startfield, endfield);
	Move  move2      = new Move (startfield, endfield2);
	Move  move3      = new Move (startfield, endfield3);
	

	@Test
	void testCheckMove() {
		startfield.setPiece(rook);
		Move  move 		 = new Move (startfield, endfield);
		assertEquals(false, rook.checkMove(game, move));
		assertEquals(true, rook.checkMove(game, move2));
		assertEquals(true, rook.checkMove(game, move3));
	}

}
