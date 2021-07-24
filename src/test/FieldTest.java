package test;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Field;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Rook;

class FieldTest {
	Board  board  = new Board();
	Pawn   pawn   = new Pawn (BLACK);
	Knight knight = new Knight (WHITE);
	Field  field1 = new Field(0, 1);
	Field  field2 = new Field(2, 3);


	@Test
	void testGetX() {
		assertEquals(0, field1.getX());
		assertEquals(2, field2.getX());
	}

	@Test
	void testGetY() {
		assertEquals(1, field1.getY());
		assertEquals(3, field2.getY());
	}

	@Test
	void testGetPiece() {
		field1.setPiece(pawn);
		field2.setPiece(knight);
		assertEquals(pawn, field1.getPiece().get());
		assertEquals(knight, field2.getPiece().get());
	}

	@Test
	void testSetPiece() {
		field1.setPiece(knight);
		assertEquals(knight, field1.getPiece().get());
		field1.setPiece(null);
		assertEquals(false, field1.getPiece().isPresent());
		assertEquals(Rook.class, board.getField(0, 0).getPiece().get().getClass());
	}
	
	@Test
	void testPiecePresent() {
		field2.setPiece(knight);
		assertEquals(knight, field2.getPiece().get());
		assertEquals(true, field2.piecePresent());
		field2.setPiece(null);
		assertEquals(false, field2.piecePresent());
	}
}
