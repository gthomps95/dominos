package net.gthomps.domino.ai;

import net.gthomps.domino.Bid;
import net.gthomps.domino.Game;
import net.gthomps.domino.Player;

public class SimpleBidChooserMoon implements PlayChooser<Bid> {

	@Override
	public Bid choose(Game game, Player player) {
		
		// check for force bid
		if (game.getCurrentHand().getBids().size() == 2 && game.getCurrentHand().getWinningBid() == null)
			return new Bid(player, 4);
		
		return new Bid(player, Bid.PASS);
	}

}
