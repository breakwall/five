package argorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.Stone;
import argorithm.BoardEvaluator.Type;

public class PatternMap {
	private static final Map<Type, String[]> typePatternMap = new HashMap<Type, String[]>();
	static {
		typePatternMap.put(Type.LIAN5, new String[] { "11111" });
		typePatternMap.put(Type.HUO4, new String[] { "011110" });
		typePatternMap.put(Type.CHONG4, new String[] { "011112", "211110", "10111", "11101", "11011" });
		typePatternMap.put(Type.HUO3, new String[] { "001110|011100", "010110", "011010", });
		typePatternMap.put(Type.MIAN3, new String[] { "001112", "211100", "010112", "211010", "011012", "210110", "10011", "11001", "10101", "2011102" });
		typePatternMap.put(Type.HUO2, new String[] { "001100", "001010", "010100", "010010", });
		typePatternMap.put(Type.MIAN2, new String[] { "211000" ,"000112", "210100", "001012", "210010", "010012", "10001", });
	}

	private static final Map<Type, String[]> blackPatternMap = new HashMap<Type, String[]>();
	private static final Map<Type, String[]> whitePatternMap = new HashMap<Type, String[]>();
	static {
		for(Entry<Type, String[]> e: typePatternMap.entrySet()) {
			String[] patterns = e.getValue();
			String[] blackPatterns = new String[patterns.length];
			String[] whitePatterns = new String[patterns.length];
			for (int i = 0; i < patterns.length; i++) {
				blackPatterns[i] = patterns[i];
				whitePatterns[i] = patterns[i];

				blackPatterns[i] = blackPatterns[i].replace('0', Stone.NONE.chr);
				blackPatterns[i] = blackPatterns[i].replace('1', Stone.BLACK.chr);
				blackPatterns[i] = blackPatterns[i].replace('2', Stone.WHITE.chr);

				whitePatterns[i] = whitePatterns[i].replace('0', Stone.NONE.chr);
				whitePatterns[i] = whitePatterns[i].replace('1', Stone.WHITE.chr);
				whitePatterns[i] = whitePatterns[i].replace('2', Stone.BLACK.chr);
			}

			blackPatternMap.put(e.getKey(), blackPatterns);
			whitePatternMap.put(e.getKey(), whitePatterns);
		}
	}

	public static Map<Type, String[]> getPatternMap(Stone stone) {
		if (stone == Stone.BLACK) {
			return blackPatternMap;
		} else {
			return whitePatternMap;
		}
	}
}
