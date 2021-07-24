package test;
import static chess.util.Converter.getAllBlackViewPieces;
import static chess.util.Converter.getAllWhiteViewPieces;
import static chess.util.Converter.getModelPiece;
import static chess.util.Converter.getOneViewPiece;
import static chess.util.Converter.getPathToImage;
import static chess.util.Converter.xyModelPiece;
import static chess.util.Converter.xyViewPiece;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.util.Converter;

public class ConverterTest {
	
	@Test
	void testXyViewPiece() {
		assertEquals("♖", xyViewPiece.apply(0, 0).get());
		assertEquals("♝", xyViewPiece.apply(7, 5).get());
		assertEquals(false, xyViewPiece.apply(4, 4).isPresent());
	}
	
	@Test
	void testXyModelPiece() {
		assertEquals(WHITE, xyModelPiece.apply(0, 5).get().getColor());
		assertEquals(true, xyModelPiece.apply(0, 5).get() instanceof Bishop);
		assertEquals(BLACK, xyModelPiece.apply(6, 5).get().getColor());
		assertEquals(true, xyModelPiece.apply(6, 5).get() instanceof Pawn);
		assertEquals(false, xyModelPiece.apply(2, 3).isPresent());
	}
	
	@Test
	void testGetOneViewPiece() {
		assertEquals("♔", getOneViewPiece("K"));
		assertEquals("♚", getOneViewPiece("k"));
		assertEquals("♝", getOneViewPiece("b"));
		assertEquals("♘", getOneViewPiece("N"));
	}
	
	@Test
	void testGetAllWhiteViewPieces() {
		assertEquals(true, getAllWhiteViewPieces().containsAll(Arrays.asList("♔", "♕", "♖", "♗", "♘", "♙")));
	}
	
	@Test
	void testGetAllBlackViewPieces() {
		assertEquals(true, getAllBlackViewPieces().containsAll(Arrays.asList("♚", "♛", "♜", "♝", "♞", "♟")));
	}
	
	@Test
	void testGetPathToImage() {
		assertEquals("file:/C:/workspace/Chess/bin/resources/images/whiteKing.png", getPathToImage("♔", 70.0));
		assertEquals("file:/C:/workspace/Chess/bin/resources/images/whiteKing2.png", getPathToImage("♔", 78.1));
		assertEquals("file:/C:/workspace/Chess/bin/resources/images/blackBishop.png", getPathToImage("♝", 20.0));
	}
	
	@Test
	void testGetModelPiece() {
		assertEquals(WHITE, getModelPiece("♘").getColor());
		assertEquals(true, getModelPiece("♘") instanceof Knight);
		assertEquals(BLACK, getModelPiece("♛").getColor());
		assertEquals(true, getModelPiece("♛") instanceof Queen);
		assertEquals(BLACK, getModelPiece("♟").getColor());
		assertEquals(true, getModelPiece("♟") instanceof Pawn);

	}
}
