package net.gthomps.domino.app;

import java.util.ArrayList;
import java.util.Hashtable;

import net.gthomps.domino.GameService;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.GameState.State;
import net.gthomps.domino.app.GamePlayer;
import net.gthomps.domino.rules.Rules;
import net.gthomps.domino.validation.BidValidator;
import net.gthomps.domino.validation.PlayValidator;

public class GameRunner 
{
	private GameService service;
	private Hashtable<Player,GamePlayer> gamePlayers;
	private ArrayList<Player> players;
	private MessageShower shower;
	private Rules rules;
	private PlayValidator playValidator;
	private BidValidator bidValidator;
	
	public GameRunner(GameService service, Hashtable<Player,GamePlayer> gamePlayers, ArrayList<Player> players, MessageShower shower, BidValidator bidValidator, PlayValidator playValidator, Rules rules) {
		this.service = service;
		this.gamePlayers = gamePlayers;
		this.players = players;
		this.shower = shower;
		this.rules = rules;
		this.bidValidator = bidValidator;
		this.playValidator = playValidator;
	}
	
	public void run() {
		GameState state = service.createNewGame(players.toArray(new Player[players.size()]), bidValidator, playValidator, rules);
		while (!state.getState().equals(State.Over)) {
			state = gamePlayers.get(state.getNextPlayer()).play(state);
			shower.outputStateMessages(state);
		}
	}
}
