package net.gthomps.tx42;

import net.gthomps.tx42.GameState.State;

public class PlayValidator42 implements PlayValidator {

	// TODO add tests for can bid
	// TODO add tests for game state check methods
	// TODO move messages back to this class
	
	@Override
	public String canBid(GameState currentState, Bid bid, Bid winningBid) {
		String msg = currentState.checkState(State.Bidding);
		if ( msg != null )
			return msg;
		
		msg = currentState.checkNextPlayer(bid.getPlayer());
		if ( msg != null )
			return msg;
		
		if (winningBid == null) {
			if (bid.getBid() < 30)
				return String.format("Bid of %s is too low.  Should be %s or higher.", bid.getBid(), 30);
		} else {
			if (bid.getBid() <= bid.getBid())
				return String.format("Bid of %s is too low.  Should be higher than %s.", bid.getBid(), winningBid.getBid());
		}
		
		return null;
	}
	
}
