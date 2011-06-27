package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;

public class EndToEndTest {

	@Test
	public void newGameAndPlayers() {
		GameDriver driver = new GameDriver();
		driver.createNewGame();
		driver.checkPlayerAndDominoCount();
	}
	
	@Test
	public void startGameAndHandAndShuffleDominos() {
		GameDriver driver = new GameDriver();
		driver.createNewGame();
		driver.startGame();
		driver.gameHasHand();
		driver.playersHaveSevenDominos();
	}

}
