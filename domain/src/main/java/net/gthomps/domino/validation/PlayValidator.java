package net.gthomps.domino.validation;

import java.util.ArrayList;

import net.gthomps.domino.Domino;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;

public interface PlayValidator {

	ArrayList<String> canPlay(GameState currentState, Player player, Domino ledDomino, Domino playedDomino, int trump);
	ArrayList<String> canDiscard(GameState currentState, Player player, Domino discardedDomino);

}
