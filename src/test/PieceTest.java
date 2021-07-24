package test;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.pieces.Knight;
import chess.pieces.Pawn;

class PieceTest {

	private Pawn   pawn   = new Pawn (BLACK);
	private Knight knight = new Knight (WHITE);



	@Test
	void testGetColor() {
		assertEquals(BLACK, pawn.getColor());
		assertEquals(WHITE, knight.getColor());
	}
}
