package model;

public enum Stone {
	BLACK('B'), WHITE('W'), NONE('-');

	public char chr;

	private Stone (char chr) {
		this.chr = chr;
	}

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
