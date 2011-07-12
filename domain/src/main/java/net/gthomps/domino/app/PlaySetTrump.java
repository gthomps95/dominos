package net.gthomps.domino.app;

import net.gthomps.domino.GameService;
import net.gthomps.domino.GameState;

public class PlaySetTrump implements Play {

	public GameState playObject(GameService service, GameState state, Object trump) {
		return service.setTrump(state.getNextPlayer(), (Integer) trump);
	}
}
