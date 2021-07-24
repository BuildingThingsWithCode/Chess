package chess.util;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

public final class Converter {
	
	public static final Map<Piece, List<String>>  FEN_MAP 		= new HashMap<Piece, List<String>>();
	public static final Map<Integer, String> 	  EMPTY_FIELDS 	= new HashMap<Integer, String>();
	public static final Map<String, int[]>        CASTLING_MAP  = new HashMap<String, int[]>();
	public static final Map<int[][],String> 	  PIECES  		= new HashMap<>();
	
	private Converter() {}
	
	static {
		//white
		FEN_MAP.put(new King  (WHITE), Arrays.asList("K","♔", Converter.class.getClassLoader().getResource("resources/images/normal/whiteKing.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/whiteKing2.png").toString()));
		FEN_MAP.put(new Queen (WHITE), Arrays.asList("Q","♕", Converter.class.getClassLoader().getResource("resources/images/normal/whiteQueen.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/whiteQueen2.png").toString()));
		FEN_MAP.put(new Rook  (WHITE), Arrays.asList("R","♖", Converter.class.getClassLoader().getResource("resources/images/normal/whiteRook.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/whiteRook2.png").toString()));
		FEN_MAP.put(new Bishop(WHITE), Arrays.asList("B","♗", Converter.class.getClassLoader().getResource("resources/images/normal/whiteBishop.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/whiteBishop2.png").toString()));
		FEN_MAP.put(new Knight(WHITE), Arrays.asList("N","♘", Converter.class.getClassLoader().getResource("resources/images/normal/whiteKnight.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/whiteKnight2.png").toString()));
		FEN_MAP.put(new Pawn  (WHITE), Arrays.asList("P","♙", Converter.class.getClassLoader().getResource("resources/images/normal/whitePawn.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/whitePawn2.png").toString()));
		//black
		FEN_MAP.put(new King  (BLACK), Arrays.asList("k","♚", Converter.class.getClassLoader().getResource("resources/images/normal/blackKing.png").toString(), 
				Converter.class.getClassLoader().getResource("resources/images/big/blackKing2.png").toString()));
		FEN_MAP.put(new Queen (BLACK), Arrays.asList("q","♛", Converter.class.getClassLoader().getResource("resources/images/normal/blackQueen.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/blackQueen2.png").toString()));
		FEN_MAP.put(new Rook  (BLACK), Arrays.asList("r","♜", Converter.class.getClassLoader().getResource("resources/images/normal/blackRook.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/blackRook2.png").toString()));
		FEN_MAP.put(new Bishop(BLACK), Arrays.asList("b","♝", Converter.class.getClassLoader().getResource("resources/images/normal/blackBishop.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/blackBishop2.png").toString()));
		FEN_MAP.put(new Knight(BLACK), Arrays.asList("n","♞", Converter.class.getClassLoader().getResource("resources/images/normal/blackKnight.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/blackKnight2.png").toString()));
		FEN_MAP.put(new Pawn  (BLACK), Arrays.asList("p","♟", Converter.class.getClassLoader().getResource("resources/images/normal/blackPawn.png").toString(),
				Converter.class.getClassLoader().getResource("resources/images/big/blackPawn2.png").toString()));
	}


	static {
		EMPTY_FIELDS.put(8,"00000000");
		EMPTY_FIELDS.put(7, "0000000");
		EMPTY_FIELDS.put(6,  "000000");
		EMPTY_FIELDS.put(5,   "00000");
		EMPTY_FIELDS.put(4,    "0000");
		EMPTY_FIELDS.put(3,     "000");
		EMPTY_FIELDS.put(2,      "00");
		EMPTY_FIELDS.put(1,       "0");

	}
	
	static {
		PIECES.put(new int[][] {{0,3}}, 	   "♔");
		PIECES.put(new int[][] {{7,3}}, 	   "♚");
		PIECES.put(new int[][] {{0,4}},		   "♕");
		PIECES.put(new int[][] {{7,4}}, 	   "♛");
		PIECES.put(new int[][] {{0,0}, {0,7}}, "♖");
		PIECES.put(new int[][] {{7,0}, {7,7}}, "♜");
		PIECES.put(new int[][] {{0,2}, {0,5}}, "♗");
		PIECES.put(new int[][] {{7,2}, {7,5}}, "♝");
		PIECES.put(new int[][] {{0,1}, {0,6}}, "♘");
		PIECES.put(new int[][] {{7,1}, {7,6}}, "♞");
	}
	
