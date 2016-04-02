package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import model.Board;
import model.Cell;
import model.Direction;
import model.Line;
import model.Point;
import model.Stone;

public class Utils {

	public static final Direction[] directions = {Direction.N, Direction.NW, Direction.W, Direction.SW};

	public static final Stone[] sides = new Stone[] { Stone.BLACK, Stone.WHITE };

	public static final Random RANDOM = new Random();

	private static final Map<Cell,List<Line>> linesCache = new HashMap<Cell, List<Line>>();

	public static List<Line> getReferenceLines2(Cell cell) {
		List<Line> list = linesCache.get(cell);
		if (list == null) {
			list = new ArrayList<Line>();
			for (Direction direction : directions) {
				Line line = getLine(cell, direction);
				if (line.size() >= 5) {
					list.add(line);
				}
			}
			linesCache.put(cell, list);
		}

		return list;
	}

	public static Line getLine(Cell cellInLine, Direction direction) {
		Direction oppo = direction.getOpposite();
		int x = cellInLine.getX();
		int y = cellInLine.getY();
		if (oppo.x == 0) {
			y = get(y, oppo.y);
		} else if (oppo.y == 0){
			x = get(x, oppo.x);
		} else {
			while (isInBoardRange(x + oppo.x)
					&& isInBoardRange(y + oppo.y)) {
				x = x + oppo.x;
				y = y + oppo.y;
			}
		}
		
		Board board = cellInLine.getBoard();
		Cell fromCell = board.get(x, y);
		Line line = fromCell.getReferenceLine(direction);
		if (line != null) {
			return line;
		}
		return new Line(board.get(x, y), direction);
	}

	private static int get(int y, int y2) {
		if (y2 == 1) {
			return Board.COLUMN - 1;
		} else {
			return 0;
		}
	}

	public static boolean isInBoardRange(int i) {
		return i >= 0 && i < Board.COLUMN;
	}

	public static boolean isInBoardRange(Point p) {
		return isInBoardRange(p.getX()) && isInBoardRange(p.getY());
	}

	public static int random(int range) {
		return RANDOM.nextInt(range);
	}

	public static int random(int rangeStart, int rangeEnd) {
		int range = rangeEnd + 1 - rangeStart;
		return random(range) + rangeStart;
	}

	public static Point random(Point point, int range) {
		int x = point.getX();
		int y = point.getY();
		int xRangeStart = x - range < 0 ? 0 : x - range;
		int xRangeEnd = x + range >= Board.COLUMN ? Board.COLUMN - 1 : x + range;
		int yRangeStart = y - range < 0 ? 0 : y - range;
		int yRangeEnd = y + range >= Board.COLUMN ? Board.COLUMN - 1 : y + range;
		return Point.get(random(xRangeStart, xRangeEnd), random(yRangeStart, yRangeEnd));
	}
}
