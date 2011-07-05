package net.gthomps.tx42.ai;

import java.util.Random;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public class SimplePlayMaker implements PlayMaker {

	@Override
	public Domino chooseDomino(Game game, Player player) {
		if (player.getDominosInHand().size() == 1)
			return player.getDominosInHand().get(0);
		
		Domino ledDomino = game.getCurrentHand().getCurrentTrick().getLedDomino();
		int trump = game.getCurrentHand().getTrump();
		
		if (ledDomino == null)
			return player.getDominosInHand().get(0);
		
		for (Domino domino : player.getDominosInHand()) {
			if (domino.followsSuit(ledDomino, trump))
				return domino;
		}
		
		int index = (new Random()).nextInt(player.getDominosInHand().size());

		return player.getDominosInHand().get(index);
	}

}
