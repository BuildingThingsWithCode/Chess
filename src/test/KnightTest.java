package test;

import static javafx.scene.paint.Color.BLACK;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Field;
import chess.Game;
import chess.Move;
import chess.pieces.Knight;
import chess.pieces.Pawn;

class KnightTest {
	
	Game game;
	Knight knight     = new Knight(BLACK);
	Pawn   pawn       = new Pawn(BLACK);
	Field  startField = new Field(1, 3);
	Field  endField   = new Field(5, 7);
	Field  endField2  = new Field(0, 5);
	Field  endField3  = new Field(1, 5);
	Move move = new Move (startField, endField);
	Move move2 = new Move (startField, endField2);
	Move move3 = new Move (startField, endField3);

	
	@Test
	void testcheckMove() {
		startField.setPiece(knight);
		assertEquals(false, knight.checkMove(game, move));
		assertEquals(true, knight.checkMove(game, move2));
		endField2.setPiece(pawn);
		assertEquals(false, knight.checkMove(game, move2));
		assertEquals(false, knight.checkMove(game, move3));
	}

}
