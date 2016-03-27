package model;

import java.util.ArrayList;
import java.util.List;

public class Line {
	private List<Cell> cells;
	private Direction direction;

	public Line(List<Cell> cells, Direction direction) {
		this.cells = cells;
		this.direction = direction;
	}
	
	public Line(Cell from, Direction direction) {
		this(calcCells(from, direction), direction);
	}
	
	private static List<Cell> calcCells(Cell from, Direction direction) {
		List<Cell> cells = new ArrayList<Cell>();
		for (Cell cell = from; cell != null; cell = cell
				.getNearbyCell(direction)) {
			cells.add(cell);
		}
		return cells;
	}
	
	public int size() {
		return cells.size();
	}

	public Direction getDirection() {
		return direction;
	}
	
	public List<Cell> getCells() {
		return cells;
	}
	
	public Cell getCell(int index) {
		return cells.get(index);
	}
	
	public Line getSubLine(int fromIndex, int toIndex) {
		List<Cell> list = cells.subList(fromIndex, toIndex + 1);
		Line subLine = new Line(list, direction);
		return subLine;
	}
	
	public boolean containsCell(Cell cell) {
		for (Cell p : cells) {
			if(p.equals(cell)) {
				return true;
			}
		}
		
		return false;
	}
	
	public Cell getForwardCell() {
		Cell fromCell = cells.get(0);
		return fromCell.getNearbyCell(direction.getOpposite());
	}
	
	public Cell getBackwardCell() {
		Cell toCell = cells.get(cells.size() - 1);
		return toCell.getNearbyCell(direction);
	}
	
	public int indexOf(Cell cell) {
		return cells.indexOf(cell);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(direction).append(":");
		for (Cell c : cells) {
			sb.append(c.getStone().str);
		}
		
		sb.append(cells.get(0).getPoint());
		sb.append("->");
		sb.append(cells.get(cells.size() - 1).getPoint());
		return sb.toString();
	}
}
