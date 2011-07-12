package net.gthomps.domino;

import java.util.ArrayList;
import java.util.Random;

import net.gthomps.domino.rules.Rules;

public class Hand {
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	private Bid winningBid;
	private Trick currentTrick;
	private ArrayList<Trick> playedTricks = new ArrayList<Trick>();
	private Player[] players;
	private int trump = -1;
	private Team[] teams;
	private Rules rules;

	public Hand(Team[] teams, Player[] players, Rules rules) {
		this.teams = teams;
		this.players = players;
		this.rules = rules;
	}

	protected ArrayList<Domino> dealDominos(Domino[] fullDominoSet, Player[] players) {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		for (Domino d: fullDominoSet)
			dominos.add(d);
		
		int drawn = 0;
		int playerCount = players.length;
		int countToDeal = ((int) (fullDominoSet.length / playerCount)) * playerCount;
		
		while (drawn < countToDeal) {
			int index = (new Random()).nextInt(dominos.size());
			
			players[drawn % playerCount].addDominoToHand(dominos.remove(index));
			drawn += 1;			
		}		
		
		return dominos;
	}
	
	protected void addBid(Bid bid) {
		bids.add(bid);

		checkForWinningBid(bid);	
	}
	
	private void checkForWinningBid(Bid bid) {
		if ((winningBid == null && bid.getBid() > 0) || (winningBid != null && winningBid.getBid() < bid.getBid()))
			winningBid = bid;
	}

	public ArrayList<Bid> getBids() {
		return bids;
	}

	public Bid getWinningBid() {
		return winningBid;
	}

	public Trick getCurrentTrick() {
		return currentTrick;
	}

	protected Trick startNewTrick(Player firstPlayer) {
		return currentTrick = new Trick(Player.getReorderedPlayers(players, firstPlayer), getTrump());
	}

	public ArrayList<Trick> getPlayedTricks() {
		return playedTricks;
	}

	protected Trick completeTrick() {
		if (currentTrick != null) 
			playedTricks.add(currentTrick);
		
		Trick previousTrick = currentTrick;
		currentTrick = null;
		return previousTrick;
	}
	
	public boolean biddingIsOver() {
		return bids.size() == players.length;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Player getNextBidder() {
		return players[bids.size()];
	}

	public int getTrump() {
		return trump;
	}
	
	protected void setTrump(int suit) {
		trump = suit;
	}
	
	public String toString() {
		return String.format("Hand - %d is bid, %d is trump, %d tricks have been played", winningBid.getBid(), trump, playedTricks.size());
	}

	public void completeHand() {
		for (Player player : players) {
			player.clearDominosInHand();
		}
		
	}

	public Team[] getTeams() {
		return teams;
	}
	
	public Team getHandWinner() {
		return rules.getHandWinner(this);
	}

	public boolean isOver() {
		return rules.isOver(this);
	}
}
