package net.gthomps.domino;

import java.util.ArrayList;

public class GameState {
	private State state;
	private Player nextPlayer;
	private ArrayList<String> messages = new ArrayList<String>();
	
	public enum State {
		Bidding,
		SettingTrump,
		DiscardDomino,
		Playing,
		Over		
	}
	
	public GameState(State state, Player nextPlayer) {
		this(state, nextPlayer, (String) null);
	}

	public GameState(State state, Player nextPlayer, String message) {
		ArrayList<String> messages = new ArrayList<String>();
		
		if (message != null)
			messages.add(message);
		
		initialize(state, nextPlayer, messages);
	}
	
	private void initialize(State state, Player nextPlayer, ArrayList<String> messages) {
		this.state = state;
		this.nextPlayer = nextPlayer;
		
		if (messages == null)
			messages = new ArrayList<String>();
		
		this.messages = messages;
	}

	public GameState(State state, Player nextPlayer, ArrayList<String> messages) {
		initialize(state, nextPlayer, messages);
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

	public void addMessage(String message) {
		messages.add(message);
	}
	
	public ArrayList<String> getMessages() {
		return messages;
	}
}
