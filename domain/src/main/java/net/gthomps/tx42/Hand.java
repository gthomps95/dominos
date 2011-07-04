package net.gthomps.tx42;

import java.util.ArrayList;
import java.util.Hashtable;

public class Hand {
	private ArrayList<Bid> bids = new ArrayList<Bid>();
	private Bid winningBid;
	private Trick currentTrick;
	private ArrayList<Trick> playedTricks = new ArrayList<Trick>();
	private Player[] players;
	private int trump = -1;

	public Hand(Player[] players) {
		this.players = players;
	}

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

	public Player[] getTrickOrderedPlayers(Player firstPlayer) {
		Player[] trickOrderedPlayers = new Player[4];

		int playerIndex = 0;
		for (; playerIndex < 4; playerIndex++)
			if (players[playerIndex].equals(firstPlayer))
				break;
		
		for (int i = 0; i < 4; i++)
			trickOrderedPlayers[i] = players[(i + playerIndex) % 4];
		
		return trickOrderedPlayers;
	}

	public Trick getCurrentTrick() {
		return currentTrick;
	}

	public Trick startNewTrick(Player firstPlayer) {
		return currentTrick = new Trick(getTrickOrderedPlayers(firstPlayer), getTrump());
	}

	public ArrayList<Trick> getPlayedTricks() {
		return playedTricks;
	}

	public Player completeTrick() {
		if (currentTrick != null) 
			playedTricks.add(currentTrick);
		
		Player trickWinner = currentTrick.getWinningPlayer();
		currentTrick = null;
		return trickWinner;
	}
	
	public Team getHandWinner(Team[] teams) {
		return Hand.getHandWinner(teams, players, getPlayedTricks(), getWinningBid());
	}
	
	public static Team getHandWinner(Team[] teams, Player[] players, ArrayList<Trick> playedTricks, Bid winningBid) {
		// TODO factor in nello
		
		Hashtable<Player, Integer> wonPoints = new Hashtable<Player, Integer>();
		for (Player p : players)
			wonPoints.put(p, new Integer(0));
				
		for (Trick t : playedTricks) {
			Player winningPlayer = t.getWinningPlayer();
			wonPoints.put(winningPlayer, wonPoints.get(winningPlayer) + t.getPointCount() );
		}
		
		Player bidWinner = winningBid.getPlayer();
		Team bidWinningTeam = Team.getTeam(teams, bidWinner);
		Team settingTeam = Team.getOtherTeam(teams, bidWinner);

		int biddingTeamPoints = wonPoints.get(bidWinningTeam.getPlayer1()) + wonPoints.get(bidWinningTeam.getPlayer2());

		if (winningBid.enoughPointsToWinBid(biddingTeamPoints))
			return bidWinningTeam;
		else
			return settingTeam;
	}

	public boolean isOver() {
		return playedTricks.size() == 7;
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
	
	public void setTrump(int suit) {
		trump = suit;
	}
	
	public String toString() {
		return String.format("Hand - %d is bid, %d is trump, %d tricks have been played", winningBid.getBid(), trump, playedTricks.size());
	}
}
