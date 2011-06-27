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
		short high = 4;
		short low = 2;
		Domino domino = new Domino(high, low);
		
		assertEquals(high, domino.getHighSide());		
	}

	@Test
	public void testNewDominoHasCorrectLowSide() {
		short high = 4;
		short low = 2;
		Domino domino = new Domino(high, low);
		
		assertEquals(low, domino.getLowSide());		
	}

	@Test
	public void testNewDominoIsDouble() {
		short high = 4;
		short low = 4;
		Domino domino = new Domino(high, low);
		
		assertTrue(domino.getIsDouble());		
	}
	
	@Test 
	public void testDominoToString() {
		short high = 4;
		short low = 2;
		
		Domino d = new Domino(high, low);
		assertEquals("4:2", d.toString());
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testMaxValueCheck() {
		short tooHigh = 7;
		short low = 0;
		
		exception.expect(RuntimeException.class);
		new Domino(tooHigh, low);
	}

	@Test
	public void testMinValueCheck() {
		short high = 6;
		short tooLow = -1;
		
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

}
