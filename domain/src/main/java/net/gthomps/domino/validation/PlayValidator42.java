package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.Domino;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;

public class PlayValidator42 extends PlayValidatorBase {

	@Override
	public ArrayList<String> canDiscard(GameState currentState, Player player,
			Domino discardedDomino) {
		return new ArrayList<String>();
	}
}