	/*
	 * returns an Optional containing the symbol for the piece to be used in the view or no value, given a row - and column index.
	 */
	public static BiFunction<Integer, Integer, Optional<String>> xyViewPiece = (rowIndex, columnIndex) -> {
		if (rowIndex == 1) return Optional.of("♙");
		if (rowIndex == 6) return Optional.of("♟");
		return PIECES.entrySet()
				.stream()
				.filter(entryset -> Stream.of(entryset.getKey()).filter(key -> Arrays.equals(key, new int[]{rowIndex, columnIndex})).count() == 1)
				.map(entryset -> entryset.getValue())
				.findFirst();
	};

	/*
	 * returns an Optional containing the piece to be used in the model or no value, given a row - and column index.
	 */
	public static BiFunction<Integer, Integer, Optional<Piece>> xyModelPiece = (rowIndex, columnIndex) -> {
		if (rowIndex == 1) 											 return Optional.of(new Pawn(WHITE));
		if (rowIndex == 6) 											 return Optional.of(new Pawn(BLACK));
		if (rowIndex == 0 && columnIndex == 3) 						 return Optional.of(new King(WHITE));
		if (rowIndex == 7 && columnIndex == 3) 						 return Optional.of(new King(BLACK));
		if (rowIndex == 0 && columnIndex == 4) 						 return Optional.of(new Queen(WHITE));
		if (rowIndex == 7 && columnIndex == 4) 						 return Optional.of(new Queen(BLACK));
		if (rowIndex == 0 && (columnIndex == 0 || columnIndex == 7)) return Optional.of(new Rook(WHITE));
		if (rowIndex == 7 && (columnIndex == 0 || columnIndex == 7)) return Optional.of(new Rook(BLACK));
		if (rowIndex == 0 && (columnIndex == 2 || columnIndex == 5)) return Optional.of(new Bishop(WHITE));
		if (rowIndex == 7 && (columnIndex == 2 || columnIndex == 5)) return Optional.of(new Bishop(BLACK));
		if (rowIndex == 0 && (columnIndex == 1 || columnIndex == 6)) return Optional.of(new Knight(WHITE));
		if (rowIndex == 7 && (columnIndex == 1 || columnIndex == 6)) return Optional.of(new Knight(BLACK));
		return Optional.empty();
	};

	/*
	 * help method to get what we need from FenMap. Values are: 0 (FEN notation), 1 (symbol for
	 * the piece in the view), 2 (path to image used in the DragView when moving pieces). The key
	 * is a Piece for the model.
	 */
	public static List<String> getValues(Predicate<Entry<Piece, List<String>>> predicate, int value){
		return FEN_MAP
				.entrySet()
				.stream()
				.filter(predicate)
				.map(e -> e.getValue().get(value))
				.sorted()
				.collect(Collectors.toList());
	}

	//given a FEN notation for a piece, returns a Piece for the view.
	public static String getOneViewPiece(String fen) {
		return getValues(e -> e.getValue().get(0) == fen, 1).get(0);
	}

	//returns all white pieces used in the view.
	public static List<String> getAllWhiteViewPieces() {
		return getValues(e -> e.getKey().getColor() == WHITE, 1);
	}

	//returns all black pieces used in the view.
	public static List<String> getAllBlackViewPieces() {
		return getValues(e -> e.getKey().getColor() == BLACK, 1);
	}

	//return the path to the image,needed to handle dragging the pieces.
	public static String getPathToImage(String viewPiece, Double labelWidth) {
		int imageSize = labelWidth < 78.0 ? 2 : 3;
		return getValues(e -> (e.getValue().get(1)) == viewPiece, imageSize).get(0);
	}

	//help method.
	public static Piece getKey(Predicate<Entry<Piece, List<String>>> predicate){
		return FEN_MAP
				.entrySet()
				.stream()
				.filter(predicate)
				.map(e -> e.getKey())
				.findFirst()
				.get();
	}

	//given the view piece, this method returns a corresponding model piece.
	public static Piece getModelPiece(String viewPiece) {
		return getKey(e -> e.getValue().get(1) == viewPiece);
	}
}