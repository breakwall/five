package model;

import java.util.ArrayList;
import java.util.List;

public class Line implements ICellListener {
	private List<Cell> cells;
	private Direction direction;
	private String lineStr = null;

	public Line(List<Cell> cells, Direction direction) {
		this.cells = cells;
		this.direction = direction;
		updateLineStrMap();
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

	public Cell getCell(int index) {
		return cells.get(index);
	}

	public String getStr(Stone currentFocus) {
		char oppo = currentFocus.getOpposite().chr;
		return oppo + lineStr.toString() + oppo;
	}

	private void updateLineStrMap() {
		StringBuffer sb = new StringBuffer();
		for (Cell c : cells) {
			sb.append(c.getStone().chr);
		}
		
		lineStr = sb.toString();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (Cell c : cells) {
			sb.append(c.getStone().chr);
		}

		sb.append(cells.get(0).toString());
		sb.append("->");
		sb.append(cells.get(cells.size() - 1).toString());
		return sb.toString();
	}

	@Override
	public void cellChanged(Cell cell, Stone oldVal, Stone newVal) {
		int i = cells.indexOf(cell);
		lineStr = lineStr.substring(0, i) + newVal.chr + lineStr.substring(i + 1);
	}
}
