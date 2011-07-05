package net.gthomps.tx42.cli;

import java.io.IOException;
import net.gthomps.tx42.GameService;
import net.gthomps.tx42.GameState;
import net.gthomps.tx42.Player;
import net.gthomps.tx42.GameState.State;
import net.gthomps.tx42.ai.DominoChooser2;
import net.gthomps.tx42.ai.SimpleBidChooser;
import net.gthomps.tx42.ai.SimpleTrumpChooser;
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
		
		while (!state.getState().equals(State.Over)) {

			while (state.getState().equals(State.Bidding)) {
				state = (new PlayMakeBid(service, new SimpleBidChooser(), new HumanBidGetter())).play(human, state);
			}

			while (state.getState().equals(State.SettingTrump)) {
				state = (new PlaySetTrump(service, new SimpleTrumpChooser(), new HumanTrumpGetter())).play(human, state);
			}
			
			while (state.getState().equals(State.Playing)) {
				state = (new PlayDomino(service, new DominoChooser2(), new HumanDominoGetter())).play(human, state);
			}
		}
	}

	private Player[] createPlayers() {
		Player[] players = new Player[4];
		players[0] = new Player("Human");
		players[1] = new Player("CPU");
		players[2] = new Player("Human's Partner");
		players[3] = new Player("CPU's Partner");
		return players;
	}

	public static void main( String[] args ) throws ValidatorException, IOException
    {
		App app = new App();
		app.run();
    }
}
