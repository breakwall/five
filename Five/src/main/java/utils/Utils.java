package utils;

import java.util.Random;

import model.Board;
import model.Direction;
import model.Point;
import model.Stone;
import algorithm.Type;

public class Utils {

	public static final Direction[] rays = {Direction.N, Direction.NW, Direction.W, Direction.SW};
	public static final Stone[] sides = new Stone[] { Stone.BLACK, Stone.WHITE };

	public static final Type[] ALL_TYPES = Type.values();
	public static final Type[] KEY_TYPES = {Type.HUO4, Type.CHONG4, Type.HUO3};
	public static final Type[] INTERSECT_TO_KEY_TYPES = {Type.MIAN3, Type.HUO2};
	public static final Type[] NON_KEY_TYPES = {Type.MIAN3, Type.HUO2, Type.MIAN2};


	public static final Random RANDOM = new Random();

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
