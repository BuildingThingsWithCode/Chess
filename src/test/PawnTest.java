package test;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Game;
import chess.Move;
import chess.Player;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Rook;

class PawnTest {

	private Pawn   pawn  = new Pawn(WHITE);
	private Rook   rook  = new Rook(BLACK);
	private Board  board = new Board();
	private Player piet  = new Player("Piet", BLACK);
	private Player jan   = new Player("Jan", WHITE); 
	private Game   game  = new Game(board, jan, piet);


	@Test
	void testCheckMove() {
		Move move = new Move (game.getField(1, 3), game.getField(5, 7));
		assertEquals(false, pawn.checkMove(game, move));
		move = new Move(game.getField(1, 3), game.getField(3, 3));
		assertEquals(true, pawn.checkMove(game, move));
		move = new Move(game.getField(1, 3), game.getField(2, 2));
		assertEquals(false, pawn.checkMove(game, move));
		//can make a diagonal move when taking opponents piece
		board.setPiece(2, 2, rook);	
		assertEquals(true, pawn.checkMove(game, move));
		board.setPiece(3, 1, board.getPiece(1, 1).get());
		board.setPiece(1, 1, null);
		board.setPiece(2, 2, board.getPiece(1, 2).get());
		board.setPiece(1, 2, null);
		board.setPiece(4, 3, board.getPiece(6, 3).get());
		board.setPiece(6, 3, null);
		board.setPiece(3, 0, board.getPiece(7, 4).get());
		board.setPiece(7, 4, null);
		move = new Move(game.getField(1, 3), game.getField(2, 1));
		assertEquals(false, pawn.checkMove(game, move));
	}
	
	@Test
	void testCheckForEnPassant() {
		Piece pawnWhite = board.getPiece(1, 4).get();
		board.setPiece(1, 4, null);
		board.setPiece(4, 4, pawnWhite);
		Move black = new Move(board.getField(6, 5),board.getField(4, 5));
		game.setActivePlayer(piet);
		game.play(black);
		Move enpassant = new Move(board.getField(4, 4), board.getField(5, 5));
		assertEquals(true, ((Pawn) pawnWhite).checkForEnPassant(game, enpassant));
	}
}


