package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utils;


public class Board {
	public static final int COLUMN = 15;
	private Cell[][] model = new Cell[COLUMN][COLUMN];

	private static final Map<Cell,List<Line>> linesCache = new HashMap<Cell, List<Line>>();
	private List<ICellListener> listeners = new ArrayList<ICellListener>();

	public Board() {
		initBoard();
	}

	private void initBoard() {
		for (int i = 0; i < COLUMN; i++) {
			for (int j = 0 ; j < COLUMN; j++) {
				model[i][j] = new Cell(i, j, this);
			}
		}
	}

	public void addListener(ICellListener listener) {
		listeners.add(listener);
	}

//	public boolean isAvailable(Point point) {
//		int x = point.getX();
//		int y = point.getY();
//		if (Utils.isInBoardRange(x) && Utils.isInBoardRange(y)) {
//			return model[x][y].getStone().equals(Stone.NONE);
//		}
//
//		return false;
//	}

	public Cell get(int x, int y) {
		if (Utils.isInBoardRange(x) && Utils.isInBoardRange(y)) {
			return model[x][y];
		}

		return null;
	}

	public void fireCellChanged(Cell cell, Stone side) {
		List<Line> list = getReferenceLines(cell);
		for (Line line : list) {
			line.cellChanged(cell, side);
		}

		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).cellChanged(cell, side);
		}
	}

	public List<Line> getReferenceLines(Cell cell) {
		List<Line> list = linesCache.get(cell);
		if (list == null) {
			list = new ArrayList<Line>();
			for (Direction direction : Utils.rays) {
				Line line = getLine(cell, direction);
				if (line.size() >= 5) {
					list.add(line);
				}
			}
		}

		return list;
	}

	public Line getLine(Cell cellInLine, Direction direction) {
		Direction oppo = direction.getOpposite();
		int x = cellInLine.getX();
		int y = cellInLine.getY();
		if (oppo.x == 0) {
			// for vertically
			y = getXY(y, oppo.y);
		} else if (oppo.y == 0){
			// for horizontally
			x = getXY(x, oppo.x);
		} else {
			// for slanting
			while (Utils.isInBoardRange(x + oppo.x)
					&& Utils.isInBoardRange(y + oppo.y)) {
				x = x + oppo.x;
				y = y + oppo.y;
			}
		}

		Board board = cellInLine.getBoard();
		Cell fromCell = board.get(x, y);
		Line line = fromCell.getReferenceLine(direction);
		if (line == null) {
			line = new Line(fromCell, direction);
			fromCell.addReferenceLine(direction, line);
		}

		return line;
	}

	private int getXY(int y, int y2) {
		if (y2 == 1) {
			return Board.COLUMN - 1;
		} else {
			return 0;
		}
	}
}
