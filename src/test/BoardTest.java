package test;

import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import chess.Board;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;

class BoardTest {
	Board board = new Board();

	@Test
	void testGetField() {
		assertEquals(2, board.getField(2, 6).getX());
		assertEquals(6, board.getField(2, 6).getY());
		assertNotEquals(3, board.getField(4, 2).getX());
		assertNotEquals(6, board.getField(4, 2).getY());
	}

	@Test
	void testGetPiece() {
		assertEquals(true, board.getPiece(1, 6).get() instanceof Pawn);
		assertEquals(true , board.getPiece(6, 6).get() instanceof Pawn);
		assertEquals(true, board.getPiece(7, 3).get() instanceof King);
	}

	@Test
	void testClear() {
	    assertEquals(true, board.getPiece(1, 6).isPresent());
	    assertEquals(true, board.getPiece(6, 6).isPresent());
	    assertEquals(true, board.getPiece(7, 3).isPresent());
	    board.clear();
	    assertEquals(false, board.getPiece(1, 6).isPresent());
	    assertEquals(false, board.getPiece(6, 6).isPresent());
	    assertEquals(false, board.getPiece(7, 3).isPresent());
	}


	@Test
	void testSetPiece() {
		assertEquals(true, board.getPiece(1, 6).get() instanceof Pawn);
		board.setPiece(1, 6, null);
		assertEquals(Optional.ofNullable(null), board.getPiece(1, 6));
		Queen queen= new Queen(WHITE);
		board.setPiece(1, 6, queen);
		assertEquals(queen, board.getPiece(1, 6).get());
	}

}

