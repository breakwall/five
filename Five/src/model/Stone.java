package model;

public enum Stone {
	BLACK("B"), WHITE("W"), NONE("-");

	public String str;
	
	private Stone (String str) {
		this.str = str;
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
