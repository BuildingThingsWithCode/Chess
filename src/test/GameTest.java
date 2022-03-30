package test;

import static chess.Game.attackingMoves;
import static chess.Game.kingSave;
import static chess.Game.pathUnobstructed;
import static chess.Game.searchForKings;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import chess.Board;
import chess.Game;
import chess.Move;
import chess.Player;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Queen;


class GameTest {
	Board  board      = new Board();
	Player player1    = new Player("James", WHITE);
	Player player2    = new Player("Dirk", BLACK);
	Game   game       = new Game(board, player1, player2);
	King   whiteKing  = new King(WHITE);
	King   blackKing  = new King(BLACK);
	Pawn   blackPawn  = new Pawn(BLACK);		
	Queen  whiteQueen = new Queen(WHITE);


	@Test
	void testPathUnobstructed() {
		Bishop bishop = (Bishop) board.getPiece(0, 5).get();
		board.setPiece(0, 5, null);
		board.setPiece(3, 2, bishop);
		Move move = new Move(board.getField(3, 2), board.getField(6, 5));
		assertEquals(true, pathUnobstructed.test(game, move));
		Pawn pawn = (Pawn) board.getPiece(6, 4).get();
		board.setPiece(6, 4, null);
		board.setPiece(5, 4, pawn);
		assertEquals(false, pathUnobstructed.test(game, move));
	}

	@Test
	void testSearchForKings() {
		assertEquals(board.getField(0, 3), searchForKings.apply(game).get(WHITE));
		assertEquals(board.getField(7, 3), searchForKings.apply(game).get(BLACK));
		board.setPiece(0, 3, null);
		board.setPiece(7, 3, null);
		board.setPiece(3, 2, whiteKing);
		board.setPiece(6, 7, blackKing);
		assertEquals(board.getField(3, 2), searchForKings.apply(game).get(WHITE));
		assertEquals(board.getField(6, 7), searchForKings.apply(game).get(BLACK));
	}

	@Test
	void testAttackingMoves() {
		Bishop bishop = (Bishop) board.getPiece(0, 5).get();
		board.setPiece(0, 5, null);
		board.setPiece(3, 2, bishop);
		assertEquals(1, attackingMoves.apply(game, game.getField(6, 5)).size());
		Move move = attackingMoves.apply(game, game.getField(6, 5)).get(0);
		assertEquals(board.getField(3, 2), move.getStart());
		assertEquals(bishop, move.getPiece());
		board.setPiece(3, 5, board.getPiece(0, 0).get());
		assertEquals(2, attackingMoves.apply(game, game.getField(6, 5)).size());
	}

	@Test
	void testKingSave() {
		Queen queen =  (Queen) game.getPiece(7, 4).get();
		board.setPiece(4, 7, queen);
		Move move1 = new Move(game.getField(1, 1), game.getField(3, 1));
		Move move2 = new Move(game.getField(1, 4), game.getField(3, 4));
		assertEquals(true, kingSave.test(game, move1));
		assertEquals(false, kingSave.test(game, move2));
	}

	@Test
	void testCheckOnOpponent() {
		Queen queen =  (Queen) game.getPiece(7, 4).get();
		Move move = new Move(game.getField(7, 4), game.getField(4, 7));
		board.setPiece(1, 4, null);
		board.setPiece(4, 7, queen);
		assertEquals(true, game.checkOnOpponent.test(move));
	}

	@Test
	void testCheckmate() {
		//this sets the board in the position for "Fool's mate" with black winning
		board.setPiece(3, 1, board.getPiece(1, 1).get());
		board.setPiece(1, 1, null);
		board.setPiece(2, 2, board.getPiece(1, 2).get());
		board.setPiece(1, 2, null);
		Move move = new Move(board.getField(7, 4),board.getField(3, 0));
		board.setPiece(4, 3, board.getPiece(6, 3).get());
		board.setPiece(6, 3, null);
		board.setPiece(3, 0, board.getPiece(7, 4).get());
		board.setPiece(7, 4, null);
		
		Move move2 = new Move(board.getField(3, 0),board.getField(0, 3));
		assertEquals(true, board.getPiece(3, 0).get().checkMove(game, move2));
		assertEquals(1, attackingMoves.apply(game, board.getField(0, 3)).size());
		assertEquals(true, game.checkmate.test(move));
		//setting the white horse in a position that it can make a move to block check and 
		//see if checkmate is false
		board.setPiece(4, 2, board.getPiece(0, 1).get());
		board.setPiece(0, 1, null);
		assertEquals(false, game.checkmate.test(move));
	}

	@Test
	void testStalemate() {
		//white queen pins the black king in a corner without giving check. Stalemate position.
		board.clear();
		board.setPiece(7, 7, blackKing);
		board.setPiece(4, 4, whiteKing);
		board.setPiece(6, 5, whiteQueen);
		Move move = new Move(board.getField(6, 0), board.getField(6, 5));
		move.setPiece(whiteQueen);
		assertEquals(true, game.stalemate.test(move));
	}
		
	@Test
	void testInsufficientMaterialColor() {
		board.clear();
		King k   = new King(BLACK);
		King K   = new King(WHITE);
		Queen Q  = new Queen(WHITE);
		Bishop B = new Bishop(WHITE);
		Pawn p   = new Pawn(BLACK);
		board.setPiece(0, 4, K);
		board.setPiece(1, 5, B);
		board.setPiece(6, 4, k);
		assertEquals(true, game.insufficientMaterialColor.test(WHITE));
		board.setPiece(1, 5, Q);
		assertEquals(false, game.insufficientMaterialColor.test(WHITE));
		assertEquals(true, game.insufficientMaterialColor.test(BLACK));
		board.setPiece(5, 5, p);
		assertEquals(false, game.insufficientMaterialColor.test(BLACK));
	}
	
	@Test 
	void testIsPromotion(){
		board.clear();
		Pawn black = new Pawn(BLACK);
		Pawn white = new Pawn(WHITE);
		board.setPiece(6, 2, white);
		board.setPiece(1, 4, black);
		Move forWhite = new Move(board.getField(6, 2), board.getField(7, 2));
		Move forBlack = new Move(board.getField(1, 4), board.getField(0, 4));
		assertEquals(true, game.isPromotion.test(forWhite));
		assertEquals(true, game.isPromotion.test(forBlack));
		board.setPiece(3, 3, white);
		Move forWhite2 = new Move(board.getField(3, 3), board.getField(4, 3));
		assertEquals(false, game.isPromotion.test(forWhite2));
		forWhite.setPiece(black);
		assertEquals(false, game.isPromotion.test(forWhite));
		
	}
	
	@Test
	void testGetActivePlayer() {
		assertNotEquals(player2, game.getActivePlayer());
		game.setActivePlayer(player2);
		assertNotEquals(player1, game.getActivePlayer());
		assertEquals(player2, game.getActivePlayer());
	}

}


