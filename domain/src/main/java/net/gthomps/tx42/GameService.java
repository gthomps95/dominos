package net.gthomps.tx42;

import java.util.ArrayList;

import net.gthomps.tx42.GameState.State;
import net.gthomps.tx42.validation.BidValidator;
import net.gthomps.tx42.validation.PlayValidator;
import net.gthomps.tx42.validation.ValidatorException;

public class GameService {
	private Game game;
	private BidValidator bidValidator;
	private PlayValidator playValidator;
	private GameState currentState;
	
	public GameState createNewGame(Player[] players, BidValidator bidValidator, PlayValidator playValidator) {
		this.bidValidator = bidValidator;
		this.playValidator = playValidator;
		game = new Game(players);
		Hand hand = game.startNewHand();
		hand.dealDominos(Domino.getFullDominoSet(), players);
		
		return currentState = new GameState(State.Bidding, players[0]);
	}

	public GameState placeBid(Bid bid) throws ValidatorException {
		ArrayList<String> messages = bidValidator.canBid(currentState, bid, game.getCurrentHand().getWinningBid(), game.getCurrentHand().getBids());
		if (!messages.isEmpty()) 
			throw new ValidatorException(messages);

		game.getCurrentHand().addBid(bid);
		
		if (!game.getCurrentHand().biddingIsOver())
			return currentState = new GameState(State.Bidding, game.getCurrentHand().getNextBidder());

		Player bidWinner = game.getCurrentHand().getWinningBid().getPlayer();
		return currentState = new GameState(State.SettingTrump, bidWinner);
	}
	
	public GameState setTrump(int suit) {
		game.getCurrentHand().setTrump(suit);
		Player bidWinner = game.getCurrentHand().getWinningBid().getPlayer();
		game.getCurrentHand().startNewTrick(bidWinner);
		return currentState = new GameState(State.Playing, bidWinner);
	}
	
	public GameState playDomino(Player player, Domino domino) throws ValidatorException {
		ArrayList<String> messages = playValidator.canPlay(currentState, player, game.getCurrentHand().getCurrentTrick().getLedDomino(), domino, game.getCurrentHand().getTrump());
		if (!messages.isEmpty()) 
			throw new ValidatorException(messages);

		game.getCurrentHand().getCurrentTrick().playDomino(player.playDomino(domino));		
	
		// if trick is not over, keep playing
		if (!game.getCurrentHand().getCurrentTrick().isOver())
			return currentState = new GameState(State.Playing, game.getCurrentHand().getCurrentTrick().getNextPlayer());
		
		// if trick is over, mark it completed
		Player trickWinner = game.getCurrentHand().completeTrick();
			
		// if hand is not over, start new trick and keep playing
		if (!game.getCurrentHand().isOver()) {
			game.getCurrentHand().startNewTrick(trickWinner);

			return currentState = new GameState(State.Playing, trickWinner);
		}

		// complete hand
		game.completeHand();
				
		// if game is not over start new hand and keep playing
		if (!game.hasWinner()) { 
			Hand hand = game.startNewHand();
			hand.dealDominos(Domino.getFullDominoSet(), game.getPlayers());
			
			return currentState = new GameState(State.Bidding, hand.getNextBidder());
		}
		
		return currentState = new GameState(State.Over, null);
	}
	
	public GameState forfiet(Player player) {
		Team team = getGame().getOtherTeamForPlayer(player);
		team.winByForfiet();
		return currentState = new GameState(State.Over, null);
	}

	public Game getGame() {
		return game;
	}
	
	public BidValidator getPlayValidator() {
		return bidValidator;
	}

	public GameState getCurrentState() {
		return currentState;
	}
}
