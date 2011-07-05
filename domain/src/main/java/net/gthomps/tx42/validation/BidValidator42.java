package net.gthomps.tx42.validation;

import java.util.ArrayList;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.GameState.State;
import net.gthomps.tx42.Player;

public class BidValidator42 extends Validator42Base implements BidValidator {

	@Override
	public ArrayList<String> canBid(GameState currentState, Bid bid, Bid winningBid, ArrayList<Bid> bids) {
		ArrayList<String> messages = new ArrayList<String>();
		
		checkCurrentState(messages, currentState, State.Bidding);
		checkNextPlayer(messages, currentState, bid.getPlayer());
		checkForMinimumBid(messages, bid, winningBid);
		checkForMaximumBid(messages, bid, winningBid);
		checkForForceBid(messages, bid, winningBid, bids);

		return messages;
	}
	
	public void checkForForceBid(ArrayList<String> messages, Bid bid, Bid winningBid, ArrayList<Bid> bids) {
		if (bids.size() == 3 && winningBid == null && bid.getBid() == 0) 
			messages.add("Last bidder cannot pass if no bid");
	}

	public void checkForMinimumBid(ArrayList<String> messages, Bid bid, Bid winningBid) {
		if (bid.getBid() == Bid.PASS)
			return;
		
		if (winningBid == null) {
			if (bid.getBid() < 30)
				messages.add(String.format("Bid of %s is too low.  Should be 30 or higher.", bid.getBid()));
		} else {
			if (bid.getBid() <= winningBid.getBid())
				messages.add(String.format("Bid of %s is too low.  Should be higher than %s.", bid.getBid(), winningBid.getBid()));
		}
	}
	
	public void checkForMaximumBid(ArrayList<String> messages, Bid bid, Bid winningBid) {
		if (bid.getBid() > 42 && (bid.getBid() % 42 != 0))
			messages.add(String.format("Bid of %s is invalid.  Should be multiple of 42.", bid.getBid()));
		
		if (winningBid == null) {
			if (bid.getBid() > 84)
				messages.add(String.format("Bid of %s is too high.  Should be 84 or lower.", bid.getBid()));
		} else {
			if (winningBid.getBid() <= 42 && bid.getBid() > 84 )
				messages.add(String.format("Bid of %s is too high.  Should be 84 or lower.", bid.getBid()));
		}
	}

	@Override
	public ArrayList<String> canSetTrump(GameState currentState, Player player,	int suit) {
		ArrayList<String> messages = new ArrayList<String>();
		
		checkCurrentState(messages, currentState, State.SettingTrump);
		checkNextPlayer(messages, currentState, player);
		checkMinimumTrump(messages, suit);
		checkMaximumTrump(messages, suit);
		
		return messages;
	}

	private void checkMinimumTrump(ArrayList<String> messages, int suit) {
		if (suit < 0)
			messages.add(String.format("Trump of %d is too low", suit));
	}

	private void checkMaximumTrump(ArrayList<String> messages, int suit) {
		if (suit > 6)
			messages.add(String.format("Trump of %d is too high", suit));
	}
	
}
