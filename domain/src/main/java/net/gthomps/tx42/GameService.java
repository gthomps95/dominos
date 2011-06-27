package net.gthomps.tx42;

public class GameService {
	
	public static Game createNewGame(Player[] players) {
		Game game = new Game(players, Domino.getFullDominoSet());

		return game; 
	}
	
	public static void startGame(Game game) {
		Domino[] dominos = Domino.shuffle(Domino.getFullDominoSet());
		game.createNewHand();
	}

}
