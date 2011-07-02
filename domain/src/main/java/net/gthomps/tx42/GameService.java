package net.gthomps.tx42;

public class GameService {
	private Game game;

	
	public void createNewGame() {
		Player[] players = Player.createFourGenericPlayers();
		game = new Game(players);
		Hand hand = game.startNewHand();
		hand.dealDominos(Domino.getFullDominoSet(), players);
	}

	public void placeBid(Bid bid) {
		game.getCurrentHand().addBid(bid);
		
		if (game.getCurrentHand().getBids().size() == 4) {
			game.getCurrentHand().startNewTrick();
		}
	}
	
	public void playDomino(Player player, Domino domino) {
		game.getCurrentHand().getCurrentTrick().playDomino(player.playDomino(domino));
		
		if (game.getCurrentHand().getCurrentTrick().getPlayedDominos().size() == 4) {
			game.getCurrentHand().completeTrick();
			
			if (game.getCurrentHand().getPlayedTricks().size() < 7) {
				game.getCurrentHand().startNewTrick();
			}
			else {
				game.completeHand();
				
				if (!game.hasWinner()) { 
					Hand hand = game.startNewHand();
					hand.dealDominos(Domino.getFullDominoSet(), game.getPlayers());
				}
			}
		}
	}
	
	public void forfiet(Player player) {
		
	}

	public Game getGame() {
		return game;
	}
}
