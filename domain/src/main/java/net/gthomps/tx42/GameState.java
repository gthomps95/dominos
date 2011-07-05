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
	
	public boolean checkState(State testState) {
		return getState().equals(testState);
	}
	
	public boolean checkNextPlayer(Player player) {
		return player.equals(getNextPlayer());
	}


}
