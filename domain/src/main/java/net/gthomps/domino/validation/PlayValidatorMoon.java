package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.Domino;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.GameState.State;

public class PlayValidatorMoon extends PlayValidatorBase {

	@Override
	public ArrayList<String> canDiscard(GameState currentState, Player player, Domino discardedDomino) {
		ArrayList<String> messages = new ArrayList<String>();
		checkCurrentState(messages, currentState, State.DiscardDomino);
		checkNextPlayer(messages, currentState, player);

		checkDominoIsInHand(messages, player, discardedDomino);

		return messages;
	}
}
