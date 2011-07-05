package net.gthomps.tx42.ai;

import java.util.ArrayList;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public class SimpleDominoChoose implements PlayChooser<Domino> {
	DominoChooserHelper helper = new DominoChooserHelper();

	@Override
	public Domino choose(Game game, Player player) {
		Domino ledDomino = game.getCurrentHand().getCurrentTrick().getLedDomino();
		int trump = game.getCurrentHand().getTrump();

		if (ledDomino == null)
			return helper.chooseRandomDomino(player.getDominosInHand());
		
		ArrayList<Domino> followsSuit = helper.getDominosThatFollowSuit(player.getDominosInHand(), ledDomino, trump);
		if (followsSuit.size() != 0)
			return helper.chooseRandomDomino(followsSuit);
		else		
			return helper.chooseRandomDomino(player.getDominosInHand());
	}

}
