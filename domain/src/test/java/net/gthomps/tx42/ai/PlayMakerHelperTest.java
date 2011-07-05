package net.gthomps.tx42.ai;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.gthomps.tx42.Domino;

import org.junit.Test;

public class PlayMakerHelperTest {
	DominoChooserHelper helper = new DominoChooserHelper();

	@Test
	public void notPreferCountReturnsNoCount() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		dominos.add(new Domino(5,5));
		dominos.add(new Domino(5,1));
		
		assertEquals(0, helper.chooseRandomDomino(dominos, false).getPointCount());
	}

	@Test
	public void preferCountReturnsCount() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		dominos.add(new Domino(5,5));
		dominos.add(new Domino(5,1));
		
		assertEquals(10, helper.chooseRandomDomino(dominos, true).getPointCount());
	}

}
