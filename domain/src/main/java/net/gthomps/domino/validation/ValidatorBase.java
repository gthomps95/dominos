package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.GameState.State;

public abstract class ValidatorBase {

	public void checkCurrentState(ArrayList<String> messages, GameState currentState, State state) {
		if (!currentState.checkState(state))
			messages.add(String.format("Current state is %s.  It is not %s", currentState.getState().toString(), state.toString()));
	}
	
	public void checkNextPlayer(ArrayList<String> messages, GameState currentState, Player player) {
		if (!player.equals(currentState.getNextPlayer()))
			messages.add(String.format("Current player is %s.  It is not %s", currentState.getNextPlayer().toString(), player.toString()));
	}

}
