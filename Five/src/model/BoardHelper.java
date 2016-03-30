package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import logger.GameLogger;
import controller.Utils;

public class BoardHelper {
	private Board board;
	private Progress progress = new Progress();
	private int tryMoveTimes = 0;

	public BoardHelper(Board board) {
		this.board = board;
	}

	public boolean checkWin(Cell cell) {
		List<Line> lines = Utils.getReferenceLines(cell, false);
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

	public Set<Cell> getNearAvailable(Cell cell) {
		Set<Cell> set = new LinkedHashSet<Cell>();
		Direction[] directions = Utils.directions;
		for (Direction direction : directions) {
			Cell c = cell.getNearbyCell(direction);
			if (c != null && c.getStone() == Stone.NONE) {
				set.add(c);
			}

			Direction oppo = direction.getOpposite();
			c = cell.getNearbyCell(oppo);
			if (c != null && c.getStone() == Stone.NONE) {
				set.add(c);
			}
		}

		return set;
	}

	public List<Cell> getNearAvailable() {
		List<Cell> cells = progress.getCells();
		Set<Cell> all = new HashSet<Cell>();
		for (Cell cell : cells) {
			all.addAll(getNearAvailable(cell));
		}
		return new ArrayList<Cell>(all);
	}

	public Progress getProgress() {
		return progress;
	}

	public Board getBoard() {
		return board;
	}

	public void move(Stone stone, Cell cell) {
		cell.setStone(stone);
		progress.addCell(cell);
		GameLogger.getInstance().logInfo(progress.size() + "-> stone: " + cell.getStone() + ", point: " + cell.getPoint());
	}

	public void tryMove(Stone stone, Cell cell) {
		cell.setStone(stone);
		progress.addCell(cell);
		tryMoveTimes++;
	}

	/**
	 * clean the try moves
	 */
	public void rollbackTry() {
		if (tryMoveTimes == 0) {
			return;
		}
		Cell cell = progress.getLastCell();
		cell.setStone(Stone.NONE);
		progress.rollback();
		tryMoveTimes--;
	}

	public int getTryMoveTimes() {
		return tryMoveTimes;
	}

	public String getBoardStr() {
		StringBuffer sb = new StringBuffer();
		Point lastCellPoint = progress.getLastCell().getPoint();
		sb.append("\n0 1 2 3 4 5 6 7 8 9 0 1 2 3 4\n");
		for(int i = 0; i < Board.COLUMN; i++) {
			for (int j= 0; j< Board.COLUMN; j++) {
				String str;
				switch (board.get(j, i).getStone()) {
				case BLACK:
					str = "x";
					break;
				case WHITE:
					str = "o";
					break;
				default:
					str = "-";
					break;
				}
				sb.append(str);
				if (lastCellPoint.getX() == j && lastCellPoint.getY() == i) {
					sb.append(".");
				} else {
					sb.append(" ");
				}
			}
			sb.append(i).append("\n");
		}

		return sb.toString();
	}
}
