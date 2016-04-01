package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.Utils;

public class Line implements ICellListener {
	private List<Cell> cells;
	private Direction direction;
	private Map<Stone, String> lineStrMap = new HashMap<Stone, String>();

	public Line(List<Cell> cells, Direction direction) {
		this.cells = cells;
		this.direction = direction;
		updateLineStrMap();
		for (Cell cell : cells) {
			cell.addListener(this);
		}
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

//	public Line getSubLine(int fromIndex, int toIndex) {
//		List<Cell> list = cells.subList(fromIndex, toIndex + 1);
//		Line subLine = new Line(list, direction);
//		return subLine;
//	}

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

	public String getStr(Stone currentFocus) {
		return lineStrMap.get(currentFocus);
	}

	private void updateLineStrMap() {
		for(Stone s : Utils.sides) {
			StringBuffer sb = new StringBuffer();
			Cell forwardCell = getForwardCell();
			if (forwardCell == null
					|| forwardCell.getStone() == s.getOpposite()) {
				sb.append(s.getOpposite().chr);
			}

			for (Cell c : cells) {
				sb.append(c.getStone().chr);
			}

			Cell backwardCell = getForwardCell();
			if (backwardCell == null
					|| backwardCell.getStone() == s.getOpposite()) {
				sb.append(s.getOpposite().chr);
			}
			lineStrMap.put(s, sb.toString());
		}
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
		updateLineStrMap();
	}
}
