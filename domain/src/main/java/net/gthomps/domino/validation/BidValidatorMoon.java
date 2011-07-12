package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.Bid;
import net.gthomps.domino.rules.Rules;
import net.gthomps.domino.rules.ShootTheMoonRules;

public class BidValidatorMoon extends BidValidatorBase {
	private static final Rules rules = new ShootTheMoonRules();

	public void checkForMaximumBid(ArrayList<String> messages, Bid bid, Bid winningBid) {
		if (bid.getBid() > 7)
			messages.add(String.format("Bid of %s is too high.  Should be 7 or lower.", bid.getBid()));
	}

	@Override
	public Rules getRules() {
		return rules;
	}
}
