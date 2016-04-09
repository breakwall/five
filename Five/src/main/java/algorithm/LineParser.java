package algorithm;

import model.Stone;

public class LineParser {
	static int beginIndex;
	static int endIndex;
	static int maxConsecutiveFocusCount;
	static int focusCount;
	static String lineStr;

	public static boolean parse(String lineStr, Stone stone) {
		// clear cache
		beginIndex = 0;
		endIndex = 0;
		maxConsecutiveFocusCount = 0;
		focusCount = 0;
		LineParser.lineStr = lineStr;

		char focusChr = stone.chr;
		char emptyChr = Stone.NONE.chr;
		char oppoChr = stone.getOpposite().chr;

		int emptyCount = 0;
		int consecutiveCount = 0;
		int maxConsecutiveCount = 0;
		int consecutiveFocusCount = 0;
		boolean emptyCountEnd = false;
		boolean firstOppoChar = false;

		for (int i = 0; i < lineStr.length(); i++) {
			char chr = lineStr.charAt(i);
			if (chr == emptyChr) {
				consecutiveCount++;
				if (!emptyCountEnd) {
					emptyCount++;
				}
			} else {
				if (emptyCountEnd != true) {
					emptyCountEnd = true;
					if (chr == oppoChr) {
						firstOppoChar = true;
					}
				}

				if (chr == focusChr) {
					consecutiveFocusCount++;
					focusCount++;
					consecutiveCount++;
				} else {
					if (consecutiveFocusCount > maxConsecutiveFocusCount) {
						maxConsecutiveFocusCount = consecutiveFocusCount;
					}
					consecutiveFocusCount = 0;

					if (consecutiveCount > maxConsecutiveCount) {
						maxConsecutiveCount = consecutiveCount;
					}
					consecutiveCount = 0;
				}
			}

			if (i == lineStr.length() - 1) {
				if (consecutiveFocusCount > maxConsecutiveFocusCount) {
					maxConsecutiveFocusCount = consecutiveFocusCount;
				}

				if (consecutiveCount > maxConsecutiveCount) {
					maxConsecutiveCount = consecutiveCount;
				}
			}
		}

		if (emptyCount == lineStr.length() || focusCount < 2 || maxConsecutiveCount < 5) {
			return false;
		}

		if (firstOppoChar) {
			beginIndex = emptyCount;
		} else if (emptyCount > 3) {
			beginIndex = emptyCount - 3;
		} else {
			beginIndex = 0;
		}

		emptyCount = 0;
		firstOppoChar = false;
		for (int i = lineStr.length() - 1; i >= 0; i--) {
			char chr = lineStr.charAt(i);
			if (chr == emptyChr) {
				emptyCount++;
			} else {
				if (chr == oppoChr) {
					firstOppoChar = true;
				}
				break;
			}
		}

		if (firstOppoChar) {
			endIndex = lineStr.length() - emptyCount;
		} else if (emptyCount > 3) {
			endIndex = lineStr.length() - emptyCount + 3;
		} else {
			endIndex = lineStr.length();
		}

		if (endIndex - beginIndex < 5) {
			return false;
		}

		return true;
	}

	public static String getSubString() {
		return lineStr.substring(beginIndex, endIndex);
	}
}
