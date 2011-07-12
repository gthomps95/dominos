package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.Bid;
import net.gthomps.domino.rules.Rules;
import net.gthomps.domino.rules.Texas42Rules;

public class BidValidator42 extends BidValidatorBase {
	private static final Rules rules = new Texas42Rules();

	@Override
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
	public Rules getRules() {
		return rules;
	}
	
}
