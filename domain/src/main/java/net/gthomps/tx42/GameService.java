package net.gthomps.tx42;

import java.util.ArrayList;

import net.gthomps.tx42.GameState.State;
import net.gthomps.tx42.validation.BidValidator;
import net.gthomps.tx42.validation.PlayValidator;

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

	public GameState placeBid(Bid bid) {
		ArrayList<String> messages = bidValidator.canBid(currentState, bid, game.getCurrentHand().getWinningBid(), game.getCurrentHand().getBids());
		if (!messages.isEmpty()) 
			return new GameState(currentState.getState(), currentState.getNextPlayer(), messages);
		
		game.getCurrentHand().addBid(bid);
		messages.add(String.format("%s bid %d", bid.getPlayer().toString(), bid.getBid()));
		
		if (!game.getCurrentHand().biddingIsOver())
			return currentState = new GameState(State.Bidding, game.getCurrentHand().getNextBidder(), messages);

		Bid winningBid = game.getCurrentHand().getWinningBid();
		messages.add(String.format("%s won bid with %d", winningBid.getPlayer().toString(), bid.getBid()));
		
		return currentState = new GameState(State.SettingTrump, winningBid.getPlayer(), messages);
	}
	
	public GameState setTrump(Player player, int suit) {
		ArrayList<String> messages = bidValidator.canSetTrump(currentState, player, suit);
		if (!messages.isEmpty()) 
			return new GameState(currentState.getState(), currentState.getNextPlayer(), messages);
		
		game.getCurrentHand().setTrump(suit);
		Player bidWinner = game.getCurrentHand().getWinningBid().getPlayer();
		game.getCurrentHand().startNewTrick(bidWinner);
		return currentState = new GameState(State.Playing, bidWinner, String.format("%s set trump of %d", bidWinner, suit));
	}
	
	public GameState playDomino(Player player, Domino domino) {
		ArrayList<String> messages = playValidator.canPlay(currentState, player, game.getCurrentHand().getCurrentTrick().getLedDomino(), domino, game.getCurrentHand().getTrump());
		if (!messages.isEmpty()) 
			return new GameState(currentState.getState(), currentState.getNextPlayer(), messages);

		game.getCurrentHand().getCurrentTrick().playDomino(player.playDomino(domino));	
		messages.add(String.format("%s played %s", player.toString(), domino.toString()));
	
		// if trick is not over, keep playing
		if (!game.getCurrentHand().getCurrentTrick().isOver()) {
			return currentState = new GameState(State.Playing, game.getCurrentHand().getCurrentTrick().getNextPlayer(), messages);
		}
		
		// if trick is over, mark it completed
		Trick previousTrick = game.getCurrentHand().completeTrick();
		messages.add(getTrickSummar(previousTrick));
		messages.add(String.format("%s won trick", previousTrick.getWinningPlayer().toString()));
		messages.add(String.format("%s has %d trick points", game.getTeam1().toString(), game.getCurrentHand().getWonPoints(game.getTeam1())));
		messages.add(String.format("%s has %d trick points", game.getTeam2().toString(), game.getCurrentHand().getWonPoints(game.getTeam2())));
			
		// if hand is not over, start new trick and keep playing
		if (!game.getCurrentHand().isOver()) {
			game.getCurrentHand().startNewTrick(previousTrick.getWinningPlayer());
			messages.add(String.format("\nNext trick started"));

			return currentState = new GameState(State.Playing, previousTrick.getWinningPlayer(), messages);
		}

		// complete hand
		Hand previousHand = game.completeHand();
		messages.add(String.format("%s won hand", previousHand.getHandWinner().toString()));
		messages.add(String.format("%s has %d marks", game.getTeam1().toString(), game.getTeam1().getScore()));
		messages.add(String.format("%s has %d marks", game.getTeam2().toString(), game.getTeam2().getScore()));
				
		// if game is not over start new hand and keep playing
		if (!game.hasWinner()) { 
			Hand hand = game.startNewHand();
			hand.dealDominos(Domino.getFullDominoSet(), game.getPlayers());
			messages.add(String.format("\nDominos dealt"));
			
			return currentState = new GameState(State.Bidding, hand.getNextBidder(), messages);
		}
		
		messages.add(String.format("Game is over, %s won", game.getWinner().toString()));
		return currentState = new GameState(State.Over, null, messages);
	}
	
	private String getTrickSummar(Trick previousTrick) {
		StringBuffer sb = new StringBuffer();
		int count = 0;
		for (PlayedDomino pd : previousTrick.getPlayedDominos()) {
			sb.append(String.format("%s played %s", pd.getPlayer().toString(), pd.getDomino().toString()));

			if (count++ < 3)
				sb.append(", ");
		}
				
		return sb.toString();
	}

	public GameState forfeit(Player player) {
		Team team = getGame().getOtherTeamForPlayer(player);
		team.winByForfiet();

		ArrayList<String> messages = new ArrayList<String>();
		messages.add(String.format("Game is over, %s won", game.getWinner().toString()));
		return currentState = new GameState(State.Over, null, messages);
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
