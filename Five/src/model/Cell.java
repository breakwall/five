package model;

public class Cell {
	private final Point point;
	private Stone stone = Stone.NONE;
	
	public Cell(Point point) {
		this.point = point;
	}
	
	public void setStone(Stone stone) {
		this.stone = stone;
	}
	
	public Stone getStone() {
		return stone;
	}
	
	public Point getPoint() {
		return point;
	}
}
