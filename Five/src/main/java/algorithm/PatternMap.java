package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.Cell;
import model.Line;
import model.Stone;

public class PatternMap {
	private static final Map<Type, String[]> typePatternMap = new LinkedHashMap<Type, String[]>();

	public static final Type[] KEY_TYPES = {Type.HUO4, Type.CHONG4, Type.HUO3};
	public static final Type[] INTERSECT_TO_KEY_TYPES = {Type.MIAN3, Type.HUO2};
	public static final Type[] NON_KEY_TYPES = {Type.MIAN3, Type.HUO2, Type.MIAN2};

	static {
		typePatternMap.put(Type.LIAN5, new String[] { "11111" });
		typePatternMap.put(Type.HUO4, new String[] { "011110" });
		typePatternMap.put(Type.CHONG4, new String[] { "011112", "10111", "11011" });
		typePatternMap.put(Type.HUO3, new String[] { "011100", "010110" });
		typePatternMap.put(Type.MIAN3, new String[] { "001112", "010112", "011012","11001", "10101", "2011102" });
		typePatternMap.put(Type.HUO2, new String[] { "001100", "001010", "010010" });
		typePatternMap.put(Type.MIAN2, new String[] {"000112", "210100", "210010", "10001" });
	}

	private static HashMap<Type, Pattern[]> patterns = new LinkedHashMap<Type, Pattern[]>();

	static {
		for(Entry<Type, String[]> e: typePatternMap.entrySet()) {
			Type type = e.getKey();
			String[] patternStrs = e.getValue();
			Pattern[] patArr = new Pattern[patternStrs.length];
			patterns.put(type, patArr);

			for (int i = 0; i < patternStrs.length; i++) {
				patArr[i] = new Pattern(patternStrs[i], type);
			}
		}
	}

	public static Map<Type,Pattern[]> getPatterns() {
		return patterns;
	}

	public static Set<Cell> getEmptyCells(Stone s, Line line, Type... types) {
		Set<Cell> emptyCells = new LinkedHashSet<Cell>();
		String lineStr = line.getStr();
		if (!LineParser.parse(lineStr, s)) {
//			GameLogger.getInstance().logInfo(s.chr + ":" + lineStr);
			return emptyCells;
		}
//		int beginIndex = LineParser.beginIndex;
//		int count = LineParser.focusCount;
		int maxConsecutiveCount = LineParser.maxConsCount;
		for (Type type : types) {
			if (LineParser.focusCount < type.count) {
				continue;
			}

			Pattern[] patterns = PatternMap.getPatterns().get(type);
			for (Pattern pattern : patterns) {
				if (maxConsecutiveCount < pattern.getConsecutiveCount()) {
//					GameLogger.getInstance().logInfo("cons:" + maxConsecutiveCount + "<" + pattern.getConsecutiveCount());
					continue;
				}
				List<Cell> list = pattern.getEmptyCells(s, line);
				if (list != null) {
					emptyCells.addAll(list);
				}
			}
		}
		return emptyCells;
	}

	public static List<Type> getLineScore(Stone s, Line line, Type... types) {
		List<Type> lineTypes = new ArrayList<>();
		String lineStr = line.getStr();
		if (!LineParser.parse(lineStr, s)) {
			return lineTypes;
		}

		int maxConsecutiveCount = LineParser.maxConsCount;
		int focusCount = LineParser.focusCount;

		for (Type type : types) {
			if (focusCount < type.count) {
				continue;
			}

			Pattern[] patterns = PatternMap.getPatterns().get(type);
			for (Pattern pattern : patterns) {
				if (maxConsecutiveCount < pattern.getConsecutiveCount()) {
					continue;
				}

				if (pattern.isMatch(s, lineStr)) {
					lineTypes.add(type);
				}
			}
		}
		return lineTypes;
	}
}
