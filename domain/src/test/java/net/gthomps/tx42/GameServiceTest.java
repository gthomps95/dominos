package net.gthomps.tx42;

import static org.junit.Assert.*;

import net.gthomps.tx42.GameState.State;

import org.junit.Test;

public class GameServiceTest {
	private final GameService service = new GameService();
	
	@Test
	public void newGameHasFourPlayers() {
		service.createNewGame();
		assertEquals(4, service.getGame().getPlayers().length);
	}

	@Test
	public void newGameHasCurrentHand() {
		service.createNewGame();
		assertNotNull(service.getGame().getCurrentHand());
	}

	@Test
	public void newGamePlayersHaveSevenDominos() {
		service.createNewGame();
		assertEquals(7, service.getGame().getPlayers()[0].getDominosInHand().size());
	}
	
	@Test
	public void newGameReturnsCorrectState() {
		GameState state = service.createNewGame();
		assertEquals(State.Bidding, state.getState());
		assertEquals(service.getGame().getPlayers()[0], state.getNextPlayer());		
	}

	@Test
	public void addOneBidAddsBidAndReturnsCorrectState() {
		service.createNewGame();
		Bid bid = new Bid(service.getGame().getPlayers()[0], Bid.PASS);
		
		GameState state = service.placeBid(bid);
		
		assertNotNull(service.getGame().getCurrentHand().getBids().contains(bid));
		assertEquals(State.Bidding, state.getState());
		assertEquals(service.getGame().getPlayers()[1], state.getNextPlayer());
	}

	private Bid[] createFourBids(Player[] players) {
		Bid[] bids = new Bid[4];
		bids[0] = new Bid(service.getGame().getPlayers()[0], Bid.PASS);
		bids[1] = new Bid(service.getGame().getPlayers()[1], Bid.PASS);
		bids[2] = new Bid(service.getGame().getPlayers()[2], Bid.PASS);
		bids[3] = new Bid(service.getGame().getPlayers()[3], 30);
		
		return bids;
	}
	
	@Test
	public void addingFourBidsDeclaresBidWinner() {
		service.createNewGame();
		
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
		service.createNewGame();
		
		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		
		service.setTrump(0);
		
		assertNotNull(service.getGame().getCurrentHand().getCurrentTrick());
	}
	
	@Test
	public void addingFourBidsWithWinnerSetsCorrectNextPlayer() {
		service.createNewGame();
		
		Bid[] bids = createFourBids(service.getGame().getPlayers());
		GameState state = placeBids(bids);
		
		assertEquals(service.getGame().getPlayers()[3], state.getNextPlayer());
	}
	
	@Test
	public void addingFourDominosCausesPlayedTricksCountToIncrease() {
		service.createNewGame();

		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		service.setTrump(0);
		
		for (Player p : service.getGame().getPlayers())
			service.playDomino(p, p.getDominosInHand().get(0));
		
		assertEquals(1, service.getGame().getCurrentHand().getPlayedTricks().size());
	}

	@Test
	public void playingAllDominosCausesPlayedHandsCountToIncrease() {
		service.createNewGame();

		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		service.setTrump(0);

		for (int i = 0; i < 7; i++) {
			for (Player p : service.getGame().getPlayers())
				service.playDomino(p, p.getDominosInHand().get(0));
		}
		
		assertEquals(1, service.getGame().getPlayedHands().size());
	}
	
	@Test
	public void playFullGame() {
		GameState state = service.createNewGame();
		assertEquals(State.Bidding, state.getState());
		assertFalse(service.getGame().hasWinner());

		while (state.getState().equals(State.Bidding)) {
			assertFalse(service.getGame().getCurrentHand().isOver());
			assertFalse(service.getGame().hasWinner());

			// TODO set create bidding order
			Bid[] bids = createFourBids(service.getGame().getPlayers());
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
