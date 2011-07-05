package net.gthomps.tx42.validation;

import java.util.ArrayList;

import net.gthomps.tx42.GameState;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.GameState.State;

public abstract class Validator42Base {

	public void checkCurrentState(ArrayList<String> messages, GameState currentState, State state) {
		if (!currentState.checkState(state))
			messages.add(String.format("Current state is %s.  It is not %s", currentState.getState().toString(), State.Bidding.toString()));
	}
	
	public void checkNextPlayer(ArrayList<String> messages, GameState currentState, Player player) {
		if (!player.equals(currentState.getNextPlayer()))
			messages.add(String.format("Current player is %s.  It is not %s", currentState.getNextPlayer().toString(), player.toString()));
	}

}
