package net.gthomps.tx42.ai;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public interface BidMaker {
	Bid makeBid(Game game, Player player);
}
