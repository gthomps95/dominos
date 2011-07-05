package net.gthomps.tx42.ai;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public interface BidMaker {
	
	// TODO make smarter bids
	// TODO select smarter trumps
	
	Bid makeBid(Game game, Player player);
	int selectTrump(Game game, Player player);
}
