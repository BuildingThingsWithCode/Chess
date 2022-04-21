package test;

import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Field;
import chess.Game;
import chess.Move;
import chess.pieces.Queen;

class QueenTest {
	
	Game  game;
	Queen queen      = new Queen(WHITE);
	Field startfield = new Field(1, 3);
	Field endfield   = new Field(5, 7);
	Field endfield2  = new Field(2, 1);
	Field endfield3  = new Field(7, 3);
	Field endfield4  = new Field(0, 4);
	Move  move       = new Move (startfield, endfield);
	Move  move2      = new Move (startfield, endfield2);
	Move  move3      = new Move (startfield, endfield3);
	Move  move4      = new Move (startfield, endfield4);
	
	
	
	@Test
	void testCheckMove() {
		startfield.setPiece(queen);
		Move move = new Move (startfield, endfield);
		assertEquals(true, queen.checkMove(game, move));
		assertEquals(false, queen.checkMove(game, move2));
		assertEquals(true, queen.checkMove(game, move3));
		assertEquals(true, queen.checkMove(game, move4));
	}
}
