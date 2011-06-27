package net.gthomps.tx42;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void toStringTest() {
		String name = "player 1";
		Player player = new Player(name);
		assertEquals(name, player.toString());
	}

}
