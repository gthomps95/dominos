package net.gthomps.tx42;

import org.apache.commons.lang.ArrayUtils;

public class Team {
	private Player[] players = new Player[2];
	private int score = 0;
	private boolean winByForfiet = false;
	
	public Team(Player player1, Player player2) {
		players[0] = player1;
		players[1] = player2;
	}
	
	public Player getPlayer1() {
		return players[0];
	}

	public Player getPlayer2() {
		return players[1];
	}

	public boolean containsPlayer(Player player) {
		return ArrayUtils.contains(players, player);
	}
	
	protected void addToScore(int marks) {
		score += marks;
	}
	
	public boolean isWinner() {
		return winByForfiet || score >= 7;
	}
	
	public int getScore() {
		return score;
	}

	public static Team getTeam(Team[] teams, Player player) {
		if (teams[0].containsPlayer(player))
			return teams[0];
		
		if (teams[1].containsPlayer(player))
			return teams[1];		
		
		return null;
	}

	public static Team getOtherTeam(Team[] teams, Player player) {
		if (teams[0].containsPlayer(player))
			return teams[1];
		
		if (teams[1].containsPlayer(player))
			return teams[0];		
		
		return null;
	}
	
	public static Team[] createTeams(Player[] players) {
		Team[] teams = new Team[2];
		teams[0] = new Team(players[0], players[2]);
		teams[1] = new Team(players[1], players[3]);
		
		return teams;
	}
	
	public String toString() {
		return String.format("%s and %s have %d marks", players[0].toString(), players[1].toString(), score);
	}

	protected void winByForfiet() {
		winByForfiet = true;
	}
}
