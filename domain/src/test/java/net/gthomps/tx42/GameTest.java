package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {
	
	@Test
	public void gameDoesNotHaveWinnerAfterSixHands() {
		Game game = new Game(PlayerTest.createFourGenericPlayers());
		
		for (int i = 0; i < 6; i++) {
			game.startNewHand();
			game.getCurrentHand().addBid(new Bid(game.getPlayers()[0], 30));
			game.completeHand();
		}
		
		assertFalse(game.hasWinner());
	}

	@Test
	public void gameHasWinnerAfterSevenHands() {
		Game game = new Game(PlayerTest.createFourGenericPlayers());
		
		for (int i = 0; i < 7; i++) {
			game.startNewHand();
			game.getCurrentHand().addBid(new Bid(game.getPlayers()[0], 30));
			game.completeHand();
		}
		
		assertTrue(game.hasWinner());
	}
	
	@Test
	public void handOrderedPlayerHasSecondPlayerFirstForSecondHand() {
		Player[] players = PlayerTest.createFourGenericPlayers();
		Game game = new Game(players);
		game.startNewHand();
		assertEquals(players[0], game.getHandOrderedPlayers()[0]);
		
		game.getCurrentHand().addBid(new Bid(game.getPlayers()[0], 30));
		game.completeHand();
		game.startNewHand();
		assertEquals(players[1], game.getHandOrderedPlayers()[0]);		
		assertEquals(players[2], game.getHandOrderedPlayers()[1]);		
		assertEquals(players[3], game.getHandOrderedPlayers()[2]);		
		assertEquals(players[0], game.getHandOrderedPlayers()[3]);		
	}

}
