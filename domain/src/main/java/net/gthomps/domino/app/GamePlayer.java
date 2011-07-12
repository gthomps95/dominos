package net.gthomps.domino.app;

import java.util.Hashtable;

import net.gthomps.domino.GameService;
import net.gthomps.domino.GameState;
import net.gthomps.domino.Player;
import net.gthomps.domino.ai.PlayChooser;

public class GamePlayer {
	private static Hashtable<GameState.State, Play> servicePlayMakers = new Hashtable<GameState.State, Play>();
	{
		servicePlayMakers.put(GameState.State.Bidding, new PlayMakeBid());
		servicePlayMakers.put(GameState.State.SettingTrump, new PlaySetTrump());
		servicePlayMakers.put(GameState.State.DiscardDomino, new PlayDiscard());
		servicePlayMakers.put(GameState.State.Playing, new PlayDomino());
	}
	
	private GameService service;
	private Hashtable<GameState.State, PlayChooser<?>> choosers;
	private Player player;
	
	public GamePlayer (Player player, GameService service, Hashtable<GameState.State, PlayChooser<?>> choosers) {
		this.player = player;
		this.service = service;
		this.choosers = choosers;
	}
	
	public GameState play(GameState state) {
		PlayChooser<?> chooser = choosers.get(state.getState());
		Object object = chooser.choose(service.getGame(), player);
		
		// if no object is played, player is forfeiting
		if (object == null)
			state = service.forfeit(state.getNextPlayer());
		else
			state = servicePlayMakers.get(state.getState()).playObject(service, state, object);
		
		return state;
	}
}
