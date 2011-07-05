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
	
	public boolean isTrump(int trump) {
		return getHighSide() == trump || getLowSide() == trump;
	}

	// TODO does 0:0 beat 0:3 if trump is zero
	public boolean beats(int trump, Domino domino) {
		if ( isTrump(trump) && !domino.isTrump(trump) ) {
			return true;
		} else if (!isTrump(trump) && domino.isTrump(trump)) {
			return false;
		} else if (isTrump(trump) && domino.isTrump(trump)) {
			if (domino.getIsDouble())
				return false;
			
			if (domino.getHighSide() == trump && getHighSide() == trump)
				return getLowSide() > domino.getLowSide();
				
			else if (domino.getLowSide() == trump && getLowSide() == trump)
				return getHighSide() > domino.getHighSide();
				
			else if (domino.getLowSide() == trump && getHighSide() == trump)
				return getLowSide() > domino.getHighSide();
			
			else
				return getHighSide() > domino.getLowSide();
			
		} else {
			if (domino.getIsDouble())
				return false;
			
			if (domino.getHighSide() == getHighSide())
				return getIsDouble() || domino.getLowSide() < getLowSide();
				
			if (domino.getHighSide() == getLowSide())
				return getIsDouble() || domino.getLowSide() < getHighSide();
			
			return false;
		}
	}
	
	public int getPointCount() {
		int sum = getLowSide() + getHighSide();
		
		if (sum % 5 == 0)
			return sum;
		else
			return 0;
	}

	public boolean followsSuit(Domino ledDomino, int trump) {
		if (ledDomino.isTrump(trump))
			return isTrump(trump);
		
		return ledDomino.getHighSide() == getHighSide() || ledDomino.getHighSide() == getLowSide();
	}
	
	public boolean equals(Domino rhs) {
		return getHighSide() == rhs.getHighSide() && getLowSide() == rhs.getLowSide();
	}
}
