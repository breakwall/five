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

	public enum Type {
		LIAN5(500), HUO4(200), CHONG4(100), HUO3(60), MIAN3(8), HUO2(4), MIAN2(1);
		int score;
		private Type(int score) {
			this.score = score;
		}
	};

	private Map<Stone, Map<Line, Type>> stoneMap = new HashMap<Stone, Map<Line, Type>>();
	public BoardEvaluator(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		stoneMap.put(stone, new HashMap<Line, Type>());
		stoneMap.put(stone.getOpposite(), new HashMap<Line, Type>());
	}

	public int evaluate(int boardId) {
		stoneMap.get(stone).clear();
		stoneMap.get(stone.getOpposite()).clear();
		visitedLines.clear();

		for (Cell cell : boardHelper.getNearAvailable()) {
			List<Line> lines = Utils.getReferenceLines2(cell);
			for (Line line : lines) {
				if (visitedLines.contains(line)) {
					continue;
				}

				visitorLine(line, stone);
				visitorLine(line, stone.getOpposite());

				visitedLines.add(line);
			}
		}

		StringBuffer sb = new StringBuffer();
		int totalValue = 0;

		for(Entry<Stone, Map<Line, Type>> e : stoneMap.entrySet()) {
			Stone targetStone = e.getKey();
			Map<Line, Type> lineTypeMap = e.getValue();
			int posNeg = (targetStone == stone) ? 1 : -1;
			for(Entry<Line, Type> ee : lineTypeMap.entrySet()) {
				int value = posNeg * ee.getValue().score;
				sb.append(ee.getKey().toString() + ":" + ee.getValue()).append(":").append(value).append(";");
				totalValue += value;
			}
		}
		gameLogger.logFiner(boardId + "("+ totalValue + "):" + sb.toString());
		return totalValue;
	}

	private void visitorLine(Line line, Stone targetStone) {
		String lineStr = line.getStr(targetStone);
		Map<Type, String[]> typePatternMap = PatternMap.getPatternMap(targetStone);
		for (Entry<Type, String[]> e : typePatternMap.entrySet()) {
			Type type = e.getKey();
			String[] patterns = e.getValue();
			for (int i = 0; i < patterns.length; i++) {
				Line subLine = getLineMatchPattern(line, lineStr, patterns[i]);
				if (subLine != null) {
					stoneMap.get(targetStone).put(subLine, type);
				}
			}
		}
	}

	private Line getLineMatchPattern(Line line, String lineStr, String pattern) {
		int orIdx = pattern.indexOf("\\|");
		int index;
		if (orIdx == -1) {
			index = lineStr.indexOf(pattern);
		} else {
			pattern = pattern.substring(0, orIdx);
			index = lineStr.indexOf(pattern);
			if (index == -1) {
				pattern = pattern.substring(orIdx + 1);
				index = lineStr.indexOf(pattern);
			}
		}

		if (index == -1) {
			return null;
		}

		int fromIndex = (index == 0) ? 0 : index - 1;
		int tmp = index + pattern.length() - 1;
		int toIndex = (tmp > line.size()) ? tmp - 2 : tmp - 1;
		Line subLine = line.getSubLine(fromIndex, toIndex);
		return subLine;
	}
}
