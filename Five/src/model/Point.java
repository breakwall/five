package model;

import java.util.HashMap;
import java.util.Map;

public class Point {
	
	private static Map<String, Point> cache = new HashMap<>();
	
	public static Point get(int x, int y) {
		String key = getKey(x, y);
		Point point = cache.get(key);
		if (point == null) {
			point = new Point(x, y);
			cache.put(key, point);
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
	
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
