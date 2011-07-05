package net.gthomps.tx42.validation;

import java.util.ArrayList;

import net.gthomps.tx42.Domino;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.GameState.State;

public class PlayValidator42 extends Validator42Base implements PlayValidator {

	public ArrayList<String> canPlay(GameState currentState, Player player, Domino ledDomino, Domino playedDomino, int trump) {
		ArrayList<String> messages = new ArrayList<String>();
		checkCurrentState(messages, currentState, State.Playing);
		checkNextPlayer(messages, currentState, player);

		checkDominoIsInHand(messages, player, playedDomino);
		checkFollowedSuitOrTrump(messages, player, ledDomino, playedDomino, trump);
		
		return messages;
	}

	public void checkDominoIsInHand(ArrayList<String> messages, Player player, Domino playedDomino) {
		for (Domino domino : player.getDominosInHand()) {
			if (domino.equals(playedDomino)) {
				return;
			}
		}

		messages.add(String.format("%s must be in player's hand", playedDomino.toString()));
	}

	public void checkFollowedSuitOrTrump(ArrayList<String> messages, Player player, Domino ledDomino, Domino playedDomino, int trump) {
		if (ledDomino == null)
			return;
		
		if (playedDomino.followsSuit(ledDomino, trump))
				return;
			
		for (Domino domino : player.getDominosInHand()) {
			if (domino.followsSuit(ledDomino, trump)) {
				int suit = ledDomino.getHighSide();
				if (ledDomino.isTrump(trump))
					suit = trump;
				messages.add(String.format("%s must follow suit of %d", player.toString(), suit));
				break;
			}
		}
	}

}
