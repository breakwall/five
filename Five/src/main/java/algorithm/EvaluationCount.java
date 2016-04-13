package algorithm;

public class EvaluationCount {
	private static int count = 0;
	private static int oneMoveCount = 0;
	public static int generate() {
		return oneMoveCount++;
	}

	public static int getCount() {
		return oneMoveCount;
	}

	public static int getTotalCount() {
		return count;
	}

	/**
	 * evaluate this move
	 */
	public static void beginEvalute() {
		oneMoveCount = 0;
	}

	public static void endEvaluate() {
		count += oneMoveCount;
	}
}
