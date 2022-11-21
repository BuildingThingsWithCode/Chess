package test;
import static chess.util.Converter.convert;
import static chess.util.Converter.getAllBlackViewPieces;
import static chess.util.Converter.getAllWhiteViewPieces;
import static chess.util.Converter.getFENFromModelPiece;
import static chess.util.Converter.getPathToImage;
import static chess.util.Converter.getViewPieceFromFEN;
import static chess.util.Converter.modelPieceAtIndexes;
import static chess.util.Converter.viewPieceAtIndexes;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ConverterTest {
	
	@Test
	void testViewPieceAtIndexes() {
		assertEquals("♖", viewPieceAtIndexes.apply(0, 0).get());
		assertEquals("♝", viewPieceAtIndexes.apply(7, 5).get());
		assertEquals(false, viewPieceAtIndexes.apply(4, 4).isPresent());
	}
	
	@Test
	void testModelPieceAtIndexes() {
		assertEquals(WHITE, modelPieceAtIndexes.apply(0, 5).get().getColor());
		assertEquals(true, modelPieceAtIndexes.apply(0, 5).get() instanceof Bishop);
		assertEquals(BLACK, modelPieceAtIndexes.apply(6, 5).get().getColor());
		assertEquals(true, modelPieceAtIndexes.apply(6, 5).get() instanceof Pawn);
		assertEquals(false, modelPieceAtIndexes.apply(2, 3).isPresent());
	}
	
	@Test
	//from model to view
	void testConvertModel() {
	   assertEquals("♔", convert(new King(WHITE)));
	   assertEquals("♕", convert(new Queen(WHITE)));
	   assertEquals("♜", convert(new Rook(BLACK)));
	   assertEquals("♟", convert(new Pawn(BLACK)));
	}
	
	@Test
	void testGetViewPieceFromFEN() {
		assertEquals("♔", getViewPieceFromFEN("K"));
		assertEquals("♚", getViewPieceFromFEN("k"));
		assertEquals("♝", getViewPieceFromFEN("b"));
		assertEquals("♘", getViewPieceFromFEN("N"));
	}
	
	@Test
	void testGetFENFromModelPiece() {
	   assertEquals("K", getFENFromModelPiece(new King(WHITE)));
	   assertEquals("P", getFENFromModelPiece(new Pawn(WHITE)));
	   assertEquals("b", getFENFromModelPiece(new Bishop(BLACK)));
	   assertEquals("r", getFENFromModelPiece(new Rook(BLACK)));
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
		assertEquals("file:/C:/workspace/Chess/bin/resources/images/normal/whiteKing.png", getPathToImage("♔", 70.0));
		assertEquals("file:/C:/workspace/Chess/bin/resources/images/big/whiteKing2.png", getPathToImage("♔", 78.1));
		assertEquals("file:/C:/workspace/Chess/bin/resources/images/normal/blackBishop.png", getPathToImage("♝", 20.0));
	}
	
	@Test
	//from view to model
	void testConvertView() {
		assertEquals(WHITE, convert("♘").getColor());
		assertEquals(true, convert("♘") instanceof Knight);
		assertEquals(BLACK, convert("♛").getColor());
		assertEquals(true, convert("♛") instanceof Queen);
		assertEquals(BLACK, convert("♟").getColor());
		assertEquals(true, convert("♟") instanceof Pawn);

	}
}
