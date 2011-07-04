package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	private Player createNewPlayer() {
		String name = "player 1";
		return new Player(name);		
	}
	
	@Test
	public void toStringTest() {
		String name = "player 1";
		assertEquals(name, (new Player(name)).toString());
	}
	
	@Test 
	public void addDominoToHand() {
		Player player = createNewPlayer();
		Domino domino = new Domino(4, 2);
		player.addDominoToHand(domino);
		assertTrue(player.getDominosInHand().contains(domino));
	}
	
	@Test
	public void playDominoRemovesDominoFromHand() {
		Player player = createNewPlayer();
		Domino domino = new Domino(4,2);
		
		player.addDominoToHand(domino);
		assertTrue(player.getDominosInHand().contains(domino));
		player.playDomino(domino);
		assertFalse(player.getDominosInHand().contains(domino));
	}
	
	public static Player[] createFourGenericPlayers() {
		Player[] players = new Player[4];
		players[0] = new Player("Player 1");
		players[1] = new Player("Player 2");
		players[2] = new Player("Player 3");
		players[3] = new Player("Player 4");
		
		return players;
	}
	
	@Test
	public void secondPlayerIsFirstPlayerAfterSetOrder() {
		Player[] players = createFourGenericPlayers();
		Player[] newOrderedPlayers = Player.getReorderedPlayers(players, players[1]);
		
		assertEquals(players[1], newOrderedPlayers[0]);
		assertEquals(players[2], newOrderedPlayers[1]);
		assertEquals(players[3], newOrderedPlayers[2]);
		assertEquals(players[0], newOrderedPlayers[3]);
	}
}
