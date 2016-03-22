package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Cell;
import model.Direction;
import model.Line;
import model.Point;
import model.Stone;
import model.Table;

public class Utils {
	
	public static final Direction[] directions = {Direction.N, Direction.NW, Direction.W, Direction.SW};
	
	public static final Random RANDOM = new Random();
	
	public static List<Cell> getLineCells(Cell cell, Direction direction) {
		Cell startCell = cell;
		Direction oppo = direction.getOpposite();
		while(true) {
			Cell c = startCell.getNearbyCell(oppo);
			if (c == null) {
				break;
			}
			startCell = c;
		}
		
		List<Cell> cells = new ArrayList<Cell>();
		while(startCell != null) {
			cells.add(startCell);
			startCell = startCell.getNearbyCell(direction);
		}
		
		return cells;
	}
	
	public static List<Line> getReferenceLines(Cell cell) {
		List<Line> list = new ArrayList<Line>();
		for (Direction direction : directions) {
			List<Cell> cells = getLineCells(cell, direction);
			Line line = new Line(cells, direction);
			list.add(line);
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
	
	public static boolean isInTableRange(int i) {
		return i >= 0 && i < Table.COLUMN;
	}
	
	public static boolean isInTableRange(Point p) {
		return isInTableRange(p.getX()) && isInTableRange(p.getY());
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
		int xRangeEnd = x + range >= Table.COLUMN ? Table.COLUMN - 1 : x + range;
		int yRangeStart = y - range < 0 ? 0 : y - range;
		int yRangeEnd = y + range >= Table.COLUMN ? Table.COLUMN - 1 : y + range;
		return Point.get(random(xRangeStart, xRangeEnd), random(yRangeStart, yRangeEnd));
	}
}
