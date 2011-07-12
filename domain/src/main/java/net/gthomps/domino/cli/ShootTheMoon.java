package net.gthomps.domino.cli;

import java.util.ArrayList;
import java.util.Hashtable;

import net.gthomps.domino.GameService;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.ai.PlayChooser;
import net.gthomps.domino.ai.SimpleBidChooserMoon;
import net.gthomps.domino.ai.SimpleDominoChooser;
import net.gthomps.domino.ai.SimpleTrumpChooser;
import net.gthomps.domino.app.GamePlayer;
import net.gthomps.domino.app.GameRunner;
import net.gthomps.domino.rules.ShootTheMoonRules;
import net.gthomps.domino.validation.BidValidatorMoon;
import net.gthomps.domino.validation.PlayValidatorMoon;

public class ShootTheMoon 
{
	public static void main( String[] args )
    {
		GameService service = new GameService();

		ArrayList<Player> players = new ArrayList<Player>();
		Hashtable<Player, GamePlayer> gamePlayers = new Hashtable<Player, GamePlayer>();

		Hashtable<GameState.State, PlayChooser<?>> humanChoosers = new Hashtable<GameState.State, PlayChooser<?>>();
		humanChoosers.put(GameState.State.Bidding, new HumanBidChooser());
		humanChoosers.put(GameState.State.SettingTrump, new HumanTrumpChooser());
		humanChoosers.put(GameState.State.DiscardDomino, new HumanDominoChooser());
		humanChoosers.put(GameState.State.Playing, new HumanDominoChooser());

		Hashtable<GameState.State, PlayChooser<?>> choosers = new Hashtable<GameState.State, PlayChooser<?>>();
		choosers.put(GameState.State.Bidding, new SimpleBidChooserMoon());
		choosers.put(GameState.State.SettingTrump, new SimpleTrumpChooser());
		choosers.put(GameState.State.DiscardDomino, new SimpleDominoChooser());
		choosers.put(GameState.State.Playing, new SimpleDominoChooser());

		Player player = new Player("Human"); 
		players.add(player);
		gamePlayers.put(player, new GamePlayer(player, service, humanChoosers));
		
		player = new Player("CPU 1"); 
		players.add(player);
		gamePlayers.put(player, new GamePlayer(player, service, choosers));

		player = new Player("CPU 2"); 
		players.add(player);
		gamePlayers.put(player, new GamePlayer(player, service, choosers));
		
		GameRunner runner = new GameRunner(service, gamePlayers, players, new SystemOutMessageShower(), new BidValidatorMoon(), new PlayValidatorMoon(), new ShootTheMoonRules());
		runner.run();
    }
}
