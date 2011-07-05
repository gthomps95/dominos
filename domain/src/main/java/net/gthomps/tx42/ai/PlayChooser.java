package net.gthomps.tx42.ai;

import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public interface PlayChooser<T> {
	T choose(Game game, Player player);
}
