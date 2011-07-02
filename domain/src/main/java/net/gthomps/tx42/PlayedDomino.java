package net.gthomps.tx42;

public class PlayedDomino {
	
	private Player player;
	private Domino domino;

	public PlayedDomino(Player player, Domino domino) {
		this.player = player;
		this.domino = domino;
	}

	public Player getPlayer() {
		return player;
	}

	public Domino getDomino() {
		return domino;
	}

}
