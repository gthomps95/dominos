package net.gthomps.tx42.ai;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public class SimpleBidChooser implements PlayChooser<Bid> {

	@Override
	public Bid choose(Game game, Player player) {
		
		// check for force bid
		if (game.getCurrentHand().getBids().size() == 3 && game.getCurrentHand().getWinningBid() == null)
			return new Bid(player, 30);
		
		return new Bid(player, Bid.PASS);
	}

}
