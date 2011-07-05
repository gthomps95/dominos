package net.gthomps.tx42.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.gthomps.tx42.Bid;
import net.gthomps.tx42.Domino;
import net.gthomps.tx42.GameService;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.GameState.State;
import net.gthomps.tx42.ai.BidMaker;
import net.gthomps.tx42.ai.PlayMaker;
import net.gthomps.tx42.ai.SimpleBidMaker;
import net.gthomps.tx42.ai.SimplePlayMaker;
import net.gthomps.tx42.validation.BidValidator42;
import net.gthomps.tx42.validation.PlayValidator42;
import net.gthomps.tx42.validation.ValidatorException;

/**
 * Hello world!
 *
 */
public class App 
{
	GameService service = new GameService();
	public void run() throws ValidatorException, IOException {
		Player[] players = createPlayers();
		
		Player human = players[0];
		
		GameState state = service.createNewGame(players, new BidValidator42(), new PlayValidator42());
		outputStateMessages(state);
		
		while (!state.getState().equals(State.Over)) {

			while (state.getState().equals(State.Bidding)) {
				state = bid(human, state);
			}

			while (state.getState().equals(State.SettingTrump)) {
				state = setTrump(human, state);
			}
			
			while (state.getState().equals(State.Playing)) {
				state = playDomino(human, state);
			}
		}
	}

	private GameState playDomino(Player human, GameState state) throws IOException, ValidatorException {
		PlayMaker playMaker = new SimplePlayMaker();
		
		Domino domino;
		if (state.getNextPlayer().equals(human)) {
			domino = getDominoFromHuman(human);
		} else {
			domino = playMaker.chooseDomino(service.getGame(), state.getNextPlayer());
		}
		
		// if no domino is played, player is forfeiting
		if (domino != null)
			state = service.playDomino(state.getNextPlayer(), domino);
		else		
			state = service.forfeit(state.getNextPlayer());
		
		outputStateMessages(state);
		return state;
	}

	private GameState setTrump(Player human, GameState state) throws IOException {
		BidMaker bidMaker = new SimpleBidMaker();
		int trump;
		if (state.getNextPlayer().equals(human)) {
			trump = getTrumpFromHuman(human);
		} else {
			trump = bidMaker.selectTrump(service.getGame(), state.getNextPlayer());
		}
		
		state = service.setTrump(state.getNextPlayer(), trump);
		outputStateMessages(state);
		return state;
	}

	private GameState bid(Player human, GameState state) throws IOException, ValidatorException {
		Player player = state.getNextPlayer();
		Bid bid;
		if (player.equals(human)) {
			bid = getBidFromHuman(human);
		} else {
			BidMaker bidMaker = new SimpleBidMaker();
			bid = bidMaker.makeBid(service.getGame(), player);
		}
		
		state = service.placeBid(bid);
		outputStateMessages(state);
		return state;
	}

	private Player[] createPlayers() {
		Player[] players = new Player[4];
		players[0] = new Player("Human");
		players[1] = new Player("CPU");
		players[2] = new Player("Human's Partner");
		players[3] = new Player("CPU's Partner");
		return players;
	}

	private void outputStateMessages(GameState state) {
		for (String s : state.getMessages()) {
			System.out.println(s);
		}
		
		if (state.getNextPlayer() != null)
			System.out.println(String.format("%s turn for %s", state.getNextPlayer().toString(), state.getState().toString()));
	}

	private Domino getDominoFromHuman(Player human) throws IOException {
		showDominos(human);
		System.out.print("Play domino:  ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String response = br.readLine();
		
		if (response.equals("f"))
			return null;

		int index = -1;
		try {
			index = Integer.parseInt(response) - 1;
		}
		catch (NumberFormatException nfe) {
			System.out.println(nfe.getMessage());
		}

		if (index < 0 || index > human.getDominosInHand().size() - 1 ) {
			System.out.println(String.format("%s is not a valid domino", response));
			return getDominoFromHuman(human);
		}
		
		Domino domino = human.getDominosInHand().get(index);
		return domino;
	}

	private int getTrumpFromHuman(Player human) throws IOException {
		showDominos(human);
		System.out.print("Set trump:  ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String trump = br.readLine();
		return Integer.parseInt(trump);
	}

	private Bid getBidFromHuman(Player human) throws IOException {
		showDominos(human);
		System.out.print("Place bid:  ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String bid = br.readLine();
		return new Bid(human, Integer.parseInt(bid));
	}
	
	private void showDominos(Player human) {
		System.out.println(String.format("%s dominos:", human.toString()));
		int i = 1;
		for (Domino d : human.getDominosInHand()) {
			System.out.println(String.format("%d - %s", i++, d.toString()));
		}
	}

	public static void main( String[] args ) throws ValidatorException, IOException
    {
		App app = new App();
		app.run();
    }
}
