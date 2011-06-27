package net.gthomps.tx42;

public class Game {
	private Player[] _players;
	private Domino[] _dominos;
	private Hand _currentHand;
	
	public Game(Player[] players, Domino[] dominos) {
		_players = players;
		_dominos = dominos;
	}
	
	public Player[] getPlayers() {
		return _players;
	}
	
	public Domino[] getDominos() {
		return _dominos;
	}
	
	public Object getCurrentHand() {
		return _currentHand;
	}
	
	public void createNewHand() {
		_currentHand = new Hand();
	}
}
