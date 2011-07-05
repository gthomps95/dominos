package net.gthomps.tx42;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.apache.commons.math.util.MathUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DominoTest {

	@Test
	public void testNewDominoHasCorrectHighSide() {
		int high = 4;
		int low = 2;
		Domino domino = new Domino(high, low);
		
		assertEquals(high, domino.getHighSide());		
	}

	@Test
	public void testNewDominoHasCorrectLowSide() {
		int high = 4;
		int low = 2;
		Domino domino = new Domino(high, low);
		
		assertEquals(low, domino.getLowSide());		
	}

	@Test
	public void testNewDominoIsDouble() {
		int high = 4;
		int low = 4;
		Domino domino = new Domino(high, low);
		
		assertTrue(domino.getIsDouble());		
	}
	
	@Test 
	public void testDominoToString() {
		int high = 4;
		int low = 2;
		
		Domino d = new Domino(high, low);
		assertEquals("4:2", d.toString());
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testMaxValueCheck() {
		int tooHigh = 7;
		int low = 0;
		
		exception.expect(RuntimeException.class);
		new Domino(tooHigh, low);
	}

	@Test
	public void testMinValueCheck() {
		int high = 6;
		int tooLow = -1;
		
		exception.expect(RuntimeException.class);
		new Domino(high, tooLow);
	}

	@Test
	public void fullSetDoesNotHaveDuplicates() {
		Domino[] dominos = Domino.getFullDominoSet();
		HashSet<String> testHolder = new HashSet<String>();
		
		for (Domino d : dominos) {
			assertFalse(testHolder.contains(d.toString()));
			testHolder.add(d.toString());			
		}
	}
	
	@Test
	public void fullSetHasCorrectCount() {
		Domino[] dominos = Domino.getFullDominoSet();
		
		//http://www.mathsisfun.com/combinatorics/combinations-permutations-calculator.html
		// n
		int rangeCount = Domino.MAX_SIDE_VALUE - Domino.MIN_SIDE_VALUE + 1;
		
		// r
		int sides = 2;
		
		// (n + r - 1)!/((r!)(n - 1)!)
		long expected = MathUtils.factorial(rangeCount + sides - 1)/(MathUtils.factorial(sides) * MathUtils.factorial(rangeCount - 1));
		
		assertEquals(expected, dominos.length);
	}
	
	@Test
	public void dominoBeatTest() {
		assertTrue((new Domino(4,2)).beats(0, new Domino(4,1)));
		assertTrue((new Domino(4,6)).beats(0, new Domino(4,3)));
		assertFalse((new Domino(4,5)).beats(0, new Domino(4,4)));
		assertFalse((new Domino(3,2)).beats(0, new Domino(4,1)));
		assertFalse((new Domino(4,6)).beats(0, new Domino(4,4)));
	}
	
	@Test
	public void trumpBeatsNonTrump() {
		assertTrue((new Domino(4,2).beats(2, new Domino(4,4))));
	}
	
	@Test
	public void nonTrumpDoesNotBeatTrump() {
		assertFalse((new Domino(4,4).beats(2, new Domino(4,2))));
	}
	
	@Test
	public void higherTrumpBeatsLowerTrump() {
		assertTrue((new Domino(4,2).beats(2, new Domino(3,2))));
		assertTrue((new Domino(6,2).beats(6, new Domino(6,1))));
		assertTrue((new Domino(6,3).beats(3, new Domino(3,1))));
		assertTrue((new Domino(3,2).beats(3, new Domino(3,1))));
	}
	
	@Test
	public void lowerTrumpDoesNotBeatHigherTrump() {
		assertFalse((new Domino(3,2).beats(2, new Domino(4,2))));
		assertFalse((new Domino(6,1).beats(6, new Domino(6,2))));
		assertFalse((new Domino(1,3).beats(3, new Domino(3,6))));
		assertFalse((new Domino(3,1).beats(3, new Domino(3,2))));
	}
	
	@Test
	public void countTest() {
		assertEquals(10, (new Domino(5,5)).getPointCount());
		assertEquals(10, (new Domino(6,4)).getPointCount());
		assertEquals(5, (new Domino(5,0)).getPointCount());
		assertEquals(5, (new Domino(4,1)).getPointCount());
		assertEquals(5, (new Domino(3,2)).getPointCount());
		assertEquals(0, (new Domino(6,3)).getPointCount());
		assertEquals(0, (new Domino(5,1)).getPointCount());
	}
	
	@Test
	public void followsSuitTest() {
		Domino ledDomino = new Domino(5,4);

		assertTrue((new Domino(3,5)).followsSuit(ledDomino, 0));
		assertTrue((new Domino(6,5)).followsSuit(ledDomino, 0));
		assertFalse((new Domino(4,3)).followsSuit(ledDomino, 0));
	}

	@Test
	public void followsSuitTrumpTest() {
		Domino ledDomino = new Domino(4,6);

		assertTrue((new Domino(3,4)).followsSuit(ledDomino, 4));
		assertTrue((new Domino(4,5)).followsSuit(ledDomino, 4));
		assertFalse((new Domino(5,3)).followsSuit(ledDomino, 4));
	}
	
	@Test
	public void testEquals() {
		assertTrue((new Domino(4,2)).equals(new Domino(2,4)));
		assertFalse((new Domino(4,2)).equals(new Domino(1,4)));
	}
}
