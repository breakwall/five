package model;

import controller.Utils;


public class Board {
	public static final int COLUMN = 15;
	private Cell[][] model = new Cell[COLUMN][COLUMN];

	public Board() {
		initBoard();
	}

	private void initBoard() {
		for (int i = 0; i < COLUMN; i++) {
			for (int j = 0 ; j < COLUMN; j++) {
				model[i][j] = new Cell(Point.get(i, j), this);
			}
		}
	}

	public boolean isAvailable(Point point) {
		int x = point.getX();
		int y = point.getY();
		if (Utils.isInBoardRange(x) && Utils.isInBoardRange(y)) {
			return model[x][y].getStone().equals(Stone.NONE);
		}

		return false;
	}

	public Cell get(int x, int y) {
		if (Utils.isInBoardRange(x) && Utils.isInBoardRange(y)) {
			return model[x][y];
		}

		return null;
	}

	public Cell get(Point point) {
		return get(point.getX(), point.getY());
	}
}
