package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.Bid;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.GameState.State;
import net.gthomps.domino.rules.Rules;

public abstract class BidValidatorBase extends ValidatorBase implements BidValidator {

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
	
	public abstract Rules getRules();
	
	public void checkForForceBid(ArrayList<String> messages, Bid bid, Bid winningBid, ArrayList<Bid> bids) {
		if ((bids.size() == getRules().getPlayerCount() - 1) && winningBid == null && bid.getBid() == 0) 
			messages.add("Last bidder cannot pass if no bid");
	}

	public void checkForMinimumBid(ArrayList<String> messages, Bid bid, Bid winningBid) {
		if (bid.getBid() == Bid.PASS)
			return;
		
		if (winningBid == null) {
			if (bid.getBid() < getRules().getMinimumBid())
				messages.add(String.format("Bid of %s is too low.  Should be %d or higher.", bid.getBid(), getRules().getMinimumBid()));
		} else {
			if (bid.getBid() <= winningBid.getBid())
				messages.add(String.format("Bid of %s is too low.  Should be higher than %s.", bid.getBid(), winningBid.getBid()));
		}
	}
	
	public abstract void checkForMaximumBid(ArrayList<String> messages, Bid bid, Bid winningBid);

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
