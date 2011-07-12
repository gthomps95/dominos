package net.gthomps.domino.app;

import net.gthomps.domino.Bid;
import net.gthomps.domino.GameService;
import net.gthomps.domino.GameState;

public class PlayMakeBid implements Play {

	public GameState playObject(GameService service, GameState state, Object bid) {
		return service.placeBid((Bid) bid);
	}
}
