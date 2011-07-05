package net.gthomps.tx42.ai;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public class SimplePlayMaker implements PlayMaker {

	@Override
	public Domino chooseDomino(Game game, Player player) {
		Domino ledDomino = game.getCurrentHand().getCurrentTrick().getLedDomino();
		int trump = game.getCurrentHand().getTrump();
		
		if (ledDomino == null)
			return player.getDominosInHand().get(0);
		
		for (Domino domino : player.getDominosInHand()) {
			if (domino.followsSuit(ledDomino, trump))
				return domino;
		}
		
		return player.getDominosInHand().get(0);
	}

}
