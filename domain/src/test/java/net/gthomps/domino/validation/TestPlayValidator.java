package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.Domino;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.validation.PlayValidator;

public class TestPlayValidator implements PlayValidator {

	@Override
	public ArrayList<String> canPlay(GameState currentState, Player player,	Domino ledDomino, Domino playedDomino, int trump) {
		return new ArrayList<String>();
	}

	@Override
	public ArrayList<String> canDiscard(GameState currentState, Player player,
			Domino discardedDomino) {
		return new ArrayList<String>();
	}

}
