package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;
import algorithm.LineParser;
import algorithm.LineParser.ParseInfo;

public class Line implements ICellListener {
	private List<Cell> cells;
	private Direction direction;
	private String lineStr = null;
	private Map<Stone, ParseInfo> lineInfo = new HashMap<>();

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

	public String getStr() {
		return lineStr;
	}

	private void updateLineStrMap() {
		StringBuffer sb = new StringBuffer();
		for (Cell c : cells) {
			sb.append(c.getStone().chr);
		}

		lineStr = sb.toString();
		updateLineInfo();
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
	public void cellChanged(Cell cell, Stone side) {
		int i = cells.indexOf(cell);
		lineStr = lineStr.substring(0, i) + cell.getStone().chr + lineStr.substring(i + 1);
		updateLineInfo();
	}

	private void updateLineInfo() {
		for(Stone stone : Utils.sides) {
			lineInfo.put(stone, LineParser.parse(lineStr, stone));
		}
	}

	public ParseInfo getParseInfo(Stone stone) {
		return lineInfo.get(stone);
	}
}
