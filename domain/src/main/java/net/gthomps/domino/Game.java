package net.gthomps.domino;

import java.util.ArrayList;

import net.gthomps.domino.rules.Rules;

public class Game {
	private Player[] players;
	private Team[] teams;
	private Hand currentHand;
	private ArrayList<Hand> playedHands = new ArrayList<Hand>();
	private Rules rules;
	
	public Game(Player[] players, Rules rules) {
		this.players = players;
		teams = rules.getTeams(players);
		this.rules = rules;
	}

	public Team getTeam(int index) {
		return teams[index];
	}

	public Team[] getOtherTeamsForPlayer(Player player) {
		return Team.getOtherTeams(teams, player);
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
		return currentHand = new Hand(teams, getHandOrderedPlayers(), rules);		
	}
	
	public Player[] getHandOrderedPlayers() {
		return Player.getReorderedPlayers(players, players[playedHands.size() % rules.getPlayerCount()]);
	}

	
	protected Hand completeHand() {
		if (currentHand != null) {
			currentHand.completeHand();
			playedHands.add(currentHand);
			
			rules.scoreHand(currentHand);
		}
		
		Hand hand = currentHand;
		currentHand = null;
		return hand;
	}

	public ArrayList<Hand> getPlayedHands() {
		return playedHands;
	}
	
	private boolean isWinner(Team team) {
		return team.getWinByForfiet() || team.getScore() >= rules.getWinningScore();
	}
	
	public Team getWinner() {
		for (Team t : teams) {
			if (isWinner(t))
				return t;
		}
			
		return null;
	}

	public boolean hasWinner() {
		boolean hasWinner = false;

		for (Team t : teams) {
			if (isWinner(t))
				hasWinner = true;
		}

		return hasWinner;
	}

	public Team[] getTeams() {
		return teams;
	}

	public Rules getRules() {
		return rules;
	}
}
