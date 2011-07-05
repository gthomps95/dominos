package net.gthomps.tx42.cli;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.GameService;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.ai.PlayChooser;

public class PlayDomino extends Play<Domino> {
	public PlayDomino(GameService service, PlayChooser<Domino> chooser, HumanResponseGetter<Domino> getter) {
		super(service, chooser, getter);
	}

	@Override
	public GameState playObject(GameState state, Domino domino) {
		return service.playDomino(state.getNextPlayer(), domino);
	}
}
