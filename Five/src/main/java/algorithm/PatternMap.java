package algorithm;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import logger.GameLogger;
import model.Cell;
import model.Line;
import model.Stone;

public class PatternMap {
	private static final Map<Type, String[]> typePatternMap = new HashMap<Type, String[]>();
	public static final Type[] KEY_TYPES = {Type.HUO4, Type.CHONG4, Type.HUO3};
	public static final Type[] TWO_FOR_KEY_TYPE = {Type.MIAN3, Type.HUO2};
	public static final Type[] NON_KEY_TYPES = {Type.MIAN3, Type.HUO2, Type.MIAN2};

	static {
		typePatternMap.put(Type.LIAN5, new String[] { "11111" });
		typePatternMap.put(Type.HUO4, new String[] { "011110" });
		typePatternMap.put(Type.CHONG4, new String[] { "011112", "10111", "11011" });
		typePatternMap.put(Type.HUO3, new String[] { "001110", "011100", "010110" });
		typePatternMap.put(Type.MIAN3, new String[] { "001112", "010112", "011012","11001", "10101", "2011102" });
		typePatternMap.put(Type.HUO2, new String[] { "001100", "001010", "010010" });
		typePatternMap.put(Type.MIAN2, new String[] {"000112", "210100", "210010", "10001" });
	}

	private static Map<Type, Pattern[]> patterns = new HashMap<Type, Pattern[]>();

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
//		if (!LineParser.parse(lineStr, s)) {
//			GameLogger.getInstance().logInfo(s.chr + ":" + lineStr);
//			return emptyCells;
//		}
//		int beginIndex = LineParser.beginIndex;
//		int count = LineParser.focusCount;
//		int maxConsecutiveCount = LineParser.maxConsCount;
		for (Type type : types) {
			Pattern[] patterns = PatternMap.getPatterns().get(type);
			for (Pattern pattern : patterns) {
//				if (count < pattern.getCount()
//						|| maxConsecutiveCount < pattern.getConsecutiveCount()) {
////					GameLogger.getInstance().logInfo(s.chr + ":" + lineStr + ":" + pattern.getStr());
////					if (count < pattern.getCount()) {
////						GameLogger.getInstance().logInfo("count:" + count + "<" + pattern.getCount());
////					}
////					
////					if (maxConsecutiveCount < pattern.getConsecutiveCount()) {
////						GameLogger.getInstance().logInfo("cons:" + maxConsecutiveCount + "<" + pattern.getConsecutiveCount());
////					}
//					continue;
//				}
				List<Cell> list = pattern.getEmptyCells(s, line);
				if (list != null) {
					emptyCells.addAll(list);
				}
			}
		}
		return emptyCells;
	}
}
