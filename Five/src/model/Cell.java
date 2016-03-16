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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((point == null) ? 0 : point.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (point == null) {
			if (other.point != null)
				return false;
		} else if (!point.equals(other.point))
			return false;
		return true;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof Cell)) {
//			return false;
//		}
//		return point.equals(((Cell)obj).getPoint());
//	}
}
