package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import controller.Utils;

public class BoardHelper {
	private Board board;
	private Progress progress = new Progress();
	private int tryMoveTimes = -1;
	
	public BoardHelper(Board board) {
		this.board = board;
	}
	
	public boolean checkWin(Cell cell) {
		List<Line> lines = Utils.getReferenceLines(cell);
		for (Line line : lines) {
			if (containsRow(cell.getStone(), line)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsRow(Stone stone, Line line) {
		int times = 0;
		
		for (int i = 0 ; i < line.size(); i++) {
			if (line.getCell(i).getStone() == stone) {
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
				if (x + i < 0 || x + i >= Board.COLUMN || y + j < 0
						|| y + j >= Board.COLUMN) {
					continue;
				}
				
				Point p = Point.get(x + i, y + j);
				if (board.isAvailable(p)) {
					list.add(board.get(p));
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
	
	public Board getBoard() {
		return board;
	}
	
	public void move(Stone stone, Cell cell) {
		cell.setStone(stone);
		progress.addStep(cell);
	}
	
	public void tryMove(Stone stone, Cell cell) {
		move(stone, cell);
		tryMoveTimes++;
	}
	
	/**
	 * clean the try moves
	 */
	public void rollbackTry() {
		if (tryMoveTimes == 0) {
			return;
		}
		
		progress.removeStep(progress.size() - 1);
		tryMoveTimes--;
	}
}
