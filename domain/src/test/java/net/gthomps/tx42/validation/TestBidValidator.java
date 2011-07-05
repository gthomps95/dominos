package net.gthomps.tx42.validation;

import java.util.ArrayList;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.validation.BidValidator;

public class TestBidValidator implements BidValidator {

	@Override
	public ArrayList<String> canBid(GameState currentState, Bid bid, Bid winningBid, ArrayList<Bid> bids) {
		return new ArrayList<String>();
	}

	@Override
	public ArrayList<String> canSetTrump(GameState currentState, Player player, int suit) {
		return new ArrayList<String>();
	}

}
