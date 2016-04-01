package model;

import java.util.Stack;

public class Progress {
	private Stack<Cell> cells = new Stack<Cell>();

	public Cell getLastCell() {
		if (cells.size() == 0) {
			return null;
		}
		return cells.peek();
	}

	public Stone getCurrentStone() {
		if (cells.size() % 2 == 0) {
			return Stone.BLACK;
		} else {
			return Stone.WHITE;
		}
	}

	public void addCell(Cell cell) {
		cells.push(cell);
	}

	public Stack<Cell> getCells() {
		return cells;
	}

	public int size() {
		return cells.size();
	}

	public void rollback() {
		cells.pop();
	}
}
