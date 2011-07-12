package net.gthomps.domino.rules;

import java.util.ArrayList;

import net.gthomps.domino.Bid;
import net.gthomps.domino.Domino;
import net.gthomps.domino.Hand;
import net.gthomps.domino.PlayedDomino;
import net.gthomps.domino.Player;
import net.gthomps.domino.Team;
import net.gthomps.domino.Trick;

public class Texas42Rules implements Rules {

	public int getMarks(Bid bid) {
		if ( bid.getBid() < 42 )
			return 1;
		else 
			return bid.getBid() / 42;
	}

	public boolean enoughPointsToWinBid(Bid bid, int points) {
		int bidPoints = bid.getBid();
		if ( bid.getBid() > 42 )
			bidPoints = 42;
		
		return points >= bidPoints;
	}
	
	public boolean enoughPointsToSetBid(Bid bid, int points) {
		int bidPoints = bid.getBid();
		if ( bid.getBid() > 42 )
			bidPoints = 42;
		
		return points > 42 - bidPoints;
	}

	public int getPointCount(Trick trick) {
		int points = 1;
		for (PlayedDomino pd : trick.getPlayedDominos())
			points += getDominoPointCount(pd.getDomino());
				
		return points;
	}

	public int getDominoPointCount(Domino domino) {
		int sum = domino.getLowSide() + domino.getHighSide();
		
		if (sum % 5 == 0)
			return sum;
		else
			return 0;
	}
	
	@Override
	public Domino[] getFullDominoSet() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		
		for (int i = Domino.MAX_SIDE_VALUE; i >= Domino.MIN_SIDE_VALUE; i--) {
			for (int j = i; j >= Domino.MIN_SIDE_VALUE; j--) {
				dominos.add(new Domino(i,j));
			}
		}
		
		return dominos.toArray(new Domino[dominos.size()]);
	}

	@Override
	public int getPlayerCount() {
		return 4;
	}

	@Override
	public Team[] getTeams(Player[] players) {
		Team[] teams = new Team[2];
		teams[0] = new Team(players[0], players[2]);
		teams[1] = new Team(players[1], players[3]);
		return teams;
	}

	@Override
	public int getTeamCount() {
		return 2;
	}

	public Team getHandWinner(Hand hand) {
		Player bidWinner = hand.getWinningBid().getPlayer();
		Team bidWinningTeam = Team.getTeam(hand.getTeams(), bidWinner);
		Team settingTeam = Team.getOtherTeams(hand.getTeams(), bidWinner)[0];

		int biddingTeamPoints = getWonPoints(bidWinningTeam, hand);
		int otherTeamPoints = getWonPoints(settingTeam, hand);

		if (enoughPointsToWinBid(hand.getWinningBid(), biddingTeamPoints))
			return bidWinningTeam;
		else if (enoughPointsToSetBid(hand.getWinningBid(), otherTeamPoints))
			return settingTeam;
		
		return null;
	}
	
	public boolean isOver(Hand hand) {
		return hand.getPlayedTricks().size() != 0 && getHandWinner(hand) != null;
	}

	@Override
	public int getWinningScore() {
		return 7;
	}

	@Override
	public int getMinimumBid() {
		return 30;
	}

	@Override
	public void scoreHand(Hand hand) {
		if (getHandWinner(hand) != null)
			getHandWinner(hand).addToScore(getMarks(hand.getWinningBid()));
	}
	
	@Override
	public int getWonPoints(Team team, Hand hand) {
		int wonPoints = 0;
				
		for (Trick t : hand.getPlayedTricks()) {
			if (team.containsPlayer(t.getWinningPlayer()))
				wonPoints += getPointCount(t);
		}

		return wonPoints;
	}
}
