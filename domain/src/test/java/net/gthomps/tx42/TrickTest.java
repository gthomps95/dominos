package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;


public class TrickTest {
	
	@Test
	public void playedDominoIsInList() {
		Trick trick = new Trick();
		Player player = new Player("Player 1");
		Domino domino = new Domino(4, 2);
		PlayedDomino playedDomino = new PlayedDomino(player, domino);
		trick.playDomino(playedDomino);
		
		assertTrue(trick.getPlayedDominos().contains(playedDomino));
	}

}
