package net.gthomps.domino.cli;

import net.gthomps.domino.Player;

public class HumanTrumpChooser extends HumanChooser<Integer> {
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
