package net.gthomps.tx42.validation;

import java.util.ArrayList;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.Player;

public interface BidValidator {

	ArrayList<String> canSetTrump(GameState currentState, Player player, int suit);
	ArrayList<String> canBid(GameState currentState, Bid bid, Bid winningBid, ArrayList<Bid> bids);

}
