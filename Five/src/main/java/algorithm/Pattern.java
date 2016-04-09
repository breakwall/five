package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Cell;
import model.Line;
import model.Stone;
import utils.Utils;

public class Pattern {
	private Map<Stone, PatternItem[]> patterns = new HashMap<Stone, PatternItem[]>();
	private Type type;

	public Pattern(String pattern, Type type) {
		this.type = type;

		boolean headBlock = false;
		boolean tailBlock = false;

		if (pattern.charAt(0) == '2') {
			headBlock = true;
		}

		if (pattern.charAt(pattern.length() - 1) == '2') {
			tailBlock = true;
		}

		int fromIndex = headBlock ? 1 : 0;
		int toIndex = tailBlock ? pattern.length() - 1 : pattern
				.length();
		String patternStr = pattern.substring(fromIndex, toIndex);

		for (Stone s : Utils.sides) {
			String str = patternStr;
			str = str.replace('0', Stone.NONE.chr);
			str = str.replace('1', s.chr);
			String reverse = new StringBuffer(str).reverse().toString();

			if (str.equals(reverse)) {
				PatternItem item = new PatternItem(str, headBlock, tailBlock);
				patterns.put(s, new PatternItem[] { item });
			} else {
				PatternItem item = new PatternItem(str, headBlock, tailBlock);
				PatternItem item2 = new PatternItem(reverse, tailBlock, headBlock);
				patterns.put(s, new PatternItem[] {item, item2});
			}
		}
	}

	private PatternItem doIsMatch(Stone stone, String lineStr) {
		PatternItem[] items = patterns.get(stone);

		PatternItem item = null;
		for (int i = 0; i < items.length; i++) {
			int index = lineStr.indexOf(items[i].patternStr);
			if (index != -1) {
				item = items[i];
				item.index = index;
				break;
			}
		}

		if (item == null) {
			return null;
		}

		if (item.headBlock) {
			if (item.index != 0
					&& lineStr.charAt(item.index - 1) != stone.getOpposite().chr) {
				return null;
			}
		}

		if (item.tailBlock) {
			if (item.index + item.patternStr.length() != lineStr.length()
					&& lineStr.charAt(item.index + item.patternStr.length()) != stone
							.getOpposite().chr) {
				return null;
			}
		}

		return item;
	}

	public boolean isMatch(Stone stone, String lineStr) {
		return doIsMatch(stone, lineStr) != null;
	}

	public List<Cell> getEmptyCells(Stone stone, Line line) {
		String lineStr = line.getStr();
		PatternItem item = doIsMatch(stone, lineStr);

		if (item == null) {
			return null;
		}

		List<Cell> cells = new ArrayList<Cell>();
		for(int i : item.emptyIndex) {
			cells.add(line.getCell(item.index + i));
		}

		return cells;
	}

	public Type getType() {
		return type;
	}

	private class PatternItem {
		String patternStr;
		boolean headBlock;
		boolean tailBlock;
		List<Integer> emptyIndex = new ArrayList<>();
		int index;

		public PatternItem(String patternStr, boolean headBlock,
				boolean tailBlock) {
			this.patternStr = patternStr;
			this.headBlock = headBlock;
			this.tailBlock = tailBlock;
			char emptyChar = Stone.NONE.chr;
			for (int i = 0; i < patternStr.length(); i++) {
				if (patternStr.charAt(i) != emptyChar) {
					continue;
				}

				if (i == 0 && patternStr.charAt(i + 1) == emptyChar) {
					continue;
				}

				if (i == patternStr.length() - 1 && patternStr.charAt(i - 1) == emptyChar) {
					continue;
				}

				emptyIndex.add(i);
			}
		}
	}
}
