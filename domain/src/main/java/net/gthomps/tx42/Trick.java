package net.gthomps.tx42;

import java.util.ArrayList;

public class Trick {
	private final ArrayList<PlayedDomino> playedDominos = new ArrayList<PlayedDomino>();
	private PlayedDomino winningPlayedDomino;
	
	private Player[] players;
	private int trump;
	private Domino ledDomino;
	
	protected Trick(Player[] players, int trump) {
		this.players = players;
		this.trump = trump;
	}
	
	public ArrayList<PlayedDomino> getPlayedDominos() {
		return playedDominos;
	}

	protected void playDomino(PlayedDomino playedDomino) {
		if (playedDominos.size() == 0) {
			ledDomino = playedDomino.getDomino();
			winningPlayedDomino = playedDomino;
		} else if (playedDomino.getDomino().beats(trump, winningPlayedDomino.getDomino())) {
			winningPlayedDomino = playedDomino;			
		}
				
		playedDominos.add(playedDomino);
	}

	public boolean isOver() {
		return playedDominos.size() == 4;
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
	
	public int getPointCount() {
		int points = 1;
		for (PlayedDomino pd : playedDominos)
			points += pd.getDomino().getPointCount();
				
		return points;
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
