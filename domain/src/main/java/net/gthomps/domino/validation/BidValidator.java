package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.Bid;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;

public interface BidValidator {

	ArrayList<String> canSetTrump(GameState currentState, Player player, int suit);
	ArrayList<String> canBid(GameState currentState, Bid bid, Bid winningBid, ArrayList<Bid> bids);

}
