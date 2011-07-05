package net.gthomps.tx42.validation;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.validation.BidValidator42;

import org.junit.Test;

public class BidValidator42Test {
	private BidValidator42 bidValidator = new BidValidator42();
	private ArrayList<String> messages = new ArrayList<String>();
	private Player player = new Player("Player 1");
	
	@Test
	public void passBidIsValid() {
		Bid bid = new Bid(player, Bid.PASS);
		bidValidator.checkForMinimumBid(messages, bid, null);
		assertTrue(messages.isEmpty());
	}

	@Test
	public void bidIsTooLowNoWinningBid() {
		Bid bid = new Bid(player, 29);
		bidValidator.checkForMinimumBid(messages, bid, null);
		assertFalse(messages.isEmpty());
	}

	@Test
	public void bidIsMinimumNoWinningBid() {
		Bid bid = new Bid(player, 30);
		bidValidator.checkForMinimumBid(messages, bid, null);
		assertTrue(messages.isEmpty());
	}

	@Test
	public void bidIsTooLowWinningBid() {
		Bid bidWinner = new Bid(player, 30);
		Bid bid = new Bid(player, 30);
		bidValidator.checkForMinimumBid(messages, bid, bidWinner);
		assertFalse(messages.isEmpty());
	}

	@Test
	public void bidIsMinimumWinningBid() {
		Bid bidWinner = new Bid(player, 30);
		Bid bid = new Bid(player, 31);
		bidValidator.checkForMinimumBid(messages, bid, bidWinner);
		assertTrue(messages.isEmpty());
	}

	@Test
	public void bidIs42NoWinningBid() {
		Bid bid = new Bid(player, 42);
		bidValidator.checkForMaximumBid(messages, bid, null);
		assertTrue(messages.isEmpty());
	}
	
	@Test
	public void bidIs84NoWinningBid() {
		Bid bid = new Bid(player, 84);
		bidValidator.checkForMaximumBid(messages, bid, null);
		assertTrue(messages.isEmpty());
	}
	
	@Test
	public void bidIs126NoWinningBid() {
		Bid bid = new Bid(player, 126);
		bidValidator.checkForMaximumBid(messages, bid, null);
		assertFalse(messages.isEmpty());
	}
	
	@Test
	public void bidIs43NoWinningBid() {
		Bid bid = new Bid(player, 43);
		bidValidator.checkForMaximumBid(messages, bid, null);
		assertFalse(messages.isEmpty());
	}
	
	@Test
	public void bidIs85NoWinningBid() {
		Bid bid = new Bid(player, 85);
		bidValidator.checkForMaximumBid(messages, bid, null);
		assertFalse(messages.isEmpty());
	}
	
	@Test
	public void bidIsTooHighNoWinningBid() {
		Bid bid = new Bid(player, 43);
		bidValidator.checkForMaximumBid(messages, bid, null);
		assertFalse(messages.isEmpty());
	}
	
	@Test
	public void bidIs126WinningBidIs42() {
		Bid bid = new Bid(player, 126);
		bidValidator.checkForMaximumBid(messages, bid, new Bid(player, 42));
		assertFalse(messages.isEmpty());
	}
	
	@Test
	public void bidIs126WinningBidIs84() {
		Bid bid = new Bid(player, 126);
		bidValidator.checkForMaximumBid(messages, bid, new Bid(player, 84));
		assertTrue(messages.isEmpty());
	}
	
	@Test
	public void checkForForceBidFails() {
		ArrayList<Bid> bids = new ArrayList<Bid>();
		bids.add(new Bid(player, 0));
		bids.add(new Bid(player, 0));
		bids.add(new Bid(player, 0));
		Bid bid = new Bid(player, 0);
		
		bidValidator.checkForForceBid(messages, bid, null, bids);
		assertFalse(messages.isEmpty());		
	}
	
	@Test
	public void checkForForceBidPasses() {
		ArrayList<Bid> bids = new ArrayList<Bid>();
		bids.add(new Bid(player, 0));
		bids.add(new Bid(player, 0));
		bids.add(new Bid(player, 0));
		Bid bid = new Bid(player, 30);
		
		bidValidator.checkForForceBid(messages, bid, null, bids);
		assertTrue(messages.isEmpty());		
	}
	
}