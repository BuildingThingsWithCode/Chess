package test;

import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Field;
import chess.Game;
import chess.Move;
import chess.pieces.Bishop;

class BishopTest {
	
	int x  = 1;
	int y  = 3;
	int x2 = 4;
	int y2 = 0;
	Game game;
	Bishop 	  bishop 		= new Bishop(WHITE);
	Field 	  startposition = new Field(1, 3);
	Field 	  endposition   = new Field(4, 0);
	Field 	  endposition2  = new Field(2,1);

	@Test
	void testCheckMove() {
		startposition.setPiece(bishop);
		Move move = new Move(startposition, endposition);
		assertEquals(true, bishop.checkMove(game, move));
		move.setEnd(endposition2);
		assertEquals(false, bishop.checkMove(game,move));
	}

}
