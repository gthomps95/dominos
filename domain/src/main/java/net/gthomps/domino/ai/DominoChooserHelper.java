package net.gthomps.domino.ai;

import java.util.ArrayList;
import java.util.Random;

import net.gthomps.domino.Domino;
import net.gthomps.domino.Player;
import net.gthomps.domino.Team;
import net.gthomps.domino.Trick;
import net.gthomps.domino.rules.Texas42Rules;

public class DominoChooserHelper {
	private static final Texas42Rules rules = new Texas42Rules();

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
			if (preferCount && rules.getDominoPointCount(d) > 0)
				temp.add(d);
			else if (!preferCount && rules.getDominoPointCount(d) == 0)
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
