package net.gthomps.tx42.ai;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public interface PlayMaker {
	
	// TODO select smarter dominos
	
	Domino chooseDomino(Game game, Player player);
}
