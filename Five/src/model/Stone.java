package model;

public enum Stone {
	BLACK, WHITE, NONE;

	public Stone getOpposite() {
		switch (this) {
		case BLACK:
			return WHITE;
		case WHITE:
			return BLACK;
		default:
			return this;
		}
	}
}
