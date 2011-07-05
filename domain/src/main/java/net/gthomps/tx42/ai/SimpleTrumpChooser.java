package net.gthomps.tx42.ai;

import java.util.Random;

import net.gthomps.tx42.Game;
import net.gthomps.tx42.Player;

public class SimpleTrumpChooser implements PlayChooser<Integer> {

	@Override
	public Integer choose(Game game, Player player) {
		return (new Random()).nextInt(7);
	}
}
