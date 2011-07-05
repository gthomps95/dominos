package net.gthomps.tx42;

import static org.junit.Assert.*;

import net.gthomps.tx42.GameState.State;
import net.gthomps.tx42.ai.BidMaker;
import net.gthomps.tx42.ai.PlayMaker;
import net.gthomps.tx42.ai.SimpleBidMaker;
import net.gthomps.tx42.ai.SimplePlayMaker;
import net.gthomps.tx42.validation.BidValidator;
import net.gthomps.tx42.validation.BidValidator42;
import net.gthomps.tx42.validation.PlayValidator;
import net.gthomps.tx42.validation.PlayValidator42;
import net.gthomps.tx42.validation.TestBidValidator;
import net.gthomps.tx42.validation.TestPlayValidator;
import net.gthomps.tx42.validation.ValidatorException;

import org.junit.Test;

public class GameServiceTest {
	private final GameService service = new GameService();
	private BidValidator testBidValidator = new TestBidValidator();
	private PlayValidator testPlayValidator = new TestPlayValidator();
	
	// TODO add UI
	// TODO add bid AI
	// TODO add play AI
	
	@Test
	public void newGameHasFourPlayers() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);
		assertEquals(4, service.getGame().getPlayers().length);
	}

	@Test
	public void newGameHasCurrentHand() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);
		assertNotNull(service.getGame().getCurrentHand());
	}

	@Test
	public void newGamePlayersHaveSevenDominos() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);
		assertEquals(7, service.getGame().getPlayers()[0].getDominosInHand().size());
	}
	
	@Test
	public void newGameReturnsCorrectState() {
		GameState state = service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);
		assertEquals(State.Bidding, state.getState());
		assertEquals(service.getGame().getPlayers()[0], state.getNextPlayer());		
	}

	@Test
	public void addOneBidAddsBidAndReturnsCorrectState() throws ValidatorException {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);
		Bid bid = new Bid(service.getGame().getPlayers()[0], Bid.PASS);
		
		GameState state = service.placeBid(bid);
		
		assertNotNull(service.getGame().getCurrentHand().getBids().contains(bid));
		assertEquals(State.Bidding, state.getState());
		assertEquals(service.getGame().getPlayers()[1], state.getNextPlayer());
	}

	private Bid[] createFourBids(Player[] players) {
		Bid[] bids = new Bid[4];
		bids[0] = new Bid(players[0], Bid.PASS);
		bids[1] = new Bid(players[1], Bid.PASS);
		bids[2] = new Bid(players[2], Bid.PASS);
		bids[3] = new Bid(players[3], 30);
		
		return bids;
	}
	
	@Test
	public void addingFourBidsDeclaresBidWinner() throws ValidatorException {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);
		
		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		
		assertEquals(bids[3], service.getGame().getCurrentHand().getWinningBid());
	}

	private GameState placeBids(Bid[] bids) throws ValidatorException {
		GameState state = null;
		for (Bid b : bids)
			state = service.placeBid(b);
				
		return state;
	}
	
	@Test
	public void addingFourBidsAndSettingTrumpCausesNewTrick() throws ValidatorException {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);
		
		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		
		service.setTrump(0);
		
		assertNotNull(service.getGame().getCurrentHand().getCurrentTrick());
	}
	
	@Test
	public void addingFourBidsWithWinnerSetsCorrectNextPlayer() throws ValidatorException {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);
		
		Bid[] bids = createFourBids(service.getGame().getPlayers());
		GameState state = placeBids(bids);
		
		assertEquals(service.getGame().getPlayers()[3], state.getNextPlayer());
	}
	
	@Test
	public void addingFourDominosCausesPlayedTricksCountToIncrease() throws ValidatorException {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);

		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		service.setTrump(0);
		
		for (Player p : service.getGame().getPlayers())
			service.playDomino(p, new Domino(0,0));
		
		assertEquals(1, service.getGame().getCurrentHand().getPlayedTricks().size());
	}

	@Test
	public void testForfeit() {
		Player[] players = PlayerTest.createFourGenericPlayers();
		
		service.createNewGame(players, testBidValidator, testPlayValidator);
		assertNull(service.getGame().getWinner());

		service.forfiet(service.getGame().getTeam1().getPlayer1());
		assertEquals(service.getGame().getTeam2(), service.getGame().getWinner());
	}
	
	@Test
	public void playFullGame() throws ValidatorException {
//		GameState state = service.createNewGame(PlayerTest.createFourGenericPlayers(), testBidValidator, testPlayValidator);
		GameState state = service.createNewGame(PlayerTest.createFourGenericPlayers(), new BidValidator42(), new PlayValidator42());
		assertEquals(State.Bidding, state.getState());
		assertFalse(service.getGame().hasWinner());

		while (state.getState().equals(State.Bidding)) {
			assertFalse(service.getGame().getCurrentHand().isOver());
			assertFalse(service.getGame().hasWinner());

			Player[] biddingPlayers = service.getGame().getHandOrderedPlayers();
			for (Player player : biddingPlayers) {
				BidMaker bidMaker = new SimpleBidMaker();
				Bid bid = bidMaker.makeBid(service.getGame(), player);
				state = service.placeBid(bid);
			}
			
			assertEquals(-1, service.getGame().getCurrentHand().getTrump());			
			state = service.setTrump(5);
			assertEquals(5, service.getGame().getCurrentHand().getTrump());			
			assertEquals(5, service.getGame().getCurrentHand().getCurrentTrick().getTrump());			
			
			assertEquals(State.Playing, state.getState());			
			assertTrue(service.getGame().getCurrentHand().biddingIsOver());
			assertFalse(service.getGame().getCurrentHand().getCurrentTrick().isOver());
	
			while (state.getState().equals(State.Playing)) {
				PlayMaker playMaker = new SimplePlayMaker();
				Domino domino = playMaker.chooseDomino(service.getGame(), state.getNextPlayer());
				state = service.playDomino(state.getNextPlayer(), domino);
			}
		}
		
		assertTrue(service.getGame().hasWinner());
	}
}
