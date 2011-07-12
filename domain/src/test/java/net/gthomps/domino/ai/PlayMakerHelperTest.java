package net.gthomps.domino.ai;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.gthomps.domino.Domino;
import net.gthomps.domino.ai.DominoChooserHelper;
import net.gthomps.domino.rules.Texas42Rules;

import org.junit.Test;

public class PlayMakerHelperTest {
	private static final Texas42Rules rules = new Texas42Rules();
	DominoChooserHelper helper = new DominoChooserHelper();

	@Test
	public void notPreferCountReturnsNoCount() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		dominos.add(new Domino(5,5));
		dominos.add(new Domino(5,1));
		
		assertEquals(0, rules.getDominoPointCount(helper.chooseRandomDomino(dominos, false)));
	}

	@Test
	public void preferCountReturnsCount() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		dominos.add(new Domino(5,5));
		dominos.add(new Domino(5,1));
		
		assertEquals(10, rules.getDominoPointCount(helper.chooseRandomDomino(dominos, true)));
	}

}
