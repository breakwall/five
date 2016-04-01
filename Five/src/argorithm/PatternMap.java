package argorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		typePatternMap.put(Type.HUO3, new String[] { "001110", "011100", "010110", "011010", });
		typePatternMap.put(Type.MIAN3, new String[] { "001112", "211100", "010112", "211010", "011012", "210110", "10011", "11001", "10101", "2011102" });
		typePatternMap.put(Type.HUO2, new String[] { "001100", "001010", "010100", "010010", });
		typePatternMap.put(Type.MIAN2, new String[] { "211000" ,"000112", "210100", "001012", "210010", "010012", "10001", });
	}

	private static List<String> blackPatterns = new ArrayList<String>();
	private static List<String> whitePatterns = new ArrayList<String>();
	private static List<Type> types = new ArrayList<Type>();
	static {
		for(Entry<Type, String[]> e: typePatternMap.entrySet()) {
			Type type = e.getKey();
			String[] patterns = e.getValue();
//			String[] blackPatterns = new String[patterns.length];
//			String[] whitePatterns = new String[patterns.length];
			for (int i = 0; i < patterns.length; i++) {
				String blackPattern;
				String whitePattern;

				blackPattern = patterns[i].replace('0', Stone.NONE.chr);
				blackPattern = blackPattern.replace('1', Stone.BLACK.chr);
				blackPattern = blackPattern.replace('2', Stone.WHITE.chr);

				whitePattern = patterns[i].replace('0', Stone.NONE.chr);
				whitePattern = whitePattern.replace('1', Stone.WHITE.chr);
				whitePattern = whitePattern.replace('2', Stone.BLACK.chr);
				blackPatterns.add(blackPattern);
				whitePatterns.add(whitePattern);
				types.add(type);
			}
		}
	}

	public static List<String> getPatterns(Stone stone) {
		if (stone == Stone.BLACK) {
			return blackPatterns;
		} else {
			return whitePatterns;
		}
	}

	public static List<Type> getTypes() {
		return types;
	}
}
