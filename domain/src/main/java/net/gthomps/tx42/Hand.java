package net.gthomps.tx42;

import java.util.ArrayList;
import java.util.Random;

public class Hand {
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	private Bid winningBid;
	private Trick currentTrick;
	private ArrayList<Trick> playedTricks = new ArrayList<Trick>();
	private Player[] players;
	private int trump = -1;
	private Team[] teams;

	public Hand(Team[] teams, Player[] players) {
		this.teams = teams;
		this.players = players;
	}

	protected void dealDominos(Domino[] fullDominoSet, Player[] players) {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		for (Domino d: fullDominoSet)
			dominos.add(d);
		
		int drawn = 0;
		int fullCount = fullDominoSet.length;
		int playerCount = players.length;
		
		while (drawn < fullCount) {
			int index = (new Random()).nextInt(dominos.size());
			
			players[drawn % playerCount].addDominoToHand(dominos.remove(index));
			drawn += 1;			
		}		
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
	
	public Team getHandWinner() {
		Player bidWinner = winningBid.getPlayer();
		Team bidWinningTeam = Team.getTeam(teams, bidWinner);
		Team settingTeam = Team.getOtherTeam(teams, bidWinner);

		int biddingTeamPoints = getWonPoints(bidWinningTeam);
		int otherTeamPoints = getWonPoints(settingTeam);

		if (winningBid.enoughPointsToWinBid(biddingTeamPoints))
			return bidWinningTeam;
		else if (winningBid.enoughPointsToSetBid(otherTeamPoints))
			return settingTeam;
		
		return null;
	}

	public int getWonPoints(Team team) {
		int wonPoints = 0;
				
		for (Trick t : playedTricks) {
			if (team.containsPlayer(t.getWinningPlayer()))
				wonPoints += t.getPointCount();
		}

		return wonPoints;
	}

	public boolean isOver() {
		return playedTricks.size() != 0 && getHandWinner() != null;
	}

	public boolean biddingIsOver() {
		return bids.size() == 4;
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
}
