package net.gthomps.tx42;

public interface PlayValidator {

	String canBid(GameState currentState, Bid bid, Bid winningBid);

}
