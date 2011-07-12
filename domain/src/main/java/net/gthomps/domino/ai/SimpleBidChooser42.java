package net.gthomps.domino.ai;

import net.gthomps.domino.Bid;
import net.gthomps.domino.Game;
import net.gthomps.domino.Player;

public class SimpleBidChooser42 implements PlayChooser<Bid> {

	@Override
	public Bid choose(Game game, Player player) {
		
		// check for force bid
		if (game.getCurrentHand().getBids().size() == 3 && game.getCurrentHand().getWinningBid() == null)
			return new Bid(player, 30);
		
		return new Bid(player, Bid.PASS);
	}

}
