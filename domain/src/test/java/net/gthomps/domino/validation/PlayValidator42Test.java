package net.gthomps.domino.validation;

import static org.junit.Assert.*;

import java.util.ArrayList;

import net.gthomps.domino.Domino;
import net.gthomps.domino.Player;
import net.gthomps.domino.validation.PlayValidator42;

import org.junit.Test;

public class PlayValidator42Test {
	private PlayValidator42 playValidator = new PlayValidator42();
	private ArrayList<String> messages = new ArrayList<String>();
	private Player player = new Player("Player 1");

	@Test
	public void playerFollowedSuit() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		player.addDominosToHandForTesting(dominos);
		
		Domino ledDomino = new Domino(4,4);
		Domino playedDomino = new Domino(4,3);
		playValidator.checkFollowedSuitOrTrump(messages, player, ledDomino, playedDomino, 0);
		assertTrue(messages.isEmpty());
	}
	
	@Test
	public void playerDidNotFollowSuit() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		dominos.add(new Domino(4,3));
		player.addDominosToHandForTesting(dominos);
		
		Domino ledDomino = new Domino(4,4);
		Domino playedDomino = new Domino(3,3);
		playValidator.checkFollowedSuitOrTrump(messages, player, ledDomino, playedDomino, 0);
		assertFalse(messages.isEmpty());
	}
	
	@Test
	public void playerFollowedSuitIsTrump() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		player.addDominosToHandForTesting(dominos);
		
		Domino ledDomino = new Domino(4,0);
		Domino playedDomino = new Domino(0,3);
		playValidator.checkFollowedSuitOrTrump(messages, player, ledDomino, playedDomino, 0);
		assertTrue(messages.isEmpty());
	}
	
	@Test
	public void playerDidNotFollowSuitIsTrump() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		dominos.add(new Domino(0,3));
		player.addDominosToHandForTesting(dominos);
		
		Domino ledDomino = new Domino(0,4);
		Domino playedDomino = new Domino(3,3);
		playValidator.checkFollowedSuitOrTrump(messages, player, ledDomino, playedDomino, 0);
		assertFalse(messages.isEmpty());
	}
	
	@Test
	public void playedDominoNotInHand() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		dominos.add(new Domino(0,3));
		player.addDominosToHandForTesting(dominos);
		
		Domino playedDomino = new Domino(0,2);
		playValidator.checkDominoIsInHand(messages, player, playedDomino);
		assertFalse(messages.isEmpty());
	}
	
	@Test
	public void playedDominoInHand() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		dominos.add(new Domino(0,3));
		player.addDominosToHandForTesting(dominos);
		
		Domino playedDomino = new Domino(0,3);
		playValidator.checkDominoIsInHand(messages, player, playedDomino);
		assertTrue(messages.isEmpty());
	}
	
}
