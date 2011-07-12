package net.gthomps.domino;

import static org.junit.Assert.*;

import org.junit.Test;

import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.GameState.State;

public class GameStateTest {
	
	@Test
	public void checkCurrentStateSameState() {
		GameState state = new GameState(State.Bidding, null);
		
		assertTrue(state.checkState(State.Bidding));
	}

	@Test
	public void checkCurrentStateDifferentState() {
		GameState state = new GameState(State.Bidding, null);
		
		assertFalse(state.checkState(State.SettingTrump));
	}
	
	@Test
	public void checkCurrentStateNextPlayerMatches() {
		Player player = new Player("Player 1");
		GameState state = new GameState(null, player);
		
		assertTrue(state.checkNextPlayer(player));
	}

	@Test
	public void checkCurrentStateNextPlayerDoesNotMatch() {
		Player player = new Player("Player 1");
		GameState state = new GameState(null, player);
		
		assertFalse(state.checkNextPlayer(new Player("Player 2")));
	}

}
