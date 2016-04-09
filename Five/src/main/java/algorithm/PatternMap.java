package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PatternMap {
	private static final Map<Type, String[]> typePatternMap = new HashMap<Type, String[]>();
	private static final List<Type> keyTypes = new ArrayList<Type>();

	static {
		keyTypes.add(Type.HUO4);
		keyTypes.add(Type.CHONG4);
		keyTypes.add(Type.HUO3);
//		threateningTypes.add(Type.MIAN3);

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

	public static List<Type> getKeytypes() {
		return keyTypes;
	}

	public static Map<Type, String[]> getTypepatternmap() {
		return typePatternMap;
	}
}
