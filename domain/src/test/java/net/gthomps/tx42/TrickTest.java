package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;


public class TrickTest {
	
	@Test
	public void playedDominoIsInList() {
		Trick trick = new Trick(Player.createFourGenericPlayers(), 0);
		PlayedDomino playedDomino = playDomino(trick);
		
		assertTrue(trick.getPlayedDominos().contains(playedDomino));
	}

	private PlayedDomino playDomino(Trick trick) {
		Player player = new Player("Player 1");
		Domino domino = new Domino(4, 2);
		PlayedDomino playedDomino = new PlayedDomino(player, domino);
		trick.playDomino(playedDomino);
		return playedDomino;
	}
	
	@Test
	public void trickIsNotOverAfterThreeDominosPlayed() {
		Trick trick = new Trick(Player.createFourGenericPlayers(), 0);
		
		for (int i = 0; i < 3; i++)
			playDomino(trick);

		assertFalse(trick.isOver());
	}

	@Test
	public void trickIsOverAfterFourDominosPlayed() {
		Trick trick = new Trick(Player.createFourGenericPlayers(), 0);
		
		for (int i = 0; i < 4; i++)
			playDomino(trick);

		assertTrue(trick.isOver());
	}
	
	@Test
	public void trickHasCorrectNextPlayer() {
		Player[] players = Player.createFourGenericPlayers();
		Trick trick = new Trick(players, 0);
		
		assertEquals(players[0], trick.getNextPlayer());
		trick.playDomino(new PlayedDomino(players[0], new Domino(4,2)));
		assertEquals(players[1], trick.getNextPlayer());
	}
	
	@Test
	public void testTrickPoints() {
		Player[] players = Player.createFourGenericPlayers();
		Trick trick = new Trick(players, 0);
		
		trick.playDomino(new PlayedDomino(players[0], new Domino(5,5)));
		trick.playDomino(new PlayedDomino(players[1], new Domino(3,2)));
		trick.playDomino(new PlayedDomino(players[2], new Domino(0,0)));
		trick.playDomino(new PlayedDomino(players[3], new Domino(1,5)));

		assertEquals(16, trick.getPointCount());
	}
	
	@Test
	public void trumpWins() {
		Player[] players = Player.createFourGenericPlayers();
		Trick trick = new Trick(players, 0);
		
		trick.playDomino(new PlayedDomino(players[0], new Domino(5,5)));
		trick.playDomino(new PlayedDomino(players[1], new Domino(3,2)));
		trick.playDomino(new PlayedDomino(players[2], new Domino(0,0)));
		trick.playDomino(new PlayedDomino(players[3], new Domino(1,5)));

		assertEquals(players[2], trick.getWinningPlayer());
	}
}
