package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.Direction;
import model.Line;
import model.Point;
import model.Table;

public class Utils {
	
	public static final Random RANDOM = new Random();
	
	public static Point getEdgePoint(Point point, Direction direction) {
		int x = point.getX();
		int y = point.getY();
		while (isInTableRange(x + direction.x) && isInTableRange(y + direction.y)) {
			x = x + direction.x;
			y = y + direction.y;
		}
		return point.get(x, y);
	}
	
	public static List<Line> getReferenceLines(Point point) {
		Direction[] directions = {Direction.N, Direction.NW, Direction.W, Direction.SW};
		List<Line> list = new ArrayList<Line>();
		for (Direction direction : directions) {
			Point startPoint = getEdgePoint(point, direction);
			Point endPoint = getEdgePoint(point, direction.getOpposite());
			Line line = new Line(startPoint, endPoint, direction);
			list.add(line);
		}
		
		return list;
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
