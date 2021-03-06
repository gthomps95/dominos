package net.gthomps.domino;

import static org.junit.Assert.*;

import net.gthomps.domino.Player;
import net.gthomps.domino.Team;

import org.junit.Test;

public class TeamTest {
	private final Player player1 = new Player("Player 1");
	private final Player player2 = new Player("Player 2");

	@Test
	public void teamContainsPlayer() {
		Team team = createTeam();
		Player player3 = new Player("Player 3");
		assertTrue(team.containsPlayer(player1));
		assertTrue(team.containsPlayer(player2));
		assertFalse(team.containsPlayer(player3));
	}

	@Test
	public void addMarks() {
		Team team = createTeam();

		team.addToScore(1);
		assertEquals(1, team.getScore());
		
		team.addToScore(2);
		assertEquals(3, team.getScore());
	}

	private Team createTeam() {
		Team team = new Team(player1, player2);
		return team;
	}
}
