package net.gthomps.domino;

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
	
	public String toString() {
		return String.format("%s bid %d", player.toString(), bid);
	}
}
