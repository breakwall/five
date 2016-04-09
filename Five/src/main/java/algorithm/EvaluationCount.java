package algorithm;

public class EvaluationCount {
	private static int count = 0;
	public static int generate() {
		return count++;
	}

	public static int getCount() {
		return count;
	}
}
