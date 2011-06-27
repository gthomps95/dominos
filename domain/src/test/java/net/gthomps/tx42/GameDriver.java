package net.gthomps.tx42;

import static org.junit.Assert.*;

public class GameDriver {
	
	public Game game;
	
	public GameDriver createNewGame() {
		game = newGame();
		
		return this;
	}
	
	private Game newGame() {
		Player[] players = new Player[4];
		players[0] = new Player("Player 1");
		players[1] = new Player("Player 2");
		players[2] = new Player("Player 3");
		players[3] = new Player("Player 4");	
		
		return GameService.createNewGame(players);
	}

	public void checkPlayerAndDominoCount() {
		assertEquals(4, game.getPlayers().length);
		assertEquals(28, game.getDominos().length);		
	}

	public void startGame() {
		GameService.startGame(game);		
	}

	public void gameHasHand() {
		assertTrue(game.getCurrentHand() != null);
	}

	public void playersHaveSevenDominos() {
		assertEquals(7, game.getPlayers()[0].getDominosInHand().size());
	}

}
