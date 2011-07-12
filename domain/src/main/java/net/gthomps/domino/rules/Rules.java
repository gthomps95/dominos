package net.gthomps.domino.rules;

import net.gthomps.domino.Domino;
import net.gthomps.domino.Hand;
import net.gthomps.domino.Player;
import net.gthomps.domino.Team;

public interface Rules {
	Domino[] getFullDominoSet();
	
	int getPlayerCount();
	int getTeamCount();
	Team[] getTeams(Player[] players);
	boolean isOver(Hand hand);
	Team getHandWinner(Hand hand);
	int getWinningScore();
	int getMinimumBid();

	void scoreHand(Hand hand);

	int getWonPoints(Team team, Hand hand);
}
