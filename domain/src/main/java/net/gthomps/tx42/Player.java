package net.gthomps.tx42;

import java.util.ArrayList;

public class Player {
	private String name;
	private ArrayList<Domino> dominosInHand;
	
	public Player (String name) {
		this.name = name;
		dominosInHand = new ArrayList<Domino>();
	}
	
	public String toString() {
		return name;
	}
	
	public ArrayList<Domino> getDominosInHand() {
		return dominosInHand;
	}
	
	public void addDominoToHand(Domino domino) {
		dominosInHand.add(domino);
	}
		
	public void addDominosToHand(ArrayList<Domino> dominos) {
		dominosInHand.addAll(dominos);
	}
	
	public PlayedDomino playDomino(Domino domino) {
		dominosInHand.remove(domino);

		return new PlayedDomino(this, domino);
	}
	
	public static Player[] createFourGenericPlayers() {
		Player[] players = new Player[4];
		players[0] = new Player("Player 1");
		players[1] = new Player("Player 2");
		players[2] = new Player("Player 3");
		players[3] = new Player("Player 4");
		
		return players;
	}
	
}
