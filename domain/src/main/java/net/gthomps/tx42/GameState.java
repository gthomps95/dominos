package net.gthomps.tx42;

public class GameState {
	private State state;
	private Player nextPlayer;
	
	public enum State {
		Bidding,
		SettingTrump,
		Playing,
		Over		
	}
	
	public GameState(State state, Player nextPlayer) {
		this.state = state;
		this.nextPlayer = nextPlayer;
	}

	public State getState() {
		return state;
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}
	
	public String toString() {
		return String.format("%s is %s", nextPlayer.toString(), state.toString());
	}
	
	public String checkState(State testState) {
		if (!getState().equals(State.Bidding))
			return String.format("Current state is %s.  It is not %s", getState().toString(), State.Bidding.toString());
		
		return null;
	}
	
	public String checkNextPlayer(Player player) {
		if (!player.equals(getNextPlayer()))
			return String.format("Current player is %s.  It is not %s", getNextPlayer().toString(), player.toString());
		
		return null;
	}


}
