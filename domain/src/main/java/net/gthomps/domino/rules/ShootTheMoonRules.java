package net.gthomps.domino.rules;

import java.util.ArrayList;

import net.gthomps.domino.Domino;
import net.gthomps.domino.Hand;
import net.gthomps.domino.Player;
import net.gthomps.domino.Team;
import net.gthomps.domino.Trick;

public class ShootTheMoonRules implements Rules {

	@Override
	public Domino[] getFullDominoSet() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		
		for (int i = Domino.MAX_SIDE_VALUE; i >= Domino.MIN_SIDE_VALUE; i--) {
			for (int j = i; j >= Domino.MIN_SIDE_VALUE; j--) {
				if ( j != 0 )
					dominos.add(new Domino(i,j));
			}
		}
		
		dominos.add(new Domino(0,0));
		return dominos.toArray(new Domino[dominos.size()]);
	}

	@Override
	public int getPlayerCount() {
		return 3;
	}

	@Override
	public Team[] getTeams(Player[] players) {
		Team[] teams = new Team[3];
		teams[0] = new Team(players[0]);
		teams[1] = new Team(players[1]);
		teams[2] = new Team(players[2]);
		return teams;
	}

	@Override
	public int getTeamCount() {
		return 3;
	}

	@Override
	public void scoreHand(Hand hand) {
		int bidWinnerPoints = 0;
		for (Trick t : hand.getPlayedTricks()) {
			Team team = Team.getTeam(hand.getTeams(), t.getWinningPlayer());
			if (team.containsPlayer(hand.getWinningBid().getPlayer())) {
				bidWinnerPoints++;
			} else {
				team.addToScore(1);
			}
		}
		
		Team bidWinningTeam = Team.getTeam(hand.getTeams(), hand.getWinningBid().getPlayer());
		if (bidWinnerPoints >= hand.getWinningBid().getBid()) {
			bidWinningTeam.addToScore(bidWinnerPoints);
		} else {
			bidWinningTeam.addToScore(-1 * hand.getWinningBid().getBid());
		}
	}
	
	public boolean isOver(Hand hand) {
		return hand.getPlayedTricks().size() == 7;
	}

	@Override
	public int getWinningScore() {
		return 21;
	}

	@Override
	public int getMinimumBid() {
		return 4;
	}

	@Override
	public Team getHandWinner(Hand hand) {
		return null;
	}

	@Override
	public int getWonPoints(Team team, Hand hand) {
		int points = 0;
		for (Trick t : hand.getPlayedTricks())
			if (team.containsPlayer(t.getWinningPlayer()))
				points += 1;
		return points;
	}
}
