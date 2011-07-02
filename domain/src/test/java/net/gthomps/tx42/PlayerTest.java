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
}
