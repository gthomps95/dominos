package net.gthomps.domino.cli;

import net.gthomps.domino.Bid;
import net.gthomps.domino.Player;

public class HumanBidChooser extends HumanChooser<Bid> {
	public void showPrompt() {
		System.out.print("Place bid:  ");
	}

	public Bid getResponseResult(Integer index, Player human) {
		return new Bid(human, index.intValue());
	}

	public String getNoun() {
		return "bid";
	}
	
	public boolean checkIndex(int index, Player human) {
		return index != 0 && index < 30;		
	}
}
