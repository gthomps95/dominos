package net.gthomps.domino.app;

import net.gthomps.domino.Domino;
import net.gthomps.domino.GameService;
import net.gthomps.domino.GameState;

public class PlayDiscard implements Play {

	public GameState playObject(GameService service, GameState state, Object domino) {
		return service.discardDomino(state.getNextPlayer(), (Domino) domino);
	}
}
