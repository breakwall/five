package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		
		for (int i = 0 ; i < line.size(); i++) {
			if (table.get(line.getPoint(i)).getStone() == stone) {
				times ++;
				if (times == 5) {
					return true;
				}
			} else {
				times = 0;
			}
		}
		return false;
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
	
	public List<Cell> getNearAvailable() {
		List<Cell> cells = progress.getCells();
		Set<Cell> all = new HashSet<Cell>();
		for (Cell cell : cells) {
			all.addAll(getNearAvailable(cell.getPoint()));
		}
		return new ArrayList<Cell>(all);
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
	
	public Table getTable() {
		return table;
	}
	
	public void move(Stone stone, Point point) {
		Cell cell = table.get(point.getX(), point.getY());
		cell.setStone(stone);
		progress.addStep(cell);
	}
}
