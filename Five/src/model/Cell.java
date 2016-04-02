package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.GameConstants;

public class Cell {
	private final Board board;
	private int x;
	private int y;
	private Stone stone = Stone.NONE;
	private List<ICellListener> listeners = new ArrayList<ICellListener>();
	private List<Cell> nearByCells;
	private Map<Direction, Line> referenceLines = new HashMap<Direction, Line>();

	public Cell(int x, int y, Board board) {
		this.x = x;
		this.y = y;
		this.board = board;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setStone(Stone stone) {
		Stone old = this.stone;
		this.stone = stone;
		fireCellChanged(old, stone);
	}

	public Stone getStone() {
		return stone;
	}

	public Cell getNearbyCell(Direction direction) {
		return board.get(x + direction.x, y + direction.y);
	}
	
	public List<Cell> getNearbyCells() {
		if (nearByCells == null) {
			nearByCells = new ArrayList<Cell>();
			for (Direction d : Direction.values()) {
				for(int i = 1; i <= GameConstants.distance; i++) {
					Cell cell = board.get(x + d.x * i, y + d.y * i);
					if (cell != null) {
						nearByCells.add(cell);
					}
				}
			}
		}
		
		return nearByCells;
	}

	public Board getBoard() {
		return board;
	}

	@Override
	public String toString() {
		return stone.chr + "[" + x + "," + y + "]";
	}

	public void addListener(ICellListener listener) {
		listeners.add(listener);
	}

	private void fireCellChanged(Stone oldVal, Stone newVal) {
		for(ICellListener listener : listeners) {
			listener.cellChanged(this, oldVal, newVal);
		}
	}
	
	public void addReferenceLine(Direction direction, Line line) {
		referenceLines.put(direction, line);
	}
	
	
	public Line getReferenceLine(Direction direction) {
		return referenceLines.get(direction);
	}
}
