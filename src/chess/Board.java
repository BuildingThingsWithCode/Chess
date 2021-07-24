package chess;

import static chess.util.Converter.xyModelPiece;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import chess.pieces.Piece;

public class Board {

	private Field[][] playingField = new Field[8][8];

	//CONSTRUCTOR
	public Board () {
		this.set();
	}
	
	//METHODS
	/*
	 * creates all fields for the board and sets the pieces or null on empty fields.
	 */
	private void set() {
		for(int x=0; x<8; x++){
			for(int y=0; y<8; y++){
				playingField[x][y] = new Field (x, y);
				setPiece(x, y, xyModelPiece.apply(x, y).orElse(null));
			}
		}
	}

	/*
	 * clears all the pieces from the board.
	 */
	public void clear() {
		Stream.of(playingField)																				
		.flatMap(Arrays::stream) 															
		.filter(f -> f.getPiece().isPresent())		
		.forEach(f -> f.setPiece(null));
	}



	//GETTERS & SETTERS
	public Field[][] getBoardState() {
		return playingField;
	}

	public Field getField(int x, int y) {
		return playingField[x][y];
	}

	public Optional<Piece> getPiece(int x, int y) {
		return playingField[x][y].getPiece();
	}

	public void setPiece(int x, int y, Piece piece) {
		playingField[x][y].setPiece(piece);
	}

	public Field[][] getCopyOfBoardState() {
		return Arrays.stream(playingField).map(a ->  Arrays.copyOf(a, a.length)).toArray(Field[][]::new);
	}

}
