package net.gthomps.tx42.validation;

import java.util.ArrayList;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.validation.PlayValidator;

public class TestPlayValidator implements PlayValidator {

	@Override
	public ArrayList<String> canPlay(GameState currentState, Player player,	Domino ledDomino, Domino playedDomino, int trump) {
		return new ArrayList<String>();
	}

}
