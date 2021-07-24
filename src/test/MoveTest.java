package test;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Field;
import chess.Move;
import chess.pieces.Knight;
import chess.pieces.Rook;

class MoveTest {
	Knight knight     = new Knight(WHITE);
	Rook   rook       = new Rook(BLACK);
	Field  startField = new Field (1, 5);
	Field  endField   = new Field(2, 3);
	Field  endField2  = new Field(3, 0);
	
	
	Move   move3 	  = new Move(endField2, startField);
	Move   move4 	  = new Move(endField2, endField2);
	
	@Test
	void testcheckMove() {
	startField.setPiece(knight);
	endField.setPiece(rook);
	Move   move  	  = new Move(startField, endField);
	Move   move2 	  = new Move(endField, endField2);
	assertEquals(true, move.checkMove());
	assertEquals(true, move2.checkMove());
	assertEquals(false, move3.checkMove());
	assertEquals(false, move4.checkMove());
	}
	
	
}
