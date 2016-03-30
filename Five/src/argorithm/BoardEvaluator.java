package argorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import logger.GameLogger;
import model.BoardHelper;
import model.Cell;
import model.Line;
import model.Stone;
import controller.Utils;

public class BoardEvaluator {

	private BoardHelper boardHelper;
	private Stone stone;
	private Set<Line> visitedLines = new HashSet<Line>();
	private GameLogger gameLogger = GameLogger.getInstance();

	private enum Type {
		LIAN5, HUO4, CHONG4, HUO3, MIAN3, HUO2, MIAN2, OTHER, POLLUTED
	};

	private Map<Type, String[]> typePatternMap = new HashMap<Type, String[]>();
	private Map<Type, Integer> typeScoreMap = new HashMap<Type, Integer>();
	private Map<Stone, LineTypeMap> stoneMap = new HashMap<Stone, LineTypeMap>();
	public BoardEvaluator(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		initValueMap();
	}

	private void initValueMap() {
		typePatternMap.put(Type.LIAN5, new String[] { "11111" });
		typePatternMap.put(Type.HUO4, new String[] { "011110" });
		typePatternMap.put(Type.CHONG4, new String[] { "011112", "211110", "10111", "11101", "11011" });
		typePatternMap.put(Type.HUO3, new String[] { "001110", "011100", "010110", "011010", });
		typePatternMap.put(Type.MIAN3, new String[] { "001112", "211100", "010112", "211010", "011012", "210110", "10011", "11001", "10101", "2011102" });
		typePatternMap.put(Type.HUO2, new String[] { "001100", "001010", "010100", "010010", });

		typeScoreMap.put(Type.LIAN5, Integer.valueOf(50000));
		typeScoreMap.put(Type.HUO4, Integer.valueOf(20000));
		typeScoreMap.put(Type.CHONG4, Integer.valueOf(10000));
		typeScoreMap.put(Type.HUO3, Integer.valueOf(6000));
		typeScoreMap.put(Type.MIAN3, Integer.valueOf(800));
		typeScoreMap.put(Type.HUO2, Integer.valueOf(400));
	}

	public int evaluate(String boardId) {
		stoneMap.clear();
		stoneMap.put(stone, new LineTypeMap());
		stoneMap.put(stone.getOpposite(), new LineTypeMap());
		for (Cell cell : boardHelper.getNearAvailable()) {
			List<Line> lines = Utils.getReferenceLines(cell, true);
			for (Line line : lines) {
				if (visitedLines.contains(lines)) {
					continue;
				}

				visitorLine(line, stone);
				visitorLine(line, stone.getOpposite());

				visitedLines.add(line);
			}
		}

		StringBuffer sb = new StringBuffer();
		int totalValue = 0;
		for(Entry<Stone, LineTypeMap> e : stoneMap.entrySet()) {
			Stone targetStone = e.getKey();
			LineTypeMap lineTypeMap = e.getValue();
			int posNeg = (targetStone == stone) ? 1 : -1;
			for(Entry<Line, Type> ee : lineTypeMap.entrySet()) {
				int value = posNeg * typeScoreMap.get(ee.getValue());
				sb.append(targetStone.str).append(" ").append(ee.getValue()).append(":").append(value).append(";");
				totalValue += value;
			}
		}
		gameLogger.logFiner(boardId + "("+ totalValue + "):" + sb.toString());
		return totalValue;
	}

	private void visitorLine(Line line, Stone targetStone) {
		String lineStr = line.getStr(targetStone);
		for (Entry<Type, String[]> e : typePatternMap.entrySet()) {
			Type type = e.getKey();
			String[] patterns = e.getValue();
			for (int i = 0; i < patterns.length; i++) {
				int index = lineStr.indexOf(patterns[i]);
				if (index != -1) {
					int fromIndex = (index == 0) ? 0 : index - 1;
					int tmp = index + patterns[i].length() - 1;
					int toIndex = (tmp > line.size()) ? tmp - 2 : tmp - 1;
					Line subLine = line.getSubLine(fromIndex, toIndex);
					stoneMap.get(targetStone).put(subLine, type);
				}
			}
		}
	}

	private class LineTypeMap {
		Map<Line, Type> map = new HashMap<Line, Type>();
		private void put(Line line, Type type) {
			map.put(line, type);
		}

		private Set<Entry<Line, Type>> entrySet() {
			return map.entrySet();
		}
	}
}
