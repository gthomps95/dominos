package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;

public class HandTest {

	private Hand createGenericHand() {
		Player[] players = PlayerTest.createFourGenericPlayers();
		Hand hand = new Hand(Team.createTeams(players), players);
		return hand;
	}
	
	@Test
	public void dealDominosGivesSevenToEachPlayer() {
		Hand hand = createGenericHand();
		hand.dealDominos(Domino.getFullDominoSet(), hand.getPlayers());

		for (Player p : hand.getPlayers())
			assertEquals(7, p.getDominosInHand().size());
	}
	
	@Test
	public void getBidWinnerWithFourBids() {
		Hand hand = createGenericHand();

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
		Hand hand = createGenericHand();
		
		addBidsForGivenCountLastPlayerIsWinner(hand, 4, 30);
		
		assertTrue(hand.biddingIsOver());		
	}
	
	@Test
	public void bidIsNotOverAfterThreeBids() {
		Hand hand = createGenericHand();
		
		addBidsForGivenCountLastPlayerIsWinner(hand, 3, 30);
		
		assertFalse(hand.biddingIsOver());		
	}
	
	@Test
	public void secondPlayerIsNextBidderAfterFirstBid() {
		Hand hand = createGenericHand();
		Player player = new Player("Player 1");
		hand.addBid(new Bid(player, 30));
		
		assertEquals(hand.getPlayers()[1], hand.getNextBidder());
	}

	@Test
	public void testBiddingHandWinner() {
		Player[] players = PlayerTest.createFourGenericPlayers();
		Team[] teams = new Team[2];
		teams[0] = new Team(players[0], players[2]);
		teams[1] = new Team(players[1], players[3]);
		
		Hand hand = new Hand(teams, players);
		addBidsForGivenCountLastPlayerIsWinner(hand, 1, 30);
		Trick trick = hand.startNewTrick(players[0]);
		trick.playDomino(new PlayedDomino(players[0], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[1], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[2], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[3], new Domino(0,5)));
		hand.completeTrick();
		trick = hand.startNewTrick(players[0]);
		trick.playDomino(new PlayedDomino(players[0], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[1], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[2], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[3], new Domino(0,5)));
		hand.completeTrick();
		trick = hand.startNewTrick(players[0]);
		trick.playDomino(new PlayedDomino(players[0], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[1], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[2], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[3], new Domino(0,5)));
		hand.completeTrick();
		assertEquals(teams[0], hand.getHandWinner());
	}

	@Test
	public void testSettingHandWinner() {
		Player[] players = PlayerTest.createFourGenericPlayers();
		Team[] teams = new Team[2];
		teams[0] = new Team(players[0], players[2]);
		teams[1] = new Team(players[1], players[3]);
		
		Hand hand = new Hand(teams, players);
		addBidsForGivenCountLastPlayerIsWinner(hand, 1, 30);
		hand.setTrump(5);
		Trick trick = hand.startNewTrick(players[0]);
		trick.playDomino(new PlayedDomino(players[0], new Domino(0,5)));
		trick.playDomino(new PlayedDomino(players[1], new Domino(5,5)));
		trick.playDomino(new PlayedDomino(players[2], new Domino(5,4)));
		trick.playDomino(new PlayedDomino(players[3], new Domino(6,4)));
		
		assertEquals(players[1], trick.getWinningPlayer());
		
		hand.completeTrick();
		assertEquals(teams[1], hand.getHandWinner());
	}
}
