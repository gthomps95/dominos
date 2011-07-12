package net.gthomps.domino.cli;

import java.util.ArrayList;
import java.util.Hashtable;

import net.gthomps.domino.GameService;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.ai.DominoChooser2;
import net.gthomps.domino.ai.PlayChooser;
import net.gthomps.domino.ai.SimpleBidChooser42;
import net.gthomps.domino.ai.SimpleTrumpChooser;
import net.gthomps.domino.app.GamePlayer;
import net.gthomps.domino.app.GameRunner;
import net.gthomps.domino.rules.Texas42Rules;
import net.gthomps.domino.validation.BidValidator42;
import net.gthomps.domino.validation.PlayValidator42;

public class Texas42 
{
	public static void main( String[] args )
    {
		GameService service = new GameService();

		ArrayList<Player> players = new ArrayList<Player>();
		Hashtable<Player, GamePlayer> gamePlayers = new Hashtable<Player, GamePlayer>();

		Hashtable<GameState.State, PlayChooser<?>> humanChoosers = new Hashtable<GameState.State, PlayChooser<?>>();
		humanChoosers.put(GameState.State.Bidding, new HumanBidChooser());
		humanChoosers.put(GameState.State.SettingTrump, new HumanTrumpChooser());
		humanChoosers.put(GameState.State.Playing, new HumanDominoChooser());

		Hashtable<GameState.State, PlayChooser<?>> choosers = new Hashtable<GameState.State, PlayChooser<?>>();
		choosers.put(GameState.State.Bidding, new SimpleBidChooser42());
		choosers.put(GameState.State.SettingTrump, new SimpleTrumpChooser());
		choosers.put(GameState.State.Playing, new DominoChooser2());

		Player player = new Player("Human"); 
		players.add(player);
		gamePlayers.put(player, new GamePlayer(player, service, choosers));
		
		player = new Player("CPU"); 
		players.add(player);
		gamePlayers.put(player, new GamePlayer(player, service, choosers));

		player = new Player("Human's Partner"); 
		players.add(player);
		gamePlayers.put(player, new GamePlayer(player, service, choosers));
		
		player = new Player("CPU's Partner"); 
		players.add(player);
		gamePlayers.put(player, new GamePlayer(player, service, choosers));
		
		GameRunner runner = new GameRunner(service, gamePlayers, players, new SystemOutMessageShower(), new BidValidator42(), new PlayValidator42(), new Texas42Rules());
		runner.run();
    }
}
