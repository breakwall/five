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
		return model[x][y];
	}
	
	public Cell get(Point point) {
		return get(point.getX(), point.getY());
	}
	
	public void print() {
		System.out.println("0 1 2 3 4 5 6 7 8 9 0 1 2 3 4");
		for(int i = 0; i < COLUMN; i++) {
			StringBuffer sb = new StringBuffer();
			for (int j= 0; j< COLUMN; j++) {
				String str;
				switch (model[j][i].getStone()) {
				case BLACK:
					str = "x ";
					break;
				case WHITE:
					str = "o ";
					break;
				default:
					str = "- ";
					break;
				}
				sb.append(str);
			}
			System.out.println(sb.toString() + i);
		}
	}
}
