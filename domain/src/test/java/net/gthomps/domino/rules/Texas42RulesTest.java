package net.gthomps.domino.rules;

import static org.junit.Assert.*;

import java.util.HashSet;

import net.gthomps.domino.Bid;
import net.gthomps.domino.Domino;
import net.gthomps.domino.PlayedDomino;
import net.gthomps.domino.Player;
import net.gthomps.domino.PlayerTest;
import net.gthomps.domino.Trick;

import org.apache.commons.math.util.MathUtils;
import org.junit.Test;

public class Texas42RulesTest {
	private static final Texas42Rules rules = new Texas42Rules();

	@Test
	public void enoughPointsToWin() {
		Player player = new Player("Player 1");
		Bid bid = new Bid(player, 32);
		assertTrue(rules.enoughPointsToWinBid(bid, 32));
		assertFalse(rules.enoughPointsToWinBid(bid, 31));
		
		bid = new Bid(player, 42);
		assertTrue(rules.enoughPointsToWinBid(bid, 42));
		assertFalse(rules.enoughPointsToWinBid(bid, 31));

		bid = new Bid(player, 84);
		assertTrue(rules.enoughPointsToWinBid(bid, 42));
		assertFalse(rules.enoughPointsToWinBid(bid, 31));

		bid = new Bid(player, 126);
		assertTrue(rules.enoughPointsToWinBid(bid, 42));
		assertFalse(rules.enoughPointsToWinBid(bid, 31));
	}

	@Test
	public void enoughPointsToSet() {
		Player player = new Player("Player 1");
		Bid bid = new Bid(player, 32);
		assertTrue(rules.enoughPointsToSetBid(bid, 11));
		assertFalse(rules.enoughPointsToSetBid(bid, 10));

		bid = new Bid(player, 30);
		assertTrue(rules.enoughPointsToSetBid(bid, 13));
		assertFalse(rules.enoughPointsToSetBid(bid, 12));
		
		bid = new Bid(player, 42);
		assertTrue(rules.enoughPointsToSetBid(bid, 1));
		assertFalse(rules.enoughPointsToSetBid(bid, 0));

		bid = new Bid(player, 84);
		assertTrue(rules.enoughPointsToSetBid(bid, 1));
		assertFalse(rules.enoughPointsToSetBid(bid, 0));

		bid = new Bid(player, 126);
		assertTrue(rules.enoughPointsToSetBid(bid, 1));
		assertFalse(rules.enoughPointsToSetBid(bid, 0));
	}

	@Test
	public void testMarks() {
		Player player = new Player("Player 1");

		Bid bid = new Bid(player, 30);
		assertEquals(1, rules.getMarks(bid));

		bid = new Bid(player, 42);
		assertEquals(1, rules.getMarks(bid));

		bid = new Bid(player, 84);
		assertEquals(2, rules.getMarks(bid));

		bid = new Bid(player, 126);
		assertEquals(3, rules.getMarks(bid));
	}
	
	@Test
	public void countTest() {
		assertEquals(10, rules.getDominoPointCount(new Domino(5,5)));
		assertEquals(10, rules.getDominoPointCount(new Domino(6,4)));
		assertEquals(5, rules.getDominoPointCount(new Domino(5,0)));
		assertEquals(5, rules.getDominoPointCount(new Domino(4,1)));
		assertEquals(5, rules.getDominoPointCount(new Domino(3,2)));
		assertEquals(0, rules.getDominoPointCount(new Domino(6,3)));
		assertEquals(0, rules.getDominoPointCount(new Domino(5,1)));
	}
	
	@Test
	public void testTrickPoints() {
		Player[] players = PlayerTest.createFourGenericPlayers();
		Trick trick = new Trick(players, 0);
		
		trick.playDomino(new PlayedDomino(players[0], new Domino(5,5)));
		trick.playDomino(new PlayedDomino(players[1], new Domino(3,2)));
		trick.playDomino(new PlayedDomino(players[2], new Domino(0,0)));
		trick.playDomino(new PlayedDomino(players[3], new Domino(1,5)));

		assertEquals(16, rules.getPointCount(trick));
	}
	
	@Test
	public void fullSetDoesNotHaveDuplicates() {
		Domino[] dominos = rules.getFullDominoSet();
		HashSet<String> testHolder = new HashSet<String>();
		
		for (Domino d : dominos) {
			assertFalse(testHolder.contains(d.toString()));
			testHolder.add(d.toString());			
		}
	}
	
	@Test
	public void fullSetHasCorrectCount() {
		Domino[] dominos = rules.getFullDominoSet();
		
		//http://www.mathsisfun.com/combinatorics/combinations-permutations-calculator.html
		// n
		int rangeCount = Domino.MAX_SIDE_VALUE - Domino.MIN_SIDE_VALUE + 1;
		
		// r
		int sides = 2;
		
		// (n + r - 1)!/((r!)(n - 1)!)
		long expected = MathUtils.factorial(rangeCount + sides - 1)/(MathUtils.factorial(sides) * MathUtils.factorial(rangeCount - 1));
		
		assertEquals(expected, dominos.length);
	}
}
