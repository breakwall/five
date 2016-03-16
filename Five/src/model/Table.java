package model;

import controller.Utils;


public class Table {
	public static final int COLUMN = 15;
	private Cell[][] tableModel = new Cell[COLUMN][COLUMN];
	
	public Table() {
		initTable();
	}
	
	private void initTable() {
		for (int i = 0; i < COLUMN; i++) {
			for (int j = 0 ; j < COLUMN; j++) {
				tableModel[i][j] = new Cell(Point.get(i, j));
			}
		}
	}
	
	public boolean isAvailable(Point point) {
		int x = point.getX();
		int y = point.getY();
		if (Utils.isInTableRange(x) && Utils.isInTableRange(y)) {
			return tableModel[x][y].equals(Stone.NONE);
		}
		
		return false;
	}
	
	public Cell get(int x, int y) {
		return tableModel[x][y];
	}
	
	public void print() {
		for(int i = 0; i < COLUMN; i++) {
			StringBuffer sb = new StringBuffer();
			for (int j= 0; j< COLUMN; j++) {
				String str;
				switch (tableModel[j][i].getStone()) {
				case BLACK:
					str = "B ";
					break;
				case WHITE:
					str = "W ";
					break;
				default:
					str = "- ";
					break;
				}
				sb.append(str);
			}
			System.out.println(sb.toString());
		}
	}
}
