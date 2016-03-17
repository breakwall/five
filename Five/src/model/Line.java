package model;

import java.util.ArrayList;
import java.util.List;

public class Line {
	private List<Point> points;
	private Direction direction;

	public Line(List<Point> points, Direction direction) {
		this.points = points;
		this.direction = direction;
	}
	
	public int size() {
		return points.size();
	}

	public Direction getDirection() {
		return direction;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public Point getPoint(int index) {
		return points.get(index);
	}
	
	public Line getSubLine(int fromIndex, int toIndex) {
		List<Point> list = points.subList(fromIndex, toIndex + 1);
		Line subLine = new Line(list, direction);
		return subLine;
	}
	
	public boolean containsPoint(Point point) {
		for (Point p : points) {
			if(p.equals(point)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return points.get(0).toString() + "->" + points.get(points.size() - 1).toString();
	}
}
