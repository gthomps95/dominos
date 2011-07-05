package net.gthomps.tx42.cli;

import java.io.IOException;

import net.gthomps.tx42.GameService;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.ai.PlayChooser;

public abstract class Play<T> {
	protected GameService service;
	private PlayChooser<T> chooser;
	private HumanResponseGetter<T> getter;
	
	public Play (GameService service, PlayChooser<T> chooser, HumanResponseGetter<T> getter) {
		this.service = service;
		this.chooser = chooser;
		this.getter = getter;
	}
	
	public void outputStateMessages(GameState state) {
		for (String s : state.getMessages()) {
			System.out.println(s);
		}
		
		if (state.getNextPlayer() != null)
			System.out.println(String.format("%s turn for %s", state.getNextPlayer().toString(), state.getState().toString()));
	}
	
	public abstract GameState playObject(GameState state, T object);
	
	public GameState play(Player human, GameState state) throws IOException {
		T object;
		if (state.getNextPlayer().equals(human)) {
			object = getter.getResponseFromHuman(human);
		} else {
			object = chooser.choose(service.getGame(), state.getNextPlayer());
		}
		
		// if no object is played, player is forfeiting
		if (object == null)
			state = service.forfeit(state.getNextPlayer());
		else
			state = playObject(state, object);
		
		outputStateMessages(state);
		return state;
	}
}
