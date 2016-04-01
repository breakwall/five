package model;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	private final Board board;
	private int x;
	private int y;
	private Stone stone = Stone.NONE;
	private List<ICellListener> listeners = new ArrayList<ICellListener>();

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
}
