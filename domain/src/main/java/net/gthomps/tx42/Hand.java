package net.gthomps.tx42;

import java.util.ArrayList;

public class Hand {
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	private Bid winningBid;
	private Trick currentTrick;
	private ArrayList<Trick> playedTricks = new ArrayList<Trick>();

	public void dealDominos(Domino[] fullDominoSet, Player[] players) {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		for (Domino d: fullDominoSet)
			dominos.add(d);
		
		int drawn = 0;
		int fullCount = fullDominoSet.length;
		int playerCount = players.length;
		
		while (drawn < fullCount) {
			int index = (int) (Math.random() * dominos.size());
			
			players[drawn % playerCount].addDominoToHand(dominos.remove(index));
			drawn += 1;			
		}		
	}
	
	public void addBid(Bid bid) {
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

	public void startNewTrick() {
		currentTrick = new Trick();
	}

	public ArrayList<Trick> getPlayedTricks() {
		return playedTricks;
	}

	public void completeTrick() {
		if (currentTrick != null)
			playedTricks.add(currentTrick);
		
		currentTrick = null;
	}
}
