package chess.util;

import static chess.util.Converter.FEN_MAP;
import static javafx.scene.paint.Color.WHITE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chess.Field;
import chess.Game;
import chess.pieces.King;
import chess.pieces.Piece;
import chess.pieces.Rook;

public final class FENBuilder { 

    static final int NR_OF_FIELDS_IN_ROW = 8;


    /*
     * returns true if field contains a piece that hasn't moved.
     */
    public static Predicate<Field> hasNotMoved = f -> {
        return	f.getPiece().isPresent() 
                && !f.getPiece().get().hasMoved();
    };

    /*
     * returns true if field contains a rook eligible to castle.
     */
    public static Predicate<Field> rookOKToCastle = f -> {
        return hasNotMoved.test(f) && f.getPiece().get() instanceof Rook;
    };

    /*
     * returns true if field contains a king eligible to castle.
     */
    public static Predicate<Field> kingOKToCastle = f -> {
        return hasNotMoved.test(f) && f.getPiece().get() instanceof King;
    };

    /*
     * returns a String representation for the active player.
     */
    public static Function<Game, String> activePlayer = g -> {
        return g.getActivePlayer().getColor() == WHITE ? " w" : " b";
    };

    /*
     * returns a string representation of the castling situation in FEN notation.
     */
    public static Function<Game, String> castling = g -> {
        StringBuilder builder = new StringBuilder();
        if (rookOKToCastle.test(g.getField(0, 0)) && kingOKToCastle.test(g.getField(0, 3))) builder.append("K");
        if (rookOKToCastle.test(g.getField(0, 7)) && kingOKToCastle.test(g.getField(0, 3))) builder.append("Q");
        if (rookOKToCastle.test(g.getField(7, 0)) && kingOKToCastle.test(g.getField(7, 3))) builder.append("k");
        if (rookOKToCastle.test(g.getField(7, 7)) && kingOKToCastle.test(g.getField(7, 3))) builder.append("q");
        if (builder.length() == 0) builder.append("-");
        builder.insert(0, " ");
        return builder.toString();
    };

    /*
     * returns the enpassant field.
     */
    public static Function<Game, String> enpassant = g -> {
        return g.getEnpassantField();
    };

    /*
     * returns a String representation of the half move and the full move clock.
     */
    public static Function<Game, String> count = g -> {
        return " "+ g.getHalfMove()+ " "+ g.getFullMove();
    };

    /*
     * returns a String containing the FEN notation for a piece.
     */
    public static Function<Piece, String> pieceToFEN =  p -> { 
        return	FEN_MAP
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().getClass() == p.getClass())
                .filter(entry -> entry.getKey().getColor() == p.getColor())
                .map(entry -> entry.getValue().get(0))
                .reduce("", String::concat);
    };

    /*
     * returns a String representation of the board with pieces, using
     * the FEN notation. Number of empty fields is not counted.
     * 
     */
    public static Function<Game,String> boardToFENEmptyFieldsNotCounted = g -> {
        List<Field> board = Stream.of(g.getBoardState())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        //FEN notation starts with black, so reverse.
        Collections.reverse(board);
        return board.stream()
                .map(f -> f.getPiece().isPresent() ? pieceToFEN.apply(f.getPiece().get()) : "0")
                .reduce("", String::concat);
    };

    /*
     * returns a StringBuilder containing the complete String representation of the board with pieces, 
     * using the FEN notation.
     */
    public static Function<Game, StringBuilder> boardToFEN = g -> {
        StringBuilder builder = new StringBuilder();
        builder.append(boardToFENEmptyFieldsNotCounted.apply(g));
        //+= 9, by inserting /, the next insert has to be add at 9 and not 8.
        for (int offset = NR_OF_FIELDS_IN_ROW; offset < 63; offset += 9) builder.insert(offset, "/");
        countEmptyFields(builder, NR_OF_FIELDS_IN_ROW); 
        return builder;
    };


    /*
     * returns the string representation of the game, using the FEN notation.
     */
    public static Function<Game, String> gameToFEN = g -> {
        return boardToFEN.apply(g)
                .append(activePlayer.apply(g))
                .append(castling.apply(g))
                .append(enpassant.apply(g))
                .append(count.apply(g))
                .toString();
    };

    /*
     * returns a string representation of the board, with only the information
     * needed to check if the 3 fold repetition rule is applicable.
     */
    public static Function<Game, String> gameToFEN3FoldRule = g -> {
        return boardToFEN.apply(g)
                .append(castling.apply(g))
                .toString(); 
    };


    /*
     * FEN notation replaces one empty field by number 1. And a number
     * of consecutive empty fields in a row of the board, by their total.
     * Recursively counting the empty fields.
     */
    public static void countEmptyFields(StringBuilder builder, int emptyFields) {
        StringBuilder consecZeros = new StringBuilder(emptyFields);
        for (int j=0; j<emptyFields; j++) consecZeros.append("0");
        //indexOf changes after delete, need int index to persist correct index for insert.
        while (builder.indexOf(consecZeros.toString()) > 0) {
            int index = builder.indexOf(consecZeros.toString());
            builder.delete(index, index + emptyFields);
            builder.insert(index, emptyFields);
        }
        if (emptyFields > 1) countEmptyFields(builder, emptyFields - 1);
    }
}
