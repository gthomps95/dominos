package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;

public class HandTest {

	@Test
	public void dealDominosGivesSevenToEachPlayer() {
		Player[] players = Player.createFourGenericPlayers();
		Hand hand = new Hand();
		hand.dealDominos(Domino.getFullDominoSet(), players);

		for (Player p : players)
			assertEquals(7, p.getDominosInHand().size());
	}
	
	@Test
	public void getBidWinnerWithFourBids() {
		Player[] players = Player.createFourGenericPlayers();
			
		Hand hand = new Hand();
		hand.addBid(new Bid(players[0], 0));
		hand.addBid(new Bid(players[1], 0));
		hand.addBid(new Bid(players[2], 0));
		hand.addBid(new Bid(players[3], 30));
		
		assertEquals(players[3], hand.getWinningBid().getPlayer());		
	}
	
}
