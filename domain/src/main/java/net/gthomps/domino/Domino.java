package net.gthomps.domino;

public class Domino implements Comparable<Domino> {
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
	
	public boolean isTrump(int trump) {
		return getHighSide() == trump || getLowSide() == trump;
	}

	public boolean beats(Domino ledDomino, int trump, Domino domino) {
		if (ledDomino.isTrump(trump))
			return beats(trump, trump, domino);
		else
			return beats(ledDomino.getHighSide(), trump, domino);
		
	}
	
	public boolean beats(int suit, int trump, Domino domino) {
		if ( isTrump(trump) && !domino.isTrump(trump) ) {
			return true;
		} else if (!isTrump(trump) && domino.isTrump(trump)) {
			return false;
		} else if (isTrump(trump) && domino.isTrump(trump)) {
			if (domino.getIsDouble())
				return false;

			if (getIsDouble())
				return true;
			
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
			
			if (suit == getHighSide())
				return getIsDouble() || domino.getLowSide() < getLowSide();
				
			if (suit == getLowSide())
				return getIsDouble() || domino.getLowSide() < getHighSide();
			
			return false;
		}
	}
	
	public boolean followsSuit(Domino ledDomino, int trump) {
		if (ledDomino.isTrump(trump))
			return isTrump(trump);
		
		if (isTrump(trump))
			return false;
		
		return ledDomino.getHighSide() == getHighSide() || ledDomino.getHighSide() == getLowSide();
	}
	
	public boolean equals(Domino rhs) {
		return getHighSide() == rhs.getHighSide() && getLowSide() == rhs.getLowSide();
	}

	@Override
	public int compareTo(Domino rhs) {
		return toString().compareTo(rhs.toString());
	}
}
