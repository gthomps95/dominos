package net.gthomps.tx42;

import java.util.ArrayList;

public class Trick {
	private final ArrayList<PlayedDomino> playedDominos = new ArrayList<PlayedDomino>();
	
	public ArrayList<PlayedDomino> getPlayedDominos() {
		return playedDominos;
	}

	public void playDomino(PlayedDomino playedDomino) {
		playedDominos.add(playedDomino);
	}
}
