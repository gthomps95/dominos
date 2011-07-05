package net.gthomps.tx42.ai;

import java.util.ArrayList;
import net.gthomps.tx42.Domino;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Hand;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.Trick;

public class DominoChooser2 implements PlayChooser<Domino> {
	DominoChooserHelper helper = new DominoChooserHelper();

	@Override
	public Domino choose(Game game, Player player) {
		if (helper.hasOneDomino(player))
			return player.getDominosInHand().get(0);
		
		Hand hand = game.getCurrentHand();
		Trick trick = hand.getCurrentTrick();
		
		if (helper.playsFirst(trick, player)) {
			return chooseLeadDomino(player);
		} else {
			return chooseFollowDomino(game, player, hand, trick);
		}
	}

	private Domino chooseFollowDomino(Game game, Player player, Hand hand, Trick trick) {
		ArrayList<Domino> dominos = helper.getDominosThatFollowSuit(player.getDominosInHand(), trick.getLedDomino(), hand.getTrump());
		boolean preferCount = helper.isWinningTrick(trick, game.getTeamForPlayer(player));
		
		if (dominos.size() > 0)
			return helper.chooseRandomDomino(dominos, preferCount);
		
		return helper.chooseRandomDomino(player.getDominosInHand(), preferCount);
	}

	private Domino chooseLeadDomino(Player player) {
		return helper.chooseRandomDomino(player.getDominosInHand());
	}
}
