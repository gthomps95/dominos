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

	public Team getOtherTeamForPlayer(Player player) {
		return Team.getOtherTeam(teams, player);
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
	
	protected Hand startNewHand() {
		return currentHand = new Hand(teams, getHandOrderedPlayers());		
	}
	
	public Player[] getHandOrderedPlayers() {
		return Player.getReorderedPlayers(players, players[playedHands.size() % 4]);
	}

	protected Hand completeHand() {
		if (currentHand != null) {
			currentHand.completeHand();
			playedHands.add(currentHand);
			
			if (currentHand.getHandWinner() != null)
				currentHand.getHandWinner().addToScore(currentHand.getWinningBid().getMarks());
		}
		
		Hand hand = currentHand;
		currentHand = null;
		return hand;
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
