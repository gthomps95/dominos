package net.gthomps.tx42;

import java.util.ArrayList;

public class Domino {
	public static final short MIN_SIDE_VALUE = 0;
	public static final short MAX_SIDE_VALUE = 6;
	
	private short _lowSide;
	private short _highSide;
	private boolean _isDouble;
	
	private void checkSideBounds(short side) {
		if (side > MAX_SIDE_VALUE || side < MIN_SIDE_VALUE)
			throw new RuntimeException(String.format("Side (%d) must be in range [%d-%d].", side, MIN_SIDE_VALUE, MAX_SIDE_VALUE));
	}

	public Domino(short side1, short side2) {
		checkSideBounds(side1);
		checkSideBounds(side2);
		
		_highSide = (short) Math.max(side1, side2);
		_lowSide = (short) Math.min(side1, side2);
		_isDouble = _lowSide == _highSide;
	}
	
	public short getHighSide() {
		return _highSide;
	}
	
	public short getLowSide() {
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
		
		for (short i = MAX_SIDE_VALUE; i >= MIN_SIDE_VALUE; i--) {
			for (short j = i; j >= MIN_SIDE_VALUE; j--) {
				dominos.add(new Domino(i,j));
			}
		}
		
		return dominos.toArray(new Domino[dominos.size()]);
	}

	public static Domino[] shuffle(Domino[] dominos) {
		return dominos;
	}
}
