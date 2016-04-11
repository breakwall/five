package algorithm;

import model.Stone;

public class LineParser {
	static int beginIndex;
	static int maxConsCount;
	static int focusCount;
	static String lineStr;

	public static boolean parse(String lineStr, Stone stone) {
		// clear cache
		beginIndex = 0;
		maxConsCount = 0;
		focusCount = 0;
		LineParser.lineStr = lineStr;

		final char focusChr = stone.chr;
		final char emptyChr = Stone.NONE.chr;
		final char oppoChr = stone.getOpposite().chr;

		int emptyCount = 0;
		int availableCount = 0;
		int maxAvailableCount = 0;
		int tmp = 0;
		boolean emptyCountEnd = false;
		boolean firstOppoChar = false;

		for (int i = 0; i < lineStr.length(); i++) {
			char chr = lineStr.charAt(i);

			if (chr == emptyChr) {
				availableCount++;
				if (!emptyCountEnd) {
					emptyCount++;
				}

				maxConsCount = Math.max(maxConsCount, tmp);
				tmp = 0;
			} else if (chr == focusChr) {
				if (emptyCountEnd != true) {
					emptyCountEnd = true;
					if (chr == oppoChr) {
						firstOppoChar = true;
					}
				}

				tmp++;
				focusCount++;
				availableCount++;
			} else {
				if (emptyCountEnd != true) {
					emptyCountEnd = true;
					if (chr == oppoChr) {
						firstOppoChar = true;
					}
				}

				maxAvailableCount = Math.max(maxAvailableCount, availableCount);
				availableCount = 0;

				maxConsCount = Math.max(maxConsCount, tmp);
				tmp = 0;
			}

			if (i == lineStr.length() - 1) {
				maxConsCount = Math.max(maxConsCount, tmp);
				tmp = 0;
				maxAvailableCount = Math.max(maxAvailableCount, availableCount);
				availableCount = 0;
			}
		}

		if (emptyCount == lineStr.length() || focusCount < 2 || maxAvailableCount < 5) {
			return false;
		}

		if (firstOppoChar) {
			beginIndex = emptyCount;
		} else if (emptyCount > 3) {
			beginIndex = emptyCount - 3;
		} else {
			beginIndex = 0;
		}

		return true;
	}

	public static String getSubString() {
		return lineStr.substring(beginIndex);
	}
}
