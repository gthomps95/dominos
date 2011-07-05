package net.gthomps.tx42.ai;

import java.util.Random;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public class SimpleBidMaker implements BidMaker {

	@Override
	public Bid makeBid(Game game, Player player) {
		
		// check for force bid
		if (game.getCurrentHand().getBids().size() == 3 && game.getCurrentHand().getWinningBid() == null)
			return new Bid(player, 30);
		
		return new Bid(player, Bid.PASS);
	}

	@Override
	public int selectTrump(Game game, Player player) {
		return (new Random()).nextInt(7);
	}

}
