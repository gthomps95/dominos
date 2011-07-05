package net.gthomps.tx42.cli;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.GameService;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.ai.PlayChooser;

public class PlayMakeBid extends Play<Bid> {
	public PlayMakeBid(GameService service, PlayChooser<Bid> chooser, HumanResponseGetter<Bid> getter) {
		super(service, chooser, getter);
	}

	@Override
	public GameState playObject(GameState state, Bid bid) {
		return service.placeBid(bid);
	}



}
