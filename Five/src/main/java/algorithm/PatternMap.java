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
import algorithm.LineParser.ParseInfo;

public class PatternMap {
	private static final Map<Type, String[]> typePatternMap = new LinkedHashMap<Type, String[]>();

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
		ParseInfo parseInfo = line.getParseInfo(s);
		if (parseInfo == null) {
			return emptyCells;
		}

		int beginIndex = parseInfo.beginIndex;
		int maxConsecutiveCount = parseInfo.maxConsCount;
		for (Type type : types) {
			if (parseInfo.focusCount < type.count) {
				continue;
			}

			Pattern[] patterns = PatternMap.getPatterns().get(type);
			for (Pattern pattern : patterns) {
				if (maxConsecutiveCount < pattern.getConsecutiveCount()) {
					continue;
				}
				List<Cell> list = pattern.getEmptyCells(s, line, beginIndex);
				if (list != null) {
					emptyCells.addAll(list);
				}
			}
		}
		return emptyCells;
	}

	public static List<Type> getLineScore(Stone s, Line line, Type... types) {
		List<Type> lineTypes = new ArrayList<>();
		ParseInfo parseInfo = line.getParseInfo(s);
		if (parseInfo == null) {
			return lineTypes;
		}

		int maxConsecutiveCount = parseInfo.maxConsCount;
		int focusCount = parseInfo.focusCount;
		int beginIndex = parseInfo.beginIndex;
		String lineStr = line.getStr();

		for (Type type : types) {
			if (focusCount < type.count) {
				continue;
			}

			Pattern[] patterns = PatternMap.getPatterns().get(type);
			for (Pattern pattern : patterns) {
				if (maxConsecutiveCount < pattern.getConsecutiveCount()) {
					continue;
				}

				if (pattern.isMatch(s, lineStr, beginIndex)) {
					lineTypes.add(type);
				}
			}
		}
		return lineTypes;
	}
}
