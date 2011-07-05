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
	
	//TODO more messages
	//TODO messages from state
	
	GameService service = new GameService();
	public void run() throws ValidatorException, IOException {
		Player[] players = new Player[4];
		Player human = players[0] = new Player("Player 1");
		players[1] = new Player("Player 2");
		players[2] = new Player("Player 3");
		players[3] = new Player("Player 4");
		
		GameState state = service.createNewGame(players, new BidValidator42(), new PlayValidator42());

		while (state.getState().equals(State.Bidding)) {

			Player[] biddingPlayers = service.getGame().getHandOrderedPlayers();
			for (Player player : biddingPlayers) {
				BidMaker bidMaker = new SimpleBidMaker();
				
				Bid bid;
				if (player.equals(human)) {
					bid = getBidFromHuman(human);
				} else {
					bid = bidMaker.makeBid(service.getGame(), player);
				}
				
				System.out.println(bid);
				state = service.placeBid(bid);
			}

			int trump;
			if (state.getNextPlayer().equals(human)) {
				trump = getTrumpFromHuman(human);
			} else {
				trump = 5;
			}
			
			state = service.setTrump(trump);
			
			while (state.getState().equals(State.Playing)) {
				PlayMaker playMaker = new SimplePlayMaker();
				
				Domino domino;
				if (state.getNextPlayer().equals(human)) {
					domino = getDominoFromHuman(human);
				} else {
					domino = playMaker.chooseDomino(service.getGame(), state.getNextPlayer());
				}
				
				System.out.println(domino);
				state = service.playDomino(state.getNextPlayer(), domino);
			}
		}
		
		System.out.println(service.getGame().getWinner());
	}

	private Domino getDominoFromHuman(Player human) throws IOException {
		showDominos(human);
		System.out.print("Play domino:  ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String domino = br.readLine();
		return human.getDominosInHand().get(Integer.parseInt(domino) - 1);
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
