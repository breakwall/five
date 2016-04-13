package utils;

public class GameConstants {
	/**
	 * search depth
	 */
	public static final int DEPTH = 4;

	/**
	 * max search depth
	 */
	public static final int MAX_DEPTH = 20;


	/**
	 * max evaluation count for one move
	 */
	public static final int MAX_EVALUATION_COUNT = 30000;

	/**
	 * nearby distance from focus cell to available cells
	 */
	public static final int NEARBY_DISTANCE = 2;

	/**
	 * true if black side is AI player
	 */
	public static final boolean BLACK_AI = true;

	/**
	 * true if white side is AI player
	 */
	public static final boolean WHITE_AI = true;

	/**
	 * true if start game with gui
	 */
	public static final boolean START_UI = false || !BLACK_AI ||!WHITE_AI;

	/**
	 * debug using, break the game when reaching the move count
	 */
	public static final int MOVES= -1;
}
