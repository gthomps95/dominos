package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.Bid;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.validation.BidValidator;

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
