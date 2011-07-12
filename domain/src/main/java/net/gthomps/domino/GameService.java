package net.gthomps.domino;

import java.util.ArrayList;

import net.gthomps.domino.GameState.State;
import net.gthomps.domino.rules.Rules;
import net.gthomps.domino.validation.BidValidator;
import net.gthomps.domino.validation.PlayValidator;

public class GameService {
	
	// TODO combine play interface and game service
	
	// TODO add shoot the moon
	// TODO convert to objective c
	// TODO add gui
	// TODO add multi player
	// TODO improve AI
	// TODO add tutorial
	
	private Game game;
	private BidValidator bidValidator;
	private PlayValidator playValidator;
	private GameState currentState;
	private ArrayList<Domino> leftOverDominos;
	private boolean needToDiscard;
	
	public GameState createNewGame(Player[] players, BidValidator bidValidator, PlayValidator playValidator, Rules rules) {
		this.bidValidator = bidValidator;
		this.playValidator = playValidator;
		game = new Game(players, rules);
		Hand hand = game.startNewHand();
		needToDiscard = false;
		leftOverDominos = hand.dealDominos(rules.getFullDominoSet(), players);
		
		return currentState = new GameState(State.Bidding, players[0]);
	}

	public GameState placeBid(Bid bid) {
		ArrayList<String> messages = bidValidator.canBid(currentState, bid, game.getCurrentHand().getWinningBid(), game.getCurrentHand().getBids());
		if (!messages.isEmpty()) 
			return currentState = new GameState(currentState.getState(), currentState.getNextPlayer(), messages);
		
		game.getCurrentHand().addBid(bid);
		messages.add(String.format("%s bid %d", bid.getPlayer().toString(), bid.getBid()));
		
		if (!game.getCurrentHand().biddingIsOver())
			return currentState = new GameState(State.Bidding, game.getCurrentHand().getNextBidder(), messages);

		Bid winningBid = game.getCurrentHand().getWinningBid();
		messages.add(String.format("%s won bid with %d", winningBid.getPlayer().toString(), bid.getBid()));
		
		if (leftOverDominos.size() > 0) {
			winningBid.getPlayer().addDominosToHand(leftOverDominos);
			leftOverDominos.removeAll(leftOverDominos);
			needToDiscard = true;
		}
		
		return currentState = new GameState(State.SettingTrump, winningBid.getPlayer(), messages);
	}
	
	public GameState setTrump(Player player, int suit) {
		ArrayList<String> messages = bidValidator.canSetTrump(currentState, player, suit);
		if (!messages.isEmpty()) 
			return currentState = new GameState(currentState.getState(), currentState.getNextPlayer(), messages);
		
		game.getCurrentHand().setTrump(suit);
		Player bidWinner = game.getCurrentHand().getWinningBid().getPlayer();
		game.getCurrentHand().startNewTrick(bidWinner);
		
		State state = State.Playing;
		if (needToDiscard)
			state = State.DiscardDomino;
		
		return currentState = new GameState(state, bidWinner, String.format("%s set trump of %d", bidWinner, suit));
	}
	
	public GameState discardDomino(Player player, Domino domino) {
		ArrayList<String> messages = playValidator.canDiscard(currentState, player, domino);
		if (!messages.isEmpty()) 
			return currentState = new GameState(currentState.getState(), currentState.getNextPlayer(), messages);
		
		player.getDominosInHand().remove(domino);
		needToDiscard = false;
		Player bidWinner = game.getCurrentHand().getWinningBid().getPlayer();
		game.getCurrentHand().startNewTrick(bidWinner);
		return currentState = new GameState(State.Playing, bidWinner, String.format("%s discarded domino", player));
	}
	
	public GameState playDomino(Player player, Domino domino) {
		ArrayList<String> messages = playValidator.canPlay(currentState, player, game.getCurrentHand().getCurrentTrick().getLedDomino(), domino, game.getCurrentHand().getTrump());
		if (!messages.isEmpty()) 
			return currentState = new GameState(currentState.getState(), currentState.getNextPlayer(), messages);

		game.getCurrentHand().getCurrentTrick().playDomino(player.playDomino(domino));	
		messages.add(String.format("%s played %s", player.toString(), domino.toString()));
	
		// if trick is not over, keep playing
		if (!game.getCurrentHand().getCurrentTrick().isOver()) {
			return currentState = new GameState(State.Playing, game.getCurrentHand().getCurrentTrick().getNextPlayer(), messages);
		}
		
		// if trick is over, mark it completed
		Trick previousTrick = game.getCurrentHand().completeTrick();
		messages.add(getTrickSummary(previousTrick));
		messages.add(String.format("%s won trick", previousTrick.getWinningPlayer().toString()));
		
		for (Team t : game.getTeams()) 
			messages.add(String.format("%s has %d trick points", t.toString(), game.getRules().getWonPoints(t, game.getCurrentHand())));
			
		// if hand is not over, start new trick and keep playing
		if (!game.getCurrentHand().isOver()) {
			game.getCurrentHand().startNewTrick(previousTrick.getWinningPlayer());
			messages.add(String.format("\nNext trick started"));

			return currentState = new GameState(State.Playing, previousTrick.getWinningPlayer(), messages);
		}

		// complete hand
		Hand previousHand = game.completeHand();
		if (previousHand.getHandWinner() != null)
			messages.add(String.format("%s won hand", previousHand.getHandWinner().toString()));
		
		for (Team t : game.getTeams()) {
			messages.add(String.format("%s has %d marks", t.toString(), t.getScore()));
		}
				
		// if game is not over start new hand and keep playing
		if (!game.hasWinner()) { 
			Hand hand = game.startNewHand();
			needToDiscard = false;
			leftOverDominos = hand.dealDominos(game.getRules().getFullDominoSet(), game.getPlayers());
			messages.add(String.format("\nDominos dealt"));
			
			return currentState = new GameState(State.Bidding, hand.getNextBidder(), messages);
		}
		
		messages.add(String.format("Game is over, %s won", game.getWinner().toString()));
		return currentState = new GameState(State.Over, null, messages);
	}
	
	private String getTrickSummary(Trick previousTrick) {
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for (PlayedDomino pd : previousTrick.getPlayedDominos()) {
			sb.append(String.format("%s played %s", pd.getPlayer().toString(), pd.getDomino().toString()));

			if (count++ < previousTrick.getPlayedDominos().size() - 1)
				sb.append(", ");
		}
				
		return sb.toString();
	}

	public GameState forfeit(Player player) {
		Team[] teams = getGame().getOtherTeamsForPlayer(player);
		
		for (Team t: teams) 
			t.winByForfiet();

		ArrayList<String> messages = new ArrayList<String>();
		messages.add(String.format("Game is over, %s won", game.getWinner().toString()));
		return currentState = new GameState(State.Over, null, messages);
	}

	public Game getGame() {
		return game;
	}
	
	public GameState getCurrentState() {
		return currentState;
	}
}
