package net.gthomps.domino.app;

import net.gthomps.domino.GameService;
import net.gthomps.domino.GameState;

public interface Play {
	GameState playObject(GameService service, GameState state, Object object);
}
