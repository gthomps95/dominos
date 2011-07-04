package net.gthomps.tx42;

import net.gthomps.tx42.GameState.State;

public class GameService {
	private Game game;

	
	public GameState createNewGame() {
		Player[] players = Player.createFourGenericPlayers();
		game = new Game(players);
		Hand hand = game.startNewHand();
		hand.dealDominos(Domino.getFullDominoSet(), players);
		
		return new GameState(State.Bidding, players[0]);
	}

	public GameState placeBid(Bid bid) {
		game.getCurrentHand().addBid(bid);
		
		if (!game.getCurrentHand().biddingIsOver())
			return new GameState(State.Bidding, game.getCurrentHand().getNextBidder());

		Player bidWinner = game.getCurrentHand().getWinningBid().getPlayer();
		return new GameState(State.SettingTrump, bidWinner);
	}
	
	public GameState setTrump(int suit) {
		game.getCurrentHand().setTrump(suit);
		Player bidWinner = game.getCurrentHand().getWinningBid().getPlayer();
		game.getCurrentHand().startNewTrick(bidWinner);
		return new GameState(State.Playing, bidWinner);
	}
	
	public GameState playDomino(Player player, Domino domino) {
		game.getCurrentHand().getCurrentTrick().playDomino(player.playDomino(domino));		
	
		// if trick is not over, keep playing
		if (!game.getCurrentHand().getCurrentTrick().isOver())
			return new GameState(State.Playing, game.getCurrentHand().getCurrentTrick().getNextPlayer());
		
		// if trick is over, mark it completed
		Player trickWinner = game.getCurrentHand().completeTrick();
			
		// if hand is not over, start new trick and keep playing
		if (!game.getCurrentHand().isOver()) {
			game.getCurrentHand().startNewTrick(trickWinner);

			return new GameState(State.Playing, trickWinner);
		}

		// complete hand
		game.completeHand();
				
		// if game is not over start new hand and keep playing
		if (!game.hasWinner()) { 
			Hand hand = game.startNewHand();
			hand.dealDominos(Domino.getFullDominoSet(), game.getPlayers());
			
			return new GameState(State.Bidding, hand.getNextBidder());
		}
		
		return new GameState(State.Over, null);
	}
	
	public GameState forfiet(Player player) {
		return new GameState(State.Over, null);
	}

	public Game getGame() {
		return game;
	}
}
