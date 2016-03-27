package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Board;
import model.Cell;
import model.Direction;
import model.Line;
import model.Point;
import model.Stone;

public class Utils {
	
	public static final Direction[] directions = {Direction.N, Direction.NW, Direction.W, Direction.SW};
	
	public static final Random RANDOM = new Random();
	
	public static List<Cell> getLineCells(Cell cellInLine, Direction direction) {
		Cell startCell = cellInLine;
		Direction oppo = direction.getOpposite();
		for (int i = 0; i < 4; i++) {
			Cell c = startCell.getNearbyCell(oppo);
			if (c == null) {
				break;
			}
			startCell = c;
		}
		
		List<Cell> cells = new ArrayList<Cell>();
		Cell cell = startCell;
		for (int i = 0; i < 9; i++) {
			cells.add(cell);
			cell = cell.getNearbyCell(direction);
			if (cell ==null) {
				break;
			}
		}
		
		return cells;
	}
	
	public static List<Line> getReferenceLines(Cell cell) {
		List<Line> list = new ArrayList<Line>();
		for (Direction direction : directions) {
			List<Cell> cells = getLineCells(cell, direction);
			if (cells.size() >= 5) {
				Line line = new Line(cells, direction);
				list.add(line);
			}
		}
		
		return list;
	}
	
	public static int getWeightOfCell(Cell cell, Stone targetStone) {
		int weight = 0;
		for (Direction direction : directions) {
			int numberOfCell = getNumberOfNearbyCell(cell, direction, targetStone);
			weight = weight + (int)Math.pow(10, numberOfCell);
		}
		
		return weight;
	}
	
	private static int getNumberOfNearbyCell(Cell cell, Direction direction, Stone targetStone) {
		int numberOfNearbyCell = 0;
		int cellCapbility = 1;
		for (Direction d : new Direction[] {direction, direction.getOpposite()}) {
			boolean isNearby = true;
			Cell nearby = cell.getNearbyCell(d);
			while(nearby != null) {
				if (isNearby && nearby.getStone() == targetStone) {
					numberOfNearbyCell ++;
				} else {
					isNearby = false;
				}
				
				if (nearby.getStone() == targetStone.getOpposite()) {
					break;
				}
				cellCapbility ++;
				nearby = nearby.getNearbyCell(d);
			}
		}
		
		if (cellCapbility >= 5) {
			return numberOfNearbyCell;
		}
		
		return 0;
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
	
	public static String getStrPattern(String pattern, Stone currentStone) {
		String strPattern = pattern;
		strPattern = strPattern.replace("1", currentStone.str);
		strPattern = strPattern.replace("0", Stone.NONE.str);
		strPattern = strPattern.replace("2", currentStone.getOpposite().str);
		return strPattern;
	}
}
