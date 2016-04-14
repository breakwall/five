package algorithm;

import model.Stone;

public class LineParser {
	public static ParseInfo parse(String lineStr, Stone stone) {
		ParseInfo info = new ParseInfo();

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

				info.maxConsCount = Math.max(info.maxConsCount, tmp);
				tmp = 0;
			} else if (chr == focusChr) {
				if (emptyCountEnd != true) {
					emptyCountEnd = true;
					if (chr == oppoChr) {
						firstOppoChar = true;
					}
				}

				tmp++;
				info.focusCount++;
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

				info.maxConsCount = Math.max(info.maxConsCount, tmp);
				tmp = 0;
			}

			if (i == lineStr.length() - 1) {
				info.maxConsCount = Math.max(info.maxConsCount, tmp);
				tmp = 0;
				maxAvailableCount = Math.max(maxAvailableCount, availableCount);
				availableCount = 0;
			}
		}

		if (emptyCount == lineStr.length() || info.focusCount < 2 || maxAvailableCount < 5) {
			return null;
		}

		if (firstOppoChar) {
			info.beginIndex = emptyCount;
		} else if (emptyCount > 3) {
			info.beginIndex = emptyCount - 3;
		} else {
			info.beginIndex = 0;
		}

		return info;
	}

	public static class ParseInfo {
		int beginIndex;
		int maxConsCount;
		int focusCount;
	}
}
