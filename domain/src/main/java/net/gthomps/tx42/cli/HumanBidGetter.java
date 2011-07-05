package net.gthomps.tx42.cli;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.Player;

public class HumanBidGetter extends HumanResponseGetter<Bid> {
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
