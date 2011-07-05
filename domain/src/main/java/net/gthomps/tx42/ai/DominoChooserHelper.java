package net.gthomps.tx42.ai;

import java.util.ArrayList;
import java.util.Random;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.Team;
import net.gthomps.tx42.Trick;

public class DominoChooserHelper {

	public boolean hasOneDomino(Player player) {
		return player.getDominosInHand().size() == 1;
	}

	public boolean playsFirst(Trick trick, Player player) {
		return trick.getPlayedDominos().size() == 0;
	}

	public ArrayList<Domino> getDominosThatFollowSuit(ArrayList<Domino> dominosInHand, Domino ledDomino, int trump) {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		
		for (Domino d : dominosInHand)
			if (d.followsSuit(ledDomino, trump))
				dominos.add(d);
				
		return dominos;
	}

	public Domino chooseRandomDomino(ArrayList<Domino> dominos, boolean preferCount) {
		ArrayList<Domino> temp = new ArrayList<Domino>();

		for (Domino d: dominos) {
			if (preferCount && d.getPointCount() > 0)
				temp.add(d);
			else if (!preferCount && d.getPointCount() == 0)
				temp.add(d);
		}
		
		if (temp.size() > 0)
			dominos = temp;
		
		return chooseRandomDomino(dominos);
	}

	public Domino chooseRandomDomino(ArrayList<Domino> dominos) {
		return dominos.get((new Random()).nextInt(dominos.size()));
	}

	public boolean isWinningTrick(Trick trick, Team team) {
		if (trick.getWinningPlayer() == null)
			return true;
		return team.containsPlayer(trick.getWinningPlayer());
	}
}
