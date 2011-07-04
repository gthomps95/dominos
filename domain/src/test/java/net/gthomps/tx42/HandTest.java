package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;

public class HandTest {

	@Test
	public void dealDominosGivesSevenToEachPlayer() {
		Player[] players = PlayerTest.createFourGenericPlayers();
		Hand hand = new Hand(players);
		hand.dealDominos(Domino.getFullDominoSet(), players);

		for (Player p : players)
			assertEquals(7, p.getDominosInHand().size());
	}
	
	@Test
	public void getBidWinnerWithFourBids() {
		Hand hand = new Hand(PlayerTest.createFourGenericPlayers());

		Player winningBidder = addBidsForGivenCountLastPlayerIsWinner(hand, 4, 30);
		
		assertEquals(winningBidder, hand.getWinningBid().getPlayer());		
	}

	private Player addBidsForGivenCountLastPlayerIsWinner(Hand hand, int count, int winningBid) {
		Player[] players = hand.getPlayers();
		
		for (int i = 0; i < count; i++) {
			int bid = Bid.PASS;
			if ( count == (i + 1) ) 
				bid = winningBid;
				
			hand.addBid(new Bid(players[i], bid));
		}

		return players[count - 1];
	}
	
	@Test
	public void bidIsOverAfterFourBids() {
		Hand hand = new Hand(PlayerTest.createFourGenericPlayers());
		
		addBidsForGivenCountLastPlayerIsWinner(hand, 4, 30);
		
		assertTrue(hand.biddingIsOver());		
	}
	
	@Test
	public void bidIsNotOverAfterThreeBids() {
		Hand hand = new Hand(PlayerTest.createFourGenericPlayers());
		
		addBidsForGivenCountLastPlayerIsWinner(hand, 3, 30);
		
		assertFalse(hand.biddingIsOver());		
	}
	
	@Test
	public void handIsNotOverAfterSixTricks() {
		Hand hand = new Hand(PlayerTest.createFourGenericPlayers());
		
		for (int i = 0; i < 6; i++) {
			hand.startNewTrick(hand.getPlayers()[0]);
			hand.completeTrick();
		}
		
		assertFalse(hand.isOver());
	}

	@Test
	public void handIsOverAfterSevenTricks() {
		Hand hand = new Hand(PlayerTest.createFourGenericPlayers());
		
		for (int i = 0; i < 7; i++) {
			hand.startNewTrick(hand.getPlayers()[0]);
			hand.completeTrick();
		}
		
		assertTrue(hand.isOver());
	}
	
	@Test
	public void secondPlayerIsNextBidderAfterFirstBid() {
		Hand hand = new Hand(PlayerTest.createFourGenericPlayers());
		Player player = new Player("Player 1");
		hand.addBid(new Bid(player, 30));
		
		assertEquals(hand.getPlayers()[1], hand.getNextBidder());
	}

	@Test
	public void testHandWinner() {
		Player[] players = PlayerTest.createFourGenericPlayers();
		Team[] teams = new Team[2];
		teams[0] = new Team(players[0], players[2]);
		teams[1] = new Team(players[1], players[3]);
		
		Hand hand = new Hand(players);
		addBidsForGivenCountLastPlayerIsWinner(hand, 4, 30);
		Trick trick = hand.startNewTrick(players[0]);
		trick.playDomino(new PlayedDomino(players[0], new Domino(4,2)));
		trick.playDomino(new PlayedDomino(players[0], new Domino(4,2)));
		trick.playDomino(new PlayedDomino(players[0], new Domino(4,2)));
		trick.playDomino(new PlayedDomino(players[0], new Domino(4,2)));
		hand.completeTrick();
		assertEquals(teams[0], hand.getHandWinner(teams));
	}
}
