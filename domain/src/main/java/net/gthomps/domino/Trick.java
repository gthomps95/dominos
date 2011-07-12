package net.gthomps.domino;

import java.util.ArrayList;

public class Trick {
	private final ArrayList<PlayedDomino> playedDominos = new ArrayList<PlayedDomino>();
	private PlayedDomino winningPlayedDomino;
	
	private Player[] players;
	private int trump;
	private Domino ledDomino;
	
	public Trick(Player[] players, int trump) {
		this.players = players;
		this.trump = trump;
	}
	
	public ArrayList<PlayedDomino> getPlayedDominos() {
		return playedDominos;
	}

	public void playDomino(PlayedDomino playedDomino) {
		if (playedDominos.size() == 0) {
			ledDomino = playedDomino.getDomino();
			winningPlayedDomino = playedDomino;
		} else if (playedDomino.getDomino().beats(getLedDomino(), trump, winningPlayedDomino.getDomino())) {
			winningPlayedDomino = playedDomino;			
		}
				
		playedDominos.add(playedDomino);
	}

	public boolean isOver() {
		return playedDominos.size() == players.length;
	}
	
	public Player getNextPlayer() {
		return players[playedDominos.size()];
	}
	
	public Player getWinningPlayer() {
		return winningPlayedDomino == null ? null : winningPlayedDomino.getPlayer();
	}

	public Player[] getPlayers() {
		return players;
	}
	
	public String toString() {
		return String.format("Trick - %d is trump, %d dominos have been played", trump, playedDominos.size());
	}

	public int getTrump() {
		return trump;
	}

	public Domino getLedDomino() {
		return ledDomino;
	}
}
