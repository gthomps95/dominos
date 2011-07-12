package net.gthomps.domino.cli;

import net.gthomps.domino.GameState;
import net.gthomps.domino.app.MessageShower;

public class SystemOutMessageShower implements MessageShower {
	public void outputStateMessages(GameState state) {
		for (String s : state.getMessages()) {
			System.out.println(s);
		}
		
		if (state.getNextPlayer() != null)
			System.out.println(String.format("%s turn for %s", state.getNextPlayer().toString(), state.getState().toString()));
	}
}
