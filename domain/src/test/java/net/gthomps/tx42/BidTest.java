package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;

public class BidTest {

	@Test
	public void enoughPointsToWin() {
		Player player = new Player("Player 1");
		Bid bid = new Bid(player, 32);
		assertTrue(bid.enoughPointsToWinBid(32));
		assertFalse(bid.enoughPointsToWinBid(31));
		
		bid = new Bid(player, 42);
		assertTrue(bid.enoughPointsToWinBid(42));
		assertFalse(bid.enoughPointsToWinBid(31));

		bid = new Bid(player, 84);
		assertTrue(bid.enoughPointsToWinBid(42));
		assertFalse(bid.enoughPointsToWinBid(31));

		bid = new Bid(player, 126);
		assertTrue(bid.enoughPointsToWinBid(42));
		assertFalse(bid.enoughPointsToWinBid(31));
	}

	@Test
	public void enoughPointsToSet() {
		Player player = new Player("Player 1");
		Bid bid = new Bid(player, 32);
		assertTrue(bid.enoughPointsToSetBid(11));
		assertFalse(bid.enoughPointsToSetBid(10));

		bid = new Bid(player, 30);
		assertTrue(bid.enoughPointsToSetBid(13));
		assertFalse(bid.enoughPointsToSetBid(12));
		
		bid = new Bid(player, 42);
		assertTrue(bid.enoughPointsToSetBid(1));
		assertFalse(bid.enoughPointsToSetBid(0));

		bid = new Bid(player, 84);
		assertTrue(bid.enoughPointsToSetBid(1));
		assertFalse(bid.enoughPointsToSetBid(0));

		bid = new Bid(player, 126);
		assertTrue(bid.enoughPointsToSetBid(1));
		assertFalse(bid.enoughPointsToSetBid(0));
	}

	@Test
	public void testMarks() {
		Player player = new Player("Player 1");

		Bid bid = new Bid(player, 30);
		assertEquals(1, bid.getMarks());

		bid = new Bid(player, 42);
		assertEquals(1, bid.getMarks());

		bid = new Bid(player, 84);
		assertEquals(2, bid.getMarks());

		bid = new Bid(player, 126);
		assertEquals(3, bid.getMarks());
	}
}
