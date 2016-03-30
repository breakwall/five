package model;

import java.util.HashMap;
import java.util.Map;

import controller.Utils;

public class Point {

	private static Map<String, Point> cache = new HashMap<String, Point>();

	public static Point get(int x, int y) {
		String key = getKey(x, y);
		Point point = cache.get(key);
		if (point == null) {
			if (Utils.isInBoardRange(x) && Utils.isInBoardRange(y)) {
				point = new Point(x, y);
				cache.put(key, point);
			}
		}

		return point;
	}

	private static String getKey(int x, int y) {
		return x + "," + y;
	}

	private final int x;
	private final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Point translate(int i, int j) {
		return Point.get(x + i, y + j);
	}

	@Override
	public String toString() {
		return "[" + x + "," + y + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
