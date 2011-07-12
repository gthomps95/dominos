package net.gthomps.domino;

import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

public class Team {
	private Player[] players;
	private int score = 0;
	private boolean winByForfiet = false;
	
	public Team(Player[] players) {
		this.players = players;
	}
	
	public Team(Player player1, Player player2) {
		players = new Player[2];
		players[0] = player1;
		players[1] = player2;
	}
	
	public Team(Player player) {
		players = new Player[1];
		players[0] = player;
	}
	
	public Player getPlayer(int index) {
		return players[index];
	}

	public boolean containsPlayer(Player player) {
		return ArrayUtils.contains(players, player);
	}
	
	public void addToScore(int marks) {
		score += marks;
	}
	
	public int getScore() {
		return score;
	}

	public static Team getTeam(Team[] teams, Player player) {
		for (Team t : teams) {
			if (t.containsPlayer(player))
				return t;
		}
		
		return null;
	}

	public static Team[] getOtherTeams(Team[] teams, Player player) {
		ArrayList<Team> result = new ArrayList<Team>();
		
		for (Team t : teams) {
			if (!t.containsPlayer(player))
				result.add(t);
		}
		
		return result.toArray(new Team[result.size()]);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Team ");
		int count = 0;
		
		for (Player p : players) {
			sb.append(p.toString());
			if (count++ < players.length - 1)
				sb.append(" and ");
		}
			
		return sb.toString();
	}

	protected void winByForfiet() {
		winByForfiet = true;
	}
	
	public boolean getWinByForfiet() {
		return winByForfiet;
	}
}
