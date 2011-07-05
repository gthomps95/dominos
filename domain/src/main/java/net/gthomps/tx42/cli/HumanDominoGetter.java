package net.gthomps.tx42.cli;


import net.gthomps.tx42.Domino;
import net.gthomps.tx42.Player;

public class HumanDominoGetter extends HumanResponseGetter<Domino> {
	public void showPrompt() {
		System.out.print("Play domino:  ");
	}

	public Domino getResponseResult(Integer index, Player human) {
		return human.getDominosInHand().get(index.intValue() - 1);
	}

	public String getNoun() {
		return "domino";
	}
	
	public boolean checkIndex(int index, Player human) {
		return index < 0 || index > human.getDominosInHand().size() + 1;		
	}
}