package net.gthomps.tx42;

import java.util.ArrayList;

public class Player {
	private String _name;
	private ArrayList<Domino> _dominosInHand;
	
	public Player (String name) {
		_name = name;		
	}
	
	public String toString() {
		return _name;
	}
	
	public ArrayList<Domino> getDominosInHand() {
		return _dominosInHand;
	}

}
