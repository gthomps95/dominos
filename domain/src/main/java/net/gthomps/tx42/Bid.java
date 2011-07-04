package net.gthomps.tx42;

public class Bid {
	public static final int PASS = 0;
	
	private int bid;
	private Player player;
	
	public int getBid() {
		return bid;
	}

	public Player getPlayer() {
		return player;
	}

	public Bid (Player player, int bid) {
		this.player = player;
		this.bid = bid;		
	}
	
	public int getMarks() {
		if ( bid < 42 )
			return 1;
		else 
			return bid / 42;
	}

	public boolean enoughPointsToWinBid(int points) {
		int bidPoints = bid;
		if ( bid > 42 )
			bidPoints = 42;
		
		return points >= bidPoints;
	}
	
	public String toString() {
		return String.format("%s bid %d", player.toString(), bid);
	}
}
