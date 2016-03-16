package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.Utils;

public class TableHelper {
	private Table table;
	private Progress progress = new Progress();
	public TableHelper(Table table) {
		this.table = table;
	}
	
	public boolean checkWin(Stone stone, Point point) {
		List<Line> lines = Utils.getReferenceLines(point);
		for (Line line : lines) {
			if (containsRow(stone, line)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsRow(Stone stone, Line line) {
		int times = 0;
		Point start = line.getStartPoint();
		Point end = line.getEndPoint();
		Direction direction = line.getDirection();
		
		int x = start.getX();
		int y = start.getY();
		
		while (Utils.isInTableRange(x) && Utils.isInTableRange(x)
				&& x != end.getX() && y != end.getY()) {
			if (table.get(x, y).equals(stone)) {
				times = times + 1;
			} else {
				times = 0;
			}

			x = x + direction.x;
			y = y + direction.y;
		}
		
		return times >= 5;
	}
	
	public List<Cell> getNearAvailable(Point point) {
		int x = point.getX();
		int y = point.getY();
		List<Cell> list = new ArrayList<Cell>();
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (x + i < 0 || x + i >= Table.COLUMN || y + j < 0
						|| y + j >= Table.COLUMN) {
					continue;
				}
				
				Point p = Point.get(x + i, y + j);
				if (table.isAvailable(p)) {
					list.add(table.get(p));
				}
			}
		}
		
		return list;
	}
	
	public Step getLastStep() {
		return progress.getLastStep();
	}
	
//	public Map<Integer, Line> getRow(Stone stone) {
//		List<Cell> cells = progress.getCells(stone);
//		for (Cell cell : cells) {
//			List<Line> lines = Utils.getReferenceLines(cell.getPoint());
//		}
//	}
	
	public Progress getProgress() {
		return progress;
	}
	
	public void move(Stone stone, Point point) {
		Cell cell = table.get(point.getX(), point.getY());
		cell.setStone(stone);
		progress.addStep(cell);
	}
}
