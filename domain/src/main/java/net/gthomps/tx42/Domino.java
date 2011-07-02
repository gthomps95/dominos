package net.gthomps.tx42;

import java.util.ArrayList;

public class Domino {
	public static final int MIN_SIDE_VALUE = 0;
	public static final int MAX_SIDE_VALUE = 6;
	
	private int _lowSide;
	private int _highSide;
	private boolean _isDouble;
	
	private void checkSideBounds(int side) {
		if (side > MAX_SIDE_VALUE || side < MIN_SIDE_VALUE)
			throw new RuntimeException(String.format("Side (%d) must be in range [%d-%d].", side, MIN_SIDE_VALUE, MAX_SIDE_VALUE));
	}
	
	public Domino(int side1, int side2) {
		checkSideBounds(side1);
		checkSideBounds(side2);
		
		_highSide = (int) Math.max(side1, side2);
		_lowSide = (int) Math.min(side1, side2);
		_isDouble = _lowSide == _highSide;
	}
	
	public int getHighSide() {
		return _highSide;
	}
	
	public int getLowSide() {
		return _lowSide;
	}
	
	public boolean getIsDouble() {
		return _isDouble;
	}
	
	public String toString() {
		return String.format("%d:%d", _highSide, _lowSide);
	}
	
	public static Domino[] getFullDominoSet() {
		ArrayList<Domino> dominos = new ArrayList<Domino>();
		
		for (int i = MAX_SIDE_VALUE; i >= MIN_SIDE_VALUE; i--) {
			for (int j = i; j >= MIN_SIDE_VALUE; j--) {
				dominos.add(new Domino(i,j));
			}
		}
		
		return dominos.toArray(new Domino[dominos.size()]);
	}
}
