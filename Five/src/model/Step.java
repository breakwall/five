package model;

public class Step {
	private int index;
	private Cell cell;
	public Step(int index, Cell cell) {
		this.index = index;
		this.cell = cell;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Cell getCell() {
		return cell;
	}
	
	@Override
	public String toString() {
		return index + "-> stone: " + cell.getStone() + ", point: " + cell.getPoint();
	}
}
