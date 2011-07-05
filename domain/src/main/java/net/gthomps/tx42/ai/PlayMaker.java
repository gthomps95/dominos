package net.gthomps.tx42.ai;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public interface PlayMaker {
	Domino chooseDomino(Game game, Player player);
}
