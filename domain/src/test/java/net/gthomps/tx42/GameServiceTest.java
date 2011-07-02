package net.gthomps.tx42;

import static org.junit.Assert.*;

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
	public void addBidAddsBid() {
		service.createNewGame();
		Bid bid = new Bid(service.getGame().getPlayers()[0], Bid.PASS);
		
		service.placeBid(bid);
		
		assertNotNull(service.getGame().getCurrentHand().getBids().contains(bid));
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

	private void placeBids(Bid[] bids) {
		for (Bid b : bids)
			service.placeBid(b);
	}
	
	@Test
	public void addingFourBidsCausesNewTrick() {
		service.createNewGame();
		
		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		
		assertNotNull(service.getGame().getCurrentHand().getCurrentTrick());
	}
	
	@Test
	public void addingFourDominosCausesPlayedTricksCountToIncrease() {
		service.createNewGame();

		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);
		
		for (Player p : service.getGame().getPlayers())
			service.playDomino(p, p.getDominosInHand().get(0));
		
		assertEquals(1, service.getGame().getCurrentHand().getPlayedTricks().size());
	}

	@Test
	public void playingAllDominosCausesPlayedHandsCountToIncrease() {
		service.createNewGame();

		Bid[] bids = createFourBids(service.getGame().getPlayers());
		placeBids(bids);

		for (int i = 0; i < 7; i++) {
			for (Player p : service.getGame().getPlayers())
				service.playDomino(p, p.getDominosInHand().get(0));
		}
		
		assertEquals(1, service.getGame().getPlayedHands().size());
	}
}
