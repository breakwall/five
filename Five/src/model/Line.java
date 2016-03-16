package model;

public class Line {
	private Point startPoint;
	private Point endPoint;
	private Direction direction;

	public Line(Point startPoint, Point endPoint, Direction direction) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.direction = direction;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public Point getStartPoint() {
		return startPoint;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	@Override
	public String toString() {
		return startPoint.toString() + "->" + endPoint.toString();
	}
}
