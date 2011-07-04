package net.gthomps.tx42;

import java.util.ArrayList;

public class Game {
	private Player[] players;
	private Team[] teams;
	private Hand currentHand;
	private ArrayList<Hand> playedHands = new ArrayList<Hand>();
	
	public Game(Player[] players) {
		this.players = players;
		teams = new Team[2];
		teams[0] = new Team(players[0], players[2]);
		teams[1] = new Team(players[1], players[3]);
	}
	
	public Team getTeam1() {
		return teams[0];
	}
	
	public Team getTeam2() {
		return teams[1];
	}
	
	public Team getTeamForPlayer(Player player) {
		return Team.getTeam(teams, player);
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Hand getCurrentHand() {
		return currentHand;
	}
	
	public Hand startNewHand() {
		return currentHand = new Hand(getHandOrderedPlayers());		
	}
	
	public Player[] getHandOrderedPlayers() {
		Player[] handOrderedPlayers = new Player[4];
		
		for (int i = 0; i < 4; i++)
			handOrderedPlayers[i] = players[(i + playedHands.size()) % 4];
		
		return handOrderedPlayers;
	}

	public void completeHand() {
		if (currentHand != null) {
			playedHands.add(currentHand);
			
			currentHand.getHandWinner(teams).addToScore(currentHand.getWinningBid().getMarks());
		}
		
		currentHand = null;
	}

	public ArrayList<Hand> getPlayedHands() {
		return playedHands;
	}
	
	public Team getWinner() {
		if (teams[0].isWinner())
			return teams[0];
		
		if (teams[1].isWinner())
			return teams[1];
		
		return null;
	}

	public boolean hasWinner() {
		return teams[0].isWinner() || teams[1].isWinner();
	}
}
