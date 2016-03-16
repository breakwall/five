package model;

public enum Direction {
	N(0, -1), S(0, 1), W(-1, 0), E(1, 0), NW(-1, -1), NE(1, -1), SW(-1, 1), SE(
			1, 1);

	public int x;
	public int y;

	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Direction getOpposite() {
		switch (this) {
		case N:
			return S;
		case S:
			return N;
		case E:
			return W;
		case W:
			return E;
		case NE:
			return SW;
		case SW:
			return NE;
		case SE:
			return NW;
		case NW:
			return SE;
		default:
			return this;
		}
	}
}
