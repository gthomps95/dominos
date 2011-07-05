package net.gthomps.tx42.cli;

import net.gthomps.tx42.GameService;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.ai.PlayChooser;

public class PlaySetTrump extends Play<Integer> {
	public PlaySetTrump(GameService service, PlayChooser<Integer> chooser, HumanResponseGetter<Integer> getter) {
		super(service, chooser, getter);
	}
	
	@Override
	public GameState playObject(GameState state, Integer trump) {
		return service.setTrump(state.getNextPlayer(), trump.intValue());
	}
}
