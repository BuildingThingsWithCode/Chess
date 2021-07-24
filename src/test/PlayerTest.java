package test;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import chess.Player;

class PlayerTest {
	
	Player player1 = new Player("Bobby Fisher", WHITE);
	Player player2 = new Player("Magnus Carlsen", BLACK);

	@Test
	void testGetName() {
		assertEquals("Bobby Fisher", player1.getName());
		assertEquals("Magnus Carlsen", player2.getName());
	}

	@Test
	void testSetName() {
		player1.setName("Garry Kasparov");
		assertEquals("Garry Kasparov", player1.getName());
	}

	@Test
	void testGetColor() {
		assertEquals(WHITE, player1.getColor());
		assertEquals(BLACK, player2.getColor());
	}

}
