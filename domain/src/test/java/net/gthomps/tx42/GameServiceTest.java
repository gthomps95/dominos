package net.gthomps.tx42;

import static org.junit.Assert.*;

import net.gthomps.tx42.GameState.State;

import org.junit.Test;

public class GameServiceTest {
	private final GameService service = new GameService();
	private PlayValidator testPlayValidator = new TestPlayValidator();
	
	// TODO add UI
	// TODO add play validation
	// TODO add bid AI
	// TODO add play AI
	
	@Test
	public void newGameHasFourPlayers() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);
		assertEquals(4, service.getGame().getPlayers().length);
	}

	@Test
	public void newGameHasCurrentHand() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);
		assertNotNull(service.getGame().getCurrentHand());
	}

	@Test
	public void newGamePlayersHaveSevenDominos() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);
		assertEquals(7, service.getGame().getPlayers()[0].getDominosInHand().size());
	}
	
	@Test
	public void newGameReturnsCorrectState() {
		GameState state = service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);
		assertEquals(State.Bidding, state.getState());
		assertEquals(service.getGame().getPlayers()[0], state.getNextPlayer());		
	}

	@Test
	public void addOneBidAddsBidAndReturnsCorrectState() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);
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
	public void addingFourBidsDeclaresBidWinner() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);
		
		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		
		assertEquals(bids[3], service.getGame().getCurrentHand().getWinningBid());
	}

	private GameState placeBids(Bid[] bids) {
		GameState state = null;
		for (Bid b : bids)
			state = service.placeBid(b);
				
		return state;
	}
	
	@Test
	public void addingFourBidsAndSettingTrumpCausesNewTrick() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);
		
		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		
		service.setTrump(0);
		
		assertNotNull(service.getGame().getCurrentHand().getCurrentTrick());
	}
	
	@Test
	public void addingFourBidsWithWinnerSetsCorrectNextPlayer() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);
		
		Bid[] bids = createFourBids(service.getGame().getPlayers());
		GameState state = placeBids(bids);
		
		assertEquals(service.getGame().getPlayers()[3], state.getNextPlayer());
	}
	
	@Test
	public void addingFourDominosCausesPlayedTricksCountToIncrease() {
		service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);

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
		
		service.createNewGame(players, testPlayValidator);
		assertNull(service.getGame().getWinner());

		service.forfiet(service.getGame().getTeam1().getPlayer1());
		assertEquals(service.getGame().getTeam2(), service.getGame().getWinner());
	}
	
	@Test
	public void playFullGame() {
		GameState state = service.createNewGame(PlayerTest.createFourGenericPlayers(), testPlayValidator);
		assertEquals(State.Bidding, state.getState());
		assertFalse(service.getGame().hasWinner());

		while (state.getState().equals(State.Bidding)) {
			assertFalse(service.getGame().getCurrentHand().isOver());
			assertFalse(service.getGame().hasWinner());

			Bid[] bids = createFourBids(service.getGame().getHandOrderedPlayers());
			state = placeBids(bids);
			
			assertEquals(-1, service.getGame().getCurrentHand().getTrump());			
			state = service.setTrump(5);
			assertEquals(5, service.getGame().getCurrentHand().getTrump());			
			assertEquals(5, service.getGame().getCurrentHand().getCurrentTrick().getTrump());			
			
			assertEquals(State.Playing, state.getState());			
			assertTrue(service.getGame().getCurrentHand().biddingIsOver());
			assertFalse(service.getGame().getCurrentHand().getCurrentTrick().isOver());
	
			while (state.getState().equals(State.Playing)) {
				state = service.playDomino(state.getNextPlayer(), state.getNextPlayer().getDominosInHand().get(0));
			}
		}
		
		assertTrue(service.getGame().hasWinner());
	}
}
