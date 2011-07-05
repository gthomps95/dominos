package net.gthomps.tx42.cli;

import net.gthomps.tx42.Player;

public class HumanTrumpGetter extends HumanResponseGetter<Integer> {
	public void showPrompt() {
		System.out.print("Select trump:  ");
	}

	public Integer getResponseResult(Integer index, Player human) {
		return index;
	}

	public String getNoun() {
		return "trump";
	}
	
	public boolean checkIndex(int index, Player human) {
		return index < 0 || index > 6;		
	}
}
