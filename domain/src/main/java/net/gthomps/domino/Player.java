package net.gthomps.domino;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.ArrayUtils;

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
	
	protected void addDominoToHand(Domino domino) {
		dominosInHand.add(domino);
		Collections.sort(dominosInHand);
	}
		
	protected void addDominosToHand(ArrayList<Domino> dominos) {
		dominosInHand.addAll(dominos);
		Collections.sort(dominosInHand);
	}
	
	public void addDominosToHandForTesting(ArrayList<Domino> dominos) {
		addDominosToHand(dominos);
	}
	
	protected PlayedDomino playDomino(Domino domino) {
		dominosInHand.remove(domino);

		return new PlayedDomino(this, domino);
	}
	
	public static Player[] getReorderedPlayers(Player[] players, Player firstPlayer) {
		int sizeOfPlayers = players.length;
		Player[] orderedPlayers = new Player[sizeOfPlayers];

		int playerIndex = ArrayUtils.indexOf(players, firstPlayer);
		
		for (int i = 0; i < sizeOfPlayers; i++)
			orderedPlayers[i] = players[(i + playerIndex) % sizeOfPlayers];
		
		return orderedPlayers;
	}

	public void clearDominosInHand() {
		dominosInHand = new ArrayList<Domino>();
	}
}
