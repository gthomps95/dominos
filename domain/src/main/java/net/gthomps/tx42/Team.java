package net.gthomps.tx42;

public class Team {
	private Player[] players = new Player[2];
	private int score = 0;
	
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
		return players[0].equals(player) || players[1].equals(player);
	}
	
	public void addToScore(int marks) {
		score += marks;
	}
	
	public boolean isWinner() {
		return score >= 7;
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
	
	public String toString() {
		return String.format("%s and %s have %d marks", players[0].toString(), players[1].toString(), score);
	}
}
