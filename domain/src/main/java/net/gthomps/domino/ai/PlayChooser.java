package net.gthomps.domino.ai;

import net.gthomps.domino.Game;
import net.gthomps.domino.Player;

public interface PlayChooser<T> {
	T choose(Game game, Player player);
}
