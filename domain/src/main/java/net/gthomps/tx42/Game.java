package net.gthomps.tx42;

import java.util.ArrayList;

public class Game {
	private Player[] players;
	private Hand currentHand;
	private ArrayList<Hand> playedHands = new ArrayList<Hand>();
	
	public Game(Player[] players) {
		this.players = players;
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Hand getCurrentHand() {
		return currentHand;
	}
	
	public Hand startNewHand() {
		return currentHand = new Hand();		
	}

	public void completeHand() {
		if (currentHand != null)
			playedHands.add(currentHand);
		
		currentHand = null;
	}

	public ArrayList<Hand> getPlayedHands() {
		return playedHands;
	}

	public boolean hasWinner() {
		return false;
	}
}
