package net.gthomps.domino.ai;

import java.util.Random;

import net.gthomps.domino.Game;
import net.gthomps.domino.Player;

public class SimpleTrumpChooser implements PlayChooser<Integer> {

	@Override
	public Integer choose(Game game, Player player) {
		return (new Random()).nextInt(7);
	}
}
